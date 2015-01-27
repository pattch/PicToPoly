package com.pictopoly.polydemo.nav;

import android.view.View;

/**
 * Created by Marklar on 1/26/2015.
 */
public abstract class NavigationElement {
    private View view;
    private int id;

    public NavigationElement(int id) {
        this.id = id;
    }

    public NavigationElement(View v, int id) {
        this.view = view;
        this.id = id;
    }

    public abstract void onClick(View view);

    public void setView(View view) {
        this.view = view;
    }

    public void setId(int id) {
        this.id = id;
    }
}
