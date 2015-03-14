package com.pictopoly.polydemo;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pictopoly.polydemo.process.ImageProcessor;

/**
 * Created by Marklar on 1/26/2015.
 */
public class SurfaceProcessFragment extends Fragment {
    protected TriangleSurfaceView mTriangleSurfaceView;
    protected ImageProcessor handler = ImageLayerHandler.getInstance().getProcessor();
    protected View loadingPanel;

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

        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.INVISIBLE);

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
            mTriangleSurfaceView.setImageHandler(handler);
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

    public void setAddingPoints(boolean addingPoints) {
        if(mTriangleSurfaceView != null)
            mTriangleSurfaceView.setAddingPoints(addingPoints);
    }

    public void showLoadingPanel() {
        if(loadingPanel != null)
            loadingPanel.setVisibility(View.VISIBLE);
    }

    public void hideLoadingPanel() {
        if(loadingPanel != null)
            loadingPanel.setVisibility(View.INVISIBLE);
    }

    public void changeRenderType() {
        if(mTriangleSurfaceView != null)
            mTriangleSurfaceView.changeRenderType();
    }
}
