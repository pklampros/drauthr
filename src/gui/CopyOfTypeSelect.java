package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import base.DataHandler;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class CopyOfTypeSelect extends JFrame {

	private JPanel contentPane;
	private JButton [] typeButtons;
	// public TypeSelect(final DataHandler dh) {
	// public TypeSelect(final Map typeMap) {
	// public TypeSelect(String [] types) {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CopyOfTypeSelect frame = new CopyOfTypeSelect();
					// frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public CopyOfTypeSelect() {
		String [] types = {"page","story","another","sidodfg"};
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, types.length*100, 100);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, types.length, 10, 1));
		
		JLabel lblNoTypesFound = new JLabel("No types found, retry");
		contentPane.add(lblNoTypesFound);
		
		typeButtons = new JButton[types.length];
		for(int i =0; i < types.length; i++) {
			typeButtons[i] = new JButton(types[i]);
			typeButtons[i].setForeground(Color.WHITE);
			typeButtons[i].setBackground(Color.BLACK);
			typeButtons[i].setBounds(109, 50*i, 89, 23);
			contentPane.add(typeButtons[i]);
		}
	}
}
