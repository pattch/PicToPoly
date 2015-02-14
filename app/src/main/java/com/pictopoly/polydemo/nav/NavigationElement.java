package com.pictopoly.polydemo.nav;

        import android.app.Fragment;
        import android.view.View;

/**
 * Created by Marklar on 1/26/2015.
 */
public abstract class NavigationElement {
    protected View view;
    protected int id;

    public NavigationElement(int id) {
        this.id = id;
    }

    public NavigationElement(View v, int id) {
        this.view = view;
        this.id = id;
    }

    public abstract void onClick(View view);

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
