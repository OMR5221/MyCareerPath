package com.appsforprogress.android.mycareerpath;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Oswald on 1/10/2016.
 */

public class SkillList
{
    // Holds static singleton to list the various skills within
    private static SkillList sSkillList;
    private List<Skill> mSkills;

    // Constructor
    private SkillList(Context context)
    {
        // Empty List of Skills
        mSkills = new ArrayList<>();

        /* Populate the mSkills List with 100 Skill Objects
        for (int i = 0; i < 100; i++)
        {
            Skill skill = new Skill();
            skill.setIndex(i);
            skill.setTitle("Skill #" + i);
            skill.setExperienced(i % 2 == 0);
            mSkills.add(skill);
        }
        */
    }

    // get()
    public static SkillList get(Context context)
    {
        if (sSkillList == null)
        {
            sSkillList = new SkillList(context);
        }

        return sSkillList;
    }

    // Getter for mSkills List
    public List<Skill> getSkills() {
        return mSkills;
    }

    // getter to return a single skill from mSkills
    public Skill getSkill(UUID id)
    {
        // Go through the skills listed in the skills List
        for (Skill skill : mSkills)
        {
            // If it exists then return it
            if (skill.getId().equals(id))
            {
                return skill;
            }
        }

        return null;
    }

    public void addSkill(Skill newSkill)
    {
        mSkills.add(newSkill);
    }

    public void removeSkill(Skill remSkill) { mSkills.remove(remSkill); }
}
