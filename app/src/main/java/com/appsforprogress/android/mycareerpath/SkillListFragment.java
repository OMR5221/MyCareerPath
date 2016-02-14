package com.appsforprogress.android.mycareerpath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Oswald on 1/16/2016.
 */
public class SkillListFragment extends Fragment
{
    private RecyclerView mSkillRecyclerView;
    private SkillAdapter mAdapter;
    private Integer mPosition;
    private Integer mViewId;
    private boolean mCountMenuVisible;
    private static final String SAVED_COUNT_MENU_VISIBLE = "count";
    private static final Integer REQUEST_SKILL_INDEX = 1;
    private Integer mPriorCount;
    private Integer mCurrentCount;

    @Override
    //
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Tells the FragmentManager that this Fragment has a menu
        // so the onCreateOptionsMenu() needs to be run:
        setHasOptionsMenu(true);
    }


    @Override
    // Creates the Activities Menu:
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        // Inflate the Menu resource layout
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_skill_list, menu);

        // Get the countMenu reference:
        MenuItem countItem = menu.findItem(R.id.menu_item_show_count);

        if (mCountMenuVisible) {
            countItem.setTitle(R.string.hide_count);
        }
        else {
            countItem.setTitle(R.string.show_count);
        }

    }

    @Override
    // Respond to the user's menu selection
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Use a switch to differentiate Menu items:
        switch (item.getItemId())
        {
            // In this case the New Skill Menu Option was selected:
            // a. Create a new skill
            // b. Add it to the Skill list
            // c. Start an instance of SkillScrollerActivity to allow the user to edit the Skill
            case R.id.menu_item_new_skill:

                Skill newSkill = new Skill();

                // Check if this Context(SkillListFragment) has a SkillList object defined
                // If not create a SkillList
                SkillList.get(getActivity()).addSkill(newSkill);

                // Define intent to start the SkillScrollerActivity by passing this skill's id reference
                Intent intent = SkillScrollerActivity.newIntent(getActivity(), newSkill.getId());

                // Call the SkillScrollerActivities onCreate()
                // and expect an Integer result (Skill index) to be returned
                startActivity(intent);

                return true;

            // Menu item to show count of skills
            case R.id.menu_item_show_count:

                mCountMenuVisible = !mCountMenuVisible;
                getActivity().invalidateOptionsMenu();
                updateCount();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Initialize view from the fragment_skill_list layout file
        View view = inflater.inflate(R.layout.fragment_skill_list, container, false);

        // Reference to the Views Recycler widget:
        mSkillRecyclerView = (RecyclerView) view.findViewById(R.id.skill_recycler_view);

        // Create a LinearLayout object to manage positioning of items and scrolling in a list vertically:
        mSkillRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Check if we have added additional values to the savedInstanceState (Bundle)
        if (savedInstanceState != null)
        {
            // Check if the Count Menu was visible or not before we paused then resumed this activity
            mCountMenuVisible = savedInstanceState.getBoolean(SAVED_COUNT_MENU_VISIBLE);
        }

        // Reload the list of skills and create an adapter
        updateUI();

        return view;
    }

    // Call on return from the SkillActivity
    public void onResume()
    {
        super.onResume();

        // Get current Count of skill list items:
        mCurrentCount = getSkillListCount();

        // Get index of item returned from SkillFragment



        updateUI();
    }

    @Override
    // Allow us to save additional values to the Bundle Hash which is read when onCreateView is called
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SAVED_COUNT_MENU_VISIBLE, mCountMenuVisible);
    }

    // Refresh the UI by loading all skills in SkillList
    // and setting up the RecyclerView's Adapter
    private void updateUI()
    {
        // Get reference to our SkillList Object
        SkillList skillList = SkillList.get(getActivity());

        // Create a new list of skills and copy the elements of our SkillList object
        List<Skill> skills = skillList.getSkills();

        // Set up the adapter if it is not already in place:
        if (mAdapter == null) {

            // Create an adapter and pass the List of skills for it to manage
            mAdapter = new SkillAdapter(skills);

            // Connect the RecyclerView to the Adapter
            mSkillRecyclerView.setAdapter(mAdapter);

        } else {

            if (mPosition == null)
            {
                // Tells the adapter to reload all of the items that are visible
                // in the RecyclerView
                mAdapter.notifyDataSetChanged();

            } else {

                Log.d("Data Item Changed:", "About to reload Adapter holder item that was edited.", new Exception());
                Log.d("Prior Count:", "About to reload Adapter holder item that was edited.", new Exception());
                // We have deleted an item from the SkillList
                if (mPriorCount > mCurrentCount)
                {
                    // Tell the adapter to expect that the holder's data was removed
                    mAdapter.notifyItemRemoved(mPosition);

                    Integer mItemCount = mCurrentCount - mPosition;

                    mAdapter.notifyItemRangeChanged(mPosition, mItemCount);
                }
                // We have edited an item from the SkillList
                else
                {
                    // Tell the adapter to reload only the item that was edited in the detail View
                    mAdapter.notifyItemChanged(mPosition);
                }
            }
        }

        // Update the skill count one refresh of the SkillList UI
        updateCount();

    }

    private int getSkillListCount()
    {
        SkillList skillList = SkillList.get(getActivity());

        // Get the int count of skills
        int skillCount = skillList.getSkills().size();

        return skillCount;
    }

    // Code to show count when menu option selected:
    private void updateCount()
    {
        // Get the int count of skills
        int skillCount = getSkillListCount();

        // Convert the int count to a string
        String totalCount = getString(R.string.subtitle_format, skillCount);

        // if the menu is not visible then set to null
        if (!mCountMenuVisible)
        {
            totalCount = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(totalCount);
    }


    // Set up the RecyclerView's Adapter: controller that sits between
    // the RecyclerView and the data and manages there interaction.
    // RecyclerView communicates with this adapter when a ViewHolder needs to be:
    // 1. created a ViewHolder or
    // 2. bind a View to a ViewHolder
    // to a Skill Object
    private class SkillAdapter extends RecyclerView.Adapter<SkillHolder>
    {
        private List<Skill> mSkills;

        public SkillAdapter(List<Skill> skills)
        {
            // Initialize adapter to manage a list of skills
            mSkills = skills;
        }

        @Override
        // Create a ViewHolder for the RecyclerView which houses a simple TextView Object (simple_list_item_1)
        public SkillHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            // Create a simple TextView using the android library:
            // View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            // Create our custom Skill View item:
            View view = layoutInflater.inflate(R.layout.list_item_skill, parent, false);

            // Create a new SkillHolder to hold the TextView
            return new SkillHolder(view);
        }

        @Override
        //  Bind a skill to a holder
        public void onBindViewHolder(SkillHolder holder, int index)
        {
            // Get a skill from the skills List (Model Data)
            Skill skill = mSkills.get(index);

            // Place the text in the TextView held by the holder to the skill object's Title
            holder.bindSkill(skill);
        }

        @Override
        // Return the number of items in the data set held by the adapter
        public int getItemCount()
        {
            return mSkills.size();
        }

        @Override
        public long getItemId(int position)
        {
            return super.getItemId(position);
        }
    }

    // Set up the RecyclerView's ViewHolder: holds a reference to a view widget
    private class SkillHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTitleTextView;
        private TextView mDateAddedTextView;
        private CheckBox mExperienceCheckBox;
        private Skill mSkill;

        public SkillHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            // Get references to the widgets described by our custom layout for a skill list item
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_skill_title_text_view);
            mDateAddedTextView = (TextView) itemView.findViewById(R.id.list_item_skill_date_text_view);
            mExperienceCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_skill_exp_check_box);
        }

        // Get a skill and bind its fields to the RecyclerView.Holder
        // initially when the holder is first created
        public void bindSkill(Skill skill)
        {
            mSkill = skill;
            mTitleTextView.setText(mSkill.getTitle());
            mDateAddedTextView.setText(mSkill.getAddedDate().toString());
            mExperienceCheckBox.setChecked(mSkill.isExperienced());
        }

        @Override
        public void onClick(View v)
        {
            // Show a toast upon a click
            // Toast.makeText(getActivity(), mSkill.getTitle() + " Clicked!", Toast.LENGTH_SHORT).show();

            // Get the prior Count of the number of items in our SkillList
            mPriorCount = getSkillListCount();

            // Create an Intent to start the Skill Scroller Activity's intent
            // to update the SkillFragment
            Intent intent = SkillScrollerActivity.newIntent(getActivity(), mSkill.getId());

            // Get the position index of the Skill List item selected:
            // INCORRECT: ITEM SELECTED NOT ALWAYS THE ITEM RETURNED
            //mPosition = this.getAdapterPosition();

            // Start the activity to update the Skill displayed in the SkillFragment
            startActivityForResult(intent, REQUEST_SKILL_INDEX);
        }
    }

    @Override
    // Have this Fragment process the result Intent sent from SkillFragment
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }

        // Retrieve the result Intent with the index of the skill last displayed by the user
        // before they return to the SkillList
        if (requestCode == REQUEST_SKILL_INDEX)
        {
            if (data == null)
            {
                return;
            }

            // Get the index value from the Intent sent to this Fragment
            mPosition = SkillScrollerActivity.getSkillIndex(data);
        }
    }
}