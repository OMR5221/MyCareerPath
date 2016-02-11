package com.appsforprogress.android.mycareerpath;

import android.support.v4.app.Fragment;

/**
 * Created by Oswald on 1/10/2016.
 */
public class SkillListActivity extends SingleFragmentActivity
{

    @Override
    // Describe the Fragment to be implemented
    protected Fragment createFragment()
    {
        return new SkillListFragment();
    }
}
