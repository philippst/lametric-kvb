package com.lametric.utils;

import java.util.ArrayList;

public class LametricApp {

    public ArrayList<LametricAppFrame> frames = new ArrayList<>();

    public ArrayList<LametricAppFrame> getFrames() {
        return frames;
    }

    public void setFrames(ArrayList<LametricAppFrame> frames) {
        this.frames = frames;
    }

    public void addFrame(LametricAppFrame frame) {
        this.frames.add(frame);
    }

}
