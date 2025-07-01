package org.premsc.analyser;

import io.github.treesitter.jtreesitter.*;
import org.premsc.analyser.indexer.IndexerManager;
import org.premsc.analyser.parser.queries.builder.QueryBuilder;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;
import java.util.List;
import java.util.stream.Collectors;

public class Visualizer {

    public static void main(String[] args) {

        System.loadLibrary("tree-sitter");

        Language css = loadLanguage("tree-sitter-css");

        Parser parser = new Parser();
        parser.setLanguage(css);

        String sourceHtml = loadResource("index.html");
        String sourceCss = loadResource("style.css");

        Tree tree = parser.parse(sourceCss).orElseThrow();
        Node node = tree.getRootNode();

        query(css, node);

        display(node, sourceCss);

        tree.close();

    }

    private static void query(Language css, Node node) {
        QueryBuilder<?> queryBuilder = new IndexerManager.CssClassIndexer().getQuery();

        String queryString = queryBuilder.build();

        System.out.println(queryString);

        Query query = new Query(css, queryString);
        QueryCursor cursor = new QueryCursor(query);

        List<QueryMatch> matches = cursor.findMatches(node).toList();
        List<Node> nodes = matches.stream().flatMap(m -> m.captures().stream().filter((QueryCapture c) -> c.name().equals("target")).map(QueryCapture::node)).toList();
        System.out.println(nodes.stream().map(Node::getText).collect(Collectors.joining("\n")));

        query.close();
        cursor.close();

    }

    private static Language loadLanguage(String lib) {
        String library = System.mapLibraryName("libs/" + lib);
        SymbolLookup symbols = SymbolLookup.libraryLookup(library, Arena.global());
        return Language.load(symbols, lib.replace('-', '_'));

    }

    public static String loadResource(String filename) {
        InputStream is = Visualizer.class.getResourceAsStream("/" + filename);
        if (is == null) {
            throw new RuntimeException("Resource not found: " + filename);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource: " + filename, e);
        }
    }


    private static void display(Node root, String source) {

        // Build Swing tree
        DefaultMutableTreeNode swingRoot = buildTree(root, source);

        // GUI setup
        JTree jtree = new JTree(swingRoot);
        JFrame frame = new JFrame("CST Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.add(new JScrollPane(jtree), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static DefaultMutableTreeNode buildTree(Node node, String source) {
        String label = node.getType() + " [" +
                       node.getStartByte() + ".." + node.getEndByte() + "]";

        try {
            String snippet = source.substring(node.getStartByte(), node.getEndByte());
            if (!snippet.isBlank()) label += " → \"" + snippet.replace("\n", "\\n") + "\"";
        } catch (Exception ignored) {
        }

        DefaultMutableTreeNode swingNode = new DefaultMutableTreeNode(label);
        for (int i = 0; i < node.getChildCount(); i++) {
            swingNode.add(buildTree(node.getChild(i).orElseThrow(), source));
        }
        return swingNode;
    }

}


