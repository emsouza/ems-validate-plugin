package br.com.emsouza.plugin.validate.util;

import br.com.emsouza.plugin.validate.model.Dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class ConvertData {

    private static final String TEST = "test";

    private static Element rootElement;

    private static Document document;

    public static List<Dependency> readProjectFile(File file) throws MojoFailureException {
        try {
            List<Dependency> list = new ArrayList<>();

            rootElement = getContextElement(file);
            // Namespace ns = rootElement.getNamespace();
            Element dependencies = rootElement.element("dependencies");

            if (dependencies != null) {
                List<Element> dps = dependencies.elements("dependency");
                for (Element dependency : dps) {
                    Element groupId = dependency.element("groupId");
                    Element artifactId = dependency.element("artifactId");
                    Element version = dependency.element("version");
                    Element scope = dependency.element("scope");

                    list.add(new Dependency(groupId.getText(), artifactId.getText(), (version != null ? version.getText() : null),
                            ((scope != null && !scope.getText().isEmpty()) ? scope.getText() : "compile"), null));
                }
            }

            Iterator<Dependency> it = list.iterator();
            while (it.hasNext()) {
                Dependency dep = it.next();
                if (TEST.equalsIgnoreCase(dep.getScope())) {
                    it.remove();
                }
            }

            return list;
        } catch (Exception e) {
            throw new MojoFailureException("Erro ao validar projeto", e);
        }
    }

    private static Element getContextElement(File file) throws DocumentException {
        SAXReader sb = new SAXReader();
        document = sb.read(file);
        return document.getRootElement();
    }
}
