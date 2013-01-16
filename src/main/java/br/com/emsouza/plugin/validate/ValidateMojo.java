package br.com.emsouza.plugin.validate;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import br.com.emsouza.plugin.validate.factory.ConfigurationFactory;
import br.com.emsouza.plugin.validate.model.Configuration;
import br.com.emsouza.plugin.validate.model.Dependency;
import br.com.emsouza.plugin.validate.util.ConvertData;
import br.com.emsouza.plugin.validate.util.ValidateScopeUtil;
import br.com.emsouza.plugin.validate.util.ValidateSyntaxUtil;

/**
 * @author Eduardo Matos de Souza <br>
 *         13/09/2012 <br>
 * @goal validate
 * @phase validate
 */
public class ValidateMojo extends AbstractMojo {

	/**
	 * @parameter expression= "${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * @parameter default-value="${project.file}"
	 * @required
	 * @readonly
	 */
	private File pomFile;

	/**
	 * Nome do Projeto.
	 * 
	 * @parameter expression= "${projectName}"
	 * @required
	 */
	protected String projectName;

	/**
	 * Endereço da Documentação do Maven para a validação.
	 * 
	 * @parameter expression= "${mavenDocURL}"
	 * @required
	 */
	protected String mavenDocURL;

	/**
	 * Endereço da Documentação do Maven para a validação.
	 * 
	 * @parameter expression= "${configURL}"
	 * @required
	 */
	protected String configURL;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		boolean execute = Boolean.parseBoolean(project.getProperties().getProperty("validate-plugin", "true"));

		if (execute) {
			if (!project.getPackaging().equals("pom")) {

				List<Dependency> dependencies = ConvertData.readProjectFile(pomFile);
				Configuration cfg = ConfigurationFactory.build(configURL);

				// Get the list of valid scopes
				if (!ValidateScopeUtil.isValid(dependencies, cfg.getScopes())) {
					throw new MojoFailureException("[ " + projectName + " ] Existem dependências declaradas fora do padrão => " + mavenDocURL);
				}

				// Verify exclude artifact
				if (CollectionUtils.containsAny(dependencies, cfg.getExclusions())) {
					Dependency art = (Dependency) ListUtils.intersection(dependencies, cfg.getExclusions()).get(0);
					throw new MojoFailureException("[ " + projectName + " ] " + String.format(art.getDescription(), art));
				}

				// Verify correct Syntax
				ValidateSyntaxUtil.validate(projectName, dependencies, cfg.getSyntax());
			}
		}
	}
}