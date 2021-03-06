package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.PolyActivity;
import com.pictopoly.polydemo.process.ImageProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.content.pm.PackageManager;

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
    public void onClick(View view) {
        PackageManager pm = this.view.getContext().getPackageManager();
        if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA))
            super.onClick(view);
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
//            Log.v(TAG, "Cannot create file for taking picture.");
            return null;
        }
        imageFileLocation = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileLocation);

        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d(TAG, "Returned from Activity");
        Activity activity = (Activity)view.getContext();
        activity.getContentResolver().notifyChange(imageFileLocation,null);
        ContentResolver cr = activity.getContentResolver();
        Bitmap bitmap;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(cr,imageFileLocation);
            ImageProcessor polyActivityProcessor = ImageLayerHandler.getInstance().getPolyActivityImageProcessor();
            polyActivityProcessor.setImage(bitmap);
            polyActivityProcessor.setDefaultPointMaker();
            if(activity instanceof PolyActivity)
                ((PolyActivity) activity).setImage(ImageLayerHandler.getInstance().getPolyActivityImageProcessor().getRawImage());
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
//        Log.d(TAG,"Starting Activity");
        container.startActivityForResult(getIntent(),REQUEST_CODE);
    }
}
