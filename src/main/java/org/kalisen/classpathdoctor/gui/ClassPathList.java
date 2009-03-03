package org.kalisen.classpathdoctor.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;

import org.kalisen.classpathdoctor.EmptyPathEntry;
import org.kalisen.classpathdoctor.PathEntry;
import org.kalisen.common.DefaultErrorHandler;
import org.kalisen.common.ErrorHandler;

public class ClassPathList extends JList {

	private ErrorHandler errorHandler = null;

	private final DragGestureListener listDragGestureListener = new DragGestureListener() {

		public void dragGestureRecognized(DragGestureEvent dge) {
			JList list = (JList) dge.getComponent();
			Object[] selection = list.getSelectedValues();
			if (selection.length < 2) {
				PathEntry[] selectedEntries = new PathEntry[selection.length];
				System.arraycopy(selection, 0, selectedEntries, 0,
						selection.length);
				PathEntriesTransferable transferable = new PathEntriesTransferable(
						selectedEntries);
				dge.startDrag(DragSource.DefaultMoveDrop, transferable);
			} else {
				getErrorHandler().handleError(
						"Only one entry can be moved at a time");
			}
		}
	};

	private final DragSourceListener listDragSourceListener = new DragSourceAdapter() {

		public void dragDropEnd(DragSourceDropEvent dsde) {
			if (dsde.getDropSuccess()) {
				JList list = (JList) dsde.getDragSourceContext().getComponent();
				int[] selectedIndices = list.getSelectedIndices();
				((ClassPathListModel) list.getModel())
						.removeElementAt(selectedIndices[0]);
				list.setSelectedIndex(ClassPathList.this.listDropTargetListener
						.getFirstDropIndex());
				list.repaint();
			}
		}
	};

	private final ClassPathListDropTargetListener listDropTargetListener = new ClassPathListDropTargetListener();

	/**
	 * Constructs a <code>ClassPathList</code> that displays the elements in the
	 * specified, non-<code>null</code> model. All <code>ClassPathList</code>
	 * constructors delegate to this one.
	 * 
	 * @param dataModel
	 *            the data model for this list
	 * @exception IllegalArgumentException
	 *                if <code>dataModel</code> is <code>null</code>
	 */
	public ClassPathList(ClassPathListModel dataModel) {
		super(dataModel);
		setCellRenderer(new ClassPathListCellRenderer());

		// set Drag enable to false as it is related to swing DnD support
		setDragEnabled(false);
		System.setProperty("awt.dnd.drag.threshold", "1");
		DragSource dSource = new DragSource();
		dSource.addDragSourceListener(this.listDragSourceListener);
		dSource.createDefaultDragGestureRecognizer(this,
				DnDConstants.ACTION_MOVE, this.listDragGestureListener);
		DropTarget dt = new DropTarget(this, this.listDropTargetListener);
		setDropTarget(dt);
	}

	/**
	 * Constructs a <code>ClassPathList</code> that displays the elements in the
	 * specified array. This constructor just delegates to the
	 * <code>ListModel</code> constructor.
	 * 
	 * @param listData
	 *            the array of Objects to be loaded into the data model
	 */
	public ClassPathList(final PathEntry[] listData) {
		this(new ClassPathListModel() {
			public int getSize() {
				return listData.length;
			}

			public PathEntry getElementAt(int i) {
				return listData[i];
			}
		});
	}

	/**
	 * Constructs a <code>ClassPathList</code> that displays the elements in the
	 * specified <code>Vector</code>. This constructor just delegates to the
	 * <code>ListModel</code> constructor.
	 * 
	 * @param listData
	 *            the <code>Vector</code> to be loaded into the data model
	 */
	public ClassPathList(final Vector<PathEntry> listData) {
		this(new ClassPathListModel() {
			public int getSize() {
				return listData.size();
			}

			public PathEntry getElementAt(int i) {
				return listData.elementAt(i);
			}
		});
	}

	/**
	 * Constructs a <code>ClassPathList</code> with an empty model.
	 */
	public ClassPathList() {
		this(new ClassPathListModel());
	}

	@Override
	public ClassPathListModel getModel() {
		return (ClassPathListModel) super.getModel();
	}

	@Override
	public void setModel(ListModel model) {
		if (model instanceof ClassPathListModel) {
			super.setModel(model);
		} else {
			throw new IllegalArgumentException("Expected an instance of "
					+ ClassPathListModel.class.getName() + " but was "
					+ (model == null ? null : model.getClass().getName()));
		}
	}

	@Override
	public void setListData(Object[] listData) {
		if (listData instanceof PathEntry[]) {
			super.setListData(listData);
		} else {
			throw new IllegalArgumentException("Expected an instance of "
					+ PathEntry[].class.getName() + " but was "
					+ (listData == null ? null : listData.getClass().getName()));
		}
	}

	public void setListData(final PathEntry[] listData) {
		setModel(new ClassPathListModel() {
			public int getSize() {
				return listData.length;
			}

			public PathEntry getElementAt(int i) {
				return listData[i];
			}
		});
	}

