package org.kalisen.classpathdoctor.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.KeyStroke;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.PathEntry;
import org.kalisen.classpathdoctor.gui.ClassPathListModel;
import org.kalisen.common.gui.ImageLoader;

public class MoveUpAction extends AbstractAction {

	private static final String MOVE_UP_ICON_PATH = "images/Up24.gif";
	private static final Icon MOVE_UP_ICON = ImageLoader.getInstance().getIcon(MOVE_UP_ICON_PATH);

	private JList parent = null;
	private ClassPathListModel parentModel = null;

	public MoveUpAction(JList parent, ClassPathListModel parentModel) {
		super();
		putValue(SMALL_ICON, MOVE_UP_ICON);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('u'));
		putValue(MNEMONIC_KEY, Integer.valueOf(KeyEvent.VK_U));
		putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("UsersMessages")
				.getString("move.up.entry.action.shortdesc"));
		if (parent == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.parent = parent;
		if (parentModel == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.parentModel = parentModel;
	}

	public void actionPerformed(ActionEvent e) {
		int[] selectedIndices = this.parent.getSelectedIndices();
		if (selectedIndices.length > 0 && selectedIndices[0] > 0) {
			updateListData(selectedIndices);
			updateSelection(selectedIndices);
		}
	}

	private void updateSelection(int[] selectedIndices) {
		// restore selection
		int[] newIndices = new int[selectedIndices.length];
		for (int i = 0; i < newIndices.length; i++) {
			newIndices[i] = selectedIndices[i] - 1;
		}
		this.parent.setSelectedIndices(newIndices);
	}

	private void updateListData(int[] selectedIndices) {
		int listSize = this.parentModel.getSize();
		PathEntry[] newListData = new PathEntry[listSize];
		int j = 0;
		for (int i = 0; i < listSize; i++) {
			// if i is the destination index for the next selected index
			if (j < selectedIndices.length && i == selectedIndices[j] - 1) {
				newListData[i] = (PathEntry)this.parentModel.getElementAt(i + 1);
				// calculate destination index of the replaced object
				int k = 0;
				for (k = i + 1; k < listSize; k++) {
					if (j < (selectedIndices.length - 1) 
							&& k == selectedIndices[j + 1] - 1) {
						// process contiguous group of selected indices
						newListData[k] = (PathEntry)this.parentModel.getElementAt(k + 1);
						j++;
					} else {
						// set replaced object to new position 
						newListData[k] = (PathEntry)this.parentModel.getElementAt(i);
						break;
					}
				}
				j++;
				i = k;
			} else {
				// no position change
				newListData[i] = (PathEntry)this.parentModel.getElementAt(i); 
			}
		}
		this.parentModel.setClassPath(new ClassPath(Arrays.asList(newListData)));
	}
}
