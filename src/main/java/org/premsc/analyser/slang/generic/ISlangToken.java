package org.premsc.analyser.slang.generic;

/**
 * Interface representing a token in the slang language structure.
 *
 * @param <P> the type of the parent object, which must also be an ISlangObject
 */
public interface ISlangToken<P extends ISlangObject> extends ISlangObject {

    /**
     * Gets the parent object in the slang structure.
     *
     * @return the parent object
     */
    P getParent();

}
