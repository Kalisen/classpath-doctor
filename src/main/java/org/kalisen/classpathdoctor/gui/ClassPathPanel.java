package org.kalisen.classpathdoctor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.PathEntry;
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

	private JList classpathList = null;
	private JTextArea classpathTextArea = null;
	private JButton addButton = null;
	private JButton removeButton = null;
	private JButton moveUpButton = null;
	private JButton moveDownButton = null;

	private DefaultListModel classpathListModel = null;
	private ClassPathAdapter adapter = null;

	private DocumentListener textAreaListener = new DocumentListener() {
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

	private Observer adapterListenerForList = new Observer() {
		public void update(Observable o, final Object arg) {
			if (arg instanceof ClassPath) {
				ClassPath cp = (ClassPath) arg;
				DefaultListModel model = ClassPathPanel.this.classpathListModel;
				model.clear();
				for (PathEntry entry : cp.getEntries()) {
					model.addElement(entry);
				}
				repaint();
			}
		}
	};

	private Observer adapterListenerForTextArea = new Observer() {
		public void update(Observable o, final Object arg) {
			if (!ClassPathPanel.this.classpathTextArea.getText().equals(
					getAdapter().getClassPathAsText())) {
				ClassPathPanel.this.classpathTextArea.setText(getAdapter()
						.getClassPathAsText());
			}
		}
	};

	public ClassPathPanel() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.classpathList = buildListComponent();
		this.classpathTextArea = buildTextComponent();
		JPanel buttonPanel = buildButtonPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(.8);
		splitPane.setResizeWeight(.8);
		JScrollPane listScrollPane = new JScrollPane(this.classpathList);
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
	}

	private JPanel buildButtonPanel() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		result.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		result.add(Box.createVerticalGlue());
		this.moveUpButton = new JButton(new MoveUpAction(this.classpathList, getAdapter()));
		this.moveUpButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				this.moveUpButton.getSize().height));
		result.add(this.moveUpButton);
		this.moveDownButton = new JButton(new MoveDownAction(this.classpathList, getAdapter()));
		this.moveDownButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				this.moveDownButton.getSize().height));
		result.add(this.moveDownButton);
		result.add(Box.createVerticalStrut(5));
		this.addButton = new JButton(new AddAnEntryAction(this, getAdapter()));
		this.addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				this.addButton.getSize().height));
		result.add(this.addButton);
		this.removeButton = new JButton(new RemoveAnEntryAction(
				this.classpathList, getAdapter()));
		this.removeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,
				this.removeButton.getSize().height));
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

	private JList buildListComponent() {
		this.classpathListModel = new DefaultListModel();
		JList result = new JList(this.classpathListModel);
		return result;
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
