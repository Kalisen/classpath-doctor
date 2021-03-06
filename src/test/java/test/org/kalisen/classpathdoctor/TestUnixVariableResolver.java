package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.AbstractVariableResolver;
import org.kalisen.classpathdoctor.UnixVariableResolver;

public class TestUnixVariableResolver extends AbstractTestVariableResolver {

	private static final String NON_EXISTING_VAR_REFERENCE = "${NON_EXISTING}";
	private static final String VAR_REFERENCE = "${MY_VARIABLE}";
	private static final String UNIX_PATH_SEPARATOR = ":";

	@Override
	protected String getPathSeparator() {
		return UNIX_PATH_SEPARATOR;
	}

	@Override
	protected String getVariableReference() {
		return VAR_REFERENCE;
	}

	@Override
	protected String getNonExistingVariableReference() {
		return NON_EXISTING_VAR_REFERENCE;
	}

	@Override
	protected AbstractVariableResolver getTestedVariableResolver() {
		return new UnixVariableResolver();
	}

	@Override
	protected String getVariableValueWithSeparator() {
		return "path/value/with/separator";
	}

}
