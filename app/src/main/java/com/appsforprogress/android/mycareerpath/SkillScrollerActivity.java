package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Oswald on 1/24/2016.
 * SkillScrollerActivity: scroll through all skills by swiping left and right
 */
public class SkillScrollerActivity extends AppCompatActivity
{
    private ViewPager mViewPager;
    private List<Skill> mSkills;
    // Add an Extra to hold the SkillList item selected for display in the SkillScrollerActivity
    private static final String EXTRA_SKILL_ID = "com.appsforprogress.android.mycareerpath.skill_id";

    // Add an Extra to hold the SkillScroller item index to be returned to the SkillList
    private static final String EXTRA_SKILL_INDEX = "com.appsforprogress.android.mycareerpath.skill_index";

    // Communicate with the SkillListActivity about the position of the Skill deleted
    public static Intent newIntent(Context packageContext, UUID skillId)
    {
        // Intent(HASH)
        Intent intent = new Intent(packageContext, SkillScrollerActivity.class);

        // Insert an extra key, value pair into the hash
        // This extra is sent to the SkillFragment which skill to display
        intent.putExtra(EXTRA_SKILL_ID, skillId);

        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Load the layout file
        setContentView(R.layout.activity_skill_scroller);

        // The skill passed in through the Intent:
        UUID skillId = (UUID) getIntent().getSerializableExtra(EXTRA_SKILL_ID);

        // Get a reference to the ViewPager widget
        mViewPager = (ViewPager) findViewById(R.id.activity_skill_scroller_view_pager);

        mSkills = SkillList.get(this).getSkills();

        // Get the fragmentManager for this Activity:
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Have the viewPager Adapter manage the Fragment for this Activity
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager)
        {
            @Override
            public Fragment getItem(int position)
            {
                Skill skill = mSkills.get(position);
                return SkillFragment.newInstance(skill.getId());
            }

            @Override
            public int getCount()
            {
                return mSkills.size();
            }
        });

        // Loop through all skills
        // and check if one matches the passed in skillId
        for (int i = 0; i < mSkills.size(); i++)
        {
            // Get the skill, then its id and check if match
            if (mSkills.get(i).getId().equals(skillId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    // Function called by the SkillListFragment to get the Index
    public static int getSkillIndex(Intent result)
    {
        return result.getIntExtra(EXTRA_SKILL_INDEX, 0);
    }
}
