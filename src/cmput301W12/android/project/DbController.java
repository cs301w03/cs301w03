package cmput301W12.android.project;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
/**
 * @user Hieu Ngo
 * @date Mar 14, 2012
 * 
 * Extends system model
 * 
 * Note: This class name is expected to be changed in the next phase
 */
public class DbController extends FModel<FView> implements DbControllerInterface {
	private DbAdapter mDbAdap;

	private static DbController dbCon = null;

	private DbController(Context ct){
		super();
		Log.d("DBController", "Trail part 6");
		this.mDbAdap = DbAdapter.getDbAdapter(ct);
		Log.d("DBController", "Trail part end of 6");
		this.mDbAdap = this.mDbAdap.open();
	}

	public static DbControllerInterface getDbController(Context ct){
		if(dbCon == null){
			Log.d("DBController", "Trail part 5");
			dbCon = new DbController(ct);
		}
		return dbCon;
	}

	public void close(){
		this.mDbAdap.close();
	}


	@Override
	public Photo addPhoto(Photo phoObj) {
		Log.d("SKINOBSERVER", "There is more XD");
		// TODO Auto-generated method stub
		String location = phoObj.getLocation();
		Log.d("SKINOBSERVER", "There is more XD!");
		Timestamp timeStamp  = phoObj.getTimeStamp();
		Log.d("SKINOBSERVER", "There is more XD!!");
		String name = phoObj.getName();
		Log.d("SKINOBSERVER", "There is more XD!!!");
		int photoId = ( int) this.mDbAdap.addPhoto(location, timeStamp, name);
		Log.d("SKINOBSERVER", " " + photoId);
		Log.d("SKINOBSERVER", "There is more XD!!!!");
		phoObj.setPhotoId(photoId);

		Log.d("SKINOBSERVER", "There is more XD!!!!!!");
		Set<Integer> listOfGroupId = phoObj.getGroups();
		Log.d("SKINOBSERVER", "There is more XD!!!!!!!");
		Set<Integer> listOfSkinId = phoObj.getSkinConditions();

		for(Integer id : listOfGroupId){
			this.mDbAdap.addPhotoGroup(photoId,id);
		}
		Log.d("SKINOBSERVER", "1");


		//		for(Integer id : listOfSkinId	){
		//		    Log.d("SKINOBSERVER", "Nope");
		//			this.mDbAdap.addPhotoSkinCondition(photoId, id);
		//		}
		Log.d("SKINOBSERVER", "2");
		return phoObj;
	}

	public Container storeNewContainer(String name, OptionType option){
		Container container = null;
		if(option == OptionType.GROUP){
			int id = (int) mDbAdap.addGroup(name);
			Group newGroup = new Group(name);
			newGroup.setItemId(id);
			container = newGroup;
		} else if(option == OptionType.SKINCONDITION){
			int id = (int) mDbAdap.addSkinCondition(name);
			SkinCondition newSkinCondition = new SkinCondition(name);
			newSkinCondition.setItemId(id);
			container = newSkinCondition;
		}

		return container;
	}

	public Container addContainObj(Container containObj) {
		// TODO Auto-generated method stub
		String name = containObj.getName();
		Set<Integer> listOfPhotoIds= containObj.getPhotos();

		if(containObj instanceof Group){

			if(containObj.getItemId() == Photo.INVALID_ID){
				int groupId = (int) this.mDbAdap.addGroup(name);
				containObj.setItemId(groupId);
			}
			int validGroupId = containObj.getItemId();
			for(Integer id : listOfPhotoIds){
				this.mDbAdap.addPhotoGroup(id, validGroupId);
			}
		} 
		else if(containObj instanceof SkinCondition){
			if(containObj.getItemId() == Photo.INVALID_ID){
				int skinId = (int) this.mDbAdap.addSkinCondition(name);
				containObj.setItemId(skinId);
			}

			int validSkinId = containObj.getItemId();
			for(Integer id : listOfPhotoIds){
				this.mDbAdap.addPhotoSkinCondition(id, validSkinId);
			}
		}

		return containObj;
	}

