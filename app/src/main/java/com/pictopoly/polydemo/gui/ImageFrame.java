//package com.pictopoly.polydemo.gui;
//
//import java.awt.Container;
//import java.awt.Image;
//
//import javax.swing.JFrame;
//
//public class ImageFrame extends JFrame  {
//	protected ImagePanel imgPanel;
//
//	/**
//	 * 				Create a Frame that contains an ImagePanel
//	 *
//	 * @param img
//	 * 				The Image to be contained, drawn in the ImagePanel
//	 */
//	public ImageFrame(Image img) {
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setSize(img.getWidth(null), img.getHeight(null));
//		imgPanel = new ImagePanel(img);
//		imgPanel.setSize(img.getWidth(null), img.getHeight(null));
//		add(imgPanel);
//	}
//
//	/**
//	 * 				Set/update the Image this Panel will contain, draw.
//	 *
//	 * @param img
//	 * 				The Image the ImagePanel will now contain, draw
//	 */
//	public void setImage(Image img) {
//		imgPanel.setImage(img);
//	}
//
//	/**
//	 * 				Get the ImagePanel that this ImageFrame contains
//	 *
//	 * @return
//	 * 				The ImagePanel that this ImageFrame contains
//	 */
//	public ImagePanel getPanel() {
//		return this.imgPanel;
//	}
//}
