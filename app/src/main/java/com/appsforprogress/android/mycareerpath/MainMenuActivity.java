package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.appsforprogress.android.mycareerpath.database.AttributeDBHelper;

/**
 * Created by Oswald on 3/12/2016.
 */
public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private static final String TAG = "MainMenuActivity";
    private static final String LAST_TAB_POSITION = "last_tab_position";

    private int mCurrentNavPosition;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private Toolbar mMainMenuToolbar;
    private MainMenuOption[] mMainMenuOptions = MainMenuOption.values();

    // For Database Usage:
    private Context mContext;
    private SQLiteDatabase mAttributesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mMainMenuToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mMainMenuToolbar);

        // Enable opening drawer from Main Menu Activity:
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Define icon for user to open Menu:
        mMainMenuToolbar.setNavigationIcon(R.drawable.ic_menu_launcher);

        // Define what to do on click of the icon:
        mMainMenuToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Add Drawer Listener
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mContext = getApplicationContext();

        /*
        // Set up tabs and their titles
        if (savedInstanceState == null)
        {
            // Default to the UserProfile Activity:
            //new UserProfileFragment.newInstance();
            setupAttrTabs();
            // setupAttrTabs(0);
        }
        */
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        // Set the position for the elements in the Main Menu:
        switch (menuItem.getItemId())
        {
            case R.id.nav_menu_user:
                mCurrentNavPosition = 0;
                break;
            case R.id.nav_menu_attributes:
                mCurrentNavPosition = 1;
                setupAttrTabs();
                break;
            case R.id.nav_menu_explore:
                mCurrentNavPosition = 2;
                break;
            case R.id.nav_menu_learn:
                mCurrentNavPosition = 3;
                break;
            case R.id.nav_menu_connect:
                mCurrentNavPosition = 4;
                break;
            default:
                Log.w(TAG, "Unknown navigation drawer item selected.");
                break;
        }

        menuItem.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Used to restore the Bundle Hash
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        mCurrentNavPosition = savedInstanceState.getInt(LAST_TAB_POSITION, 0);

        final Menu menu = mNavigationView.getMenu();

        // Set the checked it to the last position saved in the bundle hash
        final MenuItem menuItem = menu.getItem(mCurrentNavPosition);

        menuItem.setChecked(true);

        if (menuItem.getItemId() == R.id.nav_menu_attributes)
        {
            setupAttrTabs();
        }
    }

    // Save key/value entries to Bundle upon Pause
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // Save our last tab position.
        outState.putInt(LAST_TAB_POSITION, mCurrentNavPosition);
    }

    private void setupAttrTabs()
    {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Want to create DB here:
        // Create the db and its empty tables and load data into tables
        mAttributesDatabase = new AttributeDBHelper(mContext).getWritableDatabase();

        // Fill each DB table and create Attribute Lists per Type
        final AttributeTabPagerAdapter attrPagerAdapter = new AttributeTabPagerAdapter(getSupportFragmentManager(), getResources()); // ,mMainMenuOptions[position]

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


    }
}
