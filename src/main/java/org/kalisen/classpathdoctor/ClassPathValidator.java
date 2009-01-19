package org.kalisen.classpathdoctor;

import java.util.List;
import java.util.ResourceBundle;

public class ClassPathValidator {

	private ClassPathParser parser = null;

	public ClassPathValidator() {
		super();
	}

	public ValidationReport validate(String classpath) {
		ValidationReport result = new ValidationReport();
		ClassPath cPath = getParser().parse(classpath);
		List<PathEntry> entries = cPath.getEntries();
		for (PathEntry pathEntry : entries) {
			if (!pathEntry.exists()) {
				result.addError(new ClassPathError(pathEntry));
			}
		}
		return result;
	}

	public ClassPathParser getParser() {
		if (this.parser == null) {
			this.parser = new ClassPathParser();
		}
		return this.parser;
	}

	public void setParser(ClassPathParser parser) {
		if (parser == null) {
			throw new IllegalArgumentException(ResourceBundle.getBundle(
					"UsersMessages").getString("null.is.not.a.valid.argument"));
		}
		this.parser = parser;
	}

}
