package com.pictopoly.polydemo;

import android.app.Activity;
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
                Activity a = SurfaceProcessFragment.this.getActivity();
                if(a instanceof PolyActivity)
                    ((PolyActivity)a).clearAllOptions();

                boolean res = false;
                if((event.getAction() == MotionEvent.ACTION_DOWN && mTriangleSurfaceView.isChangingSinglePoint()) || !mTriangleSurfaceView.isChangingSinglePoint())
                    res = mTriangleSurfaceView.handleTouch(event);
                return res;
            }
        });
        refreshImage();
        view.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);

        return view;
    }

    public void setImage(Bitmap bitmap) {
        if(mTriangleSurfaceView != null) {
            mTriangleSurfaceView.setImage(bitmap);
            mTriangleSurfaceView.invalidate();
        }
    }

    public void refreshImage() {
        if(mTriangleSurfaceView != null) {
            mTriangleSurfaceView.setImage(handler.getProcessedImage());
            mTriangleSurfaceView.invalidate();
        }
    }

    public void invalidate() {
        if(mTriangleSurfaceView != null)
            mTriangleSurfaceView.invalidate();
    }

    public void setChangingSinglePoint(boolean changingSinglePoint) {
        if(mTriangleSurfaceView != null)
            mTriangleSurfaceView.setChangingSinglePoint(changingSinglePoint);
    }
}
