package com.pictopoly.polydemo.nav;

import android.content.Intent;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;

/**
 * Created by Marklar on 1/26/2015.
 */
public class CameraIntentNavigationElement extends IntentNavigationElement {
    public CameraIntentNavigationElement(int id, final int requestCode) {
        super(id,requestCode);
    }

    public CameraIntentNavigationElement(View view, int id, final int requestCode) {
        super(view,id,requestCode);
    }

    @Override
    public Intent getIntent() {
        // TODO: Make Intent to Take a Picture
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, PolyActivity activity) {
        // TODO: set the ImageHandler's Image to the picture taken
    }
}
