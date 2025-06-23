package org.premsc.analyser;

import io.github.treesitter.jtreesitter.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.lang.foreign.Arena;
import java.lang.foreign.SymbolLookup;

public class AnalyserApplication {

	public static void main(String[] args) {
		System.loadLibrary("tree-sitter");

		String library = System.mapLibraryName("libs/tree-sitter-html");
		SymbolLookup symbols = SymbolLookup.libraryLookup(library, Arena.global());
		Language html = Language.load(symbols, "tree_sitter_html");

		Parser parser = new Parser();
		parser.setLanguage(html);


		String source = "<div><p>Hello</p></div>";
		Tree tree = parser.parse(source).orElseThrow();
		Node root = tree.getRootNode();

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
		} catch (Exception ignored) {}

		DefaultMutableTreeNode swingNode = new DefaultMutableTreeNode(label);
		for (int i = 0; i < node.getChildCount(); i++) {
			swingNode.add(buildTree(node.getChild(i).orElseThrow(), source));
		}
		return swingNode;
	}

}
