package br.com.emsouza.plugin.validate.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ValidateScopeUtil {

	private static Element rootElement;

	private static Document d;

	public static boolean isValidt(File file,List<String> scopes) {
		try {
			rootElement = getContextElement(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Namespace ns = rootElement.getNamespace();
		Element element = rootElement.getChild("dependencies", ns);
		if (element != null) {
			List<Element> dependencies = element.getChildren("dependency", ns);
			for (Element dependency : dependencies) {
				Element scope = dependency.getChild("scope", ns);
				if (scope == null) {
					return false;
				} else {
					if (!scopes.contains(scope.getText())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static Element getContextElement(File file) throws IOException, JDOMException {
		SAXBuilder sb = new SAXBuilder();
		d = sb.build(file);
		return d.getRootElement();
	}
}