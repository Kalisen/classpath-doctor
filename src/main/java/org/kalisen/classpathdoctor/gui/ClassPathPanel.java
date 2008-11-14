package org.kalisen.classpathdoctor.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ClassPathPanel extends JPanel {
    private JList classpathList = null;
    private JTextArea classpathTextField = null;
    private JPanel buttonPanel = null;
    
    public ClassPathPanel() {
        init();
    }

    private void init() {
    	setLayout(new BorderLayout());
        this.classpathList = buildListComponent();
        this.classpathTextField = buildTextComponent();
        this.buttonPanel = buildButtonPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(.8);
        splitPane.setResizeWeight(.8);
        splitPane.add(this.classpathList, JSplitPane.TOP);
        splitPane.add(this.classpathTextField, JSplitPane.BOTTOM);
        add(splitPane, BorderLayout.CENTER);
        add(this.buttonPanel, BorderLayout.EAST);
    }
    
	private JPanel buildButtonPanel() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		result.add(Box.createVerticalGlue());
		JButton addButton = new JButton(new AddEntryAction());
		result.add(addButton);
		JButton removeButton = new JButton(new RemoveEntryAction());
		result.add(removeButton);
		result.add(Box.createVerticalGlue());
		return result;
	}

	private JTextArea buildTextComponent() {
		JTextArea result = new JTextArea();
		
		return result;
	}

	private JList buildListComponent() {
		JList result = new JList();
		return result;
	}
	
	class AddEntryAction extends AbstractAction {

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	class RemoveEntryAction extends AbstractAction {

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