	private static SortedSet<Photo> getPhotoFromCursor(Cursor cursor){
		boolean repeat = false;
		int photoId;
		String location, name;
		Timestamp timeStamp;

		Photo photo;
		SortedSet<Photo> aSet = new TreeSet<Photo>();

		if (cursor != null) {
			repeat = cursor.moveToFirst();
		}

		while(repeat){
			photoId = cursor.getInt(cursor.getColumnIndexOrThrow(DbAdapter.PHOTOID));
			location = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.LOCATION));
			timeStamp = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.TIMESTAMP)));
			name = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.PHOTONAME));
			photo = new Photo(location, timeStamp, name, null, null);
			photo.setPhotoId(photoId);
			aSet.add(photo);
			repeat = cursor.moveToNext();
		}

		return aSet;
	}

	public SortedSet<Photo> getAllPhoto(){
		Cursor cursor = this.fetchAllPhotoObj();
		return DbController.getPhotoFromCursor(cursor);
	}

	public SortedSet<Photo> getAllPhotoOfAContainer(int itemId, OptionType option){
		Cursor cursor = this.fetchAllPhotoObjConnected(itemId, option);
		return DbController.getPhotoFromCursor(cursor);
	}


	public Set<? extends Container> getAllContainersOfAPhoto(int photoId, OptionType option){
		Cursor cursor = this.fetchAllContainers(photoId, option);
		boolean repeat = false;

		Container container;

		int itemId;
		String name = "";
		String column = "";
		if(photoId == Photo.INVALID_ID){
			if(option == OptionType.PHOTOGROUP){
				column = DbAdapter.GROUPID;
				name = DbAdapter.GROUPNAME;

				Set<Group> aSet = new HashSet<Group>();

				if (cursor != null) {
					repeat = cursor.moveToFirst();
				}

				while(repeat){
					itemId = cursor.getInt(1);
					//name = cursor.getString(2);

					container = new Group(name);
					container.setItemId(itemId);
					aSet.add((Group) container);
					repeat = cursor.moveToNext();
				}

				return aSet;
			}else if (option == OptionType.PHOTOSKIN){
				column = DbAdapter.SKINCONDITIONID;
				name = DbAdapter.SKINNAME;

				if (cursor != null) {
					repeat = cursor.moveToFirst();
				}

				Set<SkinCondition> aSet = new HashSet<SkinCondition>();

				while(repeat){
					itemId = cursor.getInt(1);
					//name = cursor.getString(2);

					container = new SkinCondition(name);
					container.setItemId(itemId);

					aSet.add((SkinCondition)container);
					repeat = cursor.moveToNext();
				}

				return aSet;
			}
		}
		return null;
	}

	public Cursor fetchAllPhotoObj() {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllEntries(OptionType.PHOTO);
	}


	public Cursor fetchAllPhotoObjConnected(int itemId, OptionType option) {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllPhotosOfAContainer(itemId, option);
	}


	public Cursor fetchAllContainers(int photoId, OptionType option) {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllContainersOfAPhoto(photoId, option);
	}

	public Set<? extends Container> getAllContainers(OptionType option)
	{
		Cursor cursor = this.mDbAdap.fetchAllContainers(option);
		boolean repeat = true;

		Container container;

		int itemId;
		String name = "";
		String column = "";

		if(option == OptionType.GROUP){
			Set<Group> aSet = new HashSet<Group>();

			if (cursor != null) {
					repeat = cursor.moveToFirst();
			}
			
			while(repeat){
				itemId = cursor.getInt(cursor.getColumnIndex(DbAdapter.GROUPID));
				name = cursor.getString(cursor.getColumnIndex(DbAdapter.GROUPNAME));

				container = new Group(name);
				container.setItemId(itemId);
				aSet.add((Group) container);
				repeat = cursor.moveToNext();
			}

			return aSet;
		}else if (option == OptionType.SKINCONDITION){

			if (cursor != null) {
					repeat = cursor.moveToFirst();
			}

			Set<SkinCondition> aSet = new HashSet<SkinCondition>();

			while(repeat){
				itemId = cursor.getInt(cursor.getColumnIndex(DbAdapter.SKINCONDITIONID));
				name = cursor.getString(cursor.getColumnIndex(DbAdapter.SKINNAME));

				container = new SkinCondition(name);
				container.setItemId(itemId);

				aSet.add((SkinCondition)container);
				repeat = cursor.moveToNext();
			}

			return aSet;
		}

		return null;
	}
	
	public int deleteEntry(long rowID, OptionType option) {
		return this.mDbAdap.deleteEntry(rowID, option);
	}


}

