package com.lemonice.cws.lemoniceking.flavor;

/**
 * Created by cindymichalowski on 7/22/17.
 */

public class Flavor {

    private String mLabel;
    private int mValue;

    public Flavor(String label, int value) {
        mLabel = label;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

}
