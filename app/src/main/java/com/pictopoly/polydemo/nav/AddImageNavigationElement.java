package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.R;

/**
 * Created by Marklar on 2/4/2015.
 */
public class AddImageNavigationElement extends NavigationElement {
    public AddImageNavigationElement(int id) {
        super(id);
    }

    public AddImageNavigationElement(View view, int id) {
        super(view, id);
    }

    @Override
    public void onClick(View view) {
        Activity a = (Activity)view.getContext();
        View v = a.findViewById(R.id.nav_image_options);
        if(v != null)
            v.setVisibility(View.VISIBLE);
    }
}
