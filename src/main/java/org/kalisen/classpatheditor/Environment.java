
package org.kalisen.classpatheditor;

import java.util.Map;

public class Environment {

    private Map<String, String> underlyingEnv = null;

    public Environment() {
        this.underlyingEnv = System.getenv();
    }

    public String getValue(String variable) {
        return this.underlyingEnv.get(variable);
    }
}
