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
public class AttributeScrollerActivity<T> extends AppCompatActivity
{
    private ViewPager mViewPager;
    private List<? extends Attribute> mAttributes;
    Object mAttribute;
    UUID mAttrId;
    Integer mAttrCount;


    // Add an Extra to hold the SkillList item selected for display in the SkillScrollerActivity
    private static final String EXTRA_ATTRIBUTE_ID = "com.appsforprogress.android.mycareerpath.attribute_id";

    // Add an Extra to hold the SkillScroller item index to be returned to the SkillList
    private static final String EXTRA_ATTRIBUTE_INDEX = "com.appsforprogress.android.mycareerpath.attribute_index";

    // Add an Extra to hold the type of ATTRIBUTE passed to the Scroller to display
    private static final String EXTRA_ATTRIBUTE_TYPE = "com.appsforprogress.android.mycareerpath.attribute_type";

    private final String fPackageName = "com.appsforprogress.android.mycareerpath.";

    // Communicate with the SkillListActivity about the position of the Skill deleted
    public static Intent newIntent(Context packageContext, UUID attrId, String attrType)
    {
        // Intent(HASH):
        Intent intent = new Intent(packageContext, AttributeScrollerActivity.class);

        // Insert an extra key, value pair into the hash
        // This extra is sent to the SkillFragment which skill to display
        intent.putExtra(EXTRA_ATTRIBUTE_ID, attrId);
        intent.putExtra(EXTRA_ATTRIBUTE_TYPE, attrType);

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

        String mAttrName =(String) getIntent().getSerializableExtra(EXTRA_ATTRIBUTE_TYPE);

        // Get a reference to the ViewPager widget
        mViewPager = (ViewPager) findViewById(R.id.activity_skill_scroller_view_pager);

        // Get skills data:
        // mAttributes = <> SkillList.get(this).selectFormattedRecords();

        // Create the AttributeLists Controller
        // mAttributeListsControl = new AttributeListController(this.getActivity());

        String className = fPackageName + "Skill" + "List";
        Object attributeListObject = new Object();

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
                    attributeListObject = m.invoke(t, this);

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
        mAttributes = null;


        // Get formatted Attributes:
        try {

            // Get the Class Object:
            Class<?> c = Class.forName(className);

            Method[] allMethods = c.getDeclaredMethods();

            for (Method m : allMethods)
            {
                String mName = m.getName();

                if (!mName.equals("selectFormattedRecords")) //)|| (m.getGenericReturnType() != boolean.class))
                {
                    continue;
                }

                /*
                Type[] pType = m.getGenericParameterTypes();

                if ((pType.length != 1) || Locale.class.isAssignableFrom(pType[0].getClass()))
                {
                    continue;
                }
                */

                m.setAccessible(true);

                try
                {
                    // Get reference to our AttributeList using the AttributeListController:
                    switch (mAttrName)
                    {
                        case "SKILLS":
                            // attributeList = (SkillList) attributeListObject;

                            // Create a new Instance of the Class
                            mAttributes = (List<Skill>) m.invoke(attributeListObject);

                            break;

                        case "ATTRIBUTE":
                            mAttributes = new ArrayList<Attribute>();

                    }

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

        /*
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
        */

        // Get a reference to the active Skill:
        mAttribute = new Object();

        try {

            // Get the Class Object:
            Class<?> c = Class.forName(className);

            Method[] allMethods = c.getDeclaredMethods();

            for (Method m : allMethods)
            {
                String mName = m.getName();

                if (!mName.equals("selectRecord")) //)|| (m.getGenericReturnType() != boolean.class))
                {
                    continue;
                }

                /*
                Type[] pType = m.getGenericParameterTypes();

                if ((pType.length != 1) || Locale.class.isAssignableFrom(pType[0].getClass()))
                {
                    continue;
                }
                */

                m.setAccessible(true);

                try
                {
                    // Create a new Instance of the Class
                    mAttribute = m.invoke(attributeListObject, attrId);

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

        String attrClassName = fPackageName + "Skill";


        try {

            // Get the Class Object:
            Class<?> c = Class.forName(attrClassName);

            Method[] allMethods = c.getDeclaredMethods();

            for (Method m : allMethods)
            {
                String mName = m.getName();

                if (!mName.equals("getId")) //)|| (m.getGenericReturnType() != boolean.class))
                {
                    continue;
                }

                /*
                Type[] pType = m.getGenericParameterTypes();

                if ((pType.length != 1) || Locale.class.isAssignableFrom(pType[0].getClass()))
                {
                    continue;
                }
                */

                m.setAccessible(true);

                try
                {
                    // Create a new Instance of the Class
                    mAttrId = (UUID) m.invoke(mAttribute);

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


        /*
        try {

            // Get the Class Object:
            Class<?> c = Class.forName(className);

            Method[] allMethods = c.getMethods();

            for (Method m : allMethods)
            {
                String mName = m.getName();

                if (!mName.equals("size")) //)|| (m.getGenericReturnType() != boolean.class))
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
                    mAttrCount = (Integer) m.invoke(attributeListObject);

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
        */


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
                    Attribute attribute = mAttributes.get(position);

                    // Send this skill to be retrieved in the SkillFragment
                    return AttributeScrollerFragment.newInstance(attribute.getId());
                }


                @Override
                public int getCount()
                {
                    return mAttributes.size();
                }
            }
        );

        // Loop through all skills
        // and check if one matches the passed in skillId
        for (int i = 0; i < mAttributes.size(); i++)
        {
            // Get the skill, then its id and check if match
            if (mAttributes.get(i).getId().equals(attrId))
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
        UUID skillId = (UUID) result.getSerializableExtra(EXTRA_ATTRIBUTE_INDEX);

        return skillId;
    }
}
