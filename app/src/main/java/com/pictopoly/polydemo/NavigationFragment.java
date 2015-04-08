package com.pictopoly.polydemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pictopoly.polydemo.nav.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Samuel on 1/21/2015.
 */
public class NavigationFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    protected View navAddImageOptions, navPointOptions, navAutoSettings;
    protected List<NavigationElement> navElements = new LinkedList<NavigationElement>();
    protected List<SliderElement> sliders = new LinkedList<SliderElement>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceBundle) {
        View view = inflater.inflate(R.layout.fragment_nav, parent, false);
        Typeface materialTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/material_design_icons.ttf"),
                jsLightTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/josefin_light.ttf"),
                lobsterTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/lobster.ttf");

        // Change Fonts of Splash Text
        TextView textView = (TextView)view.findViewById(R.id.nav_logo_text_1);
        textView.setTypeface(jsLightTypeface);
        textView = (TextView)view.findViewById(R.id.nav_logo_text_2);
        textView.setTypeface(lobsterTypeface);
        textView = (TextView)view.findViewById(R.id.nav_logo_text_3);
        textView.setTypeface(jsLightTypeface);
        textView = (TextView)view.findViewById(R.id.nav_poly_settings_text);
        textView.setTypeface(jsLightTypeface);

        navAutoSettings = view.findViewById(R.id.nav_auto_settings);
        if(navAutoSettings != null)
            navAutoSettings.setVisibility(View.INVISIBLE);


        // Main Buttons
        navElements.add(new ChangeTriangleViewNavigationElement(R.id.nav_change_view));
        navElements.add(new SaveImageNavigationElement(R.id.nav_save_image));
        navElements.add(new ProcessImageNavigationElement(R.id.nav_process_image));

        // Add Auto Settings Opener
        navElements.add(new AutoSettingsNavigationElement(R.id.nav_change_auto_settings));
        navElements.add(new AutoSettingsNavigationElement(R.id.nav_auto_settings));

        // Add Close Button
        navElements.add(new CloseActivityNavigationElement(R.id.nav_close_image));

        // Auto Process Settings
        navElements.add(new ProcessImageNavigationElement(R.id.nav_triangle_button));
//        navElements.add(new ProcessImageNavigationElement(R.id.nav_process_options));

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

        // Add Auto Settings Sliders
        sliders.add(new EdgePointsSliderElement(R.id.tri_delaunay_edge_points_slider));
//        sliders.add(new NearEdgePointsSliderElement(R.id.tri_delaunay_near_edge_points_slider));
//        sliders.add(new NearEdgeDistanceSliderElement(R.id.tri_delaunay_near_edge_distance_slider));
        sliders.add(new RandomPointsSliderElement(R.id.tri_delaunay_random_points_slider));

        for(final SliderElement slideEl : sliders) {
            SeekBar s = (SeekBar)view.findViewById(slideEl.getId());
            slideEl.setSeekBar(s);

            if(s != null) {
                s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        slideEl.onProgressChanged(seekBar,progress,fromUser);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        slideEl.onStartTrackingTouch(seekBar);
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        slideEl.onStopTrackingTouch(seekBar);
                    }
                });
            }
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

    // Sets the Background color of the Options and Top Bars to The Specified Colors
    public void setNavColors(int color, Activity context) {
        for(int id : PolyActivity.optionElements) {
            View v = context.findViewById(id);
            if(v != null)
                v.setBackgroundColor(color);
        }

        View v = context.findViewById(R.id.nav_top_bar);
        if(v != null) v.setBackgroundColor(color);
        v = context.findViewById(R.id.nav_main_bar);
        if(v != null) v.setBackgroundColor(color);
        v = context.findViewById(R.id.nav_auto_settings_top_bar);
        if(v != null) v.setBackgroundColor(color);
        v = context.findViewById(R.id.nav_auto_settings);
        if(v != null) v.setBackgroundColor(Color.parseColor("#ffffff"));
    }
}
