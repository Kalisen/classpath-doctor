package org.kalisen.classpathdoctor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ClassPathDoctorGUI extends JFrame {
	
	public ClassPathDoctorGUI() {	
		super("ClassPath Doctor");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width / 3;
		int height = 3 * screenSize.height / 4;
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new ClassPathPanel(), BorderLayout.CENTER);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ClassPathDoctorGUI();
	}
}
