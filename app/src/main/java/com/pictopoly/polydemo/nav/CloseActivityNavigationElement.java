package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.view.View;

/**
 * Created by Marklar on 4/7/2015.
 */
public class CloseActivityNavigationElement extends NavigationElement {
    public CloseActivityNavigationElement(int id) {
        super(id);
    }

    public CloseActivityNavigationElement(View view, int id) {
        super(view, id);
    }

    @Override
    public void onClick(View view) {
        if(this.view != null) {
            Activity a = (Activity) this.view.getContext();
            if(a != null)
                a.finish();
        }
    }
}
