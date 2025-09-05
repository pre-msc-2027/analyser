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

/**
 * Class representing a rule expression in the slang language.
 */
public class RuleExpression extends SlangObjectAbs implements IFinderParent {

    protected final IRule rule;
    protected final List<IdentifierAbs> identifiers;
    protected final FinderStatementAbs<RuleExpression> finderStatement;

    protected ISource source;
    protected DatabaseHandler dbHandler;

    /**
     * Constructor for RuleExpression.
     *
     * @param rule the rule associated with this expression
     */
    public RuleExpression(IRule rule) {
        this(rule, getTree(rule));
    }

    /**
     * Constructor for RuleExpression.
     *
     * @param rule the rule associated with this expression
     * @param tree the syntax tree representing the parsed slang code
     */
    protected RuleExpression(IRule rule, Tree tree) {
        super(tree.getRootNode());

        this.rule = rule;
        this.identifiers = new ArrayList<>();
        this.finderStatement = initFinderStatement(tree.getRootNode());

        tree.close();
    }

    /**
     * Parses the slang code of the given rule and returns the corresponding syntax tree.
     *
     * @param rule the rule containing the slang code
     * @return the syntax tree representing the parsed slang code
     */
    @SuppressWarnings("resource")
    private static Tree getTree(IRule rule) {
        String slang = rule.getSlang();
        Language language = Language.load(
                NativeLib.openGrammar("slang"),
                NativeLib.getNativeName("slang")
        );

        Parser tsParser = new Parser(language);
        return tsParser.parse(slang, InputEncoding.UTF_8).orElseThrow();
    }

    /**
     * Initializes the finder statement from the syntax tree node.
     *
     * @param node the syntax tree node
     * @return the initialized finder statement
     */
    private FinderStatementAbs<RuleExpression> initFinderStatement(Node node) {
        Node finderStatementNode = getChild(node, "statement");
        Node childNode = finderStatementNode.getChild(0).orElseThrow(() -> new IllegalStateException("No child in statement"));
        if (Objects.equals(childNode.getText(), "node")) return new NodeStatement(this, finderStatementNode);
        if (Objects.equals(childNode.getText(), "index")) return new IndexStatement<>(this, finderStatementNode);
        return null;
    }

    @Override
    public RuleExpression getRuleExpression() {
        return this;
    }

    /**
     * Gets the rule associated with this expression.
     *
     * @return the associated rule
     */
    public IRule getRule() {
        return rule;
    }

    /**
     * Adds an identifier to this rule expression.
     *
     * @param identifier the identifier to add
     */
    public void addIdentifier(IdentifierAbs identifier) {
        this.identifiers.add(identifier);
    }

    /**
     * Retrieves a parameter identifier by its name.
     *
     * @param name the name of the parameter identifier
     * @return the corresponding ParameterIdentifier, or null if not found
     */
    public ParameterIdentifier getParameterIdentifier(String name) {
        return this.identifiers
                .stream()
                .filter(ParameterIdentifier.class::isInstance)
                .filter(i -> i.getName().equals(name))
                .map(ParameterIdentifier.class::cast)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a node identifier by its name.
     *
     * @param name the name of the node identifier
     * @return the corresponding NodeIdentifier, or null if not found
     */
    public NodeIdentifier getNodeIdentifier(String name) {
        return this.identifiers
                .stream()
                .filter(NodeIdentifier.class::isInstance)
                .filter(i -> i.getName().equals(name))
                .map(NodeIdentifier.class::cast)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves an index identifier by its name.
     *
     * @param name the name of the index identifier
     * @return the corresponding IndexIdentifier, or null if not found
     */
    public IndexIdentifier getIndexIdentifier(String name) {
        return this.identifiers
                .stream()
                .filter(IndexIdentifier.class::isInstance)
                .filter(i -> i.getName().equals(name))
                .map(IndexIdentifier.class::cast)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves the target identifier from the list of identifiers.
     *
     * @return the target identifier
     */
    protected TargetIdentifierAbs getTarget() {
        return this.identifiers
                .stream()
                .filter(TargetIdentifierAbs.class::isInstance)
                .map(TargetIdentifierAbs.class::cast)
                .filter(TargetIdentifierAbs::isTarget)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No target"));
    }

    /**
     * Executes the rule expression using the provided database handler and tree helper.
     *
     * @param handler    the database handler to execute the query
     * @param treeHelper the tree helper for additional context
     * @return a list of warnings generated by the execution
     */
    public List<Warning> execute(DatabaseHandler handler, ITreeHelper treeHelper) {

        this.source = treeHelper.getSource();

        this.finderStatement.execute(handler, treeHelper);

        return this.getTarget().getResults(treeHelper);
    }

    @Override
    public String getPath() {
        return this.getClass().getSimpleName();
    }

    /**
     * Gets the current file (source) associated with this rule expression.
     *
     * @return the current source file
     */
    public ISource getCurrentFile() {
        return source;
    }
}
