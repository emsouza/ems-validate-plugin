/**
 * Copyright (C) 2013 Eduardo Souza http://www.emsouza.com.br <br>
 * <br>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of <br>
 * this software and associated documentation files (the "Software"), to deal in <br>
 * the Software without restriction, including without limitation the rights to <br>
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies <br>
 * of the Software, and to permit persons to whom the Software is furnished to do <br>
 * so, subject to the following conditions: <br>
 * <br>
 * The above copyright notice and this permission notice shall be included in all <br>
 * copies or substantial portions of the Software. <br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR <br>
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, <br>
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE <br>
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER <br>
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, <br>
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE <br>
 * SOFTWARE. <br>
 */
package br.com.emsouza.plugin.validate;

import br.com.emsouza.plugin.validate.factory.ConfigurationFactory;
import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;
import br.com.emsouza.plugin.validate.util.ConvertData;
import br.com.emsouza.plugin.validate.util.ValidateExclusionUtil;
import br.com.emsouza.plugin.validate.util.ValidateSyntaxUtil;

import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * @author Eduardo Matos de Souza <br>
 *         27/04/2013 <br>
 *         <a href=
 *         "mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@Mojo(name = "validate-pom", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true, requiresOnline = true)
public class ValidatePomMojo extends AbstractMojo {

	private static final String POM = "pom";

	/**
	 * The Maven session
	 */
	@Parameter(defaultValue = "${session}", readonly = true, required = true)
	protected MavenSession session;

	@Component
	private BuildContext buildContext;

	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	@Parameter(defaultValue = "${configURL}", readonly = true, required = true)
	protected String configURL;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		boolean execute = Boolean.parseBoolean(project.getProperties().getProperty("validate-plugin", "true"));

		if (execute && !session.isOffline() && !POM.equals(project.getPackaging())) {

			Configuration cfg = ConfigurationFactory.build(getLog(), configURL);

			if (cfg != null) {

				List<Dependency> dependencies = ConvertData.readProjectFile(project.getFile());

				boolean erros = false;

				// Verify exclude artifact
				erros = ValidateExclusionUtil.validate(project, buildContext, cfg, dependencies);

				if (erros) {
					throw new MojoFailureException("Existem erros nas dependências do projeto.");
				}

				// Verify correct Syntax
				erros = ValidateSyntaxUtil.validate(project, buildContext, cfg, dependencies);

				if (erros) {
					throw new MojoFailureException("Existem erros nas dependências do projeto.");
				}
			}
		}
	}
}
