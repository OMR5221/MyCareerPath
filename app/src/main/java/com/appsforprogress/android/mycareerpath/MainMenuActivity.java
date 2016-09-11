package com.appsforprogress.android.mycareerpath;

import android.app.SearchManager;
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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.appsforprogress.android.mycareerpath.database.AttributeDBHelper;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Oswald on 3/12/2016.
 */
public class MainMenuActivity
    extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
{
    private static final String TAG = "MainMenuActivity";
    private static final String EXTRA_FIRST_NAME = "last_tab_position";
    private static final String EXTRA_LAST_NAME = "last_tab_position";
    private static final String EXTRA_IMAGE_LINK = "last_tab_position";
    private static final String LAST_TAB_POSITION = "last_tab_position";

    private int mCurrentNavPosition;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private Toolbar mMainMenuToolbar;
    private MainMenuOption[] mMainMenuOptions = MainMenuOption.values();

    // For Database Usage:
    private Context mContext;
    private SQLiteDatabase mAttributesDatabase;

    // For FaceBook Login:
    CallbackManager mCallbackManager;

    public static Intent launchProfile(Context packageContext, String firstName, String lastName, String profileImg)
    {
        Intent userProfile = new Intent(packageContext, MainMenuActivity.class);

        userProfile.putExtra(LAST_TAB_POSITION, 0);
        userProfile.putExtra(EXTRA_FIRST_NAME, firstName);
        userProfile.putExtra(EXTRA_LAST_NAME, lastName);
        userProfile.putExtra(EXTRA_IMAGE_LINK, profileImg);

        return userProfile;
    }

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

        // Want to create DB here:
        // Create the db and its empty tables and load data into tables
        mAttributesDatabase = new AttributeDBHelper(mContext).getWritableDatabase();

        // Set up tabs and their titles
        if (savedInstanceState == null)
        {
            // Default to the UserProfile Activity:
            //new UserProfileFragment.newInstance();
            displayView(R.id.nav_menu_user);
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
    */

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
            // Review the Users Likes for career pssobilities
            case R.id.nav_menu_explore:
                mCurrentNavPosition = 2;
                fragment = launchLikeGallery();
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


    private Fragment launchLikeGallery()
    {
        return LikeGalleryFragment.newInstance();
    }


    private Fragment setupAttrTabs()
    {
        return AttrTabsFragment.newInstance();
    }

    /*
    public void onValidLogIn(Profile userProfile)
    {
        // User logged in: Call the fragment displaying their logged profile
        // new UserProfileLogoutFragment.newInstance();

        // Otherwise, we're in the one-pane layout and must swap frags...

        // Create fragment and give it an argument for the selected article
        UserProfileLogOutFragment logoutFragment = new UserProfileLogOutFragment();
        Bundle args = new Bundle();
        args.putInt(UserProfileLogOutFragment.ARG_PROFILE, userProfile);
        logoutFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, logoutFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
    */
}
