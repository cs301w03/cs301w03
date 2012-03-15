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

package cmput301W12.android.project.backUp;

import java.sql.Timestamp;
import android.content.ContentValues;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//NOTICE: HOW TO ENFORCE INTEGRITY CONSTRAINTS IN SQLITE 3.5.9 USED IN ANDROID 2.1???
//NOT EASY AT ALL !!!!!!!!!!!!!!!!!!!!!! --> look at bookmark in safari !!!


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

	public static String returnIdColumn(String table){
		String id = "";
		if(table.equalsIgnoreCase(PHOTO_TABLE) == true){
			id = PHOTOID;
		}
		if(table.equalsIgnoreCase(GROUP_TABLE) == true){
			id = GROUPID;
		}
		if(table.equalsIgnoreCase(SKIN_TABLE) == true){
			id = SKINCONDITIONID;
		}
		if(table.equalsIgnoreCase(PHOTOGROUP_TABLE) == true){
			id = "ROWID";
		}
		if(table.equalsIgnoreCase(PHOTOSKIN_TABLE) == true){
			id = "ROWID";
		}
		return id;
	}

	/**
	 * Delete the entry with the given rowId from the given table. 
	 * CHECK: Does the "ROWID" always exist in any table? YES!
	 * @param rowId id of the fuel to delete
	 * @param table from which the entry is removed
	 * @return true if some row is deleted, false if no row is deleted.
	 * NOTICE: the rowId is infact identical to the primary key in PHOTO_TABLE, GROUP_TABLE and SKIN_TABLE.
	 */
	public int deleteEntry(long rowId, String table) {
		String id = DbAdapter.returnIdColumn(table);
		return mDb.delete(table, id + " = ?s", new String[]{rowId + ""}) ;
	}

	/**
	 * Update photo
	 * @param rowId
	 * @param newLocation
	 * @param newTimeStamp
	 * @param newName
	 * @return
	 */
	public int updatePhoto(long photoId, String newLocation, Timestamp newTimeStamp, String newName ){

		ContentValues initialValues = new ContentValues();

		if(newLocation != null){
			initialValues.put(LOCATION, newLocation);
		}

		if(newTimeStamp != null){
			initialValues.put(TIMESTAMP, newTimeStamp.toString());
		}

		if(newName != null){
			initialValues.put(PHOTONAME, newName);
		}

		return mDb.update(PHOTO_TABLE, initialValues , 
				DbAdapter.returnIdColumn(PHOTO_TABLE) + " = ?s", new String[]{photoId + ""});
	}
	/**
	 * Update group
	 * @param rowId
	 * @param newName
	 * @return
	 */
	public int updateGroup(long groupId, String newName ){
		ContentValues initialValues = new ContentValues();

		if(newName != null){
			initialValues.put(GROUPNAME, newName);
		}

		return mDb.update(GROUP_TABLE, initialValues , 
				DbAdapter.returnIdColumn(GROUP_TABLE) + " = ?s", new String[]{groupId + ""});
	}

	/**
	 * Update skin
	 * @param rowId
	 * @param newName
	 * @return
	 */
	public int updateSkin(long skinId, String newName ){
		ContentValues initialValues = new ContentValues();

		if(newName != null){
			initialValues.put(SKINNAME, newName);
		}

		return mDb.update(SKIN_TABLE, initialValues ,
				DbAdapter.returnIdColumn(SKIN_TABLE) + " = ?s", new String[]{skinId + ""});
	}

	public int updatePhotoGroup(long rowId, int photoId, int groupId	){
		ContentValues cv = new ContentValues();
		cv.put(PHOTOID, photoId);
		cv.put(GROUPID,groupId);
		return mDb.update(PHOTOGROUP_TABLE, cv, 
				DbAdapter.returnIdColumn(PHOTOGROUP_TABLE) + " = " + rowId, null);
	}

	public int updatePhotoSkin(long rowId, int photoId, int skinId	){
		ContentValues cv = new ContentValues();
		cv.put(PHOTOID, photoId);
		cv.put(SKINCONDITIONID,skinId);
		return mDb.update(PHOTOSKIN_TABLE, cv, 
				DbAdapter.returnIdColumn(PHOTOSKIN_TABLE) + " = " + rowId, null);
	}

	
	
	/**
	 * fetch all containers associated with the indicated photo. table = PHOTOGROUP_TABLE for
	 * retrieving all groups associated with the photo, table = PHOTOSKIN_TABLE for retrieving all
	 * skin conditions associated with the photo.
	 * @param photoId
	 * @param table
	 * @return
	 */
	public Cursor fetchAllContainersOfAPhoto(int photoId, String table){
		String idName = "";

		if(table.equalsIgnoreCase(PHOTOGROUP_TABLE) == true){
			idName = GROUPID;
		}else if(table.equalsIgnoreCase(PHOTOSKIN_TABLE) == true){
			idName = SKINCONDITIONID;
		}

		Cursor mCursor = mDb.query(true, table, 
				new String[]{idName}, PHOTOID + " = " + photoId, null, null, null, null, null);

		return mCursor;
	}

	/**
	 * fetch all photos associated with the indicated container ( group or skin condition ). table = PHOTOGROUP_TALBE
	 * for retrieving all photos associated with the group, table = PHOTOSKIN_TABLE for retrieving all
	 * photos associated with the skin condition.
	 * @param containerId
	 * @param table
	 * @return
	 */
	public Cursor fetchAllPhotosOfAContainer(int containerId, String table){
		String idName = "";

		if(table.equalsIgnoreCase(PHOTOGROUP_TABLE) == true){
			idName = GROUPID;
		}else if(table.equalsIgnoreCase(PHOTOSKIN_TABLE) == true){
			idName = SKINCONDITIONID;
		}

		Cursor mCursor = mDb.query(true, table, 
				new String[]{PHOTOID}, idName + " = " + containerId, null, null, null, null, null);

		return mCursor;


	}

	/**
	 * Return a Cursor over the list of all entries in the indicated table
	 * 
	 * @return Cursor over all entries
	 */
	public Cursor fetchAllEntries(String table) {

		return mDb.query(table, null, 
				null, null, null, null, null);
	}

	public Cursor fetchAnEntry(long rowId, String table) throws SQLException {

		Cursor mCursor =

				mDb.query(true, table, null, 
						DbAdapter.returnIdColumn(table) + " = " + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}
}