package org.kalisen.classpathdoctor;

import java.util.regex.Pattern;

public class UnixVariableResolver extends AbstractVariableResolver {

    public static final Pattern UNIX_VARIABLE = Pattern.compile("\\$\\{.*?\\}"); 

    @Override
    protected Pattern getVariablePattern() {
        return UNIX_VARIABLE;
    }

    @Override
    protected String isolateVarName(String var) {
        return var.substring(2, var.length() -1);
    }
    
}
