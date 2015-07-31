package com.sam_chordas.android.androidflavors.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class FlavorsDBHelper extends SQLiteOpenHelper {
	public static final String LOG_TAG = FlavorsDBHelper.class.getSimpleName();

	//name & version
	private static final String DATABASE_NAME = "flavors.db";
	private static final int DATABASE_VERSION = 12;

	public FlavorsDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Create the database
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
				FlavorsContract.FlavorEntry.TABLE_FLAVORS + "(" + FlavorsContract.FlavorEntry._ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				FlavorsContract.FlavorEntry.COLUMN_VERSION_NAME + " TEXT NOT NULL, " +
				FlavorsContract.FlavorEntry.COLUMN_DESCRIPTION +
				" TEXT NOT NULL, " +
				FlavorsContract.FlavorEntry.COLUMN_ICON +
				" INTEGER NOT NULL);";

		sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
	}

	// Upgrade database when version is changed.
	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
				newVersion + ". OLD DATA WILL BE DESTROYED");
		// Drop the table
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FlavorsContract.FlavorEntry.TABLE_FLAVORS);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                FlavorsContract.FlavorEntry.TABLE_FLAVORS + "'");

		// re-create database
		onCreate(sqLiteDatabase);
	}
}
