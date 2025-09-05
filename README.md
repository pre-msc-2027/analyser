# Secuscan / Analyser

**Status:** Beta  
**Repo:** https://github.com/pre-msc-2027/analyser  
**License:** _TBD MIT/Apache-2.0_

Analyser module for the **Secuscan** application (which comprises a web UI, an API, this analyser module, and an IA module for improved propositions).

The analyser is invoked **by the API**, not manually. It receives:
- **argument 1:** the _analyse id_
- **argument 2 (optional):** a GitHub token (used to clone the target repository)

The analyser then communicates back to the API via HTTP calls. Rule evaluation uses a custom **Slang** language (see `Slang.md`).

---

## Requirements

- **Operating systems:** Windows or Linux
- **Runtime:** Java 24
- **Storage:** SQLite
- **Distribution:** Prebuilt JARs published via GitHub Releases

> This module is designed to be orchestrated by the API; end-users don’t run it directly.

---

## High-level flow

1. **Repository fetch:** if a GitHub token is provided, the Repository module clones the target repo.
2. **Indexing:** the Indexer scans sources and writes rows into a local SQLite database (`index_table`).
3. **Analysis:** the Analyser loads the configured Slang rules and evaluates them against:
    - the **syntax tree** (for `node` queries), and
    - the **index** (for `index` queries).
4. **Reporting:** results are sent back to the API over HTTP.

---

## Slang rule language

This project uses a minimal rule language tailored for Secuscan.  
See **[`Slang.md`](./Slang.md)** for the full end-user guide, operators, and examples.

---

## Index schema

The Indexer writes a flat table with multiple helpful indexes:

```sql
CREATE TABLE IF NOT EXISTS index_table
(
    id        INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
    language  TEXT,
    source    TEXT,
    type      TEXT,
    value     TEXT,
    line      INTEGER,
    startByte INTEGER,
    endByte   INTEGER
);

CREATE UNIQUE INDEX idx  ON index_table(id);
CREATE INDEX idx1 ON index_table(language, type, value);
CREATE INDEX idx2 ON index_table(source,   type, value);
CREATE INDEX idx3 ON index_table(language, source, type, value);
```

- **source**: path within the fetched repository
- **language**: currently html and css
- **type**: analyzer-specific key
- **value**: analyzer-specific content
- **line/startByte/endByte**: location info for precise reporting

For additional data models and schemas, see the API repository.

---

## Supported languages

- **HTML**
- **CSS**

---

## Project structure

- **Api** — interface & contracts between the analyser and the Secuscan API (HTTP calls)
- **DB** — SQLite access, migrations, and query helpers
- **Indexer** — produces index_table rows from the repository contents
- **Analyser** — loads and runs Slang rules, aggregates findings, sends results to the API
- **Repository** — cloning & workspace management for the target repo
- **Slang parser** — parses and evaluates rule files written in Slang

---

## Versioning

Semantic versioning: **Major.Minor.Patches**

---

## Roadmap

- Expand **Slang** capabilities
- Add more **indexing** coverage
- Support additional **languages** beyond HTML/CSS
