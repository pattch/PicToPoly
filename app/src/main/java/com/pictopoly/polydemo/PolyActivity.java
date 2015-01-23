package com.pictopoly.polydemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.pictopoly.polydemo.process.ImageHandler;


public class PolyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poly);

        // Load Default Image
//        loadDefaultImage();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.image_container, new ImageProcessFragment())
                    .add(R.id.nav_container, new NavigationFragment())
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
        Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.bird);
        ImageHandler handler = ImageLayerHandler.getInstance().getProcessor();
        handler.setImage(image);
        handler.processImage();
    }
}
