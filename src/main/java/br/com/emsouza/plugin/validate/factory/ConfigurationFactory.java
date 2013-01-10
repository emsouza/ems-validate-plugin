package br.com.emsouza.plugin.validate.factory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ConfigurationFactory {

	public static Configuration build(String url) {
		try {
			List<String> scs = new ArrayList<String>();
			List<Dependency> exs = new ArrayList<Dependency>();
			List<Dependency> syt = new ArrayList<Dependency>();

			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(new URL(url));
			Element rootElement = doc.getRootElement();

			Element scopes = rootElement.getChild("scopes");
			if (scopes != null) {
				List<Element> scopeArray = scopes.getChildren("scope");
				for (Element elm : scopeArray) {
					scs.add(elm.getText());
				}
			}

			Element exclusions = rootElement.getChild("exclusions");
			if (exclusions != null) {
				List<Element> exclusionArray = exclusions.getChildren("artifact");
				for (Element elm : exclusionArray) {
					Element groupId = elm.getChild("groupId");
					Element artifactId = elm.getChild("artifactId");
					Element description = elm.getChild("description");
					exs.add(new Dependency(groupId.getText(), artifactId.getText(), null, null, description.getText()));
				}
			}

			Element syntax = rootElement.getChild("syntax");
			if (syntax != null) {
				List<Element> syntaxArray = syntax.getChildren("artifact");
				for (Element elm : syntaxArray) {
					Element groupId = elm.getChild("groupId");
					Element artifactId = elm.getChild("artifactId");
					Element version = elm.getChild("version");
					Element description = elm.getChild("description");
					syt.add(new Dependency(groupId.getText(), artifactId.getText(), version.getText(), null, description.getText()));
				}
			}

			return new Configuration(scs, exs, syt);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}