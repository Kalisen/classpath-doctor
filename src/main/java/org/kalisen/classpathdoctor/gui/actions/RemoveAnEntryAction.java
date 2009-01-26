package org.kalisen.classpathdoctor.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JList;
import javax.swing.KeyStroke;

import org.kalisen.classpathdoctor.adapter.ClassPathAdapter;

@SuppressWarnings("serial")
public class RemoveAnEntryAction extends AbstractAction {

	private static final String REMOVE_AN_ENTRY_LABEL = ResourceBundle
			.getBundle("UsersMessages").getString(
					"remove.an.entry.action.label");

	private ClassPathAdapter cpAdapter = null;
	private JList parent = null;

	public RemoveAnEntryAction(JList parent, ClassPathAdapter adapter) {
		super(REMOVE_AN_ENTRY_LABEL);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('r'));
		putValue(MNEMONIC_KEY, Integer.valueOf(KeyEvent.VK_R));
		putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("UsersMessages")
				.getString("remove.an.entry.action.shortdesc"));
		setParent(parent);
		setClassPathAdapter(adapter);
	}

	protected ClassPathAdapter getClassPathAdapter() {
		return this.cpAdapter;
	}

	protected void setClassPathAdapter(ClassPathAdapter cpAdapter) {
		if (cpAdapter == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.cpAdapter = cpAdapter;
	}

	protected JList getParent() {
		return this.parent;
	}

	protected void setParent(JList parent) {
		if (parent == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {
		final ClassPathAdapter adapter = this.cpAdapter;
		Object[] selectedEntries = getParent().getSelectedValues();
		for (int i = 0; i < selectedEntries.length; i++) {
			adapter.removeEntry(selectedEntries[i].toString());
		}
		//clear selection after element has been removed
		getParent().getSelectionModel().clearSelection();
	}
}
