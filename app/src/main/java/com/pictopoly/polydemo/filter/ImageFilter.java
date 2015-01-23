package com.pictopoly.polydemo.filter;

//import java.awt.Image;

import android.graphics.Bitmap;

public interface ImageFilter {
    public Bitmap filter(Bitmap bitmap);
	public int[] filter(int[] pixels, int width, int height);
}
