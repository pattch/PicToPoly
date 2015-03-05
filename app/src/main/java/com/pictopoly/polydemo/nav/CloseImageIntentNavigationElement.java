package com.pictopoly.polydemo.nav;

import android.content.Intent;
import android.view.View;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.SplashActivity;
import com.pictopoly.polydemo.process.ImageHandler;

/**
 * Created by Marklar on 2/15/2015.
 */
public class CloseImageIntentNavigationElement extends IntentNavigationElement {
    public CloseImageIntentNavigationElement(int id) {
        super(id, PolyActivity.INTENT_SPLASH);
    }

    public Intent getIntent() {
        Intent i = new Intent(this.view.getContext(), SplashActivity.class);
        return i;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}
}
