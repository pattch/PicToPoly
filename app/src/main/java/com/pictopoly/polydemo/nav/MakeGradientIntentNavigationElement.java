package com.pictopoly.polydemo.nav;

import android.content.Intent;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;

/**
 * Created by Marklar on 3/14/2015.
 */
public class MakeGradientIntentNavigationElement extends IntentNavigationElement {
    public MakeGradientIntentNavigationElement(int id) {
        super(id, PolyActivity.INTENT_GRADIENT);
    }

    public MakeGradientIntentNavigationElement(View view, int id) {
        super(view,id,PolyActivity.INTENT_GRADIENT);
    }


    @Override
    public Intent getIntent() {
        // TODO: Instead, should make a GradientActivity so the user can pick their colors and route them there with this method.
        return new Intent(this.view.getContext(), PolyActivity.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
