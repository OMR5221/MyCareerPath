package com.appsforprogress.android.mycareerpath;

/**
 * Created by Oswald on 3/15/2016.
 */
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import java.util.ArrayList;

public class AttributeListFragment extends ListFragment implements AdapterView.OnItemClickListener
{

    private static final String ARG_ATTR_TYPE = "attrType";
    private static final String ARG_ATTR_TAB = "attrTab";

    private AttributeType mAttrType;
    private AttributeTabPagerAdapter.AttributeTab mAttrTab;
    private AttributeArrayAdapter mAttrArrayAdapter;

    public static AttributeListFragment newInstance(AttributeTabPagerAdapter.AttributeTab attrTab)
    {
        final AttributeListFragment fragment = new AttributeListFragment();
        final Bundle args = new Bundle();
        // args.putString(ARG_ATTR_TYPE, toolType.name());

        // Set the tab passed into the List as an argument when creating the object:
        args.putString(ARG_ATTR_TAB, attrTab.name());
        fragment.setArguments(args);
        return fragment;
    }

    public AttributeListFragment()
    {
        // Required empty public constructor
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

        // Generate a list of Attributes by the Tab we are currently on:
        final ArrayList<Attribute> attrs = new AttributeTestDataGen(mAttrTab.hashCode()).getTestAttributes(mAttrTab, 20);

        // Create a list of the Attribute items to be handled by the ArrayAdapter
        mAttrArrayAdapter = new AttributeArrayAdapter(getActivity(), attrs);
        setListAdapter(mAttrArrayAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        final Attribute attr = mAttrArrayAdapter.getItem(position);
        AttributeDetailActivity.startActivity(getActivity(), attr);
    }
}