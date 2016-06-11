package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

        // Set up tabs and their titles
        if (savedInstanceState == null)
        {
            // Default to the UserProfile Activity:
            //new UserProfileFragment.newInstance();
            displayView(R.id.nav_menu_user);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        displayView(menuItem.getItemId());
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displayView(int viewId)
    {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        // Set the position for the elements in the Main Menu:
        switch (viewId)
        {
            case R.id.nav_menu_user:
                mCurrentNavPosition = 0;
                fragment = launchUserProfile();
                break;
            case R.id.nav_menu_attributes:
                mCurrentNavPosition = 1;
                fragment = setupAttrTabs();
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

        if (fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(title);
        }
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

        displayView(menuItem.getItemId());
    }

    // Save key/value entries to Bundle upon Pause
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // Save our last tab position.
        outState.putInt(LAST_TAB_POSITION, mCurrentNavPosition);
    }

    private Fragment launchUserProfile()
    {
        // Intent intent = UserProfileActivity.newIntent(this);
        // startActivity(intent);
        return UserProfileFragment.newInstance();
    }

    private Fragment setupAttrTabs()
    {
        return AttrTabsFragment.newInstance();
    }
}
