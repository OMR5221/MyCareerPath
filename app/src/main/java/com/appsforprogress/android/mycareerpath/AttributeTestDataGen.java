package com.appsforprogress.android.mycareerpath;

import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Oswald on 3/12/2016.
 */
public class AttributeTestDataGen
{
    private static final String[] ATTRIBUTE_NAME =
    {
        "Skills", "Abilities", "Interests", "Education, Training, and Experience", "Knowledge"
    };

    private static final String[] CAREER_TITLE =
    {
        "Chief Executives", "Chief Sustainability Officers", "Prosthodontists", "Dietitians and Nutritionists",
        "Construction Carpenters", "Cleaning, Washing, and Metal Pickling Equipment Operators and Tenders", "Biological Technicians"
    };

    private static final String[] ELEMENT_ID =
    {
        "2.A.1.a", "2.A.1.b", "2.A.1.c", "2.A.1.d", "2.A.1.e", "2.A.1.f"
    };

    private static final String[] ELEMENT_NAME =
    {
        "Reading Comprehension", "Active Listening", "Writing", "Speaking", "Mathematics", "Science"
    };


    private final Random mRandom;

    public AttributeTestDataGen()
    {
        this(0);
    }

    public AttributeTestDataGen(long seed)
    {
        mRandom = new Random(seed);
    }


    public ArrayList<Attribute> getTestAttributes(AttributeTabPagerAdapter.AttributeTab attrTab, int count)
    {
        final ArrayList<Attribute> results = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
        {
            results.add(getTestAttribute(attrTab, i));
        }
        return results;
    };


    public Attribute getTestAttribute(AttributeTabPagerAdapter.AttributeTab attrTab, Integer index)
    {
        String attribute = attrTab.toString();
        String career = "";
        String elementId = "";
        String elementName = "";
        // final String[] details = new String[3];
        String attrOutput = "";
        String output = "";

        career = getRandom(CAREER_TITLE);
        elementId = getRandom(ELEMENT_ID);
        elementName = getRandom(ELEMENT_NAME);
        attrOutput += career + ": " + elementName + " (" + elementId + ")";

        if (attrTab == AttributeTabPagerAdapter.AttributeTab.SKILLS)
        {
            output = attribute + " # " + index + ": " + attrOutput;
        }

        else if (attrTab == AttributeTabPagerAdapter.AttributeTab.KNOWLEDGE)
        {
            output = attrTab + " # " + index + ": " + attrOutput;
        }

        else if (attrTab == AttributeTabPagerAdapter.AttributeTab.ABILITIES)
        {
            output = attrTab + " # " + index + ": " + attrOutput;
        }

        else if (attrTab == AttributeTabPagerAdapter.AttributeTab.EDUCATION_TRAINING_EXPERIENCE)
        {
            output = attrTab + " # " + index + ": " + attrOutput;
        }

        else if (attrTab == AttributeTabPagerAdapter.AttributeTab.INTERESTS)
        {
            output = attrTab + " # " + index + ": " + attrOutput;
        }

        else
        {
            output = "Incorrect tab position selected.";
        }

        return new Attribute(attribute, career, elementId, elementName);
    };

    private String getRandom(String[] strings)
    {
        return strings[mRandom.nextInt(strings.length)];
    }

}
