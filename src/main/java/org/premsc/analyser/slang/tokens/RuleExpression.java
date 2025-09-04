package org.premsc.analyser.slang.tokens;

import io.github.treesitter.jtreesitter.*;
import org.premsc.analyser.NativeLib;
import org.premsc.analyser.db.DatabaseHandler;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.repository.ISource;
import org.premsc.analyser.rules.IRule;
import org.premsc.analyser.rules.Warning;
import org.premsc.analyser.slang.generic.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RuleExpression extends SlangObjectAbs implements IFinderParent {

    protected final IRule rule;
    protected final List<IdentifierAbs> identifiers;
    protected final FinderStatementAbs<RuleExpression> finderStatement;

    protected ISource source;
    protected DatabaseHandler dbHandler;

    public RuleExpression(IRule rule) {
        this(rule, getRootNode(rule));
    }

    private static Node getRootNode(IRule rule) {
        String slang = rule.getSlang();
        Language language = Language.load(
                NativeLib.openGrammar("slang"),
                NativeLib.getNativeName("slang")
        );

        Parser tsParser = new Parser(language);
        Tree tsTree = tsParser.parse(slang, InputEncoding.UTF_8).orElseThrow();
        return tsTree.getRootNode();
    }

    public RuleExpression(IRule rule, Node node) {
        super(node);

        this.rule = rule;
        this.identifiers = new ArrayList<>();
        this.finderStatement = initFinderStatement(node);
    }

    private FinderStatementAbs<RuleExpression> initFinderStatement(Node node) {
        Node finderStatementNode = getChild(node, "statement");
        Node childNode = finderStatementNode.getChild(0).get();
        if (Objects.equals(childNode.getText(), "node")) return new NodeStatement(this, finderStatementNode);
        if (Objects.equals(childNode.getText(), "index")) return new IndexStatement<>(this, finderStatementNode);
        return null;
    }

    @Override
    public RuleExpression getRuleExpression() {
        return this;
    }

    public IRule getRule() {
        return rule;
    }

    public void addIdentifier(IdentifierAbs identifier) {
        this.identifiers.add(identifier);
    }

    public ParameterIdentifier getParameterIdentifier(String name) {
        return this.identifiers
                .stream()
                .filter(ParameterIdentifier.class::isInstance)
                .filter(i -> i.getName().equals(name))
                .map(ParameterIdentifier.class::cast)
                .findFirst()
                .orElse(null);
    }

    public NodeIdentifier getNodeIdentifier(String name) {
        return this.identifiers
                .stream()
                .filter(NodeIdentifier.class::isInstance)
                .filter(i -> i.getName().equals(name))
                .map(NodeIdentifier.class::cast)
                .findFirst()
                .orElse(null);
    }

    public IndexIdentifier getIndexIdentifier(String name) {
        return this.identifiers
                .stream()
                .filter(IndexIdentifier.class::isInstance)
                .filter(i -> i.getName().equals(name))
                .map(IndexIdentifier.class::cast)
                .findFirst()
                .orElse(null);
    }

    protected TargetIdentifierAbs getTarget() {
        return this.identifiers
                .stream()
                .filter(TargetIdentifierAbs.class::isInstance)
                .map(TargetIdentifierAbs.class::cast)
                .filter(TargetIdentifierAbs::isTarget)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No target"));
    }

    public List<Warning> execute(DatabaseHandler handler, ITreeHelper treeHelper) {

        this.source = treeHelper.getSource();

        this.finderStatement.execute(handler, treeHelper);

        return this.getTarget().getResults(treeHelper);
    }

    @Override
    public String getPath() {
        return this.getClass().getSimpleName();
    }

    public ISource getCurrentFile() {
        return source;
    }
}
