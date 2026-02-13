package com.lafabricadeandroides.filesorganizer.gui;

import javax.swing.JOptionPane;

public class GUIUtil {
	public static void showErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(null,
			    errorMessage,
			    "LFDA Files Organizer",
			    JOptionPane.ERROR_MESSAGE);
	}
}
