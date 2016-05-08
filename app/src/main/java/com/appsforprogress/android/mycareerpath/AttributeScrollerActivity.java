package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Oswald on 1/24/2016.
 * AttributeScrollerActivity: scroll through all skills by swiping left and right
 */
public class AttributeScrollerActivity extends AppCompatActivity
{
    /*
    private ViewPager mViewPager;
    private List<? extends Attribute> mAttributes;

    // Add an Extra to hold the SkillList item selected for display in the SkillScrollerActivity
    private static final String EXTRA_ATTRIBUTE_ID = "com.appsforprogress.android.mycareerpath.attribute_id";

    // Add an Extra to hold the SkillScroller item index to be returned to the SkillList
    private static final String EXTRA_ATTRIBUTE_INDEX = "com.appsforprogress.android.mycareerpath.attribute_index";

    // Add an Extra to hold the type of ATTRIBUTE passed to the Scroller to display
    private static final String EXTRA_ATTRIBUTE_TYPE = "com.appsforprogress.android.mycareerpath.attribute_type";

    // Communicate with the SkillListActivity about the position of the Skill deleted
    public static Intent newIntent(Context packageContext, UUID attrId)
    {
        // Intent(HASH):
        Intent intent = new Intent(packageContext, AttributeScrollerActivity.class);

        // Insert an extra key, value pair into the hash
        // This extra is sent to the SkillFragment which skill to display
        intent.putExtra(EXTRA_ATTRIBUTE_ID, attrId);

        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Load the layout file
        setContentView(R.layout.activity_skill_scroller);

        // The skill passed in through the Intent:
        UUID attrId = (UUID) getIntent().getSerializableExtra(EXTRA_ATTRIBUTE_ID);

        // Get a reference to the ViewPager widget
        mViewPager = (ViewPager) findViewById(R.id.activity_skill_scroller_view_pager);

        // Get skills data:
        // mAttributes = <> SkillList.get(this).selectFormattedRecords();


        // Create the AttributeLists Controller
        // mAttributeListsControl = new AttributeListController(this.getActivity());

        String className = fPackageName + "SkillList";
        Object attributeListObject = null;

        try {

            // Get the Class Object:
            Class<?> c = Class.forName(className);

            Constructor<?> ct;

            Method[] allMethods = c.getDeclaredMethods();

            Object t = c.newInstance();

            for (Method m : allMethods)
            {
                String mname = m.getName();

                if (!mname.equals("get")) //)|| (m.getGenericReturnType() != boolean.class))
                {
                    continue;
                }

                Type[] pType = m.getGenericParameterTypes();

                if ((pType.length != 1) || Locale.class.isAssignableFrom(pType[0].getClass()))
                {
                    continue;
                }

                m.setAccessible(true);

                try
                {
                    // Create a new Instance of the Class
                    attributeListObject = m.invoke(t, this.getActivity());

                    // c.toString() AttrSubList = (c.toString()) o;

                    // Add created Objects to the Hash (Key: Attribute Name, Value: AttributeList Subclass Object)
                    //mAttributeLists.put(attr, o);
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
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("No applicable Class found.");
            System.exit(0);
        }
        catch (java.lang.InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        // Class c = mAttributeList.getClass();
        Object attributeList = null;
        List<? extends Attribute> attributes = null;

        // Get reference to our AttributeList using the AttributeListController:
        switch (mAttrName)
        {
            case "SKILLS":
                attributeList = (SkillList) attributeListObject;
                attributes =  new ArrayList<Skill>();

            case "ATTRIBUTE":
                attributes = new ArrayList<Attribute>();

        }

        // Get a reference to the active Skill List available:
        try {

            Method m = attributeList.getClass().getMethod("selectFormattedRecords");

            try {
                mAttributes = (ArrayList<Skill>) m.invoke(attributeList);
            }
            catch (IllegalAccessException e) {
                System.err.println("The method specified does not exist.");
                System.exit(0);
            }
            catch (InvocationTargetException e) {
                System.err.println("The method specified does not exist.");
                System.exit(0);
            }
        } catch (NoSuchMethodException e) {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }

        // Get the fragmentManager for this Activity:
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Have the viewPager Adapter manage the Fragment for this Activity:
        mViewPager.setAdapter
        (
            new FragmentStatePagerAdapter(fragmentManager)
            {
                @Override
                public Fragment getItem(int position)
                {
                    // Get a skill from the skillList
                    Skill skill = mSkills.get(position);

                    // Send this skill to be retrieved in the SkillFragment
                    return SkillScrollerFragment.newInstance(skill.getId());
                }

                @Override
                public int getCount()
                {
                    return mSkills.size();
                }
            }
        );

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
    public static UUID getSkillIndex(Intent result)
    {
        // The skill passed in through the Intent:
        UUID skillId = (UUID) result.getSerializableExtra(EXTRA_SKILL_INDEX);

        return skillId;
    }
    */
}
