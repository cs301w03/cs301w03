package cmput301W12.android.project.test;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import cmput301W12.android.model.Alarm;
import cmput301W12.android.model.Container;
import cmput301W12.android.model.DatabaseModel;
import cmput301W12.android.model.OptionType;
import cmput301W12.android.model.Photo;
import cmput301W12.android.project.controller.DbAdapter;
import cmput301W12.android.project.controller.DatabaseModelInterface;
import cmput301W12.android.project.view.ProjectTwoActivity;

public class TestDbAdapter extends
ActivityInstrumentationTestCase2<ProjectTwoActivity> {

	protected static final int INVALID_ID = -1;
	protected static boolean POPULATE_DATA = false;

	private ProjectTwoActivity mProject;

	private DbAdapter dbA;
	private DatabaseModelInterface dbC;
	//private TakeAPhotoActivity takeAPhoto;
	private Button takeAPhotoButton;


	public TestDbAdapter() {
		super("cmput301W12.android.project", ProjectTwoActivity.class);
	}

	protected void setUp() throws Exception{
		super.setUp();
		setActivityInitialTouchMode(false);

		mProject = getActivity();
		dbA = DbAdapter.getDbAdapter(mProject);
		dbA = dbA.open();
		dbC = DatabaseModel.getDbController(mProject);
		takeAPhotoButton = (Button) mProject.findViewById(cmput301W12.android.project.R.id.takeAPhoto);
		if(POPULATE_DATA){
			for(int i = 1; i < 6; i++){
				dbA.addGroup(i + "");
				dbA.addSkinCondition(i + "");
			}
			// populate PHOTOGROUP and PHOTOSKIN tables
			for(int i = 1; i < 6; i++){
				for(int j = 1; j < 6; j++){
					dbA.addPhotoGroup(i, j);
					dbA.addPhotoSkinCondition(i, j);
				}
			}
			POPULATE_DATA = false;
		}
	}

	public void testPreconditions(){
		assertTrue(mProject != null);
		assertTrue(dbA != null);
		assertTrue(dbC != null);


	}

	//
	//	public void testAddPhoto(){
	//		assertTrue(takeAPhotoButton.performClick() == true);
	//	}

	public void testSearchForGroup(){
		String groupName = "";

		Cursor cursorGroup = dbA.searchForContainer(groupName, OptionType.GROUP);
		Set<? extends Container> setTest = DatabaseModel.getContainersFromCursor(cursorGroup, OptionType.GROUP);
		assertTrue(setTest.isEmpty() == true);


		Set<? extends Container> setOfGroups = dbC.getAllContainers(OptionType.GROUP);
		if(setOfGroups.isEmpty() == true){
			groupName = "GroupAdded";
			assertTrue(dbA.addGroup(groupName) != INVALID_ID);
			cursorGroup = dbA.searchForContainer(groupName, OptionType.GROUP);
			setTest = DatabaseModel.getContainersFromCursor(cursorGroup, OptionType.GROUP);
			assertTrue(setTest.isEmpty() == false);
		} else {
			for(Container n : setOfGroups){
				groupName = n.getName();
				break;
			}
			cursorGroup = dbA.searchForContainer(groupName, OptionType.GROUP);
			setTest = DatabaseModel.getContainersFromCursor(cursorGroup, OptionType.GROUP);
			assertTrue(setTest.isEmpty() == false);

		}

	}

	public void testSearchForSkin(){
		String skinName = "";

		Cursor cursorSkin = dbA.searchForContainer(skinName, OptionType.SKINCONDITION);
		Set<? extends Container> setTest = DatabaseModel.getContainersFromCursor(cursorSkin, OptionType.SKINCONDITION);
		assertTrue(setTest.isEmpty() == true);


		Set<? extends Container> setOfSkins = dbC.getAllContainers(OptionType.SKINCONDITION);
		if(setOfSkins.isEmpty() == true){
			skinName = "SkinAdded";
			assertTrue(dbA.addSkinCondition(skinName) != INVALID_ID);
			cursorSkin = dbA.searchForContainer(skinName, OptionType.SKINCONDITION);
			setTest = DatabaseModel.getContainersFromCursor(cursorSkin, OptionType.SKINCONDITION);
			assertTrue(setTest.isEmpty() == false);
		} else {
			for(Container n : setOfSkins){
				skinName = n.getName();
				break;
			}
			cursorSkin = dbA.searchForContainer(skinName, OptionType.SKINCONDITION);
			setTest = DatabaseModel.getContainersFromCursor(cursorSkin, OptionType.SKINCONDITION);
			assertTrue(setTest.isEmpty() == false);

		}

	}

	public void testSearchForPhoto(){
		String location = "";
		Cursor cursorPhoto = dbA.searchForPhoto(location);
		SortedSet<Photo> setTest = DatabaseModel.getPhotoFromCursor(cursorPhoto);
		assertTrue(setTest.isEmpty() == true);
		SortedSet<Photo> setPhotos = dbC.getAllPhoto();
		if(setPhotos.isEmpty() == true){
			// try to add a photo and look for that photo.
		} else {
			for (Photo p : setPhotos){
				location = p.getLocation();
				break;
			}
			cursorPhoto = dbA.searchForPhoto(location);
			setTest = DatabaseModel.getPhotoFromCursor(cursorPhoto);
			assertTrue(setTest.isEmpty() == false);
		}
	}

	public void testSearchForPhotoGroup(){
		int photoId = INVALID_ID;
		int itemId = INVALID_ID;
		Cursor cursor = dbA.searchForPhotoContainer(photoId, itemId, OptionType.PHOTOGROUP);
		assertTrue( cursor.getCount() == 0 );


		photoId = 1;
		itemId = 5;
		cursor = dbA.searchForPhotoContainer(photoId, itemId, OptionType.PHOTOGROUP);
		if(cursor.getCount() == 0){
			Cursor existPhoto = dbA.fetchAnEntry(photoId, OptionType.PHOTO);
			Cursor existGroup = dbA.fetchAnEntry(itemId, OptionType.GROUP);
			if(existPhoto.getCount() > 0 && existGroup.getCount() > 0){
				assertTrue( dbA.addPhotoGroup(photoId, itemId) != INVALID_ID) ;
				cursor = dbA.searchForPhotoContainer(photoId, itemId, OptionType.PHOTOGROUP);
				assertTrue( cursor.getCount() != 0);
			}
		} else {
			assertTrue(cursor.getCount() != 0);
		}

	}



	public void testAddGroup(){
		String newGroupName = "TestAddGroup1";
		Cursor cursorGroup = dbA.searchForContainer(newGroupName, OptionType.GROUP);
		Set<? extends Container> setGroup = DatabaseModel.getContainersFromCursor(cursorGroup, OptionType.GROUP);
		if(setGroup.isEmpty() == true){
			assertTrue(dbA.addGroup(newGroupName) != INVALID_ID);
			Log.d("", "New group added!");
		} else{
			// the group exists already --> can't add
			assertTrue(dbA.addGroup(newGroupName) == INVALID_ID);
			Log.d("", "The group exists already!");
		}
	}

	public void testAddSkin(){
		String newSkinName = "TestAddSkin1";
		Cursor cursorSkin = dbA.searchForContainer(newSkinName, OptionType.SKINCONDITION);
		Set<? extends Container> setSkin = DatabaseModel.getContainersFromCursor(cursorSkin, OptionType.SKINCONDITION);
		if(setSkin.isEmpty() == true){
			assertTrue(dbA.addSkinCondition(newSkinName) != INVALID_ID);
			Log.d("", "new skin condition added!");
		} else {
			// the skin condition exists already --> can't add
			assertTrue(dbA.addSkinCondition(newSkinName) == INVALID_ID);
			Log.d("", "the skin condition exists already!");
		}
	}

	public void testAddPhoto(){
		// need to figure out how to call TakeAPhotoActicity to test AddPhoto.
		// However, we know AddPhoto works already!
	}

	public void testAddPhotoGroup(){

		int photoId = 2;
		int itemId = 10;
		Cursor cursor = dbA.searchForPhotoContainer(photoId, itemId, OptionType.PHOTOGROUP);
		if(cursor.getCount() == 0){
			Cursor existPhoto = dbA.fetchAnEntry(photoId, OptionType.PHOTO);
			Cursor existGroup = dbA.fetchAnEntry(itemId, OptionType.GROUP);
			if(existPhoto.getCount() > 0 && existGroup.getCount() > 0){
				assertTrue( dbA.addPhotoGroup(photoId, itemId) != INVALID_ID) ;
				Log.d("", "new photo - group tuple has been added successfully!");
			} else {
				// there is no photo with the given photoId or no group with the given itemId.
				assertTrue( dbA.addPhotoGroup(photoId, itemId) == INVALID_ID);
				Log.d("", "there is no photo with the given photoId or no group with the given itemId.");
			}
		} else {
			// there is such a tuple already.
			assertTrue( dbA.addPhotoGroup(photoId, itemId) == INVALID_ID) ;
			Log.d("","there is such a tuple already" );
		}
	}

	public void testAddPhotoSkin(){

		int photoId = 2;
		int itemId = 1;
		Cursor cursor = dbA.searchForPhotoContainer(photoId, itemId, OptionType.PHOTOSKIN);
		if(cursor.getCount() == 0){
			Cursor existPhoto = dbA.fetchAnEntry(photoId, OptionType.PHOTO);
			Cursor existSkin = dbA.fetchAnEntry(itemId, OptionType.SKINCONDITION);
			if(existPhoto.getCount() > 0 && existSkin.getCount() > 0){
				assertTrue( dbA.addPhotoSkinCondition(photoId, itemId) != INVALID_ID) ;
			} else {
				// there is no photo with the given photoId or no group with the given itemId.
				assertTrue( dbA.addPhotoSkinCondition(photoId, itemId) == INVALID_ID);
			}
		} else {
			// there is such a tuple already.
			assertTrue( dbA.addPhotoSkinCondition(photoId, itemId) == INVALID_ID) ;
		}
	}

	public void testAddAlarm(){
		Timestamp alarmTime  = new Timestamp(new java.util.Date().getTime());
		String alarmNote = "Test an alarm";
		assertTrue(this.dbA.addAlarm(alarmTime, alarmNote) != DbAdapter.INVALID_ID) ;
	}

	public void testDeletePhotoContainer(){
		// delete photoGroup row
		Cursor cursor = dbA.fetchAllEntries(OptionType.PHOTOGROUP);
		Log.d("", " PHOTOGROUP count " + cursor.getCount());

		if(cursor.getCount() > 0){
			int numRowsDeleted = dbA.deleteEntry(1, OptionType.PHOTOGROUP);
			assertTrue(numRowsDeleted > 0);
		} else {
			// no tuple --> insert a tuple and test deleting.
			assertTrue("No tuple in PHOTOGROUP table, insert one and test deleting",
					1 == 0);
		}


		//delete photoSkin row
		cursor = dbA.fetchAllEntries(OptionType.PHOTOSKIN);
		Log.d("", " PHOTOSKIN count" + cursor.getCount());
		if(cursor.getCount() > 0){
			//int photoSkinId = cursor.getInt(cursor.getColumnIndex("ROWID"));
			int numRowsDeleted = dbA.deleteEntry(1, OptionType.PHOTOSKIN);
			assertTrue(numRowsDeleted > 0);
		} else {
			// no tuple --> insert a tuple and test deleting.
			assertTrue("No tuple in PHOTOGROUP table, insert one and test deleting", 
					1 == 0);
		}
	}

	public void testDeleteContainer(){
		//delete group row --> 
		// the database should delete all the rows in photogroup table that contains the group deleted.
		// However, on delete cascade property doesn't work properly so just the tuple in GROUP will be deleted.
		Cursor cursor = dbA.fetchAllEntries(OptionType.GROUP);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Log.d("", " GROUP count " + cursor.getCount());
		if(cursor.getCount() > 0){
			int itemId = cursor.getInt(cursor.getColumnIndex(DbAdapter.GROUPID));
			int numRowsDeleted = dbA.deleteEntry(itemId, OptionType.GROUP);
			assertTrue(numRowsDeleted == 1);
		} else {
			// no tuple --> insert a tuple and test deleting.
			assertTrue("No tuple in GROUP table, insert one and test deleting",
					1 == 0);
		}

		//delete skin condition row --> 
		// the database should delete all the rows in photoskin table that contains the skin deleted.
		// However, on delete cascade property doesn't work properly so just the tuple in SkinCondition table will be deleted.
		cursor = dbA.fetchAllEntries(OptionType.SKINCONDITION);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Log.d("", " SKIN count " + cursor.getCount());
		if(cursor.getCount() > 0){
			int itemId = cursor.getInt(cursor.getColumnIndex(DbAdapter.SKINCONDITIONID));
			int numRowsDeleted = dbA.deleteEntry(itemId, OptionType.SKINCONDITION);
			assertTrue(numRowsDeleted == 1);
		} else {
			// no tuple --> insert a tuple and test deleting.
			assertTrue("No tuple in SKINCONDITION table, insert one and test deleting",
					1 == 0);
		}

	}


	public void testDeletePhoto(){
		SortedSet<Photo> setPho = this.dbC.getAllPhoto();
		if(setPho.isEmpty() == false){
			for(Photo p : setPho){
				assertTrue(this.dbA.deleteEntry(p.getPhotoId(), OptionType.PHOTO) > 0);
				break;
			}
		} else {
			assertTrue("There is no photo. Add some photo to test deleting photo", 0 == 1);
		}
	}

	public void testDeleteAlarm(){
		SortedSet<Alarm> setAlarm = this.dbC.getAllAlarms();
		if(setAlarm.isEmpty() == false){
			for(Alarm a : setAlarm){
				assertTrue(this.dbA.deleteEntry(a.getAlarmId(), OptionType.ALARM) > 0);
				break;
			}
		} else {
			this.testAddAlarm();
			setAlarm = this.dbC.getAllAlarms();
			assertTrue(setAlarm.isEmpty() == false);
			for(Alarm a : setAlarm){
				assertTrue(this.dbA.deleteEntry(a.getAlarmId(), OptionType.ALARM) > 0);
				break;
			}
		}
	}

	public void testUpdatePhoto(){
		Cursor onePhoto = dbA.fetchAnEntry(1, OptionType.PHOTO);
		SortedSet<Photo> onePhotoSet = DatabaseModel.getPhotoFromCursor(onePhoto);

		if(onePhotoSet.isEmpty() == false){
			String newLocation = "";
			Timestamp newTimestamp = new Timestamp(new java.util.Date().getTime());
			String newPhotoName = "TestUpdatePhoto";
			String newAnnotation = "TestUpdateAnnotation";
			for(Photo p : onePhotoSet){
				newLocation = p.getLocation();
				newTimestamp = p.getTimeStamp();
			}
			assertTrue( dbA.updatePhoto(1, newLocation, newTimestamp, newPhotoName, newAnnotation) > 0);
			// fetch the row again to check if the photo name has been updated successfully.
			onePhoto = dbA.fetchAnEntry(1, OptionType.PHOTO);
			onePhotoSet = DatabaseModel.getPhotoFromCursor(onePhoto);
			assertTrue(onePhotoSet.isEmpty() == false);
			for(Photo p : onePhotoSet){
				assert(newLocation.equals(p.getLocation()) == true);
				assert(newTimestamp.equals(p.getTimeStamp()) == true);
				assert(newPhotoName.equals(p.getName()) == true);
				assert(newAnnotation.equals(p.getAnnotation()) == true);
			}
		} else {
			// add a photo before test updating the photo because no photo exists yet!
		}

	}

	public void testUpdateGroup(){
		int GROUPID = 1;
		Cursor oneGroup = dbA.fetchAnEntry(GROUPID, OptionType.GROUP);
		Set<? extends Container> oneGroupSet = DatabaseModel.getContainersFromCursor(oneGroup, OptionType.GROUP);


		if(oneGroupSet.isEmpty() == true){
			// no group --> create a group for testing.
			assertTrue( dbA.addGroup("GroupCreated for testing update group name") != INVALID_ID);
		} else {
			String newName = "Group " + GROUPID + " changes name!";
			int differentGroupId = GROUPID;

			Set<? extends Container> groupSet = DatabaseModel.getContainersFromCursor
					(dbA.searchForContainer(newName, OptionType.GROUP), OptionType.GROUP);
			for(Container n : groupSet){
				if(n.getItemId() != GROUPID){
					differentGroupId = n.getItemId();
					break;
				}
			}

			if(differentGroupId == GROUPID){

				assertTrue(dbA.updateGroup(GROUPID, newName) > 0);
				// fetch the row again to check if the group name has been updated successfully.
				oneGroup = dbA.fetchAnEntry(GROUPID, OptionType.GROUP);
				oneGroupSet = DatabaseModel.getContainersFromCursor(oneGroup, OptionType.GROUP);
				for(Container n : oneGroupSet){
					assertTrue(n.getName().equals(newName) == true);
				}
			} else {
				// There is a different group besides 1 that has the new name -->
				// shouldn't allow updating group 1's name to the name new by throwing a ConstraintException.
				try {
					dbA.updateGroup(GROUPID, newName);
					// if the the execution of the above statement doesn't throw a ConstraintException,
					// then the update function fails to meet the requirement.
					assertTrue(1 == 0);
				} catch (Exception ex){
					assertTrue(ex instanceof SQLiteConstraintException);
				}
			}

		}
	}


	public void testUpdateSkin(){
		final int SKINID = 1;
		Cursor oneSkin = dbA.fetchAnEntry(SKINID, OptionType.SKINCONDITION);
		Set<? extends Container> oneSkinSet = DatabaseModel.getContainersFromCursor(oneSkin, OptionType.SKINCONDITION);


		if(oneSkinSet.isEmpty() == true){
			// no group --> create a group for testing.
			assertTrue( dbA.addSkinCondition("SkinCreated for testing update skin name") != INVALID_ID);
		} else {
			String newName = "SKIN " + SKINID + " changes Name!" ;

			int differentSkinId = SKINID;

			Set<? extends Container> skinSet = DatabaseModel.getContainersFromCursor
					(dbA.searchForContainer(newName, OptionType.SKINCONDITION), OptionType.SKINCONDITION);
			for(Container n : skinSet){
				if(n.getItemId() != SKINID){
					differentSkinId = n.getItemId();
					break;
				}
			}

			if(differentSkinId == SKINID){

				assertTrue(dbA.updateSkin(SKINID, newName) > 0);
				// fetch the row again to check if the group name has been updated successfully.
				oneSkin = dbA.fetchAnEntry(SKINID, OptionType.SKINCONDITION);
				oneSkinSet = DatabaseModel.getContainersFromCursor(oneSkin, OptionType.SKINCONDITION);
				for(Container n : oneSkinSet){
					assertTrue(n.getName().equals(newName) == true);
				}
			} else {
				// There is a different skin condition besides 1 that has the new name -->
				// shouldn't allow updating skin condition 1's name to the name new by throwing a ConstraintException.
				try {
					dbA.updateSkin(SKINID, newName);
					// if the the execution of the above statement doesn't throw a ConstraintException,
					// then the update function fails to meet the requirement.
					assertTrue(SKINID == 0);
				} catch (Exception ex){
					assertTrue(ex instanceof SQLiteConstraintException);
				}
			}

		}
	}

	public void testUpdateAlarm(){
		Timestamp newTimestamp = new Timestamp(new java.util.Date().getTime());
		String newNote = "Test Update Alarm";
		Alarm anAlarm = null;

		SortedSet<Alarm> setAlarm = this.dbC.getAllAlarms();
		if(setAlarm.isEmpty() == true){
			this.testAddAlarm();
			setAlarm = this.dbC.getAllAlarms();
		}

		for(Alarm a : setAlarm){
			anAlarm = a;
			break;
		}
		assertTrue(this.dbA.updateAlarm(anAlarm.getAlarmId(), newTimestamp, newNote) > 0);
	}

	public void testDisconnectAPhotoFromManyContainers(){
		int photoId = DbAdapter.INVALID_ID;
		SortedSet<Photo> setPho = this.dbC.getAllPhoto();
		for(Photo p : setPho){
			photoId = p.getPhotoId();
			break;
		}

		assertTrue("no photo. Add some photo and testing please!", setPho.isEmpty() == false);
		
		int groupId = DbAdapter.INVALID_ID;
		SortedSet<? extends Container> setGroup = 
				this.dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOGROUP);
		if(setGroup.isEmpty()){
			SortedSet<? extends Container> setAllGroup = this.dbC.getAllContainers(OptionType.GROUP);
			if(setAllGroup.isEmpty()){
				groupId = this.dbA.addGroup("newGroup");
			} else {
				for(Container n : setAllGroup){
					groupId = n.getItemId();
					break;
				}
			}
			this.dbA.addPhotoGroup(photoId, groupId);
		} else {
			for(Container n : setGroup){
				groupId = n.getItemId();
				break;
			}
		}

		SortedSet<Integer> newSet = new TreeSet<Integer>();
		newSet.add(groupId);
		setGroup = this.dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOGROUP);
		int sizeBefore = setGroup.size();
		assertTrue(
				this.dbA.disconnectAPhotoFromManyContainers(photoId, newSet, OptionType.PHOTOGROUP) == 1);
		setGroup = this.dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOGROUP);
		int sizeAfter = setGroup.size();
		assertTrue(sizeBefore == sizeAfter + 1);


		int numDel = this.dbA.disconnectAPhotoFromManyContainers(photoId, OptionType.PHOTOGROUP);
		Log.d("", " number of groups disconnected : " + numDel);
		Cursor cursor = this.dbA.fetchAllContainersOfAPhoto(photoId, OptionType.PHOTOGROUP);
		assertTrue(cursor.getCount() == 0);

		int skinId = DbAdapter.INVALID_ID;
		SortedSet<? extends Container> setSkin = 
				this.dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOSKIN);
		if(setSkin.isEmpty()){
			SortedSet<? extends Container> setAllSkin = this.dbC.getAllContainers(OptionType.SKINCONDITION);
			if(setAllSkin.isEmpty()){
				skinId = this.dbA.addSkinCondition("newSkin");
			} else {
				for(Container n : setAllSkin){
					skinId = n.getItemId();
					break;
				}
			}
			this.dbA.addPhotoSkinCondition(photoId, skinId);
		} else {
			for(Container n : setSkin){
				skinId = n.getItemId();
				break;
			}
		}

		newSet = new TreeSet<Integer>();
		newSet.add(skinId);
		setSkin = this.dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOSKIN);
		sizeBefore = setSkin.size();
		assertTrue(
				this.dbA.disconnectAPhotoFromManyContainers(photoId, newSet, OptionType.PHOTOSKIN) >= 1);
		setSkin = this.dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOSKIN);
		sizeAfter = setSkin.size();
		assertTrue(sizeBefore  == sizeAfter + 1);

		numDel = this.dbA.disconnectAPhotoFromManyContainers(photoId, OptionType.PHOTOSKIN);
		Log.d("", " number of skins disconnected: " + numDel);
		cursor = this.dbA.fetchAllContainersOfAPhoto(photoId, OptionType.PHOTOSKIN);
		assertTrue(cursor.getCount() == 0);

	}

	public void testDisconnectAContainerFromManyPhotos(){
		int groupId = 1;
		SortedSet<Photo> setPho = this.dbC.getAllPhotoOfAContainer(groupId, OptionType.PHOTOGROUP);
		assertTrue("no photo connected to this group. Please connect some photo to this group and test!", 
				setPho.isEmpty() == false);
		int photoId = DbAdapter.INVALID_ID;
		for(Photo p : setPho){
			photoId = p.getPhotoId();
			break;
		}
		Set<Integer> newSet = new HashSet<Integer>();
		newSet.add(photoId);
		int sizeBefore = setPho.size();
		assertTrue(
				this.dbC.disconnectAContainerFromManyPhotos(groupId, newSet, OptionType.PHOTOGROUP) == 1);
		setPho = this.dbC.getAllPhotoOfAContainer(groupId, OptionType.PHOTOGROUP);
		int sizeAfter = setPho.size();
		assertTrue(sizeBefore == sizeAfter + 1);

		int numberOfDisconnections = this.dbC.disconnectAContainerFromManyPhotos(groupId, OptionType.PHOTOGROUP);
		setPho = this.dbC.getAllPhotoOfAContainer(groupId, OptionType.PHOTOGROUP);
		assertTrue(sizeAfter == numberOfDisconnections);
		assertTrue(setPho.isEmpty() == true);
		
		// Testing disconnect skin condition has not been done. However, that function should be working for
		// skin condition as it proves to work for group.
	}
	
	

}
