package org.kalisen.classpathdoctor.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import org.kalisen.classpathdoctor.adapter.ClassPathAdapter;

@SuppressWarnings("serial")
public class AddAnEntryAction extends AbstractAction {

	private ClassPathAdapter cpAdapter = null;
	private JPanel parent = null;

	private static final String ADD_AN_ENTRY_LABEL = ResourceBundle.getBundle(
			"UsersMessages").getString("add.an.entry.action.label");

	public AddAnEntryAction(JPanel parent, ClassPathAdapter cpAdapter) {
		super(ADD_AN_ENTRY_LABEL);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('a'));
		putValue(MNEMONIC_KEY, Integer.valueOf(KeyEvent.VK_A));
		putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("UsersMessages")
				.getString("add.an.entry.action.shortdesc"));
		setParent(parent);
		setClassPathAdapter(cpAdapter);
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

	protected JPanel getParent() {
		return this.parent;
	}

	protected void setParent(JPanel parent) {
		if (parent == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new JavaLibraryFileFilter());
		fileChooser.setName("Add An Entry");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.showOpenDialog(getParent());
		final File[] files = fileChooser.getSelectedFiles();
		final ClassPathAdapter adapter = this.cpAdapter;
		if (files.length > 0) {
			for (File file : files) {
				adapter.addEntry(file.getPath());
			}
		}
	}

	public static class JavaLibraryFileFilter extends FileFilter {
		@Override
		public String getDescription() {
			return "Java libraries";
		}

		@Override
		public boolean accept(File f) {
			return f != null
					&& (f.isDirectory() || f.getName().endsWith(".jar") || f
							.getName().endsWith(".zip"));
		}
	}
}
