package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.process.ImageProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Marklar on 1/26/2015.
 */
public class CameraIntentNavigationElement extends IntentNavigationElement {
    private String TAG = this.getClass().getSimpleName();
    protected Uri imageFileLocation;

    public CameraIntentNavigationElement(int id) {
        super(id,PolyActivity.INTENT_CAMERA);
    }

    public CameraIntentNavigationElement(View view, int id) {
        super(view,id,PolyActivity.INTENT_CAMERA);
    }

    @Override
    public Intent getIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File image;
        try {
            if(view != null) {
                String path = Environment.getExternalStorageDirectory().getPath() + ImageProcessor.PICTURE_PATH;
                File outputDir = new File(path);
                outputDir.mkdirs();
                image = File.createTempFile("picture", ".jpg", outputDir);
            } else
                return null;
        } catch (IOException e) {
            Log.v(TAG, "Cannot create file for taking picture.");
            return null;
        }
        imageFileLocation = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileLocation);

        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Returned from Activity");
        Activity activity = (Activity)view.getContext();
        activity.getContentResolver().notifyChange(imageFileLocation,null);
        ContentResolver cr = activity.getContentResolver();
        Bitmap bitmap;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(cr,imageFileLocation);
            ImageLayerHandler.getInstance().getProcessor().setImage(bitmap);
            if(activity instanceof PolyActivity)
                ((PolyActivity) activity).setImage(ImageLayerHandler.getInstance().getProcessor().getRawImage());
        } catch (FileNotFoundException e) {
            Toast.makeText(activity,"Image not found", Toast.LENGTH_SHORT).show();
            return;
        } catch (IOException e) {
            Toast.makeText(activity,"Image failed to load",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClick(View view, Fragment container) {
        Log.d(TAG,"Starting Activity");
        container.startActivityForResult(getIntent(),REQUEST_CODE);
    }
}
