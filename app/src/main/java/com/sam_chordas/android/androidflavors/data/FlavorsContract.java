package com.sam_chordas.android.androidflavors.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class FlavorsContract{

	public static final String CONTENT_AUTHORITY = "com.sam_chordas.android.androidflavors.app";

	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final String FLAVOR = "flavor";

	public static final class FlavorEntry implements BaseColumns{
		// table name
		public static final String TABLE_FLAVORS = FLAVOR;
		// columns
		public static final String _ID = "_id";
		public static final String COLUMN_ICON = "icon";
		public static final String COLUMN_VERSION_NAME = "version_name";

		// create content uri
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
			.appendPath(TABLE_FLAVORS).build();
		// create cursor of base type directory for multiple entries
		public static final String CONTENT_DIR_TYPE =
		ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FLAVORS;
		// create cursor of base type item for single entry
		public static final String CONTENT_ITEM_TYPE =
			ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_FLAVORS;

		// for building URIs on insertion
		public static Uri buildFlavorsUri(long id){
        		return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}
}
