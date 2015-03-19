package com.pictopoly.polydemo.nav;

import android.content.Intent;
import android.view.View;

import com.pictopoly.polydemo.GradientActivity;
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
        return new Intent(this.view.getContext(), GradientActivity.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
