package com.pictopoly.polydemo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Samuel on 1/21/2015.
 */
public class NavigationFragment extends Fragment {
    protected Button mOpenImageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.fragment_nav, parent, false);

        mOpenImageButton = (Button)v.findViewById(R.id.nav_open_image);
        mOpenImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open an Image from the gallery here
            }
        });

        return v;
    }
}
