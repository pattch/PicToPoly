package com.pictopoly.polydemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pictopoly.polydemo.nav.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Samuel on 1/21/2015.
 */
public class NavigationFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    protected View navAddImageOptions, navPointOptions;
    protected List<NavigationElement> navElements = new LinkedList<NavigationElement>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceBundle) {
        View view = inflater.inflate(R.layout.fragment_nav, parent, false);
        Typeface materialTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/material_design_icons.ttf");

        // Make Options Invisible
        navAddImageOptions = view.findViewById(R.id.nav_image_options);
        if(navAddImageOptions != null)
            navAddImageOptions.setVisibility(View.INVISIBLE);
        navPointOptions = view.findViewById(R.id.nav_point_options);
        if(navPointOptions != null)
            navPointOptions.setVisibility(View.INVISIBLE);

        // 5 Main Buttons
        navElements.add(new AddImageNavigationElement(R.id.nav_add_image));
        navElements.add(new PointOptionsNavigationElement(R.id.nav_add_points));
        navElements.add(new SliderNavigationElement(R.id.nav_edit_image));                  // Needs its own Option Nav El Class
        navElements.add(new ChangeTriangleViewNavigationElement(R.id.nav_change_view));
        navElements.add(new SaveImageNavigationElement(R.id.nav_save_image));

        // Add Image Options
        navElements.add(new OpenImageIntentNavigationElement(R.id.nav_open_image));
        navElements.add(new CameraIntentNavigationElement(R.id.nav_camera));

        // Add Point Options
        navElements.add(new ProcessImageNavigationElement(R.id.nav_process_image));
        navElements.add(new NavigationElement(R.id.nav_add_single_point) {
            @Override
            public void onClick(View view) {
                Activity a = NavigationFragment.this.getActivity();
                if(a instanceof PolyActivity)
                    ((PolyActivity)a).setChangingSinglePoint(true);
            }
        });
        navElements.add(new NavigationElement(R.id.nav_add_line_point) {
            @Override
            public void onClick(View view) {
                Activity a = NavigationFragment.this.getActivity();
                if(a instanceof PolyActivity)
                    ((PolyActivity)a).setChangingSinglePoint(false);
            }
        });

        // Add Insert/Remove Buttons
        navElements.add(new NavigationElement(R.id.nav_insert_points) {
            @Override
            public void onClick(View view) {
                Activity a = NavigationFragment.this.getActivity();
                if(a instanceof PolyActivity)
                    ((PolyActivity)a).setAddingPoints(true);
            }
        });
        navElements.add(new NavigationElement(R.id.nav_remove_points) {
            @Override
            public void onClick(View view) {
                Activity a = NavigationFragment.this.getActivity();
                if(a instanceof PolyActivity)
                    ((PolyActivity)a).setAddingPoints(false);
            }
        });

        for(final NavigationElement navEl : navElements) {
            View v = view.findViewById(navEl.getId());
            navEl.setView(v);

            if(v instanceof TextView)
                ((TextView) v).setTypeface(materialTypeface);

            if(null == v)
                Log.d(TAG,navEl.getClass().getSimpleName() + " " + navEl.getId());
            else
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity a = NavigationFragment.this.getActivity();
                    if(a instanceof PolyActivity)
                        ((PolyActivity)a).clearAllOptions();

                    if(navEl instanceof IntentNavigationElement)
                        ((IntentNavigationElement)navEl).onClick(v, NavigationFragment.this); // Seriously magic 'this' action here
                    else
                        navEl.onClick(v);
                }
            });
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Returned From Activity");
        if(resultCode == Activity.RESULT_OK) {
            int rCode = requestCode;

            for (NavigationElement navEl : navElements) {
                if (navEl instanceof IntentNavigationElement) {
                    IntentNavigationElement intentNav = (IntentNavigationElement) navEl;
                    if (intentNav.getRequestCode() == rCode) {
                        Log.d(TAG,navEl.getClass().getSimpleName());
                        intentNav.onActivityResult(requestCode,resultCode,data);
                    }
                }
            }
        } else
        Log.d(TAG, "Result: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private String getPath(Uri uri) {
//        if(uri == null) {
//            Toast.makeText(getActivity(), R.string.image_uri_null, Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "Uri null");
//            return null;
//        }
//
//        Log.d(TAG, "Trying to make Cursor");
//        // try to retrieve the image from the media store first
//        // this will only work for images selected from gallery
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
//        if( cursor != null ) {
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }
//
//        // fallback code
//        return uri.getPath();
//    }
}
