package com.pictopoly.polydemo;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pictopoly.polydemo.process.ImageHandler;

/**
 * Created by Marklar on 1/26/2015.
 */
public class SurfaceProcessFragment extends Fragment {
    protected TriangleSurfaceView mTriangleSurfaceView;
    protected ImageHandler handler = ImageLayerHandler.getInstance().getProcessor();

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surface,parent,false);

        mTriangleSurfaceView = (TriangleSurfaceView)view.findViewById(R.id.triangleSurfaceView);
        mTriangleSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mTriangleSurfaceView.handleTouch(event);
            }
        });
        refreshImage();

        return view;
    }

    public void setImage(Bitmap bitmap) {
        if(mTriangleSurfaceView != null)
            mTriangleSurfaceView.setImage(bitmap);
    }

    public void refreshImageRaw() {
        if(mTriangleSurfaceView != null)
            mTriangleSurfaceView.setImage(handler.getRawImage());
    }

    public void refreshImageProcessed() {
        if(mTriangleSurfaceView != null)
            mTriangleSurfaceView.setImage(handler.getProcessedImage());
    }

    public void refreshImage() {
        if(handler.hasProcessed())
            refreshImageProcessed();
        else
            refreshImageRaw();
    }

}
