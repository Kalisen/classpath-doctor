package org.kalisen.classpathdoctor;

public interface ClassPathFormatter {

	String format(ClassPath cp);
	
	StringBuilder format(ClassPath cp, StringBuilder builder);

}
