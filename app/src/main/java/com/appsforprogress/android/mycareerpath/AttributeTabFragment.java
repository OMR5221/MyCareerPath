package com.appsforprogress.android.mycareerpath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oswald on 1/16/2016.
 */
public class AttributeTabFragment<T> extends Fragment
{
    private RecyclerView mAttributeRecyclerView;
    private TextView mNoAttributeTextView;
    private AttributeAdapter mAdapter;
    private Integer mPosition;
    private Integer mViewId;
    private boolean mCountMenuVisible;
    private static final String SAVED_COUNT_MENU_VISIBLE = "count";
    private static final Integer REQUEST_SKILL_INDEX = 1;
    private Integer mPriorCount;
    private Integer mCurrentCount;
    private Integer mAttributeCount;
    private static final String ARG_ATTR_TYPE = "attrType";
    private static final String ARG_ATTR_TAB = "attrTab";
    private String mAttrName;
    private AttributeTabPagerAdapter.AttributeTab mAttrTab;
    private AttributeArrayAdapter mAttrArrayAdapter;
    private final String fPackageName = "com.appsforprogress.android.mycareerpath";
    private static Object mAttributeList;
    // private ArrayList<T> mAttributes;


    // Create a new Fragment with the AttributeType in the Tab Object to be created.
    public static AttributeTabFragment newInstance(AttributeTabPagerAdapter.AttributeTab attrTab)
    {
        final AttributeTabFragment fragment = new AttributeTabFragment();
        final Bundle args = new Bundle();
        // args.putString(ARG_ATTR_TYPE, toolType.name());

        // Set the tab passed into the List as an argument when creating the object:
        args.putString(ARG_ATTR_TAB, attrTab.name());
        fragment.setArguments(args);
        return fragment;
    }

    public AttributeTabFragment()
    {
        // Requires an empty public constructor
    }

    @Override
    //
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

        if (args == null)
        {
            throw new IllegalStateException("No arguments set; use newInstance when constructing!");
        }

        // mAttrType = AttributeType.valueOf(args.getString(ARG_ATTR_TYPE));

        // Get the Tab we are currently on:
        mAttrTab = AttributeTabPagerAdapter.AttributeTab.valueOf(args.getString(ARG_ATTR_TAB));

        // Generate a test list of Attributes by the Tab we are currently on -> Replace with DB records
        // final ArrayList<Attribute> attrs = new AttributeTestDataGen(mAttrTab.hashCode()).getTestAttributes(mAttrTab, 20);

        // Load DB records per attribute into the ListView: Get reference to our AttributeList Object
        // AttributeList attrs = AttributeList.get(getActivity());

        // Create a list of the Attribute items to be handled by the ArrayAdapter
        // mAttrArrayAdapter = new AttributeArrayAdapter(getActivity(), attrs);

        // setListAdapter(attrs.getAttributes);

