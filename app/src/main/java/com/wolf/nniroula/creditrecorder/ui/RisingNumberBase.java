package com.wolf.nniroula.creditrecorder.ui;

public interface RisingNumberBase {
    public void start();
    public RisingNumberView withNumber(float number);
    public RisingNumberView withNumber(int number);
    public RisingNumberView setDuration(long duration);
    public void setOnEnd(RisingNumberView.EndListener callback);
}
