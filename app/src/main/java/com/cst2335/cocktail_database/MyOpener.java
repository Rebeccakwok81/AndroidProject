package com.cst2335.cocktail_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = " CocktailDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "COCKTAIL";
    public final static String COL_NAME = "NAME";
    public final static String COL_ID = "_id";

    public MyOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "

                + COL_NAME  + " text);");  // add or remove columns
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
//    {   //Drop the old table:
//        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
//
//        //Create the new table:
//        onCreate(db);
//    }
}
