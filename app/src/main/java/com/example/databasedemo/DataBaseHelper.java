package com.example.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    //using Constant for column names

    private static final String DATABASE_NAME = "EmployeeDatabase";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "employees";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_JOIN_DATE = "joinDate";
    private static final String COLUMN_SALARY = "salary";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " varchar(200) NOT NULL, " +
                COLUMN_DEPARTMENT + " varchar(200) NOT NULL, " +
                COLUMN_JOIN_DATE + " varchar(200) NOT NULL, " +
                COLUMN_SALARY + " double NOT NULL); ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    boolean addEmployee(String name, String dept, String joiningDate, double salary) {

        //inorder to insert ,we need writable database;
        //this method returns a sqlite instance;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //contai value object
        ContentValues cv = new ContentValues();
        //this first argument of the put method is the columnn name and second value

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_DEPARTMENT,dept);
        cv.put(COLUMN_JOIN_DATE,joiningDate);
        cv.put(COLUMN_SALARY,salary);
        //insert returns vallue of rownumber and -1 is not sucessfull ;

       return  sqLiteDatabase.insert(TABLE_NAME,null,cv)!= 1;

    }

    Cursor getAllEmployee(){
        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);

    }
    boolean updateEmployee(int id,String name,String dept,double salary){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        //this first argument of the put method is the columnn name and second value

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_DEPARTMENT,dept);
        cv.put(COLUMN_SALARY,String.valueOf(salary));

        //returns the affected num of rows;
        return  sqLiteDatabase.update(TABLE_NAME,cv,COLUMN_ID+" = ? ",new String[]{String.valueOf(id)}) >0 ;
    }

    boolean deleteEmployee(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID+" = ? ",new String[]{String.valueOf(id)}) >0;

    }



}
