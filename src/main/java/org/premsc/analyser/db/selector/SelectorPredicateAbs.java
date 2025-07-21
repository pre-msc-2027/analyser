package org.premsc.analyser.db.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for selector predicates that can contain child predicates.
 */
public abstract class SelectorPredicateAbs<This extends SelectorPredicateAbs<This>> extends StatementAbs<This> {

    protected final List<SelectorPredicateAbs<?>> children = new ArrayList<>();
    protected final List<Separator> separators = new ArrayList<>();
    protected boolean inverted;

    /**
     * Adds a child predicate with an AND logical separator.
     * @param predicate The predicate to add.
     * @return The current instance for method chaining.
     */
    public This and(SelectorPredicateAbs<?> predicate) {
        this.children.add(predicate);
        this.separators.add(Separator.AND);
        return me;
    }

    /**
     * Adds a child predicate with an OR logical separator.
     * @param predicate The predicate to add.
     * @return The current instance for method chaining.
     */
    public This or(SelectorPredicateAbs<?> predicate) {
        this.children.add(predicate);
        this.separators.add(Separator.OR);
        return me;
    }

    /**
     * Inverts the current predicate, changing its logical meaning.
     * @return The current instance for method chaining.
     */
    public This not() {
        this.inverted = true;
        return me;
    }

    @Override
    protected void build(StringBuilder builder) {
        this.buildComparison(builder);
        this.buildChildren(builder);
    }

    /**
     * Builds the comparison part of the predicate.
     * This method must be implemented by subclasses to define the specific comparison logic.
     * @param builder The StringBuilder to append the comparison to.
     */
    protected abstract void buildComparison(StringBuilder builder);

    /**
     * Builds the children predicates with their respective separators.
     * @param builder The StringBuilder to append the children predicates to.
     */
    protected void buildChildren(StringBuilder builder) {

        for (int index = 0; index < this.children.size(); index++) {

            builder.append(" ").append(this.separators.get(index));

            this.children.get(index).build(builder);
        }

    }

    /**
     * Enum to represent the logical separators used in predicates.
     */
    protected enum Separator {
        AND, OR
    }

}
