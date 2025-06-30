package org.premsc.analyser.rules;

import io.github.treesitter.jtreesitter.Node;
import org.premsc.analyser.repository.Source;

public class Warning {

    private final Source source;
    private final int from;
    private final int to;

    public Warning(Source source, int from, int to) {
        this.source = source;
        this.from = from;
        this.to = to;
    }

    public Warning(Source source, Node from) {
        this(source, from.getStartByte(), from.getEndByte());
    }

    public Source getSource() {
        return source;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }




}
