package com.appsforprogress.android.mycareerpath;

/**
 * Created by Oswald on 3/15/2016.
 */
import android.support.annotation.StringRes;

public enum AttributeType
{
    SKILLS(R.string.attributes_menu_skills_label, R.string.attributes_menu_skills_description),
    KNOWLEDGE(R.string.attributes_menu_knowledge_label, R.string.attributes_menu_knowledge_description),
    INTERESTS(R.string.attributes_menu_interests_label, R.string.attributes_menu_interests_description),
    EDUCATION_TRAINING_EXPERIENCE(R.string.attributes_menu_edctrnexp_label, R.string.attributes_menu_edctrnexp_description),
    ABILITIES(R.string.attributes_menu_abilities_label, R.string.attributes_menu_abilities_description)
    ;

    private final int mAttributeNameResourceId;
    private final int mAttributeDescriptionResourceId;

    private AttributeType(@StringRes int attributeName, @StringRes int attributeDescription)
    {
        mAttributeNameResourceId = attributeName;
        mAttributeDescriptionResourceId = attributeDescription;
    }

    @StringRes
    public int getAttributeDescriptionResourceId() {
        return mAttributeDescriptionResourceId;
    }

    @StringRes
    public int getAttributeNameResourceId() {
        return mAttributeNameResourceId;
    }
}