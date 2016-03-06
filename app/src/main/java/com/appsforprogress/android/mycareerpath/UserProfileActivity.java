package com.appsforprogress.android.mycareerpath;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class UserProfileActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new UserProfileFragment();
    }
}
