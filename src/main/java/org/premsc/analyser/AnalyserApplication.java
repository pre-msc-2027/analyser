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
	private final Repository repository = new Repository(this);
	private final Ruleset ruleset = new Ruleset(this);
	private Config config;

	private AnalyserApplication(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public Api getApi() {
		return this.api;
	}

	public Config getConfig() {
		return this.config;
	}

	public Repository getRepository() {
		return this.repository;
	}

	private void start() {

		this.init();
		this.run();

	}

	private void init() {
		this.config = Config.fromJson(this.api.get("configuration"));
		this.repository.init();
		this.ruleset.init();
	}

	private void run() {

		List<Warning> warnings = new ArrayList<>();

		this.repository.stream().forEach(source -> {

			try(ITreeHelper treeHelper = TreeHelperFactory.from(source)) {

				this.ruleset
						.stream()
						.filter(new IRule.LanguagePredicate(source.getLanguageHelper().getLanguage()))
						.flatMap(rule -> rule.test(treeHelper))
						.forEach(warnings::add);

			} catch (Exception e) {
                throw new RuntimeException(e);
            }

		});

		System.out.println(warnings);

	}

	public static void main(String[] args) {

		int id = Integer.parseInt(args[0]);

		AnalyserApplication app = new AnalyserApplication(id);

		app.start();

	}
}