	public void setListData(final Vector<?> listData) {
		for (Object object : listData) {
			if (!(object instanceof PathEntry)) {
				throw new IllegalArgumentException("Expected a Vector of "
						+ PathEntry.class.getName()
						+ " but contained instance of "
						+ (object == null ? null : object.getClass().getName()));
			}
		}
		setModel(new ClassPathListModel() {
			public int getSize() {
				return listData.size();
			}

			public PathEntry getElementAt(int i) {
				return (PathEntry) listData.get(i);
			}
		});
	}

	public void setListData(final List<PathEntry> listData) {
		setModel(new ClassPathListModel() {
			public int getSize() {
				return listData.size();
			}

			public PathEntry getElementAt(int i) {
				return listData.get(i);
			}
		});
	}

	public ErrorHandler getErrorHandler() {
		if (this.errorHandler == null) {
			this.errorHandler = new DefaultErrorHandler();
		}
		return this.errorHandler;
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		if (errorHandler == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.errorHandler = errorHandler;
	}

	private class ClassPathListCellRenderer extends DefaultListCellRenderer {

		private final Color ODD_COLOR = new Color(0xEEEFFF);
		private final Color NONEXIST_COLOR = new Color(0xFF0000);

		public ClassPathListCellRenderer() {
			super();
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			ClassPathList cpList = (ClassPathList) list;
			Component result = super.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
			PathEntry entry = cpList.getModel().getElementAt(index);
			setComponentBackground(entry, index, isSelected, result);
			setComponentText(entry, result);
			return result;
		}

		protected void setComponentText(PathEntry entry, Component toBeModified) {
			if (EmptyPathEntry.INSTANCE.equals(entry)) {
				((JLabel) toBeModified).setText(" ");
			}
		}

		protected void setComponentBackground(PathEntry entry, int index,
				boolean isSelected, Component toBeModified) {
			if (entry.exists() || EmptyPathEntry.INSTANCE.equals(entry)) {
				if (!isSelected && index % 2 == 1
						&& !toBeModified.getBackground().equals(this.ODD_COLOR)) {
					toBeModified.setBackground(this.ODD_COLOR);
				}
			} else if (!isSelected && !entry.exists()) {
				toBeModified.setBackground(this.NONEXIST_COLOR);
			}
		}
	}

	public static class PathEntriesTransferable implements Transferable {
		public static final DataFlavor PATHENTRIES_DATAFLAVOR = new DataFlavor(
				PathEntry[].class, "Path Entries");
		private static final DataFlavor[] FLAVORS = { PATHENTRIES_DATAFLAVOR };

		private PathEntry[] entries = null;

		public PathEntriesTransferable(PathEntry[] entries) {
			if (entries == null) {
				throw new IllegalArgumentException(
						"null is not a valid argument");
			}
			this.entries = new PathEntry[entries.length];
			System.arraycopy(entries, 0, this.entries, 0, entries.length);
		}

		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (!isDataFlavorSupported(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			}
			return this.entries;

		}

		public DataFlavor[] getTransferDataFlavors() {
			return FLAVORS;
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return FLAVORS[0].equals(flavor);
		}

	}

	private class ClassPathListDropTargetListener extends DropTargetAdapter {

		private int firstDropIndex = -1;

		public void drop(DropTargetDropEvent dtde) {
			boolean acceptDrop = dtde.isLocalTransfer()
					&& dtde
							.isDataFlavorSupported(PathEntriesTransferable.PATHENTRIES_DATAFLAVOR)
					&& dtde.getDropAction() == DnDConstants.ACTION_MOVE;
			if (acceptDrop) {
				dtde.acceptDrop(DnDConstants.ACTION_MOVE);
				dtde.dropComplete(doDrop(dtde));
			} else {
				dtde.rejectDrop();
			}
		}

		public boolean doDrop(DropTargetDropEvent dtde) {
			boolean dropSucceeded = true;
			Transferable transferable = dtde.getTransferable();
			PathEntry[] droppedEntries;
			try {
				droppedEntries = (PathEntry[]) transferable
						.getTransferData(PathEntriesTransferable.PATHENTRIES_DATAFLAVOR);
				Point dropLocation = dtde.getLocation();
				JList list = (JList) dtde.getDropTargetContext().getComponent();
				this.firstDropIndex = list.locationToIndex(dropLocation);
				if (list.getSelectedIndex() < this.firstDropIndex) {
					((ClassPathListModel) list.getModel()).insertElementAt(
							this.firstDropIndex + 1, droppedEntries[0]);
				} else {
					((ClassPathListModel) list.getModel()).insertElementAt(
							this.firstDropIndex, droppedEntries[0]);
				}
			} catch (Exception e) {
				dropSucceeded = false;
			}
			return dropSucceeded;
		}

		public int getFirstDropIndex() {
			return this.firstDropIndex;
		}

	}
}
