package br.com.emsouza.plugin.validate.factory;

import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ConfigurationFactory {

    public static Configuration build(Log log, String url) {
        try {
            URI uri = new URI(url);

            try (InputStream stream = uri.toURL().openStream()) {

                List<String> scs = new ArrayList<>();
                List<Dependency> exs = new ArrayList<>();
                List<Dependency> syt = new ArrayList<>();

                SAXReader reader = new SAXReader();
                Document doc = reader.read(new URL(url));
                Element rootElement = doc.getRootElement();

                Element scopes = rootElement.element("scopes");
                if (scopes != null) {
                    List<Element> scopeArray = scopes.elements("scope");
                    for (Element elm : scopeArray) {
                        scs.add(elm.getText());
                    }
                }

                Element exclusions = rootElement.element("exclusions");
                if (exclusions != null) {
                    List<Element> exclusionArray = exclusions.elements("artifact");
                    for (Element elm : exclusionArray) {
                        Element groupId = elm.element("groupId");
                        Element artifactId = elm.element("artifactId");
                        Element description = elm.element("description");
                        exs.add(new Dependency(groupId.getText(), artifactId.getText(), null, null, description.getText()));
                    }
                }

                Element syntax = rootElement.element("syntax");
                if (syntax != null) {
                    List<Element> syntaxArray = syntax.elements("artifact");
                    for (Element elm : syntaxArray) {
                        Element groupId = elm.element("groupId");
                        Element artifactId = elm.element("artifactId");
                        Element version = elm.element("version");
                        Element description = elm.element("description");
                        syt.add(new Dependency(groupId.getText(), artifactId.getText(), version.getText(), null, description.getText()));
                    }
                }

                return new Configuration(scs, exs, syt);

            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("Erro ao obter arquivo de configuração.", e);
                } else {
                    log.warn("Ignorando validação de dependências. Não foi possível obter o arquivo de validações.");
                }
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }
}
