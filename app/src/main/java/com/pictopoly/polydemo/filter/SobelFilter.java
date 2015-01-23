package com.pictopoly.polydemo.filter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

//import java.awt.Image;
//import java.awt.image.*;
import java.io.*;
import java.lang.Math;

//import javax.imageio.*;

public class SobelFilter implements ImageFilter {

    static String fileName, outputName;
    static final String FILE_TYPE_PNG = "png";
    static Bitmap inputFile, outputFile;
    static int width, height;
    static File example, result;

    /* NPU APPROXIMATION START */
    public static double sobel(double[][] window) {
		double x, y, r = 0;
		x = (window[0][0] + 2 * window[0][1] + window[0][2]);
		x -= (window[2][0] + 2 * window[2][1] + window[2][2]);
		y = (window[0][2] + 2 * window[1][2] + window[2][2]);
		y -= (window[0][0] + 2 * window[1][1] + window[2][0]);
		r = Math.sqrt((x * x) + (y * y));
		if (r > 255.0)
		    r = 0;
		return r;
    }

    /* NPU APPROXIMATION END */

    public static void printRGB(int clr) {
		int red = (clr & 0x00FF0000) >> 16;
		int green = (clr & 0x0000FF00) >> 8;
		int blue = (clr & 0x000000FF);
		System.out.println("Red: " + red + " Green: " + green + " Blue: "
			+ blue);
    }

    public static int getGreyScale(int clr) {
		int red = (clr & 0x00FF0000) >> 16;
		int green = (clr & 0x0000FF00) >> 8;
		int blue = (clr & 0x000000FF);
		if ((red == blue) && (blue == green))
		    return red;
		else
		    return -1;
    }

    public static int setGreyScaleValue(int clr) {
    	return (clr) + (clr << 8) + (clr << 16);
    }

    public static double[][] buildWindow(int[] pixels, int x, int y, int width, int height) {
        double[][] retVal = new double[3][3];
        for(int yPos = -1; yPos <= 1; yPos++) {
            for(int xPos = -1; xPos <= 1; xPos++) {
                int currX = xPos + x; int currY = yPos + y;
                if(currX >= 0 && currX < width && currY >= 0 && currY < height) {
                    int rgbRawValue = getIntFromArrayAsTwoDimensional(pixels, currX, currY, width, height),
                        greyScaleValue = GreyScaleFilter.getGreyScaleValue(rgbRawValue);
                    retVal[xPos + 1][yPos + 1] = greyScaleValue;
                }
            }
        }
        return retVal;
    }

    public static double[][] buildWindow(int x, int y, Bitmap srcImg){
        double[][] retVal = new double[3][3];
        for ( int ypos = -1; ypos <= 1; ypos++ ){
            for (int xpos = -1; xpos <= 1; xpos++ ){
                int currX = xpos + x; int currY = ypos + y;
                if ( (currX >= 0 && currX < width) && (currY >= 0 && currY < height) ){
                    int rgbRawValue = srcImg.getPixel(currX, currY);
                    int grayScaleValue = getGreyScale(rgbRawValue);
                    retVal[xpos + 1][ypos + 1] = grayScaleValue;
                }
                else
                    retVal[xpos + 1][ypos + 1] = 255;
            }
        }
        return retVal;
    }

    public static int getIntFromArrayAsTwoDimensional(int[] src, int x, int y, int width, int height) {
        int offset = y * width;
        int accessInt = x + offset;
        return src[accessInt];
    }

    public static void setIntInArrayAsTwoDimensional(int[] src, int x, int y, int width, int height, int value) {
        int offset = y * width;
        int accessInt = x + offset;
        src[accessInt] = value;
    }

    public static int[] edgeDetection(int[] pixels, int width, int height) {
        double[][] window;
        int[] retVal = new int[pixels.length];
        int localWidth = width,
            localHeight = height;
        for(int y  = 0; y < localHeight; y++) {
            for(int x = 0; x < localWidth; x++) {
                window = buildWindow(pixels,x,y,width,height);
                double sobelValue = sobel(window);
                int sobelRGBValue = setGreyScaleValue((int)sobelValue);
                setIntInArrayAsTwoDimensional(retVal,x,y,width,height,sobelRGBValue);
            }
        }
        return retVal;
    }

    public static Bitmap edgeDetection(Bitmap srcImg) {
        Bitmap retVal = new GreyScaleFilter().filter(srcImg);
		double[][] window = new double[3][3];
		for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                window = buildWindow(x, y, srcImg);

//                double newValue = srcImg.getPixel(x, y);
                double sobelValue = sobel(window);
                int sobelRGBValue = setGreyScaleValue((int) sobelValue);
//                int grayScaleMag = getGreyScale((int) newValue);
//                int greyscaleValue = setGreyScaleValue(grayScaleMag);
                retVal.setPixel(x, y, sobelRGBValue);
		    }
		}
		return retVal;
    }

    @Override
    public Bitmap filter(Bitmap i) {
        height = i.getHeight();
        width = i.getWidth();
        return edgeDetection(i);
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        return edgeDetection(pixels,width,height);
    }
}