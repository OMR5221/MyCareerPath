package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.UUID;

public class SkillActivity extends SingleFragmentActivity
{
    // Add an Extra to hold the SkillList item selected for display in this SkillActivity
    private static final String EXTRA_SKILL_ID = "com.appsforprogress.android.mycareerpath.skill_id";

    public static Intent newIntent(Context packageContext, UUID skillId)
    {
        // Intent(HASH)
        Intent intent = new Intent(packageContext, SkillActivity.class);

        // Insert an extra key, value pair into the hash
        // This extra is sent to the SkillFragment which skill to display
        intent.putExtra(EXTRA_SKILL_ID, skillId);

        return intent;
    }

    // Method required to be implemented from the SingleFragmentActivity
    @Override
    protected Fragment createFragment()
    {
        // Get the UUID of the Skill identified by the SkillActivity's Intent extra
        UUID skillId = (UUID) getIntent().getSerializableExtra(SkillActivity.EXTRA_SKILL_ID);

        // Create a new instance of a SkillFragment
        return SkillFragment.newInstance(skillId);
    }
}
