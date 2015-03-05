package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.TriangleSurfaceView;

/**
 * Created by Marklar on 2/6/2015.
 */
public class ChangeTriangleViewNavigationElement extends NavigationElement {
    public ChangeTriangleViewNavigationElement(int id) {
        super(id);
    }

    public ChangeTriangleViewNavigationElement(View view, int id) {
        super(view,id);
    }

    @Override
    public void onClick(View view) {
        Log.d(this.getClass().getSimpleName(), "Changing Triangle View");
        Activity a = (Activity)view.getContext();
        if(a instanceof PolyActivity) {
            PolyActivity pa = (PolyActivity)a;
            pa.changeRenderType();
            pa.refreshTriangleSurface();
        }
    }
}
