package com.pictopoly.polydemo.nav;

import android.content.Intent;
import android.view.View;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.SplashActivity;
import com.pictopoly.polydemo.process.ImageHandler;

/**
 * Created by Marklar on 3/1/2015.
 */
public class ReturnToImageIntentNavigationElement  extends IntentNavigationElement {
    public ReturnToImageIntentNavigationElement(int id) {
        super(id, PolyActivity.INTENT_RETURN);
    }

    public Intent getIntent() {
        Intent i = new Intent(this.view.getContext(), PolyActivity.class);
        return i;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}
}
