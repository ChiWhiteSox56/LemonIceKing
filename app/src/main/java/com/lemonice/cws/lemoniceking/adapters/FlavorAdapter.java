package com.lemonice.cws.lemoniceking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.lemonice.cws.lemoniceking.R;
import com.lemonice.cws.lemoniceking.flavor.Flavor;
import com.lemonice.cws.lemoniceking.ui.MainActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by cindymichalowski on 7/22/17.
 */

public class FlavorAdapter extends BaseAdapter {

    private final Context mContext;
    private final Flavor[] mFlavors;

    public FlavorAdapter(Context context, Flavor[] flavors) {
        mContext = context;
        mFlavors = flavors;
    }

    @Override
    public int getCount() {
        return mFlavors.length;
    }

    @Override
    public Object getItem(int position) {
        return mFlavors[position];
    }

    // can be used to tag items for easy reference; currently not using
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        // if convertView is null, it must be set to a view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_flavor, null);

            holder = new ViewHolder();
            holder.flavorLabel = (TextView) convertView.findViewById(R.id.flavorLabel);
            holder.flavorCheckbox = (CheckBox) convertView.findViewById(R.id.flavorCheckbox);

            convertView.setTag(holder);

        } else {
            // if ConvertView is not null, we just need to get the holder from convertView
            holder = (ViewHolder) convertView.getTag();
        }

        holder.flavorLabel.setText(mFlavors[position].getLabel());

        if (mFlavors[position].getValue() == 1) {
            holder.flavorCheckbox.setChecked(true);
        } else {
            holder.flavorCheckbox.setChecked(false);
        }

        // perform on ClickListener when I integrate custom drawable instead of checkmark (see ScoreCard app)

        return convertView;
    }

    // need a ViewHolder class to hold the views; need variables for each view in item_flavor.xml
    private class ViewHolder {
        TextView flavorLabel;
        CheckBox flavorCheckbox;
    }
}
