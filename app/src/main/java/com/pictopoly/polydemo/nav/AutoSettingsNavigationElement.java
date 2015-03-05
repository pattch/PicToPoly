package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.view.View;

import com.pictopoly.polydemo.R;

/**
 * Created by Marklar on 3/2/2015.
 */
public class AutoSettingsNavigationElement extends NavigationElement {
    public AutoSettingsNavigationElement(int id) {
        super(id);
    }

    public AutoSettingsNavigationElement(View view, int id) {
        super(view, id);
    }

    @Override
    public void onClick(View view) {
        Activity a = (Activity)view.getContext();
        View v = a.findViewById(R.id.nav_auto_settings);
        if(v != null)
            v.setVisibility(View.VISIBLE);
    }
}
