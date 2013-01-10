package br.com.emsouza.plugin.validate.util;

import java.util.List;

import org.apache.maven.plugin.MojoFailureException;

import br.com.emsouza.plugin.validate.model.Dependency;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ValidateSyntaxUtil {

	public static void validate(String projectName, List<Dependency> dependencies, List<Dependency> syntax) throws MojoFailureException {
		for (Dependency dep : dependencies) {
			for (Dependency syt : syntax) {
				if (dep.getGroupId().equalsIgnoreCase(syt.getGroupId())) {
					if (dep.getArtifactId().equalsIgnoreCase(syt.getArtifactId())) {
						System.out.println(dep.getVersion());
						if (!dep.getVersion().equalsIgnoreCase(syt.getVersion())) {
							throw new MojoFailureException("[ " + projectName + " ] " + String.format(syt.getDescription(), syt, syt.getVersion()));
						}
					}
				}
			}
		}
	}
}