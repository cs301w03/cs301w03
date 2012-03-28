package cmput301W12.android.project;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import android.content.Context;
import android.database.Cursor;
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
		String annotation = phoObj.getAnnotation();

		if(photoId == DbAdapter.INVALID_ID){
			photoId = ( int) this.mDbAdap.addPhoto(location, timeStamp, name, annotation);
			phoObj.setPhotoId(photoId);

			Set<Integer> listOfGroupId = phoObj.getGroups();
			Set<Integer> listOfSkinId = phoObj.getSkinConditions();

			for(Integer id : listOfGroupId){
				this.mDbAdap.addPhotoGroup(photoId,id);
			}
			for(Integer id : listOfSkinId	){
				this.mDbAdap.addPhotoSkinCondition(photoId, id);
			}
		}

		return phoObj;
	}

	public Container addContainObj(Container containObj) {

		String name = containObj.getName();
		Set<Integer> listOfPhotoIds= containObj.getPhotos();

		if(containObj instanceof Group){

			if(containObj.getItemId() == DbAdapter.INVALID_ID){
				int groupId = (int) this.mDbAdap.addGroup(name);
				containObj.setItemId(groupId);

				int validGroupId = containObj.getItemId();
				if(validGroupId != DbAdapter.INVALID_ID){
					for(Integer id : listOfPhotoIds){
						this.mDbAdap.addPhotoGroup(id, validGroupId);
					}

				}

			}

		} 
		else if(containObj instanceof SkinCondition){
			if(containObj.getItemId() == DbAdapter.INVALID_ID){
				int skinId = (int) this.mDbAdap.addSkinCondition(name);
				containObj.setItemId(skinId);

				int validSkinId = containObj.getItemId();
				if(validSkinId != DbAdapter.INVALID_ID){
					for(Integer id : listOfPhotoIds){
						this.mDbAdap.addPhotoSkinCondition(id, validSkinId);
					}
				}
			}
		} 

		return containObj;
	}


	@Override
	public Alarm addAlarm(Alarm alarm) {
		if(alarm.getAlarmId() == DbAdapter.INVALID_ID){
			int alarmId =(int) this.mDbAdap.addAlarm(alarm.getAlarmTime(), alarm.getAlarmNote());
			alarm.setAlarmId(alarmId);
		}
		return alarm;
	}


	public SortedSet<Photo> getAllPhoto(){
		Cursor cursor = this.fetchAllPhotoObj();
		return DbController.getPhotoFromCursor(cursor);
	}

	public SortedSet<Photo> getAllPhotoOfAContainer(int itemId, OptionType option){
		Cursor cursor = this.fetchAllPhotoObjConnected(itemId, option);
		return DbController.getPhotoFromCursor(cursor);
	}

	public SortedSet<? extends Container> getAllContainers(OptionType option)
	{
		Cursor cursor = this.fetchAllContainers(option);
		return DbController.getContainersFromCursor(cursor, option);
	}

	public SortedSet<? extends Container> getAllContainersOfAPhoto(int photoId, OptionType option){
		Cursor cursor = this.fetchAllContainersOfAPhoto(photoId, option);
		return DbController.getContainersFromCursor(cursor, option);
	}


	@Override
	public SortedSet<Alarm> getAllAlarms() {
		Cursor cursor = this.fetchAllAlarms();
		return DbController.getAlarmFromCursor(cursor);
	}

	public int deleteEntry(int rowID, OptionType option) {
		return (int) this.mDbAdap.deleteEntry(rowID, option);
	}

	public static SortedSet<? extends Container> getContainersFromCursor(Cursor cursor, OptionType option){
		boolean repeat = true;

		Container container;

		int itemId;
		String name = "";

		if(option == OptionType.GROUP || option == OptionType.PHOTOGROUP){
			SortedSet<Group> aSet = new TreeSet<Group>();

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

			SortedSet<SkinCondition> aSet = new TreeSet<SkinCondition>();

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

	public static SortedSet<Alarm> getAlarmFromCursor(Cursor cursor){
		boolean repeat = false;
		int alarmId;
		Timestamp timeStamp;
		String alarmNote;

		Alarm alarm;
		SortedSet<Alarm> aSet = new TreeSet<Alarm>();

		if (cursor != null) {
			repeat = cursor.moveToFirst();
		}

		while(repeat){
			alarmId = cursor.getInt(cursor.getColumnIndexOrThrow(DbAdapter.ALARMID));
			timeStamp = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.ALARMTIME)));
			alarmNote = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.ALARMNOTE));
			alarm = new Alarm(timeStamp, alarmNote);
			alarm.setAlarmId(alarmId);
			aSet.add(alarm);
			repeat = cursor.moveToNext();
		}

		return aSet;

	}

	public Cursor fetchAllPhotoObj() {
		return this.mDbAdap.fetchAllPhotos();
	}


	public Cursor fetchAllPhotoObjConnected(int itemId, OptionType option) {
		return this.mDbAdap.fetchAllPhotosOfAContainer(itemId, option);
	}


	public Cursor fetchAllContainers(OptionType option) {
		return this.mDbAdap.fetchAllContainers(option);
	}

	public Cursor fetchAllContainersOfAPhoto(int photoId, OptionType option){
		return this.mDbAdap.fetchAllContainersOfAPhoto(photoId, option);
	}

	public Cursor fetchAllAlarms(){
		return this.mDbAdap.fetchAllAlarms();
	}

	@Override
	public int deleteAPhoto(Photo phoObj) {
		int photoId = phoObj.getPhotoId();
		String location = phoObj.getLocation();
		int count = 0;

		new File(location).delete();

		count += this.mDbAdap.deleteEntry(photoId, OptionType.PHOTO	);
		count += this.mDbAdap.disconnectAPhotoFromManyContainers(photoId, OptionType.PHOTOGROUP);
		count += this.mDbAdap.disconnectAPhotoFromManyContainers(photoId, OptionType.PHOTOSKIN);
		return count;
	}

	@Override
	public int deleteAContainer(int containerId, OptionType option) {
		int count = 0;
		OptionType opt2 = null;
		if(option == OptionType.GROUP){
			opt2 = OptionType.PHOTOGROUP;
			count += this.mDbAdap.deleteEntry(containerId, option);
		} else if ( option == OptionType.SKINCONDITION){
			opt2 = OptionType.PHOTOSKIN;
			count += this.mDbAdap.deleteEntry(containerId, option);
		}
		count += this.mDbAdap.disconnectAContainerFromManyPhotos(containerId, opt2);
		return count;
	}

	@Override
	public int deleteAContainer(Container containerObj, OptionType option) {
		int containerId = containerObj.getItemId();
		return deleteAContainer(containerId, option);
	}


	@Override
	public int deleteAnAlarm(int alarmId) {
		return this.mDbAdap.deleteEntry(alarmId, OptionType.ALARM);
	}

	@Override
	public int disconnectAPhotoFromManyContainers(int photoId, OptionType option) {
		return this.mDbAdap.disconnectAPhotoFromManyContainers(photoId, option);
	}

	@Override
	public int disconnectAPhotoFromManyContainers(int photoId,
			Set<Integer> setOfIDs, OptionType option) {
		return this.mDbAdap.disconnectAPhotoFromManyContainers(photoId, setOfIDs, option);
	}

	@Override
	public int connectAPhotoToManyContainers(int photoId,
			Set<Integer> setOfIDs, OptionType option) {
		int count = 0;
		if(option == OptionType.PHOTOGROUP){
			for(Integer id : setOfIDs){
				count += this.mDbAdap.addPhotoGroup(photoId, id);
			}
		} else if (option == OptionType.PHOTOSKIN){
			for(Integer id : setOfIDs){
				count += this.mDbAdap.addPhotoSkinCondition(photoId, id);
			}
		}
		return count;
	}

	@Override
	public int connectAPhotoToManyContainers(int photoId, OptionType option) {
		Set<Integer> setOfIDs = new HashSet<Integer>();
		OptionType opt2 = null;
		if(option == OptionType.PHOTOGROUP){
			opt2 = OptionType.GROUP;
			Set<? extends Container> setOfContainers = this.getAllContainers(opt2);
			for(Container c : setOfContainers){
				setOfIDs.add(c.getItemId());
			}
			return this.connectAPhotoToManyContainers(photoId, setOfIDs, option);
		} else if (option == OptionType.PHOTOSKIN){
			opt2 = OptionType.SKINCONDITION;
			Set<? extends Container> setOfContainers = this.getAllContainers(opt2);
			for(Container c : setOfContainers){
				setOfIDs.add(c.getItemId());
			}
			return this.connectAPhotoToManyContainers(photoId, setOfIDs, option);
		}
		return 0;
	}



	@Override
	public int connectAContainerToManyPhotos(int containerId,
			Set<Integer> setOfIDs, OptionType option) {
		int count = 0;
		if(option == OptionType.PHOTOGROUP){
			for(Integer id : setOfIDs){
				count += this.mDbAdap.addPhotoGroup(id, containerId);
			}
		} else if (option == OptionType.PHOTOSKIN){
			for(Integer id : setOfIDs){
				count += this.mDbAdap.addPhotoSkinCondition(id, containerId);
			}
		}
		return count;

	}

	@Override
	public int connectAContainerToManyPhotos(int containerId, OptionType option) {
		Set<Photo> setOfPhotos = this.getAllPhoto();
		Set<Integer> setOfIDs = new HashSet<Integer>();

		for(Photo p : setOfPhotos){
			setOfIDs.add(p.getPhotoId());
		}
		return this.connectAContainerToManyPhotos(containerId, setOfIDs, option);
	}

	@Override
	public int disconnectAContainerFromManyPhotos(int containerId,
			Set<Integer> setOfIDs, OptionType option) {
		return this.mDbAdap.disconnectAContainerFromManyPhotos(containerId, setOfIDs, option);
	}

	@Override
	public int disconnectAContainerFromManyPhotos(int containerId,
			OptionType option) {
		return this.mDbAdap.disconnectAContainerFromManyPhotos(containerId, option);
	}

	@Override
	public int updatePhoto(int photoId, String newLocation,
			Timestamp newTimeStamp, String newName, String newAnnotation) {
		return this.mDbAdap.updatePhoto(photoId, newLocation, newTimeStamp, newName, newAnnotation);
	}

	@Override
	public int updateGroup(int groupId, String newName) {
		return this.mDbAdap.updateGroup(groupId, newName);
	}

	@Override
	public int updateSkin(int skinId, String newName) {
		return this.mDbAdap.updateSkin(skinId, newName);
	}


	@Override
	public int updateAlarm(int alarmId, Timestamp newTime, String newNote) {
		return this.mDbAdap.updateAlarm(alarmId, newTime, newNote);
	}


}


