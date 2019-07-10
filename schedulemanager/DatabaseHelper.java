package com.example.schedulemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "The abyss of data";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SUBJECT = "subject";
    private static final String TABLE_ASSIGNMENT = "assignment";
    private static final String TABLE_STATUS = "status";
    private static final String TABLE_DUE = "due";
    private static final String KEY_ID = "id";
    private static final String KEY_SUBJECT = "key_sub";
    private static final String KEY_ASSIGNMENT = "key_assign";
    private static final String KEY_STATUS = "key_status";
    private static final String KEY_DUE = "key_due";

    private static final String INDEX_SUB = "index_sub";
    private static final String INDEX_SUB_TABLE = "index_sub_table";

    private static final String INDEX_ASSIGN = "index_assign";
    private static final String INDEX_ASSIGN_TABLE = "index_assign_table";

    private static final String INDEX_STATUS = "index_status";
    private static final String INDEX_STATUS_TABLE = "index_status_table";

    private static final String INDEX_DUE = "index_due";
    private static final String INDEX_DUE_TABLE = "index_due_table";

    private static final String CREATE_SUBJECT = "CREATE TABLE " + TABLE_SUBJECT + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SUBJECT + " TEXT );";

    private static final String CREATE_ASSIGNMENT = "CREATE TABLE " + TABLE_ASSIGNMENT + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ASSIGNMENT + " TEXT );";

    private static final String CREATE_STATUS = "CREATE TABLE " + TABLE_STATUS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_STATUS + " TEXT );";

    private static final String CREATE_DUE = "CREATE TABLE " + TABLE_DUE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DUE + " TEXT );";

    private static final String CREATE_SUB_INDEX = "CREATE TABLE " + INDEX_SUB_TABLE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + INDEX_SUB + " INTEGER );";

    private static final String CREATE_ASSIGN_INDEX = "CREATE TABLE " + INDEX_ASSIGN_TABLE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + INDEX_ASSIGN + " INTEGER );";

    private static final String CREATE_STATUS_INDEX = "CREATE TABLE " + INDEX_STATUS_TABLE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + INDEX_STATUS + " INTEGER );";

    private static final String CREATE_DUE_INDEX = "CREATE TABLE " + INDEX_DUE_TABLE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + INDEX_DUE + " INTEGER );";

    public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SUBJECT);
        db.execSQL(CREATE_ASSIGNMENT);
        db.execSQL(CREATE_STATUS);
        db.execSQL(CREATE_DUE);

        db.execSQL(CREATE_SUB_INDEX);
        db.execSQL(CREATE_ASSIGN_INDEX);
        db.execSQL(CREATE_STATUS_INDEX);
        db.execSQL(CREATE_DUE_INDEX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SUBJECT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_ASSIGNMENT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_STATUS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_DUE + "'");

        db.execSQL("DROP TABLE IF EXISTS '" + INDEX_SUB_TABLE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + INDEX_ASSIGN_TABLE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + INDEX_STATUS_TABLE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + INDEX_DUE_TABLE + "'");
    }

    /**
     * HANDLING SUBJECT
     **/

    public void addSubject(String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_SUBJECT, subject);
        db.insert(TABLE_SUBJECT, null, contentValues);
    }

    public boolean deleteSubject(String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SUBJECT, KEY_SUBJECT + " = ?", new String[]{subject}) > 0;
    }

    public void addSubjectID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(INDEX_SUB, id);
        db.insert(INDEX_SUB_TABLE, null, contentValues);
    }

    public boolean deleteSubID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INDEX_SUB_TABLE, INDEX_SUB + " =?", new String[]{(String.valueOf(id))}) > 0;
    }

    public ArrayList<String> getSubject() {
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SUBJECT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT)));
            cursor.moveToNext();
        }

        return list;
    }

    public ArrayList<Integer> getSubjectIDs() {
        ArrayList<Integer> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + INDEX_SUB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            list.add(cursor.getInt(cursor.getColumnIndex(INDEX_SUB)));
            cursor.moveToNext();
        }

        return list;
    }

    public HashMap<Integer, String> getSubjectMap() {
        HashMap<Integer, String> map = new HashMap<>();
        for(int i=0; i<getSubjectIDs().size(); i++) {
            map.put(getSubjectIDs().get(i), getSubject().get(i));
        }
        return map;
    }

    /**
     * HANDLING ASSIGNMENT
     **/

    public void addAssignment(String assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_ASSIGNMENT, assignment);
        db.insert(TABLE_ASSIGNMENT, null, contentValues);
    }

    public boolean deleteAssignment(String assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ASSIGNMENT, KEY_ASSIGNMENT + " = ?", new String[]{assignment}) > 0;
    }

    public void addAssignmentID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(INDEX_ASSIGN, id);
        db.insert(INDEX_ASSIGN_TABLE, null, contentValues);
    }

    public boolean deleteAssignmentID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INDEX_ASSIGN_TABLE, INDEX_ASSIGN + " =?", new String[]{(String.valueOf(id))}) > 0;
    }

    public ArrayList<String> getAssignment() {
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ASSIGNMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(KEY_ASSIGNMENT)));
            cursor.moveToNext();
        }

        return list;
    }

    public ArrayList<Integer> getAssignmentIDs() {
        ArrayList<Integer> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + INDEX_ASSIGN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            list.add(cursor.getInt(cursor.getColumnIndex(INDEX_ASSIGN)));
            cursor.moveToNext();
        }

        return list;
    }

    public HashMap<Integer, String> getAssignmentMap() {
        HashMap<Integer, String> map = new HashMap<>();
        for(int i=0; i<getAssignmentIDs().size(); i++) {
            map.put(getAssignmentIDs().get(i), getAssignment().get(i));
        }
        return map;
    }

    /**
     * HANDLING STATUS
     **/

    public void addStatus(String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_STATUS, status);
        db.insert(TABLE_STATUS, null, contentValues);
    }

    public boolean deleteStatus(String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STATUS, KEY_STATUS + " =?", new String[]{status}) > 0;
    }

    public void addStatusID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(INDEX_STATUS, id);
        db.insert(INDEX_STATUS_TABLE, null, contentValues);
    }

    public boolean deleteStatusID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INDEX_STATUS_TABLE, INDEX_STATUS+ " =?", new String[]{(String.valueOf(id))}) > 0;
    }

    public ArrayList<String> getStatus() {
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STATUS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
            cursor.moveToNext();
        }

        return list;
    }

    public ArrayList<Integer> getStatusIDs() {
        ArrayList<Integer> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + INDEX_STATUS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            list.add(cursor.getInt(cursor.getColumnIndex(INDEX_STATUS)));
            cursor.moveToNext();
        }

        return list;
    }

    public HashMap<Integer, String> getStatusMap() {
        HashMap<Integer, String> map = new HashMap<>();
        for(int i=0; i<getStatusIDs().size(); i++) {
            map.put(getStatusIDs().get(i), getStatus().get(i));
        }
        return map;
    }


    /**
     * HANDLING DUE DATE
     **/

    public void addDue(String due) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_DUE, due);
        db.insert(TABLE_DUE, null, contentValues);
    }

    public boolean deleteDue(String due) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_DUE, KEY_DUE + " =?", new String[]{due}) > 0;
    }

    public void addDueID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(INDEX_DUE, id);
        db.insert(INDEX_DUE_TABLE, null, contentValues);
    }

    public boolean deleteDueID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INDEX_DUE_TABLE, INDEX_DUE + " =?", new String[]{(String.valueOf(id))}) > 0;
    }

    public ArrayList<String> getDue() {
        ArrayList<String> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DUE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(KEY_DUE)));
            cursor.moveToNext();
        }

        return list;
    }

    public ArrayList<Integer> getDueIDs() {
        ArrayList<Integer> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + INDEX_DUE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            list.add(cursor.getInt(cursor.getColumnIndex(INDEX_DUE)));
            cursor.moveToNext();
        }

        return list;
    }

    public HashMap<Integer, String> getDueMap() {
        HashMap<Integer, String> map = new HashMap<>();
        for(int i=0; i<getDueIDs().size(); i++) {
            map.put(getDueIDs().get(i), getDue().get(i));
        }
        return map;
    }

    /**
     * END OF HANDLING
     */

}
