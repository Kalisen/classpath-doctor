package org.kalisen.test.fest;

import javax.swing.AbstractButton;

import org.fest.swing.core.GenericTypeMatcher;

public class AbstractButtonLabelMatcher<T extends AbstractButton> extends GenericTypeMatcher<T> {


	private String label = null;

	public AbstractButtonLabelMatcher(Class<T> supportedType, String label) {
		this(supportedType, label, false);
	}

	public AbstractButtonLabelMatcher(Class<T> supportedType, String label,
			boolean requireShowing) {
		super(supportedType, requireShowing);
		this.label = label;
	}

	@Override
	protected boolean isMatching(T component) {
		return (this.label == null && component.getText() == null)
				|| (this.label != null && this.label.equals(component.getText()));
	}

}
