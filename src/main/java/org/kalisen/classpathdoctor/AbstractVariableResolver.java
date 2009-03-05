package org.kalisen.classpathdoctor;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractVariableResolver implements VariableResolver {

	private Environment env = null;

	public AbstractVariableResolver() {
		// default constructor
	}

	public String resolve(String path) {
		if (path == null) {
			throw new IllegalArgumentException(ResourceBundle.getBundle(
					"UsersMessages").getString("null.is.not.a.valid.argument"));
		}
		String result = path;
		Pattern p = getVariablePattern();
		Matcher m = p.matcher(path);
		String var = null;
		String varValue = null;
 		while (m.find()) {
			var = m.group();
			varValue = getEnvironment().getValue(isolateVarName(var));
			if (varValue != null) {
				varValue = resolve(varValue);
				result = path.replaceAll(Pattern.quote(var), Matcher.quoteReplacement(varValue));
			}
		}
		return result;
	}

	protected abstract String isolateVarName(String var);

	public Environment getEnvironment() {
		if (this.env == null) {
			this.env = new Environment();
		}
		return this.env;
	}

	public void setEnvironment(Environment env) {
		if (env == null) {
			throw new IllegalArgumentException(ResourceBundle.getBundle(
					"UsersMessages").getString("null.is.not.a.valid.argument"));
		}
		this.env = env;
	}

	protected abstract Pattern getVariablePattern();

}
