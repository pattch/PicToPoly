package com.pictopoly.polydemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.pictopoly.polydemo.process.ImageHandler;
import com.pictopoly.polydemo.tri.Point;

/**
 * Created by Samuel on 1/21/2015.
 */
public class ImageProcessFragment extends Fragment {
    private static String TAG = "ImageProcessFragment";
    protected ImageHandler processor;
    protected ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        processor = ImageLayerHandler.getInstance().getProcessor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, parent, false);

        mImageView = (ImageView)v.findViewById(R.id.imageView);
        if(processor.getProcessedImage() != null)
            mImageView.setImageBitmap(processor.getProcessedImage());
        else if(processor.getRawImage() != null)
            mImageView.setImageBitmap(processor.getRawImage());

        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d(TAG, "Adding Point: " + event.getX() + ", " + event.getY());
                    processor.addPoint(new Point(event.getX(), event.getY()));
                    if(processor.getProcessedImage() != null)
                        processor.refreshTriangles();
                    else if(processor.getRawImage() != null)
                        processor.processImage();

                    refreshImageView();
                }

                return true;
            }
        });

        return v;
    }

    public void refreshImageView() {
        if(mImageView != null) {
            if(processor.getProcessedImage() != null)
                mImageView.setImageBitmap(processor.getProcessedImage());
            else
                mImageView.setImageBitmap(processor.processImage());

            mImageView.invalidate();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == PolyActivity.SELECT_PICTURE) {
            Uri imageUri = data.getData();
            String imagePath = getPath(imageUri);
            Bitmap map = BitmapFactory.decodeFile(imagePath);
            Log.d("PolyActivity","" + ImageLayerHandler.getInstance().getProcessor().getProcessedImage().getByteCount());
            ImageLayerHandler.getInstance().getProcessor().setImage(map);
            Log.d("PolyActivity","" + ImageLayerHandler.getInstance().getProcessor().getProcessedImage().getByteCount());
            ImageLayerHandler.getInstance().getProcessor().processImage();
            refreshImageView();
        }
    }

    private String getPath(Uri uri) {
        if(uri == null) {
            Toast.makeText(getActivity(), R.string.image_uri_null, Toast.LENGTH_SHORT).show();
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if( cursor != null ) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        // fallback code
        return uri.getPath();
    }
}
