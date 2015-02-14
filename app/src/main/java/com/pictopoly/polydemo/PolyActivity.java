package com.pictopoly.polydemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import com.pictopoly.polydemo.process.ImageHandler;
import com.pictopoly.polydemo.process.ThreadCompleteListener;


public class PolyActivity extends Activity implements ThreadCompleteListener {
    public static final int INTENT_SELECT_PICTURE = 0;
    public static final int INTENT_CAMERA = 1;
    protected static SurfaceProcessFragment surfaceFragment;
    protected static NavigationFragment navFragment;
    protected View rootView;

    protected int[] optionElements = new int[] {
            R.id.nav_image_options,
            R.id.nav_point_options, };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poly);

        // First Time Running
        if (savedInstanceState == null) {
            surfaceFragment = new SurfaceProcessFragment();
            navFragment = new NavigationFragment();
//            loadDefaultImage();

            getFragmentManager().beginTransaction()
                    .add(R.id.surface_container, surfaceFragment)
                    .add(R.id.nav_container, navFragment)
                    .commit();
        }

        ImageHandler handler = ImageLayerHandler.getInstance().getProcessor();
        handler.addListener(this);

        rootView = findViewById(R.id.root_container);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllOptions();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poly, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyThreadComplete(final Runnable runnable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PolyActivity.this.setImage(ImageLayerHandler.getInstance().getProcessor().getProcessedImage());
                PolyActivity.this.hideLoadingPanel();
            }
        });
    }

    /*
     * Pre-condition: map is not null
     * Post-condition: The current Layer's Image is set to map
     */
    public void setImage(Bitmap map) {
//        ImageLayerHandler.getInstance().getProcessor().setImage(map);
//        surfaceFragment.refreshImage();
        if(surfaceFragment != null)
            surfaceFragment.setImage(map);
    }

    public void clearAllOptions() {
        for(int Id : optionElements) {
            View v = findViewById(Id);
            if(v != null)
                v.setVisibility(View.INVISIBLE);
        }
    }

    public void refreshTriangleSurface() {
        if(surfaceFragment != null)
            surfaceFragment.invalidate();
    }

    public void setChangingSinglePoint(boolean addSinglePoint) {
        if(surfaceFragment != null)
            surfaceFragment.setChangingSinglePoint(addSinglePoint);
    }

    public void setAddingPoints(boolean addingPoints) {
        if(surfaceFragment != null)
            surfaceFragment.setAddingPoints(addingPoints);
    }

    public void showLoadingPanel() {
        if(surfaceFragment != null)
            surfaceFragment.showLoadingPanel();
    }

    public void hideLoadingPanel() {
        if(surfaceFragment != null)
            surfaceFragment.hideLoadingPanel();
    }

//    public void setImageToHandlerImage() {
//        surfaceFragment.refreshImage();
//    }

//    public ImageProcessFragment getImageProcessFragment() {
//        return imageFragment;
//    }

//    public NavigationFragment getNavigationFragment() {
//        return navFragment;
//    }
}
