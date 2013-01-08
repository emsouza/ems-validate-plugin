package br.com.emsouza.plugin.validate;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import br.com.emsouza.plugin.validate.util.ValidateProjectUtil;
import br.com.emsouza.plugin.validate.util.ValidateScopeUtil;
import br.com.emsouza.plugin.validate.util.regex.IRegex;

/**
 * @author Eduardo Matos de Souza <br>
 *         13/09/2012 <br>
 * 
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

	protected String text = "scope => (test|provided)";

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		// Verify if a project generate artifact
		if (!ValidateProjectUtil.isParent(pomFile)) {

			// Get the list of valid scopes
			List<String> scopes = IRegex.getScopes(text);
			if (!ValidateScopeUtil.isValidt(pomFile, scopes)) {
				throw new MojoFailureException("[ " + projectName + " ] Existem dependências declaradas fora do padrão => " + mavenDocURL);
			}
		}
	}
}