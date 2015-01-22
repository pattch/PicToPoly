//package com.pictopoly.polydemo.gui;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
//
//import process.ImageHandler;
//
//public class UMLTestGui {
//    private static BufferedImage getImage(String title) {
//		JFileChooser imageSelector = new JFileChooser(
//			System.getProperty("user.dir") + "/images");
//		imageSelector.setDialogTitle(title);
//		while (imageSelector.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
//		    JOptionPane.showMessageDialog(null, "Please Select a " + title);
//		}
//
//		try {
//		    return ImageIO.read(imageSelector.getSelectedFile());
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
//
//		return null;
//    }
//
//	public static void main(String[] args) {
//		ImageHandler proc = new ImageHandler(getImage(""));
//		ImageFrame frame = new ImageFrame(proc.processImage());
//		frame.setVisible(true);
//	}
//}
