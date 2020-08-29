package br.com.emsouza.plugin.validate.util;

import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.maven.project.MavenProject;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * @author Eduardo Souza - SIS <br>
 *         Dígitro - 29/08/2020 <br>
 *         <a href="mailto:eduardo.souza@digitro.com">eduardo.souza@digitro.com</a>
 */
public class ValidateExclusionUtil {

    @SuppressWarnings("unchecked")
    public static boolean validate(MavenProject project, BuildContext bContext, Configuration cfg, List<Dependency> dependencies) {
        boolean erros = false;
        // Verify exclude artifact
        if (CollectionUtils.containsAny(dependencies, cfg.getExclusions())) {
            List<Dependency> listaDeErros = ListUtils.intersection(dependencies, cfg.getExclusions());
            if (listaDeErros != null && !listaDeErros.isEmpty()) {
                for (Dependency art : listaDeErros) {
                    bContext.addMessage(project.getFile(), 0, 0, String.format(art.getDescription(), art), BuildContext.SEVERITY_ERROR, null);
                }
            }
        }
        return erros;
    }
}
