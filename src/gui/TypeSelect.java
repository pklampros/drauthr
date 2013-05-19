package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import base.DataHandler;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class TypeSelect extends JFrame {

	private JPanel contentPane;
	private JButton[] typeButtons;

	// public TypeSelect(final DataHandler dh) {
	// public TypeSelect(final Map typeMap) {
	// public TypeSelect(final String [] types) {

	// public static void main(String[] args) {
	// promptSelection();
	// }
	public static void promptSelection(final Map<String, String> types) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TypeSelect frame = new TypeSelect(types);
					// frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// public TypeSelect() {
	public TypeSelect(final Map<String, String> types) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (types.size() == 0)
			setBounds(100, 100, 100, 100);
		else
			setBounds(100, 100, types.size() * 100, 100);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, types.size(), 10, 1));

		if (types.size() == 0) {
			int n = JOptionPane.showConfirmDialog(contentPane,
					"Would you like green eggs and ham?", "An Inane Question",
					JOptionPane.YES_NO_OPTION);
			System.out.println(n);
			switch (n) {
			case 0:
				break;
			case 1:
				break;
			}

		} else {
			typeButtons = new JButton[types.size()];
			// for(int i =0; i < types.length; i++) {
			int i = 0;
			for (Map.Entry<String, String> entry : types.entrySet()) {
				final String typeName = entry.getValue();
				final String type = entry.getKey();
				typeButtons[i] = new JButton(typeName);
				typeButtons[i].setForeground(Color.WHITE);
				typeButtons[i].setBackground(Color.BLACK);
				typeButtons[i].setBounds(109, 50 * i, 89, 23);
				typeButtons[i].setFocusPainted(false);
				typeButtons[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						DataHandler.getInstance().checkTypeData(type);
						MainWindow.newMainWindow(type, typeName);
					}
				});
				contentPane.add(typeButtons[i]);
				i++;
			}
		}
	}
}
