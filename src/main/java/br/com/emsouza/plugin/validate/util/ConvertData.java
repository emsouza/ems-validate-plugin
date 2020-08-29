package br.com.emsouza.plugin.validate.util;

import br.com.emsouza.plugin.validate.model.Dependency;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;
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
public class ConvertData {

    private static final String TEST = "test";

    private static Element rootElement;

    private static Document d;

    public static List<Dependency> readProjectFile(File file) throws MojoFailureException {
        try {
            List<Dependency> list = new ArrayList<>();

            rootElement = getContextElement(file);
            Namespace ns = rootElement.getNamespace();
            Element dependencies = rootElement.getChild("dependencies", ns);

            if (dependencies != null) {
                List<Element> dps = dependencies.getChildren("dependency", ns);
                for (Element dependency : dps) {
                    Element groupId = dependency.getChild("groupId", ns);
                    Element artifactId = dependency.getChild("artifactId", ns);
                    Element version = dependency.getChild("version", ns);
                    Element scope = dependency.getChild("scope", ns);

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

    private static Element getContextElement(File file) throws IOException, JDOMException {
        SAXBuilder sb = new SAXBuilder();
        d = sb.build(file);
        return d.getRootElement();
    }
}
