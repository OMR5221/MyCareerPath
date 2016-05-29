package com.appsforprogress.android.mycareerpath.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.appsforprogress.android.mycareerpath.Attribute;
import com.appsforprogress.android.mycareerpath.Skill;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;
import com.appsforprogress.android.mycareerpath.database.AttributeDBSchema.AttributeTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Oswald on 2/19/2016.
 */
public class AttributeDBHelper extends SQLiteOpenHelper
{

    private static final Integer VERSION = 1;
    private static final String DATABASE_NAME = "attributes.db";
    private static String DB_PATH = "";
    private static String ASSET_PATH = "db_files";
    private static String TAG = "DataBaseHelper";
    private static String mColumns;
    private final Context mContext;
    private static SQLiteDatabase sAttributeDB;

    public AttributeDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);

        this.mContext = context;
    }


    // get the SkillList object in use
    public static SQLiteDatabase get()
    {
        return sAttributeDB;
    }


    @Override
    public String getDatabaseName()
    {
        return super.getDatabaseName();
    }

    // Check that the database exists here: /data/data/your package/databases/DBName
    public boolean checkDBExists()
    {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        // Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException
    {
        // Read in the DB from the Assets directory:
        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);

        // Create a file to place in the /data/data directory:
        String outFileName = DB_PATH + DATABASE_NAME;

        // Write to the file:
        OutputStream mOutput = new FileOutputStream(outFileName);

        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }


    /*
    public void createDataBase() throws IOException
    {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDBExists();

        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                // Create the DB and tables

                Log.e(TAG, "createDatabase database created");
            }
            catch (IOException e)
            {
                System.out.print("");
            }
        }
    }


    //Open the database, so we can query it
    public SQLiteDatabase openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DATABASE_NAME;
        //Log.v("mPath", mPath);
        return SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }
    */


    @Override
    // Create the tables in my Attributes Table:
    public void onCreate(SQLiteDatabase db)
    {
        // db.delete(null, null, null);

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

        String fileName = null;
        AssetManager manager = mContext.getResources().getAssets();

        // Get Dirs for files:
        File fileDir = mContext.getFilesDir();

        Integer lineNum = 0;
        Integer numColumns = 0;
        String dbColHeaders[] = null;
        String row[] = null;

        InputStream inStream = null;

        String[] files = null;

        try {
            files = mContext.getResources().getAssets().list(ASSET_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Object newAttribute = null;
        ObjectMaker om = new ObjectMaker();

        for (String file : files)
        {

            String attrType = file.substring(0, file.indexOf("."));

            try {
                inStream = manager.open(ASSET_PATH + "/" + file);
            } catch (IOException e) {
                e.printStackTrace();
            }


            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

            // mAttributesDatabase.delete(SkillTable.TABLE_NAME, null, null);

            // sAttributeDB.beginTransaction();

            try
            {
                //reader = new BufferedReader(new InputStreamReader(context.getAssets().open(mSkillsFileName)));


                // do reading, usually loop until end of file reading
                String line;

                //while ((line = reader.readLine()) != null)
                while (lineNum <= 50)
                {

                    line = reader.readLine();

                    if (lineNum == 0)
                    {
                        // Get Column Headers:
                        dbColHeaders = line.split(",");

                        numColumns = dbColHeaders.length;

                        lineNum += 1;

                    }
                    else
                    {
                        row = line.split(",");

                        String className = "com.appsforprogress.android.mycareerpath." + attrType;

                        Object newAttribute = new Object();

                        try {
                            newAttribute = om.makeObject(className);

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        /*
                        if (o instanceof Skill)
                        {
                            newAttribute = (Skill) o;
                        }
                        */

                        /*
                        Class<?> c = null;

                        try {

                            // Get the Class Object:
                            c = Class.forName(className);

                            Constructor<?> ct;

                            try {

                                ct = c.getConstructor();

                                try {
                                    // Create a new Instance of the Class
                                    Object o = ct.newInstance();

                                    if (o instanceof Skill) {
                                        newAttribute = (Skill) o;
                                    }
                                }
                                catch (InstantiationException is)
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
                        }
                        catch (ClassNotFoundException e)
                        {
                            System.err.println("No applicable Class found.");
                            System.exit(0);
                        }
                        */

                        // Add a new Skill to the SkillList object
                        // this.insertRecord(newSkill);

                        // Create a new db record entry:
                        ContentValues values = new ContentValues(15);

                        UUID out = null;

                        try
                        {

                            // GET the method to be invoked:
                            Method m = newAttribute.getClass().getMethod("getId");

                            try
                            {
                                // Attempt to invoke the method found:
                                out = (UUID) m.invoke(newAttribute);
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
                            System.err.println("The method specified does not exist.");
                            System.exit(0);
                        }


                        /*
                        T table = null;

                        className = "com.appsforprogress.android.mycareerpath" + attrType + "Table";

                        c = null;

                        try {

                            // Get the Class Object:
                            c = Class.forName(className);

                            Constructor<?> ct;

                            try {

                                ct = c.getConstructor();

                                try {
                                    // Create a new Instance of the Class
                                    Object o = ct.newInstance();

                                    table = (T) o;
                                }
                                catch (InstantiationException is)
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
                        }
                        catch (ClassNotFoundException e)
                        {
                            System.err.println("No applicable Class found.");
                            System.exit(0);
                        }
                        */

                        values.put(SkillTable.Cols.UUID, out.toString());

                                                 /*
                        if (columns.length != 14)
                        {
                            Log.d("CSVParser" , "Skipping Bad CSV Row");
                            continue;
                        }
                        */

                        for (int j = 0; j < numColumns; j++) {
                            values.put(dbColHeaders[j], row[j].trim());
                        }


                        lineNum += 1;

                        // Insert formatted raw record into the DB:try
                        try {
                            db.insertOrThrow(SkillTable.TABLE_NAME, null, values);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                sAttributeDB = db;


                /*
                List<Skill> skills = this.selectFormattedRecords();

                Integer skillCount = skills.size();

                for (int i = 0; i < skillCount; i++)
                {
                    System.out.println(skills.get(i));
                }
                */
            }
            catch (IOException e)
            {
                //log the exception
                e.printStackTrace();
            }
            finally
            {
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        //log the exception
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    class ObjectMaker {
        // Constructor, fields, initialization, etc...
        public Object makeObject(String className) throws Exception
        {
            // Load the class.
            Class<?> c = Class.forName(className);

            Constructor<?> ct = c.getConstructor();

            Object o = c.newInstance();

            return o;

            // Search for an "appropriate" constructor.
            /*
            for (Constructor<?> ctor : clazz.getConstructors()) {
                Class<?>[] paramTypes = ctor.getParameterTypes();


                // Instantiate the object with the converted arguments.
                return ctor.newInstance();

                /*
                // If the arity matches, let's use it.
                if (args.size() == paramTypes.length) {

                    // Convert the String arguments into the parameters' types.
                    Object[] convertedArgs = new Object[args.size()];
                    for (int i = 0; i < convertedArgs.length; i++) {
                        convertedArgs[i] = convert(paramTypes[i], args.get(i));
                    }

                    // Instantiate the object with the converted arguments.
                    return ctor.newInstance(convertedArgs);
                }

            }
            */
        }
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
