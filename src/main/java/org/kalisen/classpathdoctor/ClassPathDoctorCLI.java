package org.kalisen.classpathdoctor;

import java.util.Locale;

public class ClassPathDoctorCLI {

	public static void main(String[] args) {
		// ClassPathValidator validator = new ClassPathValidator();
		// ValidationReport result = validator.validate(args[0]);
		// System.out.println(result.toString());
		Locale.setDefault(Locale.FRENCH);
		ClassPathParser parser = new ClassPathParser();
		ClassPath result = parser.parse(args[0]);
		System.out.println(result.toString());
	}

}
