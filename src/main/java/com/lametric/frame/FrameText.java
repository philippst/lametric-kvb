package com.lametric.frame;

public class FrameText extends FrameAbstract {

    public FrameText(){
    }

    public FrameText(String text){
        this.text = text;
    }

    public FrameText(String text, String icon){
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
