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


public class PolyActivity extends Activity {
    public static final int SELECT_PICTURE = 0;
    protected static ImageProcessFragment imageFragment;
    protected static NavigationFragment navFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poly);

        // First Time Running
        if (savedInstanceState == null) {
            loadDefaultImage();
            imageFragment = new ImageProcessFragment();
            navFragment = new NavigationFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.image_container, imageFragment)
                    .add(R.id.nav_container, navFragment)
                    .commit();
        }
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

    private void loadDefaultImage() {
        if(ImageLayerHandler.getInstance().getProcessor().getRawImage() == null) {
            Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.bird);
            ImageHandler handler = ImageLayerHandler.getInstance().getProcessor();
            handler.setImage(image);
            handler.processImage();
        }
    }

    public ImageProcessFragment getImageProcessFragment() {
        return imageFragment;
    }

    public NavigationFragment getNavigationFragment() {
        return navFragment;
    }
}
