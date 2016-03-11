package com.appsforprogress.android.mycareerpath;

import android.support.annotation.StringRes;

/**
 * Created by Oswald on 3/7/2016.
 */
public enum ToolBarType
{
    USER(R.string.user_menu_label, R.string.user_menu_description),
    DETAIL(R.string.details_menu_label, R.string.details_menu_description),
    EXPLORE(R.string.explore_menu_label, R.string.explore_menu_description),
    CONNECT(R.string.connect_menu_label, R.string.connect_menu_description),
    LEARN(R.string.learn_menu_label, R.string.learn_menu_description);

    private final int mToolNameResourceId;
    private final int mToolDescriptionResourceId;

    private ToolBarType(@StringRes int toolName, @StringRes int toolDescription)
    {
        mToolNameResourceId = toolName;
        mToolDescriptionResourceId = toolDescription;
    }

    @StringRes
    public int getToolDescriptionResourceId()
    {
        return mToolDescriptionResourceId;
    }

    @StringRes
    public int getToolNameResourceId()
    {
        return mToolNameResourceId;
    }
}
