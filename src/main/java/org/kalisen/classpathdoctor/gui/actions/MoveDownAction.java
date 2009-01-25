package org.kalisen.classpathdoctor.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.KeyStroke;

import org.kalisen.classpathdoctor.adapter.ClassPathAdapter;
import org.kalisen.common.gui.ImageLoader;

public class MoveDownAction extends AbstractAction {

	private static final String MOVE_DOWN_ICON_PATH = "images/Down24.gif";
	private static final Icon MOVE_DOWN_ICON = ImageLoader.getInstance().getIcon(MOVE_DOWN_ICON_PATH);
	
	private ClassPathAdapter cpAdapter = null;
	private JList parent = null;

	public MoveDownAction(JList parent, ClassPathAdapter adapter) {
		super();
		putValue(SMALL_ICON, MOVE_DOWN_ICON);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('d'));
		putValue(MNEMONIC_KEY, Integer.valueOf(KeyEvent.VK_D));
		putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("UsersMessages")
				.getString("move.down.entry.action.shortdesc"));
		setParent(parent);
		setClassPathAdapter(adapter);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

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

}
