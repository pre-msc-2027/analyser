package org.premsc.analyser.slang.generic;

import org.premsc.analyser.slang.tokens.RuleExpression;

public interface ISlangToken<P extends ISlangObject> extends ISlangObject {

    P getParent();

}
