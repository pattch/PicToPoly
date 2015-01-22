//package com.pictopoly.polydemo.gui;
//
//import java.awt.Image;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
//
//import process.ImageHandler;
//import tri.Point;
//
//public class UIFrame extends ImageFrame /*implements MouseListener*/{
//	protected ImageHandler processor;
//	protected boolean mouseClicked = false;
//
//	public UIFrame() {
//		this(getImage());
//	}
//
//	public UIFrame(Image i) {
//		super(i);
//		processor = new ImageHandler(i);
//		setImage(processor.processImage());
//		addListeners();
//	}
//
//	/**
//	 * 				Ask the user for an Image from the file system.
//	 *
//	 * @return
//	 * 				An Image selected by the user from the file system.
//	 */
//	private static Image getImage() {
//		JFileChooser imageSelector = new JFileChooser(
//			System.getProperty("user.dir") + "/images");
//
//		imageSelector.setDialogTitle("");
//		while (imageSelector.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
//		    JOptionPane.showMessageDialog(null, "Please Select an Image");
//
//		try {
//		    return ImageHandler.resizeImage(ImageIO.read(imageSelector.getSelectedFile()));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 				Add Mouse Listeners to the ImagePanel in order to capture mouse events,
//	 * 				and ultimately add points to the triangulation in the ImageHandler
//	 */
//	private void addListeners() {
//		imgPanel.addMouseListener(new MouseListener() {
//		    @Override
//		    public void mouseReleased(MouseEvent e) {}
//		    @Override
//		    public void mousePressed(MouseEvent e) {
//				if (isPointOnScreen(e.getPoint())) {
//				    processor.addPoint(new Point(e.getPoint()));
//				    imgPanel.setImage(processor.refreshTriangles());
//				    repaint();
//				}
//		    }
//		    @Override
//		    public void mouseExited(MouseEvent e) {}
//		    @Override
//		    public void mouseEntered(MouseEvent e) {}
//		    @Override
//		    public void mouseClicked(MouseEvent arg0) {}
//		});
//
//		imgPanel.addMouseMotionListener(new MouseMotionListener() {
//		    @Override
//		    public void mouseMoved(MouseEvent e) {}
//
//		    @Override
//		    public void mouseDragged(MouseEvent e) {
//		    	if (isPointOnScreen(e.getPoint())) {
//					processor.addPoint(new Point(e.getPoint()));
//				    imgPanel.setImage(processor.refreshTriangles());
//				    repaint();
//			    }
//			}
//		});
//	}
//
//	/**
//	 *
//	 * 		Checks whether or not a point is within the processed image.
//	 *
//	 * @param point
//	 * 		Point to be checked against image size
//	 * @return
//	 * 		True if the given point is actually in the image, false otherwise.
//	 */
//	private boolean isPointOnScreen(java.awt.Point point)
//	{
//	    if (point.getX() < 0 || point.getY() < 0)
//		return false;
//
//	    if (point.getX() > processor.getRawImage().getWidth(null) || point.getY() > processor.getRawImage().getHeight(null))
//		return false;
//	    return true;
//	}
//}
