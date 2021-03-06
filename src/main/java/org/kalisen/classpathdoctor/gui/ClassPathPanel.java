package org.kalisen.classpathdoctor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.adapter.ClassPathAdapter;
import org.kalisen.classpathdoctor.adapter.DefaultClassPathAdapter;
import org.kalisen.classpathdoctor.gui.actions.AddAnEntryAction;
import org.kalisen.classpathdoctor.gui.actions.MoveDownAction;
import org.kalisen.classpathdoctor.gui.actions.MoveUpAction;
import org.kalisen.classpathdoctor.gui.actions.RemoveAnEntryAction;
import org.kalisen.common.DefaultErrorHandler;
import org.kalisen.common.ErrorHandler;

@SuppressWarnings("serial")
public class ClassPathPanel extends JPanel {

	private ErrorHandler errorHandler = null;

	private ClassPathList pathsList = null;
	private JTextArea classpathTextArea = null;
	private JButton addButton = null;
	private JButton removeButton = null;
	private JButton moveUpButton = null;
	private JButton moveDownButton = null;

	private ClassPathListModel classpathListModel = null;
	private ClassPathAdapter adapter = null;

	private final DocumentListener textAreaListener = new DocumentListener() {
		public void changedUpdate(DocumentEvent e) {
			notifyAdapter(e);
		}

		public void insertUpdate(DocumentEvent e) {
			notifyAdapter(e);
		}

		public void removeUpdate(DocumentEvent e) {
			notifyAdapter(e);
		}

		private void notifyAdapter(final DocumentEvent e) {
			try {
				getAdapter()
						.setClassPathAsText(
								e.getDocument().getText(0,
										e.getDocument().getLength()));
			} catch (BadLocationException ble) {
				handleError(ble);
			}
		}
	};

	private final Observer adapterListenerForList = new Observer() {
		public void update(Observable o, final Object arg) {
			if (arg instanceof ClassPath) {
				ClassPathPanel.this.classpathListModel
						.setClassPath(getAdapter().getClassPath());
				ClassPathPanel.this.pathsList.repaint();
			}
		}
	};

	private final Observer adapterListenerForButtons = new Observer() {
		public void update(Observable o, final Object arg) {
			if (arg instanceof ClassPath) {
				ClassPath cp = getAdapter().getClassPath();
				boolean shouldBeEnabled = cp.getEntries().size() > 0
						&& ClassPathPanel.this.pathsList
								.getSelectedIndices().length > 0;
				if (ClassPathPanel.this.removeButton.isEnabled() != shouldBeEnabled) {
					ClassPathPanel.this.removeButton
							.setEnabled(shouldBeEnabled);
				}
				if (ClassPathPanel.this.moveUpButton.isEnabled() != shouldBeEnabled) {
					ClassPathPanel.this.moveUpButton
							.setEnabled(shouldBeEnabled);
				}
				if (ClassPathPanel.this.moveDownButton.isEnabled() != shouldBeEnabled) {
					ClassPathPanel.this.moveDownButton
							.setEnabled(shouldBeEnabled);
				}
			}
		}
	};

	private final Observer adapterListenerForTextArea = new Observer() {
		public void update(Observable o, final Object arg) {
			if (!ClassPathPanel.this.classpathTextArea.getText().equals(
					getAdapter().getClassPathAsText())) {
				ClassPathPanel.this.classpathTextArea.setText(getAdapter()
						.getClassPathAsText());
			}
		}
	};

