package com.lafabricadeandroides.filesorganizer.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lafabricadeandroides.filesorganizer.domain.FileOrganizer;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfSourceFolder;
	private JButton btnOrganize;
	private JButton btnTargetFolder;
	private JTextField tfTargetFolder;
	private JTextField tfFoldersPrefix;
	private JLabel lblProgress;
	
	private int totalFiles;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Files Organizer - By Fernando Paniagua (2020)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 868, 596);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSourceFolder = new JButton("Source folder");
		btnSourceFolder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSourceFolder.setBounds(25, 49, 129, 33);
		contentPane.add(btnSourceFolder);

		tfSourceFolder = new JTextField();
		tfSourceFolder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfSourceFolder.setBounds(166, 49, 657, 33);
		contentPane.add(tfSourceFolder);
		tfSourceFolder.setColumns(10);

		btnOrganize = new JButton("Organize");

		btnTargetFolder = new JButton("Target folder");
		btnTargetFolder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnTargetFolder.setBounds(25, 113, 129, 33);
		contentPane.add(btnTargetFolder);

		tfTargetFolder = new JTextField();
		tfTargetFolder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfTargetFolder.setColumns(10);
		tfTargetFolder.setBounds(166, 113, 657, 33);
		contentPane.add(tfTargetFolder);
		btnOrganize.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnOrganize.setBounds(398, 403, 111, 107);
		contentPane.add(btnOrganize);

		tfFoldersPrefix = new JTextField();
		tfFoldersPrefix.setText("target");
		tfFoldersPrefix.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfFoldersPrefix.setColumns(10);
		tfFoldersPrefix.setBounds(166, 180, 196, 33);
		contentPane.add(tfFoldersPrefix);

		JLabel lblFoldersPrefix = new JLabel("Folders prefix");
		lblFoldersPrefix.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFoldersPrefix.setBounds(25, 180, 94, 33);
		contentPane.add(lblFoldersPrefix);

		JLabel lblFilesPerFolder = new JLabel("Files per folder");
		lblFilesPerFolder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFilesPerFolder.setBounds(25, 235, 129, 33);
		contentPane.add(lblFilesPerFolder);

		JSpinner spFilesPerFolder = new JSpinner();
		spFilesPerFolder.setModel(new SpinnerNumberModel(new Integer(100), new Integer(1), null, new Integer(1)));
		spFilesPerFolder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		spFilesPerFolder.setBounds(166, 234, 59, 33);
		contentPane.add(spFilesPerFolder);
		
		lblProgress = new JLabel("0/0");
		lblProgress.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblProgress.setBounds(398, 359, 111, 33);
		contentPane.add(lblProgress);

		btnSourceFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("Select source folder");
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					tfSourceFolder.setText(file.getAbsolutePath());
					totalFiles = file.list().length;
					lblProgress.setText("0/" + totalFiles);
				}
			}
		});
		
		
		btnTargetFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("Select target folder");
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					tfTargetFolder.setText(file.getAbsolutePath());
				}
			}
		});
		
		btnOrganize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tfSourceFolder.getText().trim().isEmpty() || tfTargetFolder.getText().trim().isEmpty()) {
					GUIUtil.showErrorMessage("Select source and target folder");
				} else if (tfSourceFolder.getText().trim().equals(tfTargetFolder.getText().trim())) {
					GUIUtil.showErrorMessage("Source and target folder must be different");
				} else {
					try {
						contentPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						FileOrganizer.organize(tfSourceFolder.getText(), tfTargetFolder.getText(),
								tfFoldersPrefix.getText(), (Integer) spFilesPerFolder.getValue());
						lblProgress.setText(FileOrganizer.getFilesCopied() + "/" + totalFiles);
					} catch (IOException e) {
						GUIUtil.showErrorMessage("IO Error");
						e.printStackTrace();
					} finally {
						contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}
		});
	}
}
