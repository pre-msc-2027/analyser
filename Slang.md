# Slang for Secuscan — Rule Language (End‑User)

Slang is a tiny language to write **Secuscan** lint rules. You describe what to match in source trees and/or an index of facts, and Secuscan reports those matches.

> **Essentials:** A rule is one statement: either **`node (...)`** (tree pattern) or **`index &name where ...`** (index search). You can add extra requirements with **`with`**. `with` clauses are evaluated **bottom → top** and behave like **inner joins**: only items present in all branches survive.

---

# Quick start (Secuscan Slang)

```slang
# Find html tags whose name are not parametrized casing
node (element [
    (start_tag (tag_name @target))
    (end_tag (tag_name @target))
    ])
where @target !* $casing
```

```slang
# Find html classes that are not defined in linked stylesheets
index &target
    where source = filepath()
        type = "class"
        value != &other
    with &other
        where type = "class"
            source = &style
    with &style
        where type = "link_stylesheet"
            source = filepath()
```

**Notes**

* `!*` is **not-match**; `!=` is **not equals**.
* `with` blocks are evaluated **bottom → top** and are **inner joins**.
* `$casing` is a parameter value you provide to Secuscan.
* `filepath()` returns the current file’s path.
* `@target` is a capture from the tree pattern; `&target`, `&other`, `&style` are index handles.

---

## Building blocks

**Statements**

* **Tree search:** `node (<pattern>) [where <node_clause>+]`
* **Index search:** `index &<idx> where <index_clause>+`
* **Extra constraints:** `with &<idx> [where <index_clause>+]` (repeatable)

**Identifiers**

* `@name` → a **capture** from the tree pattern
* `&facts` → an **index handle**
* `$param` → a **parameter** value supplied by Secuscan (no declaration needed)

**Literals & function**

* Strings: `"text"` (double quotes; no escapes)
* Function: `filepath()` → current file’s base name

---

## Target — what gets reported

Use the reserved name **`target`** to tell Secuscan which element to report from your rule.

* **Node rules:** capture the node you want and name it `@target`. Secuscan reports the source span of that captured node.

  ```slang
  node (
    element (
      start_tag (tag_name @target)
    )
  )
  where @target * "button"
  ```

* **Index rules:** name the main index handle `&target`. Secuscan reports the matching index rows (their locations come from the `source` field).

  ```slang
  index &target where
    type = "class"
    source * "src/"
  ```

**Rules of thumb**

* Define **exactly one** `target` per rule.
* When you add `with` joins, the `target` stays the one you declared in the main statement.

---

## Operators

All comparisons are case‑sensitive.

| Operator | Meaning            | Example                |
| :------: |--------------------| ---------------------- |
|    `=`   | equals             | `type = "import"`      |
|   `!=`   | not equals         | `@name != "main"`      |
|    `*`   | **match**          | `value * "Controller"` |
|   `!*`   | does **not** match | `source !* "test/"`    |

---

## Tree search — `node (...)`

```slang
node (
  call_expression
    (identifier @fn)
    [ (argument_list) (template_argument_list) ]
)
where
  @fn * "get_"
```

**Pattern syntax**

```
(<node_type> <child_or_alt>* [@capture])
<child_or_alt> := (<node_type> ... ) | [ (<node_type> ... )* ]
```

* **Node types**: lowercase with underscores (`function_declaration`).
* **Children**: list them in order.
* **Alternation**: `[ A B C ]` means A **or** B **or** C.
* **Capture**: append `@name` inside a node to label it.

**`where` for nodes**

```
where
  @capture <op> <value>
```

* Left side is a **capture** (its textual content is compared).
* `<value>` can be a string, `$param`, or `filename()`.

---

## Index search — `index &... where ...`

```slang
index &facts where
  source * "src/"
  type   =  "import"
  value  !* "java."
```

**Fields**: `source | type | value`

**Clause form**

```
<field> <op> <value>
<value> := "string" | $param | @capture | &other_index | filename()
```

* Comparing to `@capture` lets you join tree data with index data.
* Comparing to `&other_index` lets you intersect by value with another index.

---

## `with` — extra constraints (inner joins)

Add one or more `with` blocks after your main statement. Evaluation is **bottom → top**; each `with` is an **inner join** with the current result set.

```slang
index &decls where type = "function"
with &calls where type = "call" and value * @decls
with &owners where value = filename()
```

---

## Parameters (`$param`)

Secuscan can inject parameters for your rule. Use them directly in clauses:

```slang
node (function_declaration (identifier @name))
where @name * $needle
```

No declarations are needed in the rule file.

---

## Practical examples

### 1) Ban risky function names

```slang
node (
  function_declaration (identifier @name)
)
where
  @name * "unsafe"
```

### 2) Flag files that import outside a company namespace

```slang
index &imports where
  type  =  "import"
  value !* "com.myco."
```

### 3) Match class names tied to filename

```slang
node (
  class_declaration (identifier @cls)
)
where
  @cls * filename()
```

### 4) Report declared functions that are also called

```slang
node (
  function_declaration (identifier @fn)
)
with &calls where type = "call" and value = @fn
```

---

## Reference (condensed)

```
# Statements
node (<pattern>) [where <node_clause>+]
index &<idx> where <index_clause>+
with  &<idx> [where <index_clause>+]

# Patterns
(<type> (<pattern>|[<pattern>*])*) [@capture]

# Clauses
#  Node: left is a capture
@<cap> <op> <value>
#  Index: left is a field
(source|type|value) <op> <value>

# Values
"string" | $param | @capture | &index | filename()

# Operators
=  !=  *  !*
```

That’s it—write a statement, add `with` joins if needed, and let Secuscan surface the matches.
