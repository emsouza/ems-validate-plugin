package br.com.emsouza.plugin.validate.model;

import java.util.Collections;
import java.util.List;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 09/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class Configuration {

	private List<String> scopes;

	private List<Dependency> exclusions;

	private List<Dependency> syntax;

	public Configuration(List<String> scopes, List<Dependency> exclusions, List<Dependency> syntax) {
		this.scopes = scopes;
		this.exclusions = exclusions;
		this.syntax = syntax;
	}

	public List<String> getScopes() {
		return Collections.unmodifiableList(scopes);
	}

	public List<Dependency> getExclusions() {
		return Collections.unmodifiableList(exclusions);
	}

	public List<Dependency> getSyntax() {
		return Collections.unmodifiableList(syntax);
	}
}