package br.com.emsouza.plugin.validate.util;

import java.util.Arrays;
import java.util.List;

import org.apache.maven.project.MavenProject;
import org.sonatype.plexus.build.incremental.BuildContext;

import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ValidateSyntaxUtil {

    public static boolean validate(MavenProject mavenProject, BuildContext buildContext, Configuration cfg, List<Dependency> dependencies) {
        boolean erros = false;
        for (Dependency dep : dependencies) {
            for (Dependency syt : cfg.getSyntax()) {
                if (dep.getGroupId().equalsIgnoreCase(syt.getGroupId())) {
                    if (dep.getArtifactId().equalsIgnoreCase(syt.getArtifactId()) || syt.getArtifactId().equals("*")) {
                    	
						List<String> versions =Arrays.asList(syt.getVersion().split("\\|"));
                        if (dep.getVersion() != null && !versions.stream().anyMatch((v) -> dep.getVersion().startsWith(v))) {
                            buildContext.addMessage(mavenProject.getFile(), 0, 0, String.format(syt.getDescription(), dep, syt.getVersion()),
                                    BuildContext.SEVERITY_ERROR, null);

                            erros = true;
                        }
                    }
                }
            }
        }
        return erros;
    }
}
