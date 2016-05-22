package com.appsforprogress.android.mycareerpath;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.security.PrivilegedAction;

/**
 * Created by Oswald on 3/12/2016.
 */

// We will define the tabs displayed for the user when they select the Attributes Menu
public class AttributeTabPagerAdapter extends FragmentPagerAdapter
{
    public enum AttributeTab
    {
        SKILLS(R.string.attributes_skill)
        //,KNOWLEDGE(R.string.attributes_knowledge),
        //ABILITIES(R.string.attributes_abilities),
        //EDUCATION_TRAINING_EXPERIENCE(R.string.attributes_edctrnexp),
        //INTERESTS(R.string.attributes_interests)
        ;

        // Tells us the tab selected
        private final int mStringResource;

        AttributeTab(@StringRes int stringResource)
        {
            mStringResource = stringResource;
        }

        public int getStringResource()
        {
            return mStringResource;
        }
    }

    private final AttributeTab[] mAttributeTabs = AttributeTab.values();
    private final CharSequence[] mAttributeTitles = new CharSequence[mAttributeTabs.length];

    // Used if we are passing a value selected from Menu into the Tab to be displayed:
    /*
        private final MainMenuOption mMainMenuType;
        private final MainMenuOption[] mMainMenuTypes = MainMenuOption.values();
    */

    public AttributeTabPagerAdapter(FragmentManager fm, Resources res) //, MainMenuOption menuType
    {
        // Have the Fragment Manager handle the fragments to load into this pager
        super(fm);

        // mMainMenuType = menuType;

        // Get the title strings for the Attribute tabs:
        for (int i = 0; i < mAttributeTabs.length; i++)
        {
            mAttributeTitles[i] = res.getString(mAttributeTabs[i].getStringResource());
        }
    }

    // Set the correct AttributeListFragment based on position:
    public Fragment getItem(int position)
    {
        Fragment tabFragment = null;

        tabFragment = AttributeTabFragment.newInstance(mAttributeTabs[position], position);

        /*
        // Automatically creates the AttributeTabFragment with the intended AttributeType:
        switch (position)
        {
            case 0:
                // Create a new Attribute List fragment for each tab position:
                tabFragment = AttributeTabFragment.newInstance(mAttributeTabs[0], position);
                break;
            case 1:
                // Create a new Attribute List fragment for each tab position:
                tabFragment = AttributeTabFragment.newInstance(mAttributeTabs[1]);
                break;
            case 2:
                // Create a new Attribute List fragment for each tab position:
                tabFragment = AttributeTabFragment.newInstance(mAttributeTabs[2]);
                break;
            case 3:
                // Create a new Attribute List fragment for each tab position:
                tabFragment = AttributeTabFragment.newInstance(mAttributeTabs[3]);
                break;
            case 4:
                // Create a new Attribute List fragment for each tab position:
                tabFragment =  AttributeTabFragment.newInstance(mAttributeTabs[4]);
                break;

            default:
                // Create a new Attribute List fragment for each tab position:
                tabFragment = AttributeTabFragment.newInstance(mAttributeTabs[0]);
                break;
        }
        */

        return tabFragment;
    }

    public int getCount()
    {
        return mAttributeTabs.length;
    }

    public CharSequence getPageTitle(int position)
    {
        return mAttributeTitles[position];
    }

    // Generate a simple UUID for the tab selected from the Attribute Menu and the tab position index:
    public long getTabPosition(int position)
    {

        switch (position)
        {
            case 0: // Skills
            case 1: // Knowledge
            case 2: // Abilities
            case 3: // Education, Training, Experience
            case 4: // Interests
                return position;
        }
        throw new IllegalArgumentException("Unhandled tab position selected: " + position + " or Menu Option");
    }
}
