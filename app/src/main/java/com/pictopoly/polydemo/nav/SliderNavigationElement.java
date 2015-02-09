package com.pictopoly.polydemo.nav;

import android.util.Log;
import android.view.View;

/**
 * Created by Marklar on 2/6/2015.
 */
public class SliderNavigationElement extends NavigationElement {
    public SliderNavigationElement(int id) {
        super(id);
    }

    public SliderNavigationElement(View view, int id) {
        super(view,id);
    }

    @Override
    public void onClick(View view) {
        Log.d(this.getClass().getSimpleName(), "Opening Sliders");
    }
}
