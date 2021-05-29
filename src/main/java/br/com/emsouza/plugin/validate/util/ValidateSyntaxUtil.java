package br.com.emsouza.plugin.validate.util;

import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;

import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.logging.Log;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ValidateSyntaxUtil {

    private static final String DESCRICAO = "A dependência %s deve ser declarada utilizando a variável de versão %s.";

    public static boolean validate(Log log, Configuration cfg, List<Dependency> dependencies) {
        boolean erros = false;
        for (Dependency dep : dependencies) {
            for (Dependency syt : cfg.getSyntax()) {
                if (dep.getGroupId().equalsIgnoreCase(syt.getGroupId())) {
                    if (dep.getArtifactId().equalsIgnoreCase(syt.getArtifactId()) || syt.getArtifactId().equals("*")) {
                        if (dep.getVersion() != null) {
                            erros = verificaVersao(log, dep, syt);
                        }
                    }
                }
            }
        }
        return erros;
    }

    private static boolean verificaVersao(Log log, Dependency dep, Dependency syt) {
        List<String> versions = Arrays.asList(syt.getVersion().split("\\|"));
        boolean versaoErrada = !versions.stream().anyMatch((v) -> (dep.getVersion().equals(v) || dep.getVersion().startsWith(v)));

        if (versaoErrada) {
            if (versions.size() == 1) {
                log.error(String.format(DESCRICAO, dep, syt.getVersion()));
            } else {
                log.error(String.format(DESCRICAO, dep, syt.getVersion().replaceAll("\\|", ", ou iniciar com ")));
            }

            return true;
        }
        return false;
    }
}
