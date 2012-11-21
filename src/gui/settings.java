package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import base.DataHandler;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class settings extends JFrame {

	private JPanel contentPane;
	private JTextField passField;
	private JTextField userField;
	private JTextField serverField;
	private JTextField pathField;


	public settings(final DataHandler dh) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 223, 316);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDone = new JButton("Cancel");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDone.setForeground(Color.BLACK);
		btnDone.setBackground(Color.ORANGE);
		btnDone.setBounds(109, 229, 89, 23);
		contentPane.add(btnDone);

		serverField = new JTextField();
		serverField.setBounds(10, 47, 188, 20);
		contentPane.add(serverField);
		serverField.setColumns(10);
		serverField.setText(dh.data[0]);

		pathField = new JTextField();
		pathField.setBounds(10, 94, 188, 20);
		contentPane.add(pathField);
		pathField.setColumns(10);
		pathField.setText(dh.data[1]);
		
		userField = new JTextField();
		userField.setBounds(10, 141, 188, 20);
		contentPane.add(userField);
		userField.setColumns(10);
		userField.setText(dh.data[2]);
		
		passField = new JTextField();
		passField.setBounds(10, 189, 188, 20);
		contentPane.add(passField);
		passField.setColumns(10);
		passField.setText(dh.data[3]);
		
		
		JLabel lblRestPath = new JLabel("REST Path");
		lblRestPath.setForeground(Color.WHITE);
		lblRestPath.setBounds(10, 78, 218, 14);
		contentPane.add(lblRestPath);
		
		
		JLabel lblRestServer = new JLabel("REST Server");
		lblRestServer.setForeground(Color.WHITE);
		lblRestServer.setBounds(10, 31, 218, 14);
		contentPane.add(lblRestServer);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(10, 125, 218, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(10, 172, 188, 14);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dh.data[0] = serverField.getText();
				dh.data[1] = pathField.getText();
				dh.data[2] = userField.getText();
				dh.data[3] = passField.getText();
				dh.writeSettings();
				dispose();
			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setBounds(10, 229, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setForeground(Color.WHITE);
		lblSettings.setBounds(10, 6, 89, 14);
		contentPane.add(lblSettings);
		if(MainWindow.settingsExist) {
			
		}
	}
}
