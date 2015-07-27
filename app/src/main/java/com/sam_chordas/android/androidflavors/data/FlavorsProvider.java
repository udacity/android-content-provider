package com.sam_chordas.android.androidflavors;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class FlavorsProvider extends ContentProvider{
	private static final String LOG_TAG = FlavorsProvider.class.getSimpleName();
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	private FlavorsDBhelper mOpenHelper;

	private static final int FLAVOR = 100;
	private static final int FLAVOR_WITH_ID = 200;

	private static UriMatcher buildUriMatcher(){
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = FlavorContract.CONTENT_AUTHORITY;

		matcher.addURI(authority, FlavorEntry.TABLE_FLAVORS, FLAVOR);
		matcher.addURI(authority, FlavorEntry.TABLE_FLAVORS + "/#", FLAVOR_WITH_ID);

		return matcher;	
	}

	@Override
	public boolean onCreate(){
		mOpenHelper = new FlavorDBHelper(getContext());

		return true;
	}

	@Override
	public String getType(Uri uri){
		final int match = sUriMatcher.match(uri);

		switch (match){
			case FLAVOR:{
				return FlavorEntry.CONTENT_DIR_TYPE;
			}
			case FLAVOR_WITH_ID:{ 
				return FlavorEntry.CONTENT_ITEM_TYPE;
			}
			default:{
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
		Cursor retCursor;
		switch(sUriMatcher.match(uri){
			case FLAVOR:{    
				retCursor = mOpenHelper.getReadableDatabase().query(FlavorEntry.TABLE_FLAVORS,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder);
				return retCursor;
			}
			case FLAVOR_WITH_ID:{
				retCursor = mOpenHelper.getReadableDatabase().query(FlavorEntry.TABLE_FLAVORS,
						projection,
						FlavorEntry._ID + " = ?",
						new String[] {String.valueOf(ContentUris.parseId(uri))},
						null,
						null,
						sortOrder);
				return retCursor;
			}
			default:{
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values){
		final SQLitedatabase db = mOpenHelper.getWritableDatabase();
		case FLAVOR:{
			long _id = db.insert(FlavorEntry.TABLE_FLAVORS, null, values);
			if(_id > 0){
				returnUri = FlavorEntry.buildFlavorsUri(_id);
			} else{
				throw new android.database.SQLException("Failed to insert row into: " + uri);			
			}
			break;
		}

		default: {
			throw new UnsupportedOperationException("Unknown uri: " + uri);

		}
		getContext().getContentResolver().notifyChange(uri, null);
		return returnUri;
	}

	@Override
	public Uri delete(Uri uri, String selection, String[] selectionArgs){
		final SQLitedatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		int numDeleted;
		switch(match){
			case FLAVOR:
				numDeleted = db.delete(
						FlavorEntry.TABLE_FLAVORS, selection, selectionArgs);
				// reset _ID
				db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + 
						FlavorEntry.TABLE_FLAVORS + "'");
				break;
			case FLAVOR_WITH_ID:
				numDeleted = db.delete(FlavorEntry.TABLE_FLAVORS, FlavorEntry._ID + " = ?",
						new String[]{String.valueOf(ContentsUris.parseId(uri))});
				// reset _ID
				db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + 
						FlavorEntry.TABLE_FLAVORS + "'");

				break;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values){
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);

		switch(match){
			case FLAVOR:
				db.beginTransaction();

				int numInserted = 0;
				try{
					for(ContentValues value : values){
						if (value == null){
							throw new IllegalArgumentException("Cannot have null content values");
						}
						long _id = -1;
						try{
							-id = db.insertOrThrow(FlavorEntry.TABLE_FLAVORS, null, value);
						}catch(SQLiteConstraintException e) {
							Log.w(LOG_TAG, "Attempting to insert " + value.getAsString(FlavorEntry.COLUMN_VERSION_NAME)
									+ " but value is already in database.");
						}
						if (_id != 1){
							numInserted++;
						}
					}
				} finally {
					db.endTransaction();
				}
				if (numInserted > 0){
					getContext().getContentResolver().notifyChange(uri, null);
				}
				return numInserted;
			default:
				return super.bulkInsert(uri, values);
		}
	}


}
