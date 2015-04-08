package com.pictopoly.polydemo.nav;

import android.content.Intent;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.SplashActivity;

public class CloseImageIntentNavigationElement extends IntentNavigationElement {
    public CloseImageIntentNavigationElement(int id) {
        super(id, PolyActivity.INTENT_SPLASH);
    }

    public Intent getIntent() {
        return new Intent(this.view.getContext(), SplashActivity.class);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}
}
