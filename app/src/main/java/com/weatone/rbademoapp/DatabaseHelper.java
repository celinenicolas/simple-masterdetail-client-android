package com.weatone.rbademoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * @author Celine Nicolas
 * @version 1.0.0, 2017-04-07
 * @since 1.0.0
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "main.db";

    private static final String TABLE_PEOPLE  = "people";
    private static final String TABLE_PETS    = "pets";


    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // PEOPLE
        db.execSQL(CREATE_TABLE_PEOPLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addListOfPeople(ArrayList<People> peopleList) {
        SQLiteDatabase db = getWritableDatabase();

        boolean hasRolledBackOrErrors = false;
        db.beginTransaction();

        try {

            for ( People people : peopleList ) {

                ContentValues values = new ContentValues();
                values.put("firstname", people.getFirstname());
                values.put("lastname", people.getLastname());
                /*...*/

                int id = (int) db.insertOrThrow(TABLE_PEOPLE, null, values);
                if ( id == -1 ) {
                    hasRolledBackOrErrors = true;
                    Log.e("TEST", "Error inserting the object '" + people + "'");
                    throw new Exception();
                }


            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            hasRolledBackOrErrors = true;
        } finally {
            db.endTransaction();
        }

        if (hasRolledBackOrErrors)
            Log.d("TEST", "Transaction failed.");
        else
            Log.d("TEST", "Transaction complete. " + peopleList.size() + " objects inserted.");

        db.close();

        return !hasRolledBackOrErrors;
    }





    private static final String CREATE_TABLE_PEOPLE = "CREATE TABLE " + TABLE_PEOPLE + " (" +
            "id INTEGER PRIMARY KEY, " +
            "date_created NUMERIC, " +              // Date created UTC >>> $time
            "date_synced NUMERIC, " +               // Date synchronized
            "firstname TEXT, " +
            "lastname TEXT, " +
            "age TEXT, " +
            "address_unit TEXT, " +
            "address_streetname TEXT, " +
            "address_city TEXT, " +
            "address_province TEXT, " +
            "address_postalcode TEXT, " +
            "address_country TEXT" +
            ");";
}
