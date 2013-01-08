package br.com.emsouza.plugin.validate.util;

import java.io.File;
import java.io.IOException;

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
public class ValidateProjectUtil {

	private static Element rootElement;

	private static Document d;

	public static boolean isParent(File file) {
		try {
			rootElement = getContextElement(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Namespace ns = rootElement.getNamespace();
		Element element = rootElement.getChild("packaging", ns);
		if (element != null && element.getText().equalsIgnoreCase("pom")) {
			return true;
		} else {
			return false;
		}
	}

	private static Element getContextElement(File file) throws IOException, JDOMException {
		SAXBuilder sb = new SAXBuilder();
		d = sb.build(file);
		return d.getRootElement();
	}
}