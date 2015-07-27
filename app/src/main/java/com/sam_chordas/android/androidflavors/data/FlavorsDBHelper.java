package com.sam_chordas.android.androidflavors;

import android.content.Context
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FlavorsDBHelper extends SQLiteOpenHelper{
	public static final String LOg_TAG = FlavorsDBHelper.class.getSimpleName();
	
	//name & version
	private static final String DATABASE_NAME = "flavors.db";
	private static final int DATABASE_VERSION = 1;

	public FlavorsDBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase){
		final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
			TABLE_FLAVORS + "(" + _ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_VERSION_NAME +
			+ " TEXT NOT NULL, " + COLUMN_ICON + " BLOB NOT NULL, " +
			COLUMN_VERSION_NUMBER + " TEXT NOT NULL);";

		sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
		Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " + 
				newVersion + ". OLD DATA WILL BE DESTROYED");

		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FLAVORS);

		onCreate(sqLiteDatabase);
	}
