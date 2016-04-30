package com.appsforprogress.android.mycareerpath.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Oswald on 2/19/2016.
 */
public class AttributeDBHelper extends SQLiteOpenHelper
{

    private static final Integer VERSION = 1;
    private static final String DATABASE_NAME = "attributes.db";
    private static String mColumns;

    public AttributeDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    // Create the tables in my Attributes Table:
    public void onCreate(SQLiteDatabase db)
    {

        /*
        try
        {
            m = SkillTable.Cols.class.getMethod("getColumnsString");

            try
            {
                // Attempt to invoke the method found:
                out = (String) m.invoke(in);
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
            e.printStackTrace();
        }
        */

        String skillTableCreate =
                "create table " + SkillTable.TABLE_NAME +
                        "(" + " _id integer primary key autoincrement, " +
                        SkillTable.Cols.UUID + ", " +
                        SkillTable.Cols.CAREER_NAME + ", " +
                        SkillTable.Cols.ONET_CODE + ", " +
                        SkillTable.Cols.ELEMENT_ID + ", " +
                        SkillTable.Cols.ELEMENT_NAME + ", " +
                        SkillTable.Cols.SCALE_ID + ", " +
                        SkillTable.Cols.SCALE_NAME + ", " +
                        SkillTable.Cols.DATA_VALUE + ", " +
                        SkillTable.Cols.N_VALUE + ", " +
                        SkillTable.Cols.STANDARD_ERROR + ", " +
                        SkillTable.Cols.LOWER_CI_BOUND + ", " +
                        SkillTable.Cols.UPPER_CI_BOUND + ", " +
                        SkillTable.Cols.RECOMMEND_SUPPRESS + ", " +
                        SkillTable.Cols.NOT_RELEVANT + ", " +
                        SkillTable.Cols.PROFICIENCY + ", " +
                        SkillTable.Cols.PEER_NAME + ", " +
                        SkillTable.Cols.DATE_ADDED +
                        ")";
        /*
        String interestTableCreate =
            "create table " + InterestTable.TABLE_NAME +
            "(" +
            " _id integer primary key autoincrement, " + columns +
            ")";

        String abilityTableCreate =
            "create table " + AbilityTable.TABLE_NAME +
            "(" +
            " _id integer primary key autoincrement, " + columns +
            ")";

        String edctrnexpTableCreate =
            "create table " + EdcTrnExpTable.TABLE_NAME +
            "(" +
            " _id integer primary key autoincrement, " + columns +
            ")";

        String knowledgeTableCreate =
            "create table " + KnowledgeTable.TABLE_NAME +
            "(" +
            " _id integer primary key autoincrement, " + columns +
            ")";
        */

        // Called to create DB and Tables if does not already exist:
        db.execSQL(skillTableCreate);

        /*
        db.execSQL
        (
            interestTableCreate
        );

        db.execSQL
        (
            abilityTableCreate
        );

        db.execSQL
        (
            edctrnexpTableCreate
        );


        db.execSQL
        (
            knowledgeTableCreate
        );
        */
    }

    public int getNumColumns()
    {
        return mColumns.length();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
