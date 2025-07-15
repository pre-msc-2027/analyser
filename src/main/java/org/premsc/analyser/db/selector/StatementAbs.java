package org.premsc.analyser.db.selector;

/**
 * Abstract base class for SQL-like statements.
 */
public abstract class StatementAbs<This extends StatementAbs<This>> {

    @SuppressWarnings("unchecked")
    protected final This me = (This) this;

    /**
     * Builds the SQL-like statement and returns it as a String.
     * @param builder the StringBuilder to append the statement to
     */
    abstract protected void build(StringBuilder builder);

}
