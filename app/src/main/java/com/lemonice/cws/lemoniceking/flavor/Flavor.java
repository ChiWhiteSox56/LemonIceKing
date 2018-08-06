package com.lemonice.cws.lemoniceking.flavor;

/**
 * Created by cindymichalowski on 7/22/17.
 */

public class Flavor {

    private String mLabel;
    private boolean mValue;

    public Flavor(String label, boolean value) {

        mLabel = label;
        mValue = value;
    }

    public String getLabel() {
        return mLabel;
    }

    public boolean getValue() {
        return mValue;
    }

    public void setValue(boolean value) {
        mValue = value;
    }

}
