package com.appsforprogress.android.mycareerpath;

/**
 * Created by Oswald on 3/15/2016.
 */
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;

public class AttributeListFragment<T extends Attribute> extends ListFragment implements AdapterView.OnItemClickListener
{

    private static final String ARG_ATTR_TYPE = "attrType";
    private static final String ARG_ATTR_TAB = "attrTab";

    private AttributeTabPagerAdapter.AttributeTab mAttrTab;
    private AttributeArrayAdapter mAttrArrayAdapter;
    private String mAttrType;

    public static AttributeListFragment newInstance(AttributeTabPagerAdapter.AttributeTab attrTab)
    {
        String attrType = attrTab.name();
        final AttributeListFragment fragment = new AttributeListFragment(attrType);
        final Bundle args = new Bundle();
        // args.putString(ARG_ATTR_TYPE, toolType.name());

        // Set the tab passed into the List as an argument when creating the object:
        args.putString(ARG_ATTR_TAB, attrTab.name());
        fragment.setArguments(args);
        return fragment;
    }

    public AttributeListFragment(String attrType)
    {
        // Requires an empty public constructor
        mAttrType = attrType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args == null)
        {
            throw new IllegalStateException("No arguments set; use newInstance when constructing!");
        }

        // mAttrType = AttributeType.valueOf(args.getString(ARG_ATTR_TYPE));

        // Get the Tab we are currently on:
        mAttrTab = AttributeTabPagerAdapter.AttributeTab.valueOf(args.getString(ARG_ATTR_TAB));

        SkillList skills = SkillList.get(getActivity());

        // Generate a test list of Attributes by the Tab we are currently on -> Replace with DB records
        final List<Skill> skillList = skills.selectFormattedRecords();

        // Load DB records per attribute into the ListView: Get reference to our AttributeList Object
        mAttrArrayAdapter = new AttributeArrayAdapter(getActivity(), skillList, mAttrType);

        // Create a list of the Attribute items to be handled by the ArrayAdapter
        // mAttrArrayAdapter = new AttributeArrayAdapter(getActivity(), attrs);

        setListAdapter(mAttrArrayAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        final Attribute attr = mAttrArrayAdapter.getItem(position);
        AttributeDetailActivity.startActivity(getActivity(), attr);
    }
}