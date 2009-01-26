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

public class MoveDownAction extends AbstractAction {

	private static final String MOVE_DOWN_ICON_PATH = "images/Down24.gif";
	private static final Icon MOVE_DOWN_ICON = ImageLoader.getInstance()
			.getIcon(MOVE_DOWN_ICON_PATH);

	private JList parent = null;
	private ClassPathListModel parentModel = null;

	public MoveDownAction(JList parent, ClassPathListModel parentModel) {
		super();
		putValue(SMALL_ICON, MOVE_DOWN_ICON);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('d'));
		putValue(MNEMONIC_KEY, Integer.valueOf(KeyEvent.VK_D));
		putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("UsersMessages")
				.getString("move.down.entry.action.shortdesc"));
		
		if (parentModel == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.parentModel = parentModel;

		if (parent == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {
		int[] selectedIndices = this.parent.getSelectedIndices();
		if (selectedIndices.length > 0
				&& selectedIndices[selectedIndices.length - 1] < this.parent
						.getModel().getSize() - 1) {
			updateListData(selectedIndices);
			updateSelection(selectedIndices);
		}
	}

	private void updateSelection(int[] selectedIndices) {
		// restore selection
		int[] newIndices = new int[selectedIndices.length];
		for (int i = 0; i < newIndices.length; i++) {
			newIndices[i] = selectedIndices[i] + 1;
		}
		this.parent.setSelectedIndices(newIndices);
	}

	private void updateListData(int[] selectedIndices) {
		int listSize = this.parentModel.getSize();
		PathEntry[] newListData = new PathEntry[listSize];
		int j = selectedIndices.length - 1;
		for (int i = listSize - 1; i > -1; i--) {
			// if i is the destination index for the next selected index
			if (j > -1 && i == selectedIndices[j] + 1) {
				newListData[i] = (PathEntry)this.parentModel.getElementAt(i - 1);
				// calculate destination index of the replaced object
				int k = 0;
				for (k = i - 1; k > -1; k--) {
					if (j > 0 && k == selectedIndices[j - 1] + 1) {
						// process contiguous group of selected indices
						newListData[k] = (PathEntry)this.parentModel.getElementAt(k - 1);
						j--;
					} else {
						// set replaced object to new position
						newListData[k] = (PathEntry)this.parentModel.getElementAt(i);
						break;
					}
				}
				j--;
				i = k;
			} else {
				// no position change
				newListData[i] = (PathEntry)this.parentModel.getElementAt(i);
			}
		}
		this.parentModel.setClassPath(new ClassPath(Arrays.asList(newListData)));
	}
}
