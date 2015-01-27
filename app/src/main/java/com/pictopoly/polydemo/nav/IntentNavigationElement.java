package com.pictopoly.polydemo.nav;

import android.content.Intent;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;

/**
 * Created by Marklar on 1/26/2015.
 */
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
        view.getContext().startActivity(getIntent());
    }

    /**
     *
     * @return The Intent for the Activity to be started on being clicked
     */
    public abstract Intent getIntent();

    /**
     * What should be done
     */
    public abstract void onActivityResult(int requestCode, int resultCode, Intent data, PolyActivity activity);

    public int getRequestCode() {
        return REQUEST_CODE;
    }
}
