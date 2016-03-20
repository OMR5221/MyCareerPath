package com.appsforprogress.android.mycareerpath;

/**
 * Created by Oswald on 3/15/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


public class AttributeDetailActivity extends AppCompatActivity
{

    private static final String EXTRA_ATTRIBUTE = "com.appsforprogress.android.mycareerpath.ATTRIBUTE";

    public static void startActivity(Context context, Attribute attr)
    {
        final Intent intent = new Intent(context, AttributeDetailActivity.class);
        intent.putExtra(EXTRA_ATTRIBUTE, attr);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Attribute attr = getIntent().getParcelableExtra(EXTRA_ATTRIBUTE);

        if (attr == null)
        {
            throw new IllegalStateException("Tool not available as extra; use startActivity when creating an activity instance");
        }

        findAndSetTextView(R.id.detail_attribute_name, attr.getAttributeName());
        findAndSetTextView(R.id.detail_career_name, attr.getCareerName());
        findAndSetTextView(R.id.detail_element_name, attr.getElementName());
        //findAndSetTextView(R.id.detail_0, tool.getDetails()[0]);
        //findAndSetTextView(R.id.detail_1, tool.getDetails()[1]);
        //findAndSetTextView(R.id.detail_2, tool.getDetails()[2]);
    }

    private void findAndSetTextView(int id, String text)
    {
        final TextView textView = (TextView) findViewById(id);
        textView.setText(text);
    }
}