package com.example.chris.newmanager;

/**
 * Created by Chris on 13.07.16.
 */
import android.database.sqlite.SQLiteOpenHelper;

import android .database.sqlite.SQLiteDatabase;
import android .database.sqlite.SQLiteOpenHelper;
import android .database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.widget.ListView;

import java.util.ArrayList;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products.db";
    public static final String TABLE_NAME = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "_productname";
    public static final String COLUMN_EAN = "_productean";

    //Constructor.
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME +"(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PRODUCTNAME + " TEXT," +
                COLUMN_EAN + " Text," +
                ");";

        sqLiteDatabase.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    //Add a new row to the database.
    public void addProduct(Products product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productname()); //Where, what. (Not writing to database yet.)
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NAME, null, values); //Writing to database now.
        sqLiteDatabase.close();
    }

    //Delete a product from the database.
    public void deleteProduct(String productName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCTNAME + "=\"" + productName + "\"";
        sqLiteDatabase.execSQL(query);
    }

    //Print out the database as a string.
    public ArrayList<String> databaseToString(ArrayList<String> array) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1"; //Select every column, select every row.

        Cursor cursor = sqLiteDatabase.rawQuery(query, null); //Cursor point to a location in results.
        cursor.moveToFirst(); //Move to the first row in results.
        array.clear();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("_productname")) != null) {

                    array.add(cursor.getString(cursor.getColumnIndex("_productname")));

            }
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return array;
    }

    public int getAmountOfValues(){
        int count = 0;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1 "; //Select every column, select every row.

        Cursor cursor = sqLiteDatabase.rawQuery(query, null); //Cursor point to a location in results.
        cursor.moveToFirst(); //Move to the first row in results.

        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("_productname")) != null) {
                count++;

            }
            cursor.moveToNext();
        }
        cursor.close();

        sqLiteDatabase.close();

        return count;
    }
    public void deleteAll(){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
