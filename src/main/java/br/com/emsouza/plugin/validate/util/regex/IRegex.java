package br.com.emsouza.plugin.validate.util.regex;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Eduardo Matos de Souza <br>
 *         Data: 08/01/2013 <br>
 *         E-mail: eduardomatosouza@gmail.com
 */
public class IRegex {

	static final String SCOPE = "(scope)( =>*.)(\\(.*\\))";

	static final Pattern PATTERN_SCOPE = Pattern.compile(IRegex.SCOPE);
	
	public static List<String> getScopes(String text) {
		Matcher search = PATTERN_SCOPE.matcher(text);
		while (search.find()) {
			return Arrays.asList(search.group(3).replaceAll("\\(", "").replaceAll("\\)", "").split("\\|"));
		}
		return null;
	}
}
