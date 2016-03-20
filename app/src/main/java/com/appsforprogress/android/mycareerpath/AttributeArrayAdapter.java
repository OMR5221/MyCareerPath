package com.appsforprogress.android.mycareerpath;

/**
 * Created by Oswald on 3/15/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class AttributeArrayAdapter extends ArrayAdapter<Attribute>
{
    private TextView mAttributeNameTextView;
    private TextView mCareerNameTextView;
    private TextView mElementNameTextView;
    private Attribute mAttribute;

    private final LayoutInflater mLayoutInflater;

    public AttributeArrayAdapter(Context context, List<Attribute> objects)
    {
        super(context, -1, objects);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.list_item_attribute, parent, false);
        }

        final Attribute attr = getItem(position);

        // Set TextViews
        mAttributeNameTextView = (TextView) convertView.findViewById(R.id.attribute_name);
        mAttributeNameTextView.setText(attr.getAttributeName());

        mCareerNameTextView = (TextView) convertView.findViewById(R.id.career_name);
        mCareerNameTextView.setText(attr.getCareerName());

        mElementNameTextView = (TextView) convertView.findViewById(R.id.element_name);
        mElementNameTextView.setText(attr.getElementName());

        // Set color for thumbnail
        convertView.findViewById(R.id.thumbnail).setBackgroundColor(getThumbnailColor(attr.getAttributeName().hashCode()));

        return convertView;
    }

    private int getThumbnailColor(int key)
    {
        switch (Math.abs(key) % 3) {
            case 0:
                return 0xff777777;
            case 1:
                return 0xff999999;
            case 2:
                return 0xffbbbbbb;
        }

        return 0;
    }
}