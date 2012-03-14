/* Project 1: CMPUT 301 - Winter 2012 - University of Alberta


    Copyright (C) 2012 Hieu Ngo <hcngo@ualberta.ca>

		This program reuses components from Notepad tutorial on android dev guide website.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

// NOTICE: HOW TO ENFORCE INTEGRITY CONSTRAINTS IN SQLITE 3.5.9 USED IN ANDROID 2.1???
// NOT EASY AT ALL !!!!!!!!!!!!!!!!!!!!!! --> look at bookmark in safari !!!

package cmput301W12.android.project;

import java.sql.Timestamp;
import android.content.ContentValues;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple database access helper class. Defines the basic CRUD operations
 * for the database, and gives the ability to list all entries as well as
 * retrieve or modify a specific field. 
 */
public class DbAdapter {

	public static final String PHOTOID = "Photo ID";
	public static final String LOCATION = "Location";
	public static final String TIMESTAMP = "Time Stamp";
	public static final String PHOTONAME = "Name";

	public static final String GROUPID = "Group ID";
	public static final String GROUPNAME = "Name";

	public static final String SKINCONDITIONID = "Skin Condition ID";
	public static final String SKINNAME = "Name";

	private static final String TAG = "DbAdapter";
	private static final String DATABASE_NAME = "skinObserver";

	private static final String PHOTO_TABLE	 = "Photo";
	public static final String GROUP_TABLE = "Group";
	public static final String SKIN_TABLE = "Skin Condition";
	public static final String PHOTOGROUP_TABLE = "Photo - Group";
	public static final String PHOTOSKIN_TABLE = "Photo - Skin Condition";

	private static final int DATABASE_VERSION = 3;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;


	/**
	 * Database creation sql statement
	 */
	private static final String CREATE_PHOTO_TABLE = 
			"create table " + PHOTO_TABLE + " ( " + PHOTOID + " integer primary key autoincrement, " + 
					LOCATION + " text not null, " +
					TIMESTAMP + " text, " +
					PHOTONAME + " text, " +
					" unique( " + LOCATION + ")"	+	")";

	private static final String CREATE_GROUP_TABLE = 
			"create table " + GROUP_TABLE + " ( " + GROUPID + " integer primary key autoincrement, "
					+ GROUPNAME + " text not null, " +
					"unique( " + GROUPNAME + ") " + ")";

	private static final String CREATE_SKIN_TABLE = 
			"create table " + SKIN_TABLE + " ( " + SKINCONDITIONID + " integer primary key autoincrement, "
					+ SKINNAME + " text not null, " +
					"unique( " + SKINNAME + ") " + ")";


	private static final String CREATE_PHOTOGROUP_TABLE = 
			"create table " + PHOTOGROUP_TABLE + " ( " + PHOTOID + " integer not null, " + GROUPID + " integer not null, " +
					" primary key( " + PHOTOID + ", " + GROUPID + " ), " +
					" FOREIGN KEY( " + PHOTOID + " ) REFERENCES " + PHOTO_TABLE + " ( " + PHOTOID + " ), " +
					" FOREIGN KEY( " + GROUPID + " ) REFERENCES " + GROUP_TABLE + " ( " + GROUPID + " ), " + " )" ;

