package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;

public abstract class IntentNavigationElement extends NavigationElement {
    protected int REQUEST_CODE;

    public IntentNavigationElement(int id, final int requestCode) {
        super(id);
        REQUEST_CODE = requestCode;
    }

    public IntentNavigationElement(View view, int id, int requestCode) {
        super(view,id);
        REQUEST_CODE = requestCode;
    }

    /**
     * IntentNavigationElements should start an Intent on being clicked
     */
    @Override
    public void onClick(View view) {
        Activity a = (Activity)this.view.getContext();
        a.startActivityForResult(getIntent(), REQUEST_CODE);
    }

    /**
     * Should start activity for result using REQUEST_CODE here
     * @param view
     * @param container
     */
    public void onClick(View view, Fragment container) {
        container.startActivityForResult(getIntent(), REQUEST_CODE);
    }

    /**
     *
     * @return The Intent for the Activity to be started on being clicked
     */
    public abstract Intent getIntent();

    /**
     * What should be done
     */
    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public int getRequestCode() {
        return REQUEST_CODE;
    }
}
