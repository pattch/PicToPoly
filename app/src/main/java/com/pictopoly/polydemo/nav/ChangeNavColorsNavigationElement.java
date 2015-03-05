package com.pictopoly.polydemo.nav;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.pictopoly.polydemo.PolyActivity;

/**
 * Created by Marklar on 3/2/2015.
 */
public class ChangeNavColorsNavigationElement extends NavigationElement {
    /*  #F44336 Red
        #E91E63 Pink
        #9C27B0 Purple
        #673AB7 Deep Purple
        #3F51B5 Indigo
        #2196F3 Blue
        #03A9F4 Light Blue
        #00BCD4 Cyan
        #009688 Teal
        #4CAF50 Green
        #8BC34A Light Green
        #CDDC39 Lime
        #FFEB3B Yellow
        #FFC107 Amber
        #FF9800 Orange
        #FF5722 Deep Orange
        #795548 Brown
        #9E9E9E Grey
        #607D8B*/

    protected String[] Colors = new String[] {
        "#F44336",
        "#E91E63",
        "#9C27B0",
        "#673AB7",
        "#3F51B5",
        "#2196F3",
        "#03A9F4",
        "#00BCD4",
        "#009688",
        "#4CAF50",
        "#8BC34A",
        "#CDDC39",
        "#FFEB3B",
        "#FFC107",
        "#FF9800",
        "#FF5722",
        "#795548",
        "#9E9E9E",
        "#607D8B",
    }, colorNames = new String[] {
        "Red",
        "Pink",
        "Purple",
        "Deep Purple",
        "Indigo",
        "Blue",
        "Light Blue",
        "Cyan",
        "Teal",
        "Green",
        "Light Green",
        "Lime",
        "Yellow",
        "Amber",
        "Orange",
        "Deep Orange",
        "Brown",
        "Grey",
        "Blue Grey",
    };
    protected int currentColor = 0;

    public ChangeNavColorsNavigationElement(int id) {
        super(id);
    }

    public ChangeNavColorsNavigationElement(View view, int id) {
        super(view, id);
    }

    @Override
    public void onClick(View view) {
        if(++currentColor >= Colors.length) currentColor = 0;

        Activity a = (Activity)view.getContext();
        if(a instanceof PolyActivity) {
            ((PolyActivity) a).setNavColors(Color.parseColor(Colors[currentColor]));
            Toast.makeText(a,"Color: " + colorNames[currentColor],Toast.LENGTH_SHORT).show();
        }
    }
}
