package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.R;

/**
 * Created by Marklar on 2/6/2015.
 */
public class PointOptionsNavigationElement extends NavigationElement {
    public PointOptionsNavigationElement(int id) {
        super(id);
    }

    public PointOptionsNavigationElement(View view, int id) {
        super(view, id);
    }

    @Override
    public void onClick(View view) {
        Activity a = (Activity)view.getContext();
        View v = a.findViewById(R.id.nav_point_options);
        if(v != null)
            v.setVisibility(View.VISIBLE);
    }
}
