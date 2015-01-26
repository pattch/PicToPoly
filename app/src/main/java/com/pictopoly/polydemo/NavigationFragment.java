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

import org.w3c.dom.Text;

/**
 * Created by Samuel on 1/21/2015.
 */
public class NavigationFragment extends Fragment {
    protected TextView mOpenImageButton, mProcessImageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceBundle) {
        View view = inflater.inflate(R.layout.fragment_nav, parent, false);

        Typeface materialTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/material_design_icons.ttf");

        mOpenImageButton = (TextView)view.findViewById(R.id.nav_open_image);
        mOpenImageButton.setTypeface(materialTypeface);
//        mOpenImageButton.setText(R.string.md_photo);
        mOpenImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PolyActivity.SELECT_PICTURE);
            }
        });

        mProcessImageButton = (TextView)view.findViewById(R.id.nav_process_image);
        mProcessImageButton.setTypeface(materialTypeface);
//        mProcessImageButton.setText(R.string.md_brush);
        mProcessImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: make it process the image here.
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == PolyActivity.SELECT_PICTURE) {
            Uri imageUri = data.getData();
            String imagePath = getPath(imageUri);
            if(imagePath != null) {
                Bitmap map = BitmapFactory.decodeFile(imagePath);
                if(map != null) {
                    ImageLayerHandler.getInstance().getProcessor().setImage(map);
                    ImageLayerHandler.getInstance().getProcessor().processImage();
                    ((PolyActivity) getActivity()).getImageProcessFragment().refreshImageView();
                }
            }
        }
    }

    private String getPath(Uri uri) {
        if(uri == null) {
            Toast.makeText(getActivity(), R.string.image_uri_null, Toast.LENGTH_SHORT).show();
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
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
