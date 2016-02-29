package com.appsforprogress.android.mycareerpath;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
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
import android.text.format.DateFormat;
import java.util.Date;
import java.util.UUID;

import static android.support.v4.app.ShareCompat.*;

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

    // Constant for the RequestCode to get data back from DatePicker Fragment:
    private static final int REQUEST_DATE = 0;
    // Constant for the requestCode to identify the Intent to allow the user to pick a contact:
    private static final int REQUEST_CONTACT = 1;

    private String longDateFormat = "EEEE, MMMM d, yyyy";
    private String shortDateFormat = "EEE, MMM dd";

    private static final String DIALOG_DATE = "DialogDate";

    // Button to send Skills Resume:
    private Button mSendResumeButton;
    private Button mPickPeerButton;

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

                //Log.d("REMOVAL", "About to remove an element from Skill Scroller.", new Exception());

                // Check if this Context(SkillListFragment) has a SkillList object defined
                // If not create a SkillList

                Skill remSkill = mSkill;

                // Remove the skill using the SkillList Array remove method:
                SkillList.get(getActivity()).removeSkill(mSkill);

                // Log.d("REMOVED", "Removed an element from Skill Scroller.", new Exception());

                // Define intent to communicate back to SkillListFragment the index of the item removed:
                Intent remIndex = new Intent();

                // Put he index of the item to be deleted in the EXTRA of the Intent
                remIndex.putExtra(EXTRA_REM_INDEX, remSkill.getId());

                // Call SkillScroller Activity's setResult method to send Intent data back to SkillListFragment
                // since Fragments cannot send results
                //
                getActivity().setResult(getActivity().RESULT_OK, remIndex);

                // Pop back to the SkillListActivity to return a result
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

        // DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());

        // Set up the Date Button
        mAddedDateButton = (Button) v.findViewById(R.id.skill_add_date);

        // Set the text to Skill instance AddedDate field
        // mAddedDateButton.setText(dateFormat.format(mSkill.getAddedDate()));
        // mAddedDateButton.setText(android.text.format.DateFormat.format(formatStr, mSkill.getAddedDate()));
        updateDate();

        // Disable the button from being altered for now:
        // mAddedDateButton.setEnabled(false);

        // Enable the onClickListener for the dateButton to create our DatePickerFragment
        mAddedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        mExperienceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mSkill.setExperienced(isChecked);
            }
        });

        // Set up button to allow user to pick a peer from contacts list
        mPickPeerButton = (Button) v.findViewById(R.id.skill_peer);

        // Intent to request access to Contacts to allow for the action of the user to pick
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        // Dummy code to filter our Contacts app so that our Contacts app is not found:
        // pickContact.addCategory(Intent.CATEGORY_HOME);

        // Allow us to check OS to see if the user's phone has a Contacts App:
        PackageManager packageManager = getActivity().getPackageManager();

        // PackageManager: knows about all components installed on the user's Android device:
        // resolveActivity: ask packageManager to find an Activity that matches the Intent given to it.
        // If null then no corresponding app capable of handling the Intent was found
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null)
        {
            // If no contacts app found then disable the PickPeerButton
            mPickPeerButton.setEnabled(false);
        }
        else
        {
            mPickPeerButton.setEnabled(true);

            mPickPeerButton.setOnClickListener(new View.OnClickListener()
            {

                @Override
                // Click launches Implicit Intent to contacts list
                public void onClick(View v) {
                    startActivityForResult(pickContact, REQUEST_CONTACT);
                }
            });
        }

        String peerName = mSkill.getPeer();

        // Set the Peer Pick Button to show the Peer Name if not null
        if (peerName != null)
        {
            mPickPeerButton.setText(peerName);
        }

        // Set up Button to send the Skills Resume:
        mSendResumeButton = (Button) v.findViewById(R.id.skill_resume);
        mSendResumeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Defining our own Intent:
                /* Define the action of this intent: sending data
                Intent outText = new Intent(Intent.ACTION_SEND);

                // Let intent know the type of data the action is for:
                outText.setType("text/plain");

                // Attach our generated resume as an Extra
                outText.putExtra(Intent.EXTRA_TEXT, genSkillResume());

                // Attach our subject string as an Extra
                outText.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.skill_resume_subject));

                // Demands app show the chooser app dialog:
                outText = Intent.createChooser(outText, getString(R.string.send_report));
                */

                // Creating an Intent using ShareCompat.IntentBuilder:
                IntentBuilder ib = IntentBuilder.from(getActivity());

                ib.setType("text/plain");

                ib.setText(genSkillResume());

                ib.setSubject(getString(R.string.skill_resume_subject));

                ib.createChooserIntent();

                // Create an Implicit Intent to OS for another app to handle a request: Create a plan text file with skill resume
                Intent outText = ib.getIntent();

                // Send to OS to find apps to handle
                startActivity(outText);
            }
        });

        // Return the Fragment view
        return v;
    }

    @Override
    // Send the Skill Details back to the SkillList Database
    public void onPause()
    {
        super.onPause();

        // Always edits the details of the skill paused on
        SkillList.get(getActivity()).updateSkill(mSkill);
    }

    // Used to process results from called Child Activities:
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

            updateDate();
        }
        // Handle the results for the return of the users Contact pick
        else if (requestCode == REQUEST_CONTACT && data != null)
        {
            // URI: Locator that points at the single contact the user picked.
            Uri contactUri = data.getData();

            // Specify the fields from the Contacts DB we want our query to return values for:
            String[] queryFields = new String[]
            {
                ContactsContract.Contacts.DISPLAY_NAME
            };

            // Perform our query: the contactURI is like a "where"
            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);

            // Allows us to navigate through data retrieved.
            try
            {
                // Double check that our query got results:
                if (c.getCount() == 0)
                {
                    return;
                }

                // Retrieve the first column of the first row of data - peer's name
                // and update the Skill and the Button with the contact's name
                c.moveToFirst();
                String peerName = c.getString(0);
                mSkill.setPeer(peerName);
                mPickPeerButton.setText(peerName);
            }
            finally
            {
                c.close();
            }
        }
    }


    private void updateDate()
    {
        mAddedDateButton.setText(getFormattedDate(longDateFormat));
    }

    private CharSequence getFormattedDate(String dateFormat)
    {
        return DateFormat.format(dateFormat, mSkill.getAddedDate());
    }

    // Generate the users skill resume => Should encompass all skills
    private String genSkillResume()
    {
        String peer = mSkill.getPeer();
        String endorsed = null;

        if (peer == null)
        {
            peer = getString(R.string.skill_no_peer);

            endorsed = getString(R.string.skill_not_endorsed);
        }
        else
        {
            peer = getString(R.string.skill_peer);

            // Check if this peer endorsed the skill:
            if (mSkill.isExperienced())
            {
                endorsed = getString(R.string.skill_endorsed);
            }
            else
            {
                endorsed = getString(R.string.skill_not_endorsed);
            }
        }

        String date = getFormattedDate(shortDateFormat).toString();

        String resume = getString(R.string.skill_resume, mSkill.getTitle(), date, endorsed, peer);

        return resume;

    }


}
