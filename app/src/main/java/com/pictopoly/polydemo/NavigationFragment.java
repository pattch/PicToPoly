package com.pictopoly.polydemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pictopoly.polydemo.process.ImageHandler;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Samuel on 1/21/2015.
 */
public class NavigationFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    protected TextView[] mButtons;
    protected int[] mButtonIds = new int[] {R.id.nav_open_image,
            R.id.nav_camera,
            R.id.nav_process_image,
            R.id.nav_sliders};
    protected int mButtonCount = mButtonIds.length;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceBundle) {
        View view = inflater.inflate(R.layout.fragment_nav, parent, false);
        mButtons = new TextView[mButtonCount];
        Typeface materialTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/material_design_icons.ttf");

        // TODO: Implement as NavigationElement(s) so adding new functionality is easier
        for(int i = 0; i < mButtonCount; i++) {
            mButtons[i] = (TextView)view.findViewById(mButtonIds[i]);
            mButtons[i].setTypeface(materialTypeface);
            setListener(mButtons[i], mButtonIds[i]);
        }

        return view;
    }

    private void setListener(View view, int id) {
        switch(id) {
        case R.id.nav_open_image:
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PolyActivity.INTENT_SELECT_PICTURE);
                }
            });
        break;

        case R.id.nav_process_image:
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageHandler handler = ImageLayerHandler.getInstance().getProcessor();
                    if (!handler.hasProcessed())
                        ((PolyActivity) getActivity()).setImage(handler.processImage());
                }
            });
        break;

        case R.id.nav_camera:
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Take A Picture With The Camera Here
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Returned From Activity");

        switch(requestCode) {
        case PolyActivity.INTENT_SELECT_PICTURE:
            if(resultCode == Activity.RESULT_OK)
                try {
                    InputStream stream = getActivity().getContentResolver().openInputStream(
                            data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    stream.close();

                    ((PolyActivity) getActivity()).setImage(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        break;
        case PolyActivity.INTENT_CAMERA:
        // TODO: Handle The Image Return Here
        break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getPath(Uri uri) {
        if(uri == null) {
            Toast.makeText(getActivity(), R.string.image_uri_null, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Uri null");
            return null;
        }

        Log.d(TAG, "Trying to make Cursor");
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
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
