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
		this.mDbAdap = DbAdapter.getDbAdapter(ct);
		this.mDbAdap = this.mDbAdap.open();
	}

	public static DbControllerInterface getDbController(Context ct){
		if(dbCon == null){
			dbCon = new DbController(ct);
		}
		return dbCon;
	}

	public void close(){
		this.mDbAdap.close();
	}


	@Override
	public Photo addPhoto(Photo phoObj) {
		int photoId = phoObj.getPhotoId();
		String location = phoObj.getLocation();
		Timestamp timeStamp  = phoObj.getTimeStamp();
		String name = phoObj.getName();

		if(photoId == Photo.INVALID_ID){
			photoId = ( int) this.mDbAdap.addPhoto(location, timeStamp, name);
			phoObj.setPhotoId(photoId);
		}

		Set<Integer> listOfGroupId = phoObj.getGroups();
		Set<Integer> listOfSkinId = phoObj.getSkinConditions();

		for(Integer id : listOfGroupId){
			this.mDbAdap.addPhotoGroup(photoId,id);
		}


		for(Integer id : listOfSkinId	){
			this.mDbAdap.addPhotoSkinCondition(photoId, id);
		}
		return phoObj;
	}

	public Container addContainObj(Container containObj) {

		String name = containObj.getName();
		Set<Integer> listOfPhotoIds= containObj.getPhotos();

		if(containObj instanceof Group){

			if(containObj.getItemId() == Photo.INVALID_ID){
				int groupId = (int) this.mDbAdap.addGroup(name);
				containObj.setItemId(groupId);
			}
			int validGroupId = containObj.getItemId();
			if(validGroupId != Photo.INVALID_ID){
				for(Integer id : listOfPhotoIds){
					this.mDbAdap.addPhotoGroup(id, validGroupId);
				}
			}
		} 
		else if(containObj instanceof SkinCondition){
			if(containObj.getItemId() == Photo.INVALID_ID){
				int skinId = (int) this.mDbAdap.addSkinCondition(name);
				containObj.setItemId(skinId);
			}
			int validSkinId = containObj.getItemId();
			if(validSkinId != Photo.INVALID_ID){
				for(Integer id : listOfPhotoIds){
					this.mDbAdap.addPhotoSkinCondition(id, validSkinId);
				}
			}
		}

		return containObj;
	}


	public SortedSet<Photo> getAllPhoto(){
		Cursor cursor = this.fetchAllPhotoObj();
		return DbController.getPhotoFromCursor(cursor);
	}

	public SortedSet<Photo> getAllPhotoOfAContainer(int itemId, OptionType option){
		Cursor cursor = this.fetchAllPhotoObjConnected(itemId, option);
		return DbController.getPhotoFromCursor(cursor);
	}

	public Set<? extends Container> getAllContainers(OptionType option)
	{
		Cursor cursor = this.fetchAllContainers(option);
		return DbController.getContainersFromCursor(cursor, option);
	}

	public Set<? extends Container> getAllContainersOfAPhoto(int photoId, OptionType option){
		Cursor cursor = this.fetchAllContainersOfAPhoto(photoId, option);
		return DbController.getContainersFromCursor(cursor, option);
	}



	public int deleteEntry(long rowID, OptionType option) {
		return this.mDbAdap.deleteEntry(rowID, option);
	}

	public static Set<? extends Container> getContainersFromCursor(Cursor cursor, OptionType option){
		boolean repeat = true;

		Container container;

		int itemId;
		String name = "";

		if(option == OptionType.GROUP || option == OptionType.PHOTOGROUP){
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
		}else if (option == OptionType.SKINCONDITION || option == OptionType.PHOTOSKIN){

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

	public static SortedSet<Photo> getPhotoFromCursor(Cursor cursor){
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

	public Cursor fetchAllPhotoObj() {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllEntries(OptionType.PHOTO);
	}


	public Cursor fetchAllPhotoObjConnected(int itemId, OptionType option) {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllPhotosOfAContainer(itemId, option);
	}


	public Cursor fetchAllContainers(OptionType option) {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllContainers(option);
	}

	public Cursor fetchAllContainersOfAPhoto(int photoId, OptionType option){
		return this.mDbAdap.fetchAllContainersOfAPhoto(photoId, option);
	}

	@Override
	public int deleteAPhoto(Photo PhoObj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAContainer(int containerId, OptionType option) {
		return 0;
	}

	@Override
	public int deleteAContainer(Container containerObj, OptionType option) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int disconnectAPhotoFromManyContainers(int photoId,
			Set<Integer> setOfIDs, OptionType option) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int disconnectAContainerFromManyPhotos(int containerId,
			Set<Integer> setOfIDs, OptionType option) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int connectAPhotoToManyContainers(int photoId,
			Set<Integer> setOfIDs, OptionType option) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int connectAContainerToManyPhotos(int containerId,
			Set<Integer> setOfIDs, OptionType option) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePhoto(long photoId, String newLocation,
			Timestamp newTimeStamp, String newName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateGroup(long groupId, String newName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSkin(long skinId, String newName) {
		// TODO Auto-generated method stub
		return 0;
	}

}


