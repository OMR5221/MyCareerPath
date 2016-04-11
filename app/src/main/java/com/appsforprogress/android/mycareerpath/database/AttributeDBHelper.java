package com.appsforprogress.android.mycareerpath.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

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
        String columns =
                "O*NET-SOC Code,Element ID,Element Name,Scale ID," +
                "Scale Name,Data Value,N,Standard Error,Lower CI Bound,Upper CI Bound," +
                "Recommend Suppress,Not Relevant";

        String skillTableCreate =
            "create table " + SkillTable.TABLE_NAME +
            "(" +
            " _id integer primary key autoincrement, " + columns +
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
        db.execSQL
        (
            skillTableCreate
        );

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
