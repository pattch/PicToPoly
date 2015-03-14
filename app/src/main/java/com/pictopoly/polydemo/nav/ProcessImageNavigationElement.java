package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.process.ImageProcessor;

/**
 * Created by Marklar on 1/26/2015.
 */
public class ProcessImageNavigationElement extends NavigationElement {
    public ProcessImageNavigationElement(int id) {
        super(id);
    }

    public ProcessImageNavigationElement(View view, int id) {
        super(view,id);
    }

    @Override
    public void onClick(View view) {
        ImageProcessor handler = ImageLayerHandler.getInstance().getProcessor();
        Activity a = (Activity)view.getContext();
        if (a instanceof PolyActivity) {
            Log.d(getClass().getSimpleName(), "Processing from Nav");
            PolyActivity pa = (PolyActivity)a;
            pa.showLoadingPanel();
        } else
            Log.d(getClass().getSimpleName(), "Activity for ProcessNavEl not PolyActivity");
        Thread processThread = new Thread(handler);
        processThread.start();
    }
}
