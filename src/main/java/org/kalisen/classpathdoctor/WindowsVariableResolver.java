package org.kalisen.classpathdoctor;

import java.util.regex.Pattern;

public class WindowsVariableResolver extends AbstractVariableResolver {

	public static final Pattern WINDOWS_VARIABLE = Pattern.compile("%.*?%");

	@Override
	protected Pattern getVariablePattern() {
		return WINDOWS_VARIABLE;
	}

	@Override
	protected String isolateVarName(String var) {
		return var.substring(1, var.length() - 1);
	}

}
