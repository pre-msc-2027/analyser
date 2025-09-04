package org.premsc.analyser.slang.generic;

public interface ISlangToken<P extends ISlangObject> extends ISlangObject {

    P getParent();

}
