package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.R;

/**
 * Created by Marklar on 2/4/2015.
 *
 * This is really bad - this should all be done by calls to PolyActivity
 */
public abstract class ChangeOptionsNavigationElement extends NavigationElement {
    protected int[] optionIds = new int[] { R.id.nav_image_options };

    public ChangeOptionsNavigationElement(int id) {
        super(id);
    }

    public ChangeOptionsNavigationElement(View view, int id) {
        super(view, id);
    }

    protected void hideOptionElements(View view) {
        Activity a = (Activity)view.getContext();
        for(int id : optionIds)
            a.findViewById(id).setVisibility(View.INVISIBLE);
    }
}
