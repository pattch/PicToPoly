package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.PolyActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Marklar on 1/26/2015.
 */
public class OpenImageIntentNavigationElement extends IntentNavigationElement {
    private String TAG = this.getClass().getSimpleName();

    public OpenImageIntentNavigationElement(int id) {
        super(id,PolyActivity.INTENT_SELECT_PICTURE);
    }

    public OpenImageIntentNavigationElement(View view, int id) {
        super(view,id,PolyActivity.INTENT_SELECT_PICTURE);
    }

    public Intent getIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Returning from Activity");
        if(resultCode == Activity.RESULT_OK) {
            try {
                Activity activity = (Activity) view.getContext();
                InputStream stream = activity.getContentResolver().openInputStream(
                        data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                stream.close();

                if (activity instanceof PolyActivity) {
                    ImageLayerHandler.getInstance().getProcessor().setImage(bitmap);
                    ((PolyActivity) activity).setImage(ImageLayerHandler.getInstance().getProcessor().getRawImage());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view, Fragment container) {
        container.startActivityForResult(getIntent(),REQUEST_CODE);
    }
}
