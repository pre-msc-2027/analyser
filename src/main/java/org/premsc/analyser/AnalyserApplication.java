package org.premsc.analyser;

import org.premsc.analyser.api.Api;
import org.premsc.analyser.config.Config;
import org.premsc.analyser.parser.tree.ITreeHelper;
import org.premsc.analyser.parser.tree.TreeHelperFactory;
import org.premsc.analyser.repository.Repository;
import org.premsc.analyser.rules.IRule;
import org.premsc.analyser.rules.Ruleset;
import org.premsc.analyser.rules.Warning;

import java.util.ArrayList;
import java.util.List;

public class AnalyserApplication {

	private final int id;

	private final Api api = new Api(this);

	private AnalyserApplication(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public Api getApi() {
		return this.api;
	}

	}

	}

	private void start() {

		this.init();
		this.run();

	}

	private void init() {
	}

	private void run() {
	}

	public static void main(String[] args) {

		int id = Integer.parseInt(args[0]);

		AnalyserApplication app = new AnalyserApplication(id);

		app.start();

	}
}
