package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.pictopoly.polydemo.PolyActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Marklar on 1/26/2015.
 */
public class OpenImageIntentNavigationElement extends IntentNavigationElement {
    public OpenImageIntentNavigationElement(int id, final int requestCode) {
        super(id,requestCode);
    }

    public OpenImageIntentNavigationElement(View view, int id, final int requestCode) {
        super(view,id,requestCode);
    }

    public Intent getIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, PolyActivity activity) {
        if(resultCode == Activity.RESULT_OK)
            try {
                InputStream stream = activity.getContentResolver().openInputStream(
                        data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                stream.close();

                activity.setImage(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
