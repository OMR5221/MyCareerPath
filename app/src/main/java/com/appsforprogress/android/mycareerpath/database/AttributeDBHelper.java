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


    public AttributeDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    // Create the tables in my Attributes Table:
    public void onCreate(SQLiteDatabase db)
    {
        Method m;
        String in = "";
        String out = "";
        String columns =
                "attribute_type,onet_code,element_id,element_name,scale_id,data_value,n_value," +
                "standard_error,lower_ci_bound,upper_ci_bound,recommend_suppress,not_relevant," +
                "proficiency,date_added,peer_name";

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
                "create table " + SkillTable.TABLE_NAME + "(" + " _id integer primary key autoincrement, " + columns + ")";
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
