package br.com.emsouza.plugin.validate.util;

import java.util.List;

import br.com.emsouza.plugin.validate.model.Dependency;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ValidateScopeUtil {

	public static boolean isValid(List<Dependency> dependencies, List<String> scopes) {
		for (Dependency dependency : dependencies) {
			if (dependency.getScope() == null) {
				return false;
			} else {
				if (!scopes.contains(dependency.getScope())) {
					return false;
				}
			}
		}
		return true;
	}
}