package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.appsforprogress.android.mycareerpath.database.AttributeDBHelper;

/**
 * Created by Oswald on 3/5/2016.
 */
public class AttrTabsFragment extends Fragment
{
    private SQLiteDatabase mAttributesDatabase;


    public static AttrTabsFragment newInstance() //UUID userId)
    {
        Bundle args = new Bundle();
        // args.putSerializable(ARG_USER_ID, userId);

        AttrTabsFragment fragment = new AttrTabsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Initialize view from the fragment_skill_list layout file
        View view = inflater.inflate(R.layout.fragment_attr_tabs, container, false);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        Context context = this.getActivity().getApplicationContext();

        // Want to create DB here:
        // Create the db and its empty tables and load data into tables
        mAttributesDatabase = new AttributeDBHelper(context).getWritableDatabase();

        // Fill each DB table and create Attribute Lists per Type
        final AttributeTabPagerAdapter attrPagerAdapter = new AttributeTabPagerAdapter(this.getActivity().getSupportFragmentManager(), getResources()); // ,mMainMenuOptions[position]

        tabLayout.removeAllTabs();

        // Retrieve the tabs for the layout from the pagerAdapter
        tabLayout.setTabsFromPagerAdapter(attrPagerAdapter);

        // Have the viewpager listen for tab changes:
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setAdapter(attrPagerAdapter);

        // Define what to do at different times when a tab is selected:
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {

            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}
