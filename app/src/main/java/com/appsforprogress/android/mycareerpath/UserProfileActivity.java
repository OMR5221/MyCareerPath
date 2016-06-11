package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.UUID;

public class UserProfileActivity extends SingleFragmentActivity
{
    private static final String EXTRA_USER_ID = "com.appsforprogress.android.mycareerpath.user_id";

    public static Intent newIntent(Context packageContext) //, UUID userId)
    {
        Intent intent = new Intent(packageContext, UserProfileActivity.class);
        //intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        // return new UserProfileFragment();

        // UUID userId = (UUID) getIntent().getSerializableExtra(EXTRA_USER_ID);

        return UserProfileFragment.newInstance(); //userId);
    }
}
