package br.com.emsouza.plugin.validate.util;

import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;

import java.util.List;

import org.apache.maven.project.MavenProject;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * @author Eduardo Souza - SIS <br>
 *         Dígitro - 29/08/2020 <br>
 *         <a href="mailto:eduardo.souza@digitro.com">eduardo.souza@digitro.com</a>
 */
public class ValidateExclusionUtil {

    public static boolean validate(MavenProject project, BuildContext bContext, Configuration cfg, List<Dependency> dependencies) {
        boolean erros = false;
        // Verify exclude artifact

        for (Dependency dep : dependencies) {
            for (Dependency del : cfg.getExclusions()) {
                if (dep.getGroupId().equalsIgnoreCase(del.getGroupId())) {
                    if (dep.getArtifactId().equalsIgnoreCase(del.getArtifactId()) || del.getArtifactId().equals("*")) {
                        bContext.addMessage(project.getFile(), 0, 0, String.format(del.getDescription(), del), BuildContext.SEVERITY_ERROR, null);

                        erros = true;
                    }
                }
            }
        }
        return erros;
    }
}
