package com.pictopoly.polydemo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
                        mImageView.setImageBitmap(processor.refreshTriangles());
                    else if(processor.getRawImage() != null)
                        mImageView.setImageBitmap(processor.processImage());
                }

                return true;
            }
        });

        return v;
    }
}
