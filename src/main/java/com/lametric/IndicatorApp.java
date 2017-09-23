package com.lametric;

import com.lametric.frame.FrameAbstract;

import java.util.ArrayList;

public class IndicatorApp {

    public ArrayList<FrameAbstract> frames = new ArrayList<>();

    public ArrayList<FrameAbstract> getFrames() {
        return frames;
    }

    public void setFrames(ArrayList<FrameAbstract> frames) {
        this.frames = frames;
    }

    public void addFrame(FrameAbstract frame) {
        this.frames.add(frame);
    }

}
