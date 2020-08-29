package br.com.emsouza.plugin.validate.util;

import br.com.emsouza.plugin.validate.model.Dependency;

import java.util.List;

import org.apache.maven.project.MavenProject;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ValidateSyntaxUtil {

    public static boolean validate(MavenProject mavenProject, BuildContext buildContext, List<Dependency> dependencies, List<Dependency> syntax) {
        boolean erros = false;
        for (Dependency dep : dependencies) {
            for (Dependency syt : syntax) {
                if (dep.getGroupId().equalsIgnoreCase(syt.getGroupId())) {
                    if (dep.getArtifactId().equalsIgnoreCase(syt.getArtifactId()) || syt.getArtifactId().equals("*")) {
                        if (dep.getVersion() != null && !dep.getVersion().equalsIgnoreCase(syt.getVersion())) {
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
