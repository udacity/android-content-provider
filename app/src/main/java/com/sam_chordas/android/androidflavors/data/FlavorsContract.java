package com.sam_chordas.android.androidflavors;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class FlavorsContract{

	public static final String CONTENT_AUTHORITY = "com.sam_chordas.android.androidflavors.app";

	public static final BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final String FLAVOR = "flavors";

	public static final class FlavorEntry implements BaseColumns{
		public static final String TABLE_FLAVORS = FLAVOR;
		// columns
		public static final String _ID = "_id";
		public static final String COLUMN_ICON = "icon";
		public static final String COLUMN_VERSION_NAME = "version_name";
		public static final String COLUMN_VERSION_NUMBER = "version_number";

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
			.appendPath(PATH_MOVIES).build();
		public static final String CONTENT_TYPE = 
		ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + FLAVOR;
		public static final String CONTENT_ITEM_TYPE =
			ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + FLAVOR;

		public static Uri buildFlavorsUri(long id){
        		return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}
}
