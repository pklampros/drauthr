package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import net.Uploader;
import base.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JLabel lblDescription;
	private String filename;
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	final JList<String> listTest = new JList<String>(listModel);
	public static String rServer;
	public static String rPath;
	public static String rUser;
	public static String rPass;
	public static boolean settingsExist;
	public DataHandler dh;
	public MainWindow self;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					// frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		self = this;
		dh = new DataHandler();
		dh.readSettings();
		//checkSettings();
		setTitle("Drupal Author");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 394);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBorder(new LineBorder(Color.WHITE, 3));
		textArea.setBounds(130, 84, 216, 246);
		contentPane.add(textArea);

		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(45, 50, 76, 22);
		contentPane.add(lblTitle);

		lblDescription = new JLabel("Description");
		lblDescription.setVerticalAlignment(SwingConstants.TOP);
		lblDescription.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescription.setForeground(Color.WHITE);
		lblDescription.setBounds(12, 83, 109, 47);
		contentPane.add(lblDescription);

		final JTextArea titleArea = new JTextArea();
		titleArea.setBorder(new LineBorder(Color.WHITE, 3));
		titleArea.setBounds(130, 50, 216, 22);
		contentPane.add(titleArea);

		JButton btnUpload = new JButton("Upload");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Uploader up = new Uploader(dh.data);
				if(listModel.size()>0) {
					int [] fids = new int[listModel.size()];
					for(int i = 0;i < listModel.size(); i++) {
						fids[i] = up.fileUpload(listModel.get(i));
					}
//					up.generateImageNode();
				} else {
//					up.generateSimpleNode();
				}
			}
		});
		btnUpload.setForeground(Color.BLACK);
		btnUpload.setBackground(Color.LIGHT_GRAY);
		btnUpload.setBounds(459, 319, 89, 23);
		contentPane.add(btnUpload);

		JButton btnReset = new JButton("Reset");
		btnReset.setForeground(Color.BLACK);
		btnReset.setBackground(Color.LIGHT_GRAY);
		btnReset.setBounds(358, 319, 89, 23);
		contentPane.add(btnReset);

		listTest.setBounds(358, 50, 216, 123);
		contentPane.add(listTest);

		JButton btnAddImage = new JButton("Add Image");
		btnAddImage.setForeground(Color.BLACK);
		btnAddImage.setBackground(Color.LIGHT_GRAY);
		btnAddImage.addActionListener(new OpenL()

		);
		btnAddImage.setBounds(358, 290, 89, 23);
		contentPane.add(btnAddImage);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setForeground(Color.BLACK);
		btnRemove.setBackground(Color.LIGHT_GRAY);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println("loller re " + titleArea.getText());
				params.println("loller re " + filename);
			}
		});
		btnRemove.setBounds(459, 290, 89, 23);
		contentPane.add(btnRemove);

		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							settings frame = new settings(dh);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnSettings.setBounds(555, 317, 98, 26);
		contentPane.add(btnSettings);

		JLabel lblDrupalAuthor = new JLabel("Drupal Author");
		lblDrupalAuthor.setVerticalAlignment(SwingConstants.TOP);
		lblDrupalAuthor.setForeground(Color.WHITE);
		lblDrupalAuthor.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		lblDrupalAuthor.setBounds(12, 9, 419, 29);
		contentPane.add(lblDrupalAuthor);

		JLabel lblImages = new JLabel("Images");
		lblImages.setForeground(Color.WHITE);
		lblImages.setHorizontalAlignment(SwingConstants.LEFT);
		lblImages.setVerticalTextPosition(SwingConstants.TOP);
		lblImages.setVerticalAlignment(SwingConstants.TOP);
		lblImages.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblImages.setBounds(585, 51, 88, 47);
		contentPane.add(lblImages);

	}


	class OpenL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(MainWindow.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				listModel.addElement(c.getCurrentDirectory().toString() + "  "
						+ c.getSelectedFile().getName());
			}
		}
	}
}
