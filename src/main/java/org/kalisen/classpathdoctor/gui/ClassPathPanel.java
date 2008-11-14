package org.kalisen.classpathdoctor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ClassPathPanel extends JPanel {
    private JPanel listPanel = null;
    private JPanel textPanel = null;
    private JPanel buttonPanel = null;
    
    public ClassPathPanel() {
        init();
    }

    private void init() {
    	setLayout(new BorderLayout());
    	Dimension d = new Dimension(); 
    	setSize(d);
        this.listPanel = buildListPanel();
        this.textPanel = buildTextPanel();
        this.buttonPanel = buildButtonPanel();
    }

	private JPanel buildButtonPanel() {
		JPanel result = new JPanel();
		return result;
	}

	private JPanel buildTextPanel() {
		JPanel result = new JPanel();
		return result;
	}

	private JPanel buildListPanel() {
		JPanel result = new JPanel();
		return result;
	}
}
