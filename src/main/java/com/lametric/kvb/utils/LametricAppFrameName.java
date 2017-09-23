package com.lametric.kvb.utils;

public class LametricAppFrameName extends LametricAppFrame{

    public LametricAppFrameName(){
    }

    public LametricAppFrameName(String text){
        this.text = text;
    }

    public LametricAppFrameName(String text, String icon){
        this.text = text;
        this.icon = icon;
    }

    public String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