        // Tells the FragmentManager that this Fragment has a menu
        // so the onCreateOptionsMenu() needs to be run:
        setHasOptionsMenu(true);
    }


    @Override
    // Create the Activities Menu:
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        // Inflate the Menu resource layout
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_attribute_list, menu);

        /* Get the countMenu reference:
        MenuItem countItem = menu.findItem(R.id.menu_item_show_count);

        if (mCountMenuVisible) {
            countItem.setTitle(R.string.hide_count);
        }
        else {
            countItem.setTitle(R.string.show_count);
        }
        */
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

                // Add a new Skill to the SkillList object
                SkillList.get(getActivity()).insertRecord(newSkill);

                // Define intent to start the SkillScrollerActivity by passing this skill's id reference
                Intent intent = SkillScrollerActivity.newIntent(getActivity(), newSkill.getId());

                // Call the SkillScrollerActivities onCreate()
                // and expect an Integer result (Skill index) to be returned
                startActivity(intent);

                return true;

            /* Menu item to show count of skills
            case R.id.menu_item_show_count:

                mCountMenuVisible = !mCountMenuVisible;
                getActivity().invalidateOptionsMenu();
                updateCount();
                return true;
            */

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Initialize view from the fragment_skill_list layout file
        View view = inflater.inflate(R.layout.fragment_attribute_list, container, false);

        // Reference to the Views Recycler widget:
        mAttributeRecyclerView = (RecyclerView) view.findViewById(R.id.attribute_recycler_view);

        // Reference to a TextView when no attribute is present:
        mNoAttributeTextView = (TextView) view.findViewById(R.id.no_attribute_text_view);

        // Create a LinearLayout object to manage positioning of items and scrolling in a list vertically:
        mAttributeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*
        Check if we have added additional values to the savedInstanceState (Bundle)
        if (savedInstanceState != null)
        {
            // Check if the Count Menu was visible or not before we paused then resumed this activity
            mCountMenuVisible = savedInstanceState.getBoolean(SAVED_COUNT_MENU_VISIBLE);
        }
        */

        // Reload the list of skills and create an adapter
        updateUI();

        return view;
    }

    // Call on return from the SkillActivity
    public void onResume()
    {
        super.onResume();

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
        final Bundle args = getArguments();

        if (args == null) {
            throw new IllegalStateException("No arguments set; use newInstance when constructing!");
        }

        mAttrName = args.getString(ARG_ATTR_TYPE);

        // Get the Tab we are currently on:
        mAttrTab = AttributeTabPagerAdapter.AttributeTab.valueOf(args.getString(ARG_ATTR_TAB));

        mAttributeList = AttributeListController.get(this.getActivity(), mAttrName);

        // Class c = mAttributeList.getClass();

        List<? extends Attribute> attributes = null;

        // Get reference to our AttributeList using the AttributeListController:
        switch (mAttrName)
        {
            case "SKILL":

                attributes =  new ArrayList<Skill>();

            case "ATTRIBUTE":

                attributes = new ArrayList<Attribute>();

        }

        // Get a reference to the active Skill List available:
        try {

            Method m = mAttributeList.getClass().getMethod("selectFormattedRecords");

            try {
                m.invoke(attributes);
            } catch (IllegalAccessException e) {
                System.err.println("The method specified does not exist.");
                System.exit(0);
            } catch (InvocationTargetException e) {
                System.err.println("The method specified does not exist.");
                System.exit(0);
            }
        } catch (NoSuchMethodException e) {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }

        mAttributeCount = attributes.size();

        if (mAttributeCount == 0)
        {
            mAttributeRecyclerView.setVisibility(View.GONE);
            mNoAttributeTextView.setVisibility(View.VISIBLE);
        }

        else {

            // Set up the adapter if it is not already in place:
            if (mAdapter == null)
            {

                // Create an adapter and pass the List of skills for it to manage
                mAdapter = new AttributeAdapter(attributes);

                // Connect the RecyclerView to the Adapter
                mAttributeRecyclerView.setAdapter(mAdapter);

            }

            else {

                // Give the Adapter the latest skills List
                mAdapter.setAttributes(attributes);

                // Just reload everything for now since the user can edit many items:
                if (mPosition == null)
                {
                    // Tells the adapter to reload all of the items that are visible
                    // in the RecyclerView
                    mAdapter.notifyDataSetChanged();

                }
                else {

                    // Log.d("Data Item Changed:", "About to reload Adapter holder item that was edited.", new Exception());
                    // Log.d("Prior Count:", "About to reload Adapter holder item that was edited.", new Exception());

                    // We have deleted a single item from the AttributeList
                    if (mPriorCount > mCurrentCount)
                    {
                        // Tell the adapter to expect that the holder's data was removed
                        mAdapter.notifyItemRemoved(mPosition);

                        Integer mItemCount = mCurrentCount - mPosition;

                        mAdapter.notifyItemRangeChanged(mPosition, mItemCount);
                    }
                    // We have edited an item from the SkillList
                    else {
                        // Tell the adapter to reload only the item that was edited in the detail View
                        mAdapter.notifyItemChanged(mPosition);
                    }
                }
            }

            mAttributeRecyclerView.setVisibility(View.VISIBLE);
            mNoAttributeTextView.setVisibility(View.GONE);
        }

        // Update the skill count on refresh of the UI
        updateCount();
    }

    private <T extends Attribute> T getSubClass(T attr, Class<T> type)
    {
        return type.cast(attr);
    }


    private <I, O> O callMethod(String methodName, I inObj, Class<O> outType)
    {
        // Get the Class Object:
        Class<?> c = outType;

        Constructor<?> ct;

        Object o = null;

        O output = null;

        try {

            ct = c.getConstructor();

            try {
                // Create a new Instance of the Class
                o = ct.newInstance();

                output = outType.cast(o);
            }
            catch (java.lang.InstantiationException is)
            {
                System.err.println("Cannot create an instance of the Class.");
                System.exit(0);
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("No applicable Constructor found.");
            System.exit(0);
        }

        // Try to invoke the methodName passed in:
        try {
            // GET the method to be invoked:
            Method m = inObj.getClass().getMethod(methodName);

            try {
                // Attempt to invoke the method found:
                m.invoke(output);
            }
            catch (IllegalAccessException ia) {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv) {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e) {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }

        return output;
    }

    // Code to show count when menu option selected:
    private void updateCount()
    {
        // Get the int count of skills
        int attributeCount = mAttributeCount;

        // Convert the int count to a string
        String attrCountStr = getResources().getQuantityString(R.plurals.subtitle_format, attributeCount, attributeCount);

        /* if the menu is not visible then set to null
        if (!mCountMenuVisible)
        {
            totalCount = null;
        }
        */

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        // Use the AppCompat activity to update the toolbar
        activity.getSupportActionBar().setSubtitle(attrCountStr);
    }


    // Set up the RecyclerView's Adapter: controller that sits between
    // the RecyclerView and the data and manages there interaction.
    // RecyclerView communicates with this adapter when a ViewHolder needs to be:
    // 1. created a ViewHolder or
    // 2. bind a View to a ViewHolder
    // to a Skill Object
    private class AttributeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        // Create a list of the Attribute Type currently selected by tab:
        private List<? extends Attribute> mAttributes;

        public AttributeAdapter(List<? extends Attribute> attributes)
        {
            // Initialize adapter to manage a list of skills
            mAttributes = attributes;

            getItemViewType(mAttrTab.getStringResource());
        }

        @Override
        // Called by the RecyclerView when it needs a new View to display an item
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            switch (viewType)
            {
                case 0:
                    // Create a simple TextView using the android library when no skills present:
                    View skillView = layoutInflater.inflate(R.layout.list_item_skill, parent, false);

                    // Create a new SkillHolder to hold the TextView
                    return new SkillHolder(skillView);

                default:

                    // Create a simple TextView using the android library when no skills present:
                    View noAttributeView = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

                    // Create a new SkillHolder to hold the TextView
                    return new SkillHolder(noAttributeView);
            }

        }

        @Override
        //  Bind a skill to a holder
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int index)
        {
            switch (viewHolder.getItemViewType())
            {
                case 0:

                    Skill skill = (Skill) mAttributes.get(index);

                    SkillHolder skillHolder = (SkillHolder) viewHolder;

                    // Place the text in the TextView held by the holder to the skill object's Title
                    skillHolder.mLowerCIBoundTextView.setText(R.string.no_skills);

                    skillHolder.bindAttribute(skill);

                    break;

                default:

                    break;

            }
        }

        @Override
        // Return the number of items in the data set held by the adapter
        public int getItemCount()
        {
            return mAttributes.size();
        }

        @Override
        public long getItemId(int position)
        {
            return super.getItemId(position);
        }

        @Override
        public int getItemViewType(int position)
        {
            // return the int of the tab we are currently on:
            return position;

        }

        // Update the Adapters listing of skills
        public void setAttributes(List<? extends Attribute> attributes)
        {
            mAttributes = attributes;
        }
    }

    // Set up the RecyclerView's ViewHolder: holds a reference to a view widget
    protected abstract class AttributeHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // Attribute Fields to display in Listing:
        private TextView mElementNameTextView;
        // private TextView mDateAddedTextView;
        // private CheckBox mExperienceCheckBox;

        public AttributeHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            // Get references to the widgets described by our custom layout for a skill list item
            mElementNameTextView = (TextView) itemView.findViewById(R.id.list_item_skill_title_text_view);

            //mDateAddedTextView = (TextView) itemView.findViewById(R.id.list_item_skill_date_text_view);
            //mExperienceCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_skill_exp_check_box);

            // Disable checkbox grays it out: mExperienceCheckBox.setEnabled(false);

            /* Set up the checkbox to listen to user selection:
            mExperienceCheckBox.setOnCheckedChangeListener
            (
                new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        mSkill.setExperienced(isChecked);
                    }
                }
            );
            */
        }


        // Get a skill and bind its fields to the RecyclerView.Holder
        // initially when the holder is first created
        public <T> void bindAttribute(T attr)
        {
            String outText = null;

            outText = callMethod("getElementName", attr, String.class);

            mElementNameTextView.setText(outText);

            outText = callMethod("getElementName", attr, String.class);

            // mDateAddedTextView.setText(mSkill.getAddedDate().toString());
            // mExperienceCheckBox.setChecked(mSkill.isExperienced());
        }

        @Override
        public abstract void onClick(View v);
    }

    // Add a NoAttributeHolder when no Attributes are present:
    private class SkillHolder extends AttributeHolder
    {
        private Skill mSkill;

        private TextView mLowerCIBoundTextView;

        public SkillHolder(View itemView)
        {
            super(itemView);

            // Create a TextView to Skill specific fields:
            mLowerCIBoundTextView = (TextView) itemView.findViewById(R.id.no_skills_text_view);
        }

        public <T> void bindAttribute(T attr)
        {
            mSkill = (Skill) attr;

            String outText = null;

            outText = callMethod("getElementName", attr, String.class);

            mLowerCIBoundTextView = (TextView) itemView.findViewById(R.id.list_item_skill_title_text_view);

            mLowerCIBoundTextView.setText(outText);
        }

        @Override
        public void onClick(View v)
        {
            // Show a toast upon a click
            // Toast.makeText(getActivity(), mSkill.getTitle() + " Clicked!", Toast.LENGTH_SHORT).show();

            // Get the prior Count of the number of items in our SkillList
            mPriorCount = mAttributeCount;

            // Create an Intent to start the Skill Scroller Activity's intent
            // to update the SkillFragment
            Intent intent = SkillScrollerActivity.newIntent(getActivity(), mSkill.getId());

            // Get the position index of the Skill List item selected:
            // INCORRECT: ITEM SELECTED NOT ALWAYS THE ITEM RETURNED
            //mPosition = this.getAdapterPosition();

            // Start the activity to update the Skill displayed in the SkillFragment
            // expecting the return of the result of the index of the last Skill displayed:
            // startActivityForResult(intent, REQUEST_SKILL_INDEX);
            startActivity(intent);
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
        // before they returned to the SkillList
        if (requestCode == REQUEST_SKILL_INDEX)
        {
            if (data == null)
            {
                return;
            }

            // Get the index value from the Intent sent to this Fragment by getting the INDEX
            // value sent in the extra from the SkillScrollerActivity/SkillFragment
            // mPosition = SkillScrollerActivity.getSkillIndex(data);
        }
    }
}