	private final ListSelectionListener listListenerForButtons = new ListSelectionListener() {

		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				boolean moveUpShouldBeEnabled = e.getFirstIndex() > 0;
				if (ClassPathPanel.this.moveUpButton.isEnabled() != moveUpShouldBeEnabled) {
					ClassPathPanel.this.moveUpButton
							.setEnabled(moveUpShouldBeEnabled);
				}
				boolean moveDownShouldBeEnabled = e.getLastIndex() < ClassPathPanel.this.classpathListModel
						.getSize() - 1;
				if (ClassPathPanel.this.moveDownButton.isEnabled() != moveDownShouldBeEnabled) {
					ClassPathPanel.this.moveDownButton
							.setEnabled(moveDownShouldBeEnabled);
				}
				boolean removeShouldBeEnabled = ClassPathPanel.this.pathsList
						.getSelectedIndices().length > 0;
				if (ClassPathPanel.this.removeButton.isEnabled() != removeShouldBeEnabled) {
					ClassPathPanel.this.removeButton
							.setEnabled(removeShouldBeEnabled);
				}
			}
		}
	};

	private final ListDataListener listDataListenerForAdapter = new ListDataListener() {

		public void intervalRemoved(ListDataEvent e) {
			contentsChanged(e);
		}

		public void intervalAdded(ListDataEvent e) {
			contentsChanged(e);
		}

		public void contentsChanged(ListDataEvent e) {
			getAdapter().setClassPath(
					ClassPathPanel.this.classpathListModel.getClassPath());
		}
	};

	public ClassPathPanel() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.pathsList = buildListComponent();
		this.classpathTextArea = buildTextComponent();
		JPanel buttonPanel = buildButtonPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(.8);
		splitPane.setResizeWeight(.8);
		JScrollPane listScrollPane = new JScrollPane(this.pathsList);
		splitPane.add(listScrollPane, JSplitPane.TOP);
		JScrollPane textScrollPane = new JScrollPane(this.classpathTextArea);
		splitPane.add(textScrollPane, JSplitPane.BOTTOM);
		add(splitPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
		initListeners();
	}

	private void initListeners() {
		this.classpathTextArea.getDocument().addDocumentListener(
				this.textAreaListener);
		getAdapter().addListener(this.adapterListenerForList);
		getAdapter().addListener(this.adapterListenerForTextArea);
		getAdapter().addListener(this.adapterListenerForButtons);
		this.pathsList
				.addListSelectionListener(this.listListenerForButtons);
		this.classpathListModel
				.addListDataListener(this.listDataListenerForAdapter);
	}

	private JPanel buildButtonPanel() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		result.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		result.add(Box.createVerticalGlue());

		this.moveUpButton = new JButton(new MoveUpAction(this.pathsList,
				this.classpathListModel));
		this.moveUpButton.setName("MOVE_UP");
		this.moveUpButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				this.moveUpButton.getSize().height));
		this.moveUpButton.setEnabled(false);
		result.add(this.moveUpButton);

		this.moveDownButton = new JButton(new MoveDownAction(
				this.pathsList, this.classpathListModel));
		this.moveDownButton.setName("MOVE_DOWN");
		this.moveDownButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				this.moveDownButton.getSize().height));
		this.moveDownButton.setEnabled(false);
		result.add(this.moveDownButton);

		result.add(Box.createVerticalStrut(5));

		this.addButton = new JButton(new AddAnEntryAction(this, getAdapter()));
		this.addButton.setName("ADD_ENTRY");
		this.addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				this.addButton.getSize().height));
		result.add(this.addButton);

		this.removeButton = new JButton(new RemoveAnEntryAction(
				this.pathsList, getAdapter()));
		this.removeButton.setName("REMOVE_ENTRY");
		this.removeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				this.removeButton.getSize().height));
		this.removeButton.setEnabled(false);
		result.add(this.removeButton);

		result.add(Box.createVerticalGlue());

		return result;
	}

	private JTextArea buildTextComponent() {
		JTextArea result = new JTextArea();
		PlainDocument doc = new PlainDocument();
		doc.setDocumentFilter(new DocumentFilter() {

			@Override
			public void insertString(FilterBypass fb, int offset,
					String string, AttributeSet attr)
					throws BadLocationException {
				if ("\n".equals(string)) {
					return; // don't insert any new lines
				}
				super.insertString(fb, offset, string, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length,
					String text, AttributeSet attrs)
					throws BadLocationException {
				if (length > 0) {
					fb.remove(offset, length);
				}
				insertString(fb, offset, text, null);
			}
		});
		result.setDocument(doc);
		result.setWrapStyleWord(true);
		result.setLineWrap(true);
		return result;
	}

	private ClassPathList buildListComponent() {
		this.classpathListModel = new ClassPathListModel();
		return new ClassPathList(this.classpathListModel);
	}

	public ClassPathAdapter getAdapter() {
		if (this.adapter == null) {
			this.adapter = new DefaultClassPathAdapter();
		}
		return this.adapter;
	}

	public void setAdapter(ClassPathAdapter adapter) {
		if (adapter == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.adapter = adapter;
	}

	public void handleError(Throwable t) {
		this.errorHandler.handleError(t);
	}

	public ErrorHandler getErrorHandler() {
		if (this.errorHandler == null) {
			this.errorHandler = new DefaultErrorHandler();
		}
		return this.errorHandler;
	}

	public void setErrorHandler(ErrorHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.errorHandler = handler;
	}

}
