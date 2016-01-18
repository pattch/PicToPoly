package com.pictopoly.polydemo.nav;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.process.ImageProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Marklar on 1/28/2015.
 */
public class SaveImageNavigationElement extends NavigationElement {
    public SaveImageNavigationElement(int id) {
        super(id);
    }

    public SaveImageNavigationElement(View view, int id) {
        super(view,id);
    }

    @Override
    public void onClick(View view) {
        String path = Environment.getExternalStorageDirectory().getPath() + ImageProcessor.PICTURE_PATH;
        File outputDir = new File(path);
        outputDir.mkdirs();
        FileOutputStream out = null;
        try {
            ImageProcessor handler = ImageLayerHandler.getInstance().getPolyActivityImageProcessor();
            Bitmap bitmap = handler.getProcessedImage();
            out = new FileOutputStream(path + bitmap.getGenerationId() + ".jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this.view.getContext(), "Image Save Failed", Toast.LENGTH_LONG).show();
        } finally {
            try {
                if(out != null)
                    out.close();
                Toast.makeText(this.view.getContext(), "Image Saved.", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
