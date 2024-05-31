package com.example.cis695_capstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String COLUMN_WEIGHT = "WEIGHT";
    public static final String WEIGHT_TABLE = COLUMN_WEIGHT + "_TABLE";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_IMAGE_LOCATION = "IMAGE_LOCATION";
    public static final String SETTINGS_TABLE = "SETTINGS_TABLE";
    public static final String COLUMN_BEGINNING_WEIGHT = "BEGINNING_WEIGHT";
    public static final String COLUMN_GOAL_WEIGHT = "GOAL_WEIGHT";
    public static final String COLUMN_GENDER = "GENDER";
    public static final String COLUMN_HEIGHT = "HEIGHT";
    public static final String COLUMN_GOAL_DATE = "GOAL_DATE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "weightApp.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createWeightTable = "CREATE TABLE " + WEIGHT_TABLE + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WEIGHT + " INT, " +
                COLUMN_DATE + " TEXT, " + COLUMN_IMAGE_LOCATION + " TEXT)";

        String createSettingsTable = "CREATE TABLE " + SETTINGS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_BEGINNING_WEIGHT + " INT, " + COLUMN_GOAL_WEIGHT + " INT, " + COLUMN_GENDER +
                " BOOL, " + COLUMN_HEIGHT + " INT, " + COLUMN_GOAL_DATE + " TEXT)";

        db.execSQL(createWeightTable);
        db.execSQL(createSettingsTable);

        setSettings(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Todo: In theory this would be implemented
    }

    public boolean addEntry(weightEntry weightEntry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WEIGHT, weightEntry.getWeight());
        cv.put(COLUMN_IMAGE_LOCATION, weightEntry.getImage());
        cv.put(COLUMN_DATE, weightEntry.getDate());

        long insert = db.insert(WEIGHT_TABLE, null, cv);

        db.close();

        return insert != -1;
    }

    public boolean editEntry(weightEntry weightEntry, String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WEIGHT, weightEntry.getWeight());
        cv.put(COLUMN_IMAGE_LOCATION, weightEntry.getImage());
        cv.put(COLUMN_DATE, weightEntry.getDate());

        long insert = db.update(WEIGHT_TABLE, cv, "_id = ?", new String[]{ID});

        db.close();

        return insert != -1;
    }

    // In cases where multiple users exist, each row would represent a different user. Not in scope for this though.
    public boolean updateSettings(int beginningWeight, int goalWeight, Boolean gender, int height, String goalDate){
         SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_BEGINNING_WEIGHT, beginningWeight);
        cv.put(COLUMN_GOAL_WEIGHT, goalWeight);
        cv.put(COLUMN_HEIGHT, height);
        cv.put(COLUMN_GOAL_DATE, goalDate);

        long insert = db.insert(SETTINGS_TABLE, null, cv);

        db.close();

        return insert != -1;
    }

    public void setSettings(SQLiteDatabase db){
        int beginningWeight = 200;
        int goalWeight = 180;
        boolean gender = true; // True = Male, False = Female.
        int height = 180;
        String goalDate = "Jan 27 2050";
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_BEGINNING_WEIGHT, beginningWeight);
        cv.put(COLUMN_GOAL_WEIGHT, goalWeight);
        cv.put(COLUMN_HEIGHT, height);
        cv.put(COLUMN_GOAL_DATE, goalDate);

        long insert = db.insert(SETTINGS_TABLE, null, cv);
    }

    public boolean getGender(){
        String queryString = "SELECT * FROM " + SETTINGS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToLast();
        int gender = cursor.getInt(3);
        cursor.close();
        return gender != 0;
    }

    public int getBegWeight(){
        String queryString = "SELECT * FROM " + SETTINGS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToLast();
        int begWeight = cursor.getInt(1);
        cursor.close();
        return begWeight;
    }

    public int getGoalWeight(){
        String queryString = "SELECT * FROM " + SETTINGS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToLast();
        int goalWeight = cursor.getInt(2);
        cursor.close();
        return goalWeight;
    }

    public int getHeight(){
        String queryString = "SELECT * FROM " + SETTINGS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToLast();;
        int height = cursor.getInt(4);
        cursor.close();
        db.close();
        return height;
    }

    public String getGoalDate(){
        String queryString = "SELECT * FROM " + SETTINGS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToLast();
        String goalDate = cursor.getString(5);
        cursor.close();
        db.close();
        return goalDate;
    }

    public List<weightEntry> getAllEntries(){
        List<weightEntry> history = new ArrayList<weightEntry>();
        String queryString = "SELECT * FROM " + WEIGHT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int weight = cursor.getInt(1);
                String date = cursor.getString(2);
                String location = cursor.getString(3);

                weightEntry newEntry = new weightEntry(weight, date, location);
                history.add(newEntry);
            } while(cursor.moveToNext());
        }

        cursor.close();

        return history;
    }
}
