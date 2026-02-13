package com.lafabricadeandroides.filesorganizer;

import java.awt.EventQueue;

import com.lafabricadeandroides.filesorganizer.gui.MainFrame;

public class App {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
