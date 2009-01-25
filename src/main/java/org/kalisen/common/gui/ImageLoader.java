package org.kalisen.common.gui;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageLoader {

	private static final String MISSING_RESOURCE_FOR_GIVEN_PATH = ResourceBundle
			.getBundle("UsersMessages").getString(
					"missing.resource.for.given.path.pattern");

	private static ImageLoader instance = null;
	
	private ImageLoader() {
		//singleton pattern
	}
	
	public static synchronized ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}
	
	public Icon getIcon(String path) {
		Icon result = null;
		URL imageLocation = ImageLoader.class.getClassLoader().getResource(path);
		if (imageLocation == null) {
			throw new RuntimeException(MessageFormat.format(MISSING_RESOURCE_FOR_GIVEN_PATH, path));
		}
		result = new ImageIcon(imageLocation);
		return result;
	}
}
