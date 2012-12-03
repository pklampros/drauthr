package gui;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.TransferHandler.DropLocation;
import javax.swing.TransferHandler.TransferSupport;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import net.Uploader;
import base.*;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JLabel lblDescription;
	private DrawPanel imageDisplay;
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
	JScrollPane scroller;

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
		// checkSettings();
		setTitle("Drupal Author");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 394);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final DrawPanel imageDisplay = new DrawPanel();
		imageDisplay.setBackground(Color.BLACK);
		imageDisplay.setBorder(null);
		// imageDisplay.setPreferredSize(new Dimension(5000, 5000));
		// imageDisplay.setBounds(379, 184, 98, 41);
		imageDisplay.setDropTarget(new DropTarget() {
			@Override
			public synchronized void drop(DropTargetDropEvent dtde) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				Transferable t = dtde.getTransferable();
				List fileList;
				try {
					fileList = (List) t
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (int i = 0; i < fileList.size(); i++) {
						File f = (File) fileList.get(i);
						System.out.println(f.getAbsolutePath());
						imageDisplay.addImage(f.getAbsolutePath());
					}
					// table.setValueAt(f.length(), row, column+1);
				} catch (UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		// contentPane.add(imageDisplay);

		scroller = new JScrollPane(imageDisplay);
		scroller.setBorder(new LineBorder(Color.WHITE));
		scroller.setBounds(356, 50, 327, 232);
		contentPane.add(scroller);

		final JTextArea descrArea = new JTextArea();
		descrArea.setLineWrap(true);
		descrArea.setBorder(new LineBorder(Color.WHITE, 3));
		descrArea.setBounds(130, 84, 216, 246);
		contentPane.add(descrArea);

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
				// if(formValid) pop form not valid error
				Uploader up = new Uploader(dh.data);
				if (listModel.size() > 0) {
					String[] fids = new String[listModel.size()];
					for (int i = 0; i < listModel.size(); i++) {
						fids[i] = up.fileUpload(listModel.get(i));
					}
					up.uploadNode(new String[] { "story", titleArea.getText(),
							descrArea.getText() }, fids);
					// up.generateImageNode();
				} else {
					up.uploadNode(new String[] { "story", titleArea.getText(),
							descrArea.getText() }, null);
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

		listTest.setBounds(358, 11, 216, 27);
		contentPane.add(listTest);

		JButton btnAddImage = new JButton("Add Image");
		btnAddImage.setForeground(Color.BLACK);
		btnAddImage.setBackground(Color.LIGHT_GRAY);
		btnAddImage.addActionListener(new OpenL(imageDisplay)

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
		lblImages.setBounds(32, 223, 88, 47);
		contentPane.add(lblImages);

	}

//	public void updImg(String s) {
//		imageDisplay.repaint();
//		System.out.println(imageDisplay);
//		imageDisplay.updateImage(s);
//	}

	public class imgThumb {
		private String imgLoc = "";
		private int x, y, sz;
		ImageIcon icon;
		boolean valid = false;

		public imgThumb(String s, int posX, int posY, int saiz) {
			x = posX;
			y = posY;
			sz = saiz;
			imgLoc = s;
			icon = new ImageIcon(imgLoc, "");
		}

		public boolean rebuild(String s) {
			if (!valid) {
				icon = new ImageIcon(s, "");
				valid = true;
				return false;
			} else
				return true;
		}

		public void draw(Graphics g) {
			if (valid)
				g.drawImage(icon.getImage(), x, y, sz, sz, null);
		}
	}

	class DrawPanel extends JPanel {
//		String imageLoc = "";
		int iconSize = 80;
		int borderSize = 10;
		int rowSize = 3;
		imgThumb[] iconz = new imgThumb[25];

		public DrawPanel() {
			for (int i = 0; i < iconz.length; i++) {
				iconz[i] = new imgThumb(
						"", borderSize
								+ (borderSize + iconSize) * (i % rowSize),
						borderSize + (borderSize + iconSize) * (i / rowSize),
						iconSize);
			}
			setBorder(BorderFactory.createLineBorder(Color.black));
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					repaint(0, 0, 100, 100);
					// moveSquare(e.getX(),e.getY());
				}
			});

			addMouseMotionListener(new MouseAdapter() {
				public void mouseDragged(MouseEvent e) {
					repaint(0, 0, 100, 100);
					// moveSquare(e.getX(),e.getY());
				}
			});
		}

		public Dimension getPreferredSize() {
			return new Dimension(
					rowSize * (borderSize + iconSize) + borderSize,
					(iconz.length / rowSize) * (borderSize + iconSize)
							+ borderSize);
		}

		public void addImage(String s) {
//			imageLoc = s;
//			System.out.println(imageLoc);
			for (int i = 0; i < iconz.length; i++) {
				if (!iconz[i].rebuild(s)) {
					repaint(iconz[i].x, iconz[i].y, iconSize, iconSize);
					break;
				}
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// ImageIcon icon = new ImageIcon(imageLoc,"");

			// Draw Text
			//g.drawString("This is my custom Panel!", 10, 20);
			for (int i = 0; i < iconz.length; i++) {
				iconz[i].draw(g);
			}
		}

//		@Override
//		public Object getTransferData(DataFlavor arg0)
//				throws UnsupportedFlavorException, IOException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public DataFlavor[] getTransferDataFlavors() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public boolean isDataFlavorSupported(DataFlavor arg0) {
//			// TODO Auto-generated method stub
//			return true;
//		}
//
		public boolean canImport(TransferSupport supp) {
			// Check for String flavor
			// if (!supp.isDataFlavorSupported(stringFlavor)) {
			// return false;
			// }

			// Fetch the drop location
			DropLocation loc = supp.getDropLocation();
			System.out.println(loc);
			// Return whether we accept the location
			// return shouldAcceptDropLocation(loc);
			return true;
		}

		public boolean importData(TransferSupport supp) {
			if (!canImport(supp)) {
				return false;
			}

			// Fetch the Transferable and its data
			Transferable t = supp.getTransferable();
			// String data = t.getTransferData(stringFlavor);
			// Fetch the drop location
			DropLocation loc = supp.getDropLocation();
			System.out.println(loc);

			// Insert the data at this location
			// insertAt(loc, data);

			return true;
		}
	}

	class OpenL implements ActionListener {
		DrawPanel imagePanel;

		public OpenL(DrawPanel p) {
			imagePanel = p;
		}

		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(MainWindow.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				String imageString = c.getCurrentDirectory().toString() + "\\"
						+ c.getSelectedFile().getName();
				listModel.addElement(imageString);
				imagePanel.addImage(imageString);
			}
		}
	}
}
