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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AttributeArrayAdapter<T extends Attribute> extends ArrayAdapter
{
    private TextView mAttributeNameTextView;
    private TextView mCareerNameTextView;
    private TextView mElementNameTextView;
    private Attribute mAttribute;
    private String mAttributeType;

    private final LayoutInflater mLayoutInflater;

    public AttributeArrayAdapter(Context context, List<T> objects, String attrType)
    {
        super(context, -1, objects);
        mLayoutInflater = LayoutInflater.from(context);
        mAttributeType = attrType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.list_item_attribute, parent, false);
        }

        T attr = null; //getItem(position);

        String className = "com.appsforprogress.android.mycareerpath" + mAttributeType;

        Class<?> c = null;

        try {

            // Get the Class Object:
             c = Class.forName(className);

            Constructor<?> ct;

            try {

                ct = c.getConstructor();

                try {
                    // Create a new Instance of the Class
                    Object o = ct.newInstance();

                    attr = (T) o;
                }
                catch (InstantiationException is)
                {
                    System.err.println("Cannot create an instance of the Class.");
                    System.exit(0);
                }
                catch (IllegalAccessException ia)
                {
                    System.err.println("No access to create an instance of this Class.");
                    System.exit(0);
                }
                catch (InvocationTargetException iv)
                {
                    System.err.println("AttributeList: Issue invoking instance of Object.");
                    System.exit(0);
                }
            }
            catch (NoSuchMethodException e)
            {
                System.err.println("No applicable Constructor found.");
                System.exit(0);
            }
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("No applicable Class found.");
            System.exit(0);
        }

        T out = null;

        try
        {

            // GET the method to be invoked:
            Method m = attr.getClass().getMethod("getId", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = (T) m.invoke(attr);
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }


        // Set TextViews
        mAttributeNameTextView = (TextView) convertView.findViewById(R.id.attribute_name);
        mAttributeNameTextView.setText(attr.getCareerName());

        //mCareerNameTextView = (TextView) convertView.findViewById(R.id.career_name);
        //mCareerNameTextView.setText(attr);

        mElementNameTextView = (TextView) convertView.findViewById(R.id.element_name);
        mElementNameTextView.setText(attr.getElementName());

        // Set color for thumbnail
        convertView.findViewById(R.id.thumbnail).setBackgroundColor(getThumbnailColor(attr.getId().hashCode()));

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