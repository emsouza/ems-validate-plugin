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
public class XMLUtil {

	private Element rootElement;

	private Document d;

	public XMLUtil(File file) {
		try {
			rootElement = getContextElement(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String parseParent() {
		Namespace ns = rootElement.getNamespace();
		Element element = rootElement.getChild("parent", ns);
		if (element != null) {
			Element groupId = element.getChild("groupId", ns);
			Element versionXML = element.getChild("version", ns);
			if (((groupId.getText().startsWith("br.com.digitro")) || (groupId.getText().startsWith("com.digitro"))) && (versionXML == null)) {
				Element path = element.getChild("relativePath", ns);
				if (path != null && !path.getText().isEmpty()) {
					return getParentVersion(path.getText());
				} else {
					return getParentVersion("../pom.xml");
				}
			}
		}
		return null;
	}

	private String getParentVersion(String fileParent) {
		try {
			rootElement = getContextElement(new File(".", fileParent));
			Namespace ns = rootElement.getNamespace();
			Element element = rootElement.getChild("version", ns);
			if (element != null) {
				return element.getText();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Element getContextElement(File file) throws IOException, JDOMException {
		SAXBuilder sb = new SAXBuilder();
		d = sb.build(file);
		return d.getRootElement();
	}
}