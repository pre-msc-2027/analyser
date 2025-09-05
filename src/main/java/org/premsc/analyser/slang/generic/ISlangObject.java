package org.premsc.analyser.slang.generic;

import org.premsc.analyser.slang.tokens.RuleExpression;

/**
 * Base interface for all objects in the slang language structure.
 */
public interface ISlangObject {

    /**
     * Gets the parent object in the slang structure.
     *
     * @return the parent object
     */
    RuleExpression getRuleExpression();

    /**
     * Gets the path of this object in the slang structure.
     *
     * @return the path as a string
     */
    String getPath();
}
