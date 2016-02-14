package com.appsforprogress.android.mycareerpath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Oswald on 1/4/2016.
 */

// Make a subclass of the Fragment class
public class SkillFragment extends Fragment
{
    // Create an instance of the Skill class for the fragment to manage
    private Skill mSkill;

    // EditText field
    private EditText mTitleField;

    // Reference for the Button to display the Date
    private Button mAddedDateButton;

    // Reference the checkbox for Experience
    private CheckBox mExperienceCheckBox;

    // Key for Skill value from SkillList
    private static final String ARG_SKILL_ID = "skill_id";

    // Key for DialogAlert to display Date
    private static final String EXTRA_REM_INDEX = "com.appsforprogress.android.mycareerpath.rem_index";

    // Constant for the RequestCode to get data data back from DatePicker Fragment
    private static final int REQUEST_DATE = 0;

    private String formatStr = "EEEE, MMMM d, yyyy";

    //
    private static final String DIALOG_DATE = "DialogDate";

    // Return a SkillFragment Instance containing the skill input
    // to the calling Activity
    public static SkillFragment newInstance(UUID skillId)
    {
        // Set up Bundle (HASH) with skill to populate skillFragment with
        Bundle args = new Bundle();
        args.putSerializable(ARG_SKILL_ID, skillId);

        // Call Constructor directly
        SkillFragment fragment = new SkillFragment();
        fragment.setArguments(args);

        return fragment;

    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Tells the FragmentManager that this Fragment has a menu
        // so the onCreateOptionsMenu() needs to be run:
        setHasOptionsMenu(true);

        // Instantiate the fragment with a Skill:
        // mSkill = new Skill();

        // Get the UUID of the Skill identified from the SkillActivity's Intent extra
        // UUID skillId = (UUID) getActivity().getIntent().getSerializableExtra(SkillActivity.EXTRA_SKILL_ID);

        // Get the UUID of the Skill passed in during the Fragments creation
        UUID skillId = (UUID) getArguments().getSerializable(ARG_SKILL_ID);

        // Get the skill from the SkillList Activity identified
        mSkill = SkillList.get(getActivity()).getSkill(skillId);


    }

    @Override
    // Creates the Activities Menu:
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        // Inflate the Menu resource layout
        super.onCreateOptionsMenu(menu, inflater);

        // Inflate the menu resource we created for this Fragment
        inflater.inflate(R.menu.fragment_skill_scroller, menu);

    }

    @Override
    // Respond to the user's menu selection
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Use a switch to differentiate Menu items:
        switch (item.getItemId())
        {
            // In this case the Remove Skill Menu Option was selected:
            // a. Delete the skill from the SkillList Object
            // b. Send the index of the deleted skill back to the SkillListFragment
            case R.id.menu_item_rem_skill:

                Log.d("REMOVAL", "About to remove an element from Skill Scroller.", new Exception());
                // Check if this Context(SkillListFragment) has a SkillList object defined
                // If not create a SkillList

                Skill remSkill = mSkill;

                SkillList.get(getActivity()).removeSkill(mSkill);

                Log.d("REMOVED", "Removed an element from Skill Scroller.", new Exception());

                // Define intent to communicate back to SkillListFragment the index of the item removed:
                Intent remIndex = new Intent();

                remIndex.putExtra(EXTRA_REM_INDEX, remSkill.getId());

                // Call SkillScroller Activity's setResult method to send Intent data back to SkillListFragment
                // since Fragments cannot send results
                getActivity().setResult(getActivity().RESULT_OK, remIndex);

                // Pop back to the SkillListActivity
                getActivity().finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // OnCreateView: Where we inflate the Fragments layout
    // to return its View to the hosting Activity
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Param 1: Explicitly inflate the fragments view
        // Param 2: The Views parent
        // Param 3: tells layout inflater whether to add the inflated view to the view's parent
        View v = inflater.inflate(R.layout.fragment_skill, container, false);

        // Set up the EditText widget from fragment_skill.xml
        mTitleField = (EditText) v.findViewById(R.id.skill_title);

        // Set the text to the Skill pulled in from the SkillActivity
        mTitleField.setText(mSkill.getTitle());

        // Add listener to describe what is to be done upon text edit
        mTitleField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // On Text Edit set the title of our Skill Instance to the users input
                mSkill.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());

        // Set up the Date Button
        mAddedDateButton = (Button) v.findViewById(R.id.skill_add_date);

        // Set the text to Skill instance AddedDate field
        // mAddedDateButton.setText(dateFormat.format(mSkill.getAddedDate()));
        // mAddedDateButton.setText(android.text.format.DateFormat.format(formatStr, mSkill.getAddedDate()));
        updateDate(formatStr);

        // Disable the button from being altered for now:
        // mAddedDateButton.setEnabled(false);

        // Enable the onClickListener for the dateButton to create our DatePickerFragment
        mAddedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Get the Fragment Manager to handle the alertDialog in our DatePickerFragment
                //FragmentManager manager = getFragmentManager();
                FragmentManager manager = getActivity()
                        .getSupportFragmentManager();

                // Call the DatePicker Fragments newInstance method by passing in the skill's added date
                DatePickerFragment dialog = DatePickerFragment.newInstance(mSkill.getAddedDate());

                // Set up a connection between the DatePicker and SkillFragment
                dialog.setTargetFragment(SkillFragment.this, REQUEST_DATE);

                // Display the selected DatePicker dialog
                dialog.show(manager, DIALOG_DATE);
            }
        });

        // Set up the Experience CheckBox
        mExperienceCheckBox = (CheckBox) v.findViewById(R.id.skill_experience);

        // Set to the skills value:
        mExperienceCheckBox.setChecked(mSkill.isExperienced());

        // Set up the listener:
        mExperienceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

            }
        });

        // Return the Fragment view
        return v;
    }

    // Used to get the date selected from DatePicker Dialog
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }

        // If the result matches the int for our a datepicker fragment communication
        if (requestCode == REQUEST_DATE)
        {
            // Process the returned Intent data: Get Date to update the Skill with
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            mSkill.setAddedDate(date);

            updateDate(formatStr);
        }
    }

    private void updateDate(String formatStr)
    {
        mAddedDateButton.setText(android.text.format.DateFormat.format(formatStr, mSkill.getAddedDate()));
    }
}
