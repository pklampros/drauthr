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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
	public MainWindow self;
	JScrollPane scroller;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		
//	}
	public static void newMainWindow(final String contentType,final String contentTypeName) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow(contentType,contentTypeName);
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
	public MainWindow(String type, String typeName) {
		self = this;
		DataHandler dh = DataHandler.getInstance();
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

		// imageDisplay.setBounds(356, 50, 327, 232);
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
				Uploader.getInstance().fillData();
//				if (listModel.size() > 0) {
//					String[] fids = new String[listModel.size()];
//					for (int i = 0; i < listModel.size(); i++) {
//						fids[i] = up.fileUpload(listModel.get(i));
//					}
//					up.uploadNode(new String[] { "story", titleArea.getText(),
//							descrArea.getText() }, fids);
//					// up.generateImageNode();
//				} else {
//					up.uploadNode(new String[] { "story", titleArea.getText(),
//							descrArea.getText() }, null);
//				}
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
				imageDisplay.removeSelected();
			}
		});
		btnRemove.setBounds(459, 290, 89, 23);
		contentPane.add(btnRemove);

		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.newSettings(false);
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

	// public void updImg(String s) {
	// imageDisplay.repaint();
	// System.out.println(imageDisplay);
	// imageDisplay.updateImage(s);
	// }

	public class imgThumb {
		private String imgLoc = "";
		private int x, y, sz;
		ImageIcon icon;
		boolean valid = false;
		boolean selected = false;

		public imgThumb(String s, int posX, int posY, int saiz) {
			x = posX;
			y = posY;
			sz = saiz;
			imgLoc = s;
			icon = new ImageIcon(imgLoc, "");
		}

		public boolean create(String s) {
			if (!valid) {
				imgLoc = s;
				icon = new ImageIcon(s, "");
				valid = true;
				return false;
			} else
				return true;
		}

		public void rebuild(String s) {
			imgLoc = s;
			icon = new ImageIcon(s, "");
		}

		public void draw(Graphics g) {
			if (valid)
				g.drawImage(icon.getImage(), x, y, sz, sz, null);
			if (selected)
				g.drawRect(x, y, sz, sz);
		}
		   /**
	     * Resizes an image using a Graphics2D object backed by a BufferedImage.
	     * @param srcImg - source image to scale
	     * @param w - desired width
	     * @param h - desired height
	     * @return - the new resized image
	     */
	    private Image getScaledImage(Image srcImg, int w, int h){
	        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2 = resizedImg.createGraphics();
	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2.drawImage(srcImg, 0, 0, w, h, null);
	        g2.dispose();
	        return resizedImg;
	    }
	    private Image getSquareImage(Image srcImg, int sz){
	        BufferedImage resizedImg = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2 = resizedImg.createGraphics();
	        int w = 1280; //srcImage width
	        int h = 800; //srcImage height
	        float ratio = w/h;
	        if (w < h) {
	        	w = sz;
	        	h = (int) (sz*ratio);
	        } else {
	        	w = (int) (sz*ratio);
	        	h = sz;
	        }
	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2.drawImage(srcImg, 0, 0, w, h, null);
	        g2.dispose();
	        return resizedImg;
	    }
	}

	class DrawPanel extends JPanel implements MouseListener {
		// String imageLoc = "";
		int iconSize = 80;
		int borderSize = 10;
		int rowSize = 3;
		imgThumb[] iconz = new imgThumb[25];
		Dimension saiz;

		public DrawPanel() {
			for (int i = 0; i < iconz.length; i++) {
				iconz[i] = new imgThumb("", borderSize
						+ (borderSize + iconSize) * (i % rowSize), borderSize
						+ (borderSize + iconSize) * (i / rowSize), iconSize);

			}
			setBorder(BorderFactory.createLineBorder(Color.black));
			addMouseListener(this);
			// addMouseListener(new MouseAdapter() {
			// public void mousePressed(MouseEvent e) {
			// repaint(0, 0, 100, 100);
			// // moveSquare(e.getX(),e.getY());
			// }
			// });

			addMouseMotionListener(new MouseAdapter() {
				public void mouseDragged(MouseEvent e) {
					repaint(0, 0, 100, 100);
					// moveSquare(e.getX(),e.getY());
				}
			});
		}

		public Dimension getPreferredSize() {
			saiz = new Dimension(
					rowSize * (borderSize + iconSize) + borderSize,
					(iconz.length / rowSize) * (borderSize + iconSize)
							+ borderSize);
			return saiz;
		}

		public void addImage(String s) {
			// imageLoc = s;
			// System.out.println(imageLoc);
			for (int i = 0; i < iconz.length; i++) {
				if (!iconz[i].create(s)) {
					repaint(iconz[i].x, iconz[i].y, iconSize, iconSize);
					break;
				}
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// ImageIcon icon = new ImageIcon(imageLoc,"");

			// Draw Text
			// g.drawString("This is my custom Panel!", 10, 20);
			for (int i = 0; i < iconz.length; i++) {
				iconz[i].draw(g);
			}
		}

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

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// System.out.println(e.getY());

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			int row = e.getY() / (iconSize + borderSize);
			int col = e.getX() / (iconSize + borderSize);
			// System.out.println(row + " " + col);
			for (int i = 0; i < iconz.length; i++) {
				if (e.getClickCount() == 2) {
					
				} else if (e.getClickCount() == 1) {
					if (i == row * rowSize + col && iconz[i].valid)
						iconz[i].selected = true;
					else
						iconz[i].selected = false;
				}
			}
			redraw();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void removeSelected() {
			boolean found = false;
			for (int i = 0; i < iconz.length; i++) {
				if (iconz[i].selected) {
					iconz[i].valid = false;
					iconz[i].selected = false;
					found = true;
				}
				if (found && i < iconz.length - 1) {
					iconz[i].valid = false;
					if (!iconz[i + 1].valid) {
						iconz[i].valid = false;
						break;
					} else
						iconz[i].create(iconz[i + 1].imgLoc);
				}
			}
			// for(int i = 0; i < iconz.length;i++) {
			//
			// }
			redraw();
		}

		public void redraw() {
			repaint(0, 0, saiz.width, saiz.height);
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
