package br.com.emsouza.plugin.validate.util;

import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;

import java.util.List;

import org.apache.maven.plugin.logging.Log;

/**
 * @author Eduardo Souza - SIS <br>
 *         Dígitro - 29/08/2020 <br>
 *         <a href="mailto:eduardo.souza@digitro.com">eduardo.souza@digitro.com</a>
 */
public class ValidateExclusionUtil {

    private static final String DESCRICAO = "A dependência %s não é permitida. Utilize %s.";

    public static boolean validate(Log log, Configuration cfg, List<Dependency> dependencies) {
        boolean erros = false;

        for (Dependency dep : dependencies) {
            for (Dependency del : cfg.getExclusions()) {
                if (dep.getGroupId().equalsIgnoreCase(del.getGroupId())) {
                    if (dep.getArtifactId().equalsIgnoreCase(del.getArtifactId()) || del.getArtifactId().equals("*")) {
                        log.error(String.format(DESCRICAO, del, del.getDescription()));

                        erros = true;
                    }
                }
            }
        }
        return erros;
    }
}
