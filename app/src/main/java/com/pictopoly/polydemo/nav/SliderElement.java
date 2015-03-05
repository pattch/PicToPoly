package com.pictopoly.polydemo.nav;

import android.view.View;
import android.widget.SeekBar;

/**
 * Created by Marklar on 3/2/2015.
 */
public abstract class SliderElement {

    protected SeekBar seekBar;
    protected int id;

    public SliderElement(int id) {
        this.id = id;
    }

    public SliderElement(SeekBar seekBar, int id) {
        this.seekBar = seekBar;
        this.id = id;
    }

    public abstract void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser);

    public abstract void onStartTrackingTouch (SeekBar seekBar);

    public abstract void onStopTrackingTouch (SeekBar seekBar);

    public View getSeekBar() {
        return this.seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
