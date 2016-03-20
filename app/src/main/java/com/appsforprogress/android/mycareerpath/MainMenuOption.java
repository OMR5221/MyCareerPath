package com.appsforprogress.android.mycareerpath;

import android.support.annotation.StringRes;

/**
 * Created by Oswald on 3/7/2016.
 */
public enum MainMenuOption
{
    USER(R.string.user_menu_label, R.string.user_menu_description),
    ATTRIBUTES(R.string.attributes_menu_label, R.string.attributes_menu_description),
    EXPLORE(R.string.explore_menu_label, R.string.explore_menu_description),
    CONNECT(R.string.connect_menu_label, R.string.connect_menu_description),
    LEARN(R.string.learn_menu_label, R.string.learn_menu_description);

    private final int mMenuNameResourceId;
    private final int mMenuDescriptionResourceId;

    private MainMenuOption(@StringRes int menuName, @StringRes int menuDescription)
    {
        mMenuNameResourceId = menuName;
        mMenuDescriptionResourceId = menuDescription;
    }

    @StringRes
    public int getMenuDescriptionResourceId()
    {
        return mMenuDescriptionResourceId;
    }

    @StringRes
    public int getMenuNameResourceId()
    {
        return mMenuNameResourceId;
    }
}