	private static final String CREATE_PHOTOSKIN_TABLE = 
			"create table " + PHOTOSKIN_TABLE + " ( " + PHOTOID + " integer not null, " + SKINCONDITIONID + " integer not null, " +
					" primary key( " + PHOTOID + ", " + SKINCONDITIONID + " ), " +
					" FOREIGN KEY( " + PHOTOID + " ) REFERENCES " + PHOTO_TABLE + " ( " + PHOTOID + " ), " +
					" FOREIGN KEY( " + SKINCONDITIONID + " ) REFERENCES " + SKIN_TABLE + " ( " + SKINCONDITIONID + " ), " + " )" ;



	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(CREATE_PHOTO_TABLE);
			db.execSQL(CREATE_GROUP_TABLE);
			db.execSQL(CREATE_SKIN_TABLE);
			db.execSQL(CREATE_PHOTOGROUP_TABLE);
			db.execSQL(CREATE_PHOTOSKIN_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + PHOTO_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + GROUP_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + SKIN_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + PHOTOGROUP_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + PHOTOSKIN_TABLE);
			onCreate(db);
		}
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx the Context within which to work
	 */
	public DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * Open the skinObserver database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException if the database could be neither opened or created
	 */
	public DbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}


	/**
	 * Add a new photo entry using the location, timeStamp and name provided. 
	 * If the new photo entry is successfully added, return the new PHOTOID for that entry, otherwise return
	 * -1 to indicate failure.
	 * @return PHOTOID or -1 if failed
	 */
	public long addPhoto(String location, Timestamp timeStamp, String name) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(LOCATION, location);
		initialValues.put(TIMESTAMP, timeStamp.toString());
		initialValues.put(PHOTONAME, name);

		return mDb.insert(PHOTO_TABLE, null, initialValues);
	}

	/**
	 * Add a new group entry using the name provided. 
	 * If the new group entry is successfully added, return the new GROUPID for that entry, otherwise return
	 * -1 to indicate failure.
	 * @return GROUPID or -1 if failed
	 */
	public long addGroup(String name) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(GROUPNAME, name);

		return mDb.insert(GROUP_TABLE, null, initialValues);
	}

	/**
	 * Add a new skin condition entry using the name provided. 
	 * If the new skin condition entry is successfully added, return the new SKINCONDITIONID for that entry, otherwise return
	 * -1 to indicate failure.
	 * @return SKINCONDITIONID or -1 if failed
	 */
	public long addSkinCondition(String name) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(SKINNAME, name);

		return mDb.insert(SKIN_TABLE, null, initialValues);
	}

	/**
	 * Add a new photo - group entry using the PHOTOID and GROUPID provided. 
	 * If the new photo - group entry is successfully added, return the row id for that entry, otherwise return
	 * -1 to indicate failure.
	 * @return row id or -1 if failed
	 */
	public long addPhotoGroup(int photoId, int groupId) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(PHOTOID, photoId);
		initialValues.put(GROUPID, groupId);

		return mDb.insert(PHOTOGROUP_TABLE, null, initialValues);
	}

	/**
	 * Add a new photo - skin condition entry using the PHOTOID and SKINCONDITIONID provided. 
	 * If the new photo - skin condition is successfully added, return the row id for that entry, otherwise return
	 * -1 to indicate failure.
	 * @return row id or -1 if failed
	 */
	public long addPhotoSkinCondition(int photoId, int skinConditionId) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(PHOTOID, photoId);
		initialValues.put(SKINCONDITIONID, skinConditionId);

		return mDb.insert(PHOTOSKIN_TABLE, null, initialValues);
	}

	/**
	 * Delete the fuel with the given rowId
	 * 
	 * @param rowId id of the fuel to delete
	 * @return true if deleted, false otherwise
	 */
	public boolean deleteNote(long rowId) {

		return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all fuel entries in the database
	 * 
	 * @return Cursor over all entries
	 */
	public Cursor fetchAllNotes() {

		return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID,
				KEY_DATE, KEY_STATION, KEY_GRADE, KEY_AMOUNT, KEY_UNITCOST, KEY_COST, KEY_DIS}, 
				null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the fuel that matches the given rowId
	 * 
	 * @param rowId id of the fuel to retrieve
	 * @return Cursor positioned to matching fuel, if found
	 * @throws SQLException if fuel could not be found/retrieved
	 */
	public Cursor fetchNote(long rowId) throws SQLException {

		Cursor mCursor =

				mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DATE, 
						KEY_STATION, KEY_GRADE, KEY_AMOUNT, KEY_UNITCOST, 
						KEY_COST, KEY_DIS}, 
						KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	/*
	/**
	 * Update the fuel using the details provided. The fuel to be updated is
	 * specified using the rowId, and it is altered to use the information values passed in.
	 * @param date the date of the fuel
	 * @param station the station of the fuel
	 * @param grade the fuel grade
	 * @param amount the fuel amount
	 * @param unitCost the fuel unit cost
	 * @param cost the fuel cost 
	 * @param dis the fuel trip distance
	 * @return true if the fuel entry was successfully updated, false otherwise
	 */
	/*
	public boolean updateNote(long rowId, String date, String station, String grade, Double amount, 
			Double unitCost, Double cost, Double dis){
		ContentValues initialValues = new ContentValues();
		//initialValues.put(KEY_TITLE, date);
		initialValues.put(KEY_DATE, date);
		initialValues.put(KEY_STATION, station);
		initialValues.put(KEY_GRADE, grade);
		initialValues.put(KEY_AMOUNT, amount);
		initialValues.put(KEY_UNITCOST, unitCost);
		initialValues.put(KEY_COST, cost);
		initialValues.put(KEY_DIS, dis);	
		return mDb.update(DATABASE_TABLE, initialValues, KEY_ROWID + "=" + rowId, null) > 0;
	}
	 */
}
