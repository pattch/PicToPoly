//package com.pictopoly.polydemo.gui;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//
//import javax.swing.JPanel;
//
//public class ImagePanel extends JPanel {
//	protected Image img;
//	/**
//	 * @param img
//	 * 				The image this Panel will contain, draw
//	 */
//	public ImagePanel(Image img) {
//		this.img = img;
//	}
//
//	/**
//	 * (non-Javadoc)
//	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
//	 */
//	@Override
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.drawImage(img,0,0,null);
//	}
//
//	/**
//	 * 				Set/update the Image this Panel will contain, draw
//	 * @param img
//	 * 				The Image this Panel will now contain, draw
//	 */
//	public void setImage(Image img) {
//		this.img = img;
//	}
//
//	/**
//	 * 				Get the integer width of the Image this Panel contains, draws
//	 *
//	 * @return
//	 * 				The width of the Image this Panel contains, draws
//	 */
//	public int getImageWidth() {
//		return img.getWidth(null);
//	}
//
//	/**
//	 * 				Get the integer height of the Image this Panel contains, draws
//	 *
//	 * @return
//	 * 				The height of the Image this Panel contains, draws
//	 */
//	public int getImageHeight() {
//		return img.getHeight(null);
//	}
//
//	/**
//	 * 				Clears the panel for repainting
//	 * @param g
//	 * 				The graphics context to clear.
//	 */
//	private void clearScreen(Graphics g) {
//		Color previousColor = g.getColor();
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, this.getWidth(), this.getHeight());
//		g.setColor(previousColor);
//    }
//}