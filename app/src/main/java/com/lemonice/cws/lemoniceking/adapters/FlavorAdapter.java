package com.lemonice.cws.lemoniceking.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.lemonice.cws.lemoniceking.R;
import com.lemonice.cws.lemoniceking.ui.MainActivity;

import java.util.ArrayList;

/**
 * Created by cindymichalowski on 7/22/17.
 */

public class FlavorAdapter<String> extends BaseAdapter {

    Context mContext;
    ArrayList<String> mFlavors;
    LayoutInflater inflater;

    public FlavorAdapter(Context context, ArrayList<String> flavors) {
        mContext = context;
        mFlavors = flavors;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mFlavors.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    // can be used to tag items for easy reference; currently not using
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = inflater.inflate(R.layout.item_flavor, null);
        final CheckedTextView simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.simple_checked_text_view);
        simpleCheckedTextView.setText(mFlavors.get(position).toString());
        simpleCheckedTextView.setChecked(false);
        simpleCheckedTextView.setCheckMarkDrawable(0);

        // perform on Click Event Listener on CheckedTextview
        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (simpleCheckedTextView.isChecked()) {
                    // set checkmark drawable and set checked to false
                    simpleCheckedTextView.setCheckMarkDrawable(0);
                    simpleCheckedTextView.setChecked(false);

                } else {
                    // set checkmark drawable and set checked to false
                    simpleCheckedTextView.setCheckMarkDrawable(R.mipmap.checked);
                    simpleCheckedTextView.setChecked(true);
                }
            }
        });
        return view;
    }

}
