package cmput301W12.android.project.test;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import android.database.Cursor;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import cmput301W12.android.model.Alarm;
import cmput301W12.android.model.Container;
import cmput301W12.android.model.DatabaseModel;
import cmput301W12.android.model.Group;
import cmput301W12.android.model.OptionType;
import cmput301W12.android.model.Photo;
import cmput301W12.android.model.SkinCondition;
import cmput301W12.android.project.controller.DbAdapter;
import cmput301W12.android.project.controller.DatabaseModelInterface;
import cmput301W12.android.project.view.ProjectTwoActivity;

public class DbControllerTest extends
ActivityInstrumentationTestCase2<ProjectTwoActivity> {

	private ProjectTwoActivity mProject;
	protected static final int INVALID_ID = -1;

	private DbAdapter dbA;
	private DatabaseModelInterface dbC;

	public DbControllerTest() {
		super("cmput301W12.android.project", ProjectTwoActivity.class);
		// TODO Auto-generated constructor stub
	}


	protected void setUp() throws Exception{
		super.setUp();
		setActivityInitialTouchMode(false);

		mProject = getActivity();
		dbA = DbAdapter.getDbAdapter(mProject);
		dbA = dbA.open();
		dbC = DatabaseModel.getDbController(mProject);

	}

	public void testPreconditions(){
		assertTrue(mProject != null);
		assertTrue(dbA != null);
		assertTrue(dbC != null);
	}

	public void testGetAllPhotos(){
		SortedSet<? extends Photo> setOfPhotos = dbC.getAllPhoto();
		if(setOfPhotos.isEmpty() == false){
			Log.d("", "start printing all photos");
			for (Photo p : setOfPhotos){
				Log.d(" ", "photo ID : " + p.getPhotoId() + "    Timestamp    " + p.getTimeStamp() +  "   location : " + p.getLocation());
			}
		} else{
			// test insert a photo
		}
	}

	public void testGetAllContainers(){
		Set<? extends Container> setOfGroups = dbC.getAllContainers(OptionType.GROUP);
		Set<? extends Container> setOfSkins = dbC.getAllContainers(OptionType.SKINCONDITION);
		if(setOfGroups.isEmpty() == false){
			Log.d("", "Start printing all groups");
			for(Container n : setOfGroups){
				Log.d("", n.getItemId() + " --> Name :    " + n.getName());
			}
		} else {
			// test insert a group if setOfGroups is empty
		}

		if(setOfSkins.isEmpty() == false){
			Log.d("", "Start printing all skins");
			for(Container n : setOfSkins){
				Log.d("", n.getItemId() + " ---> Name :    " + n.getName());
			}
		} else {
			// test insert a skin if setOfSkins is empty
		}

	}

	public int testGetAllContainersOfAPhoto(){
		int photoId = DbAdapter.INVALID_ID;
		SortedSet<Photo> setPho = this.dbC.getAllPhoto();

		assertTrue("there is no photo. Add some photo please!", setPho.isEmpty() == false);

		for(Photo p : setPho){
			photoId = p.getPhotoId();
			break;
		}

		Set<? extends Container> setGroupsOfAPhoto = dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOGROUP);
		if(setGroupsOfAPhoto.isEmpty() == false){
			Log.d("", "start printing all groups of photo " + photoId);
			for(Container c : setGroupsOfAPhoto){
				Log.d(" ", c.getItemId() + " ---> Name :    " + c.getName() + " ");
			}
		} else {
			// test insert a group - photo or a skin - photo if list is empty
			assertTrue( dbA.addPhotoGroup(photoId, 1) != INVALID_ID);

			setGroupsOfAPhoto = dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOGROUP);

			assertTrue(setGroupsOfAPhoto.isEmpty() == false);
			Log.d("", "start printing all groups of " + photoId);
			for(Container c : setGroupsOfAPhoto){
				Log.d(" ", c.getName() + " " + c.getItemId() + " ");
			}
		}

		Set<? extends Container> setSkinsOfAPhoto = dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOSKIN);
		if(setSkinsOfAPhoto.isEmpty() == false){
			Log.d("", "start printing all skin conditions of photo " + photoId);
			for(Container c : setSkinsOfAPhoto){
				Log.d(" ", c.getItemId() + " ---> Name :    " + c.getName() + " ");
			}
		} else {
			// test insert a group - photo or a skin - photo if list is empty
			assertTrue( dbA.addPhotoSkinCondition(photoId, 1) != INVALID_ID);
			assertTrue( dbA.addPhotoSkinCondition(photoId, 2) != INVALID_ID);

			setSkinsOfAPhoto = dbC.getAllContainersOfAPhoto(photoId, OptionType.PHOTOSKIN);

			assertTrue(setSkinsOfAPhoto.isEmpty() == false);
			Log.d("", "start printing all skin conditions of " + photoId);
			for(Container c : setGroupsOfAPhoto){
				Log.d(" ", c.getName() + " " + c.getItemId() + " ");
			}

		}
		return photoId;
	}

	public void testGetAllPhotosOfAContainer(){
		int groupId = DbAdapter.INVALID_ID;
		Set<? extends Container> setGroup = this.dbC.getAllContainers(OptionType.GROUP);
		if(setGroup.isEmpty() == false){
			for(Container n : setGroup){
				groupId = n.getItemId();
				break;
			}
		} else {
			groupId = this.dbA.addGroup("NEW GROUP");
		}
		assertTrue(groupId != DbAdapter.INVALID_ID);
		Set<Photo> setPho = dbC.getAllPhotoOfAContainer(groupId, OptionType.PHOTOGROUP);
		Log.d("", "Print all photos of the group with Id   " +  groupId	);
		for(Photo p : setPho){
			Log.d("", "photo id : " + p.getPhotoId() + " timestamp : " + p.getTimeStamp().toString() );
		}
	}

	public void testGetAllAlarms(){
		SortedSet<Alarm> setAlarm = this.dbC.getAllAlarms();
		Log.d("", "Print all alarms ");
		for(Alarm a : setAlarm){
			Log.d("", "alarm id : " + a.getAlarmId() + " timeStamp: " + a.getAlarmTime() + " note : " + a.getAlarmNote());
		}
	}

	public void testAddPhoto(){
		// Don't know how to make a photo within JUnit Testing yet!
		// However I did test manually the addPhoto(Photo p) function in the DbController.
	}

	public void testAddContainObj(){
		String groupName = "groupForTestingAdddingContainObj" + (new Timestamp(new java.util.Date().getTime())).toString();
		SortedSet<Photo> setPho = this.dbC.getAllPhoto();
		Set<Integer> setInt = new HashSet<Integer>();
		for(Photo p : setPho){
			setInt.add(p.getPhotoId());
		}
		Group newGroup = new Group(groupName, setInt);
		newGroup = (Group) this.dbC.addContainObj(newGroup);
		assertTrue(newGroup.getItemId() != DbAdapter.INVALID_ID);
		SortedSet<Photo> setPhoAfter = this.dbC.getAllPhotoOfAContainer(newGroup.getItemId(), OptionType.PHOTOGROUP);
		assertTrue(setPho.equals(setPhoAfter));
	}

	public void testAddAlarm(){
		Timestamp alarmTime  = new Timestamp(new java.util.Date().getTime());
		String alarmNote = "Test an alarm";
		Alarm alarm = new Alarm(alarmTime, alarmNote);
		alarm = this.dbC.addAlarm(alarm);
		assertTrue(alarm.getAlarmId() != DbAdapter.INVALID_ID);
	}

	public void testDeleteAPhoto(){
		SortedSet<Photo> setPho = this.dbC.getAllPhoto();
		assertTrue("no photo. Please add a photo and " +
				"connect this photo to some groups and some skin conditions for testing", setPho.isEmpty() == false);
		Photo pho = null;
		for(Photo p : setPho){
			pho = p;
			break;
		}
		String location = pho.getLocation();
		int photoId = pho.getPhotoId();

		Uri uri = Uri.parse(location);
		File file = new File(uri.getPath());
		assertTrue(file.exists() == true);

		SortedSet<? extends Container> setGroup = this.dbC.getAllContainersOfAPhoto(pho.getPhotoId(), OptionType.PHOTOGROUP);
		SortedSet<? extends Container> setSkin = this.dbC.getAllContainersOfAPhoto(pho.getPhotoId(), OptionType.PHOTOSKIN);
		int numDel = this.dbC.deleteAPhoto(pho);
		Log.d("", " number of entries deleted : " + numDel);
		//assertTrue(numDel == (setGroup.size() + setSkin.size()));
		assertTrue(file.exists() == false);
		Cursor cur = this.dbA.fetchAnEntry(photoId, OptionType.PHOTO);
		assertTrue(cur.getCount() == 0);
		cur = this.dbA.fetchAllContainersOfAPhoto(photoId, OptionType.PHOTOGROUP);
		assertTrue(cur.getCount() == 0);
		cur = this.dbA.fetchAllContainersOfAPhoto(photoId, OptionType.PHOTOSKIN);
		assertTrue(cur.getCount() == 0);

	}

	public void testDeleteAContainer(){
		Container group = null;
		SortedSet<? extends Container> setAllGroups = this.dbC.getAllContainers(OptionType.GROUP);
		assertTrue("no group. Please add some group and connect this group to some photo for testing!", setAllGroups.isEmpty() == false);
		for(Container c : setAllGroups){
			group = (Group) c;
			break;
		}
		int groupId = group.getItemId();
		Cursor cursor = this.dbA.fetchAllPhotosOfAContainer(groupId, OptionType.PHOTOGROUP)	;
		assertTrue(" no photo connected. Please attach a photo to this group with group Id + " + groupId, 
				cursor.getCount() > 0 );
		this.dbC.deleteAContainer(groupId, OptionType.GROUP);

		cursor = this.dbA.fetchAnEntry(groupId, OptionType.GROUP);
		assertTrue(cursor.getCount() == 0);
		cursor = this.dbA.fetchAllPhotosOfAContainer(groupId, OptionType.PHOTOGROUP);
		assertTrue(cursor.getCount() == 0);

		// test delete a skin condition
		Container skin = null;
		SortedSet<? extends Container> setAllSkins = this.dbC.getAllContainers(OptionType.SKINCONDITION);
		assertTrue("no skin. Please add some skin and connect this skin to some photo for testing!", setAllSkins.isEmpty() == false);
		for(Container c : setAllSkins){
			skin = (SkinCondition) c;
			break;
		}
		int skinId = skin.getItemId();
		cursor = this.dbA.fetchAllPhotosOfAContainer(skinId, OptionType.PHOTOSKIN)	;
		assertTrue(" no photo connected. Please attach a photo to this skin with skin Id + " + skinId, 
				cursor.getCount() > 0 );
		this.dbC.deleteAContainer(skinId, OptionType.SKINCONDITION);

		cursor = this.dbA.fetchAnEntry(skinId, OptionType.SKINCONDITION);
		assertTrue(cursor.getCount() == 0);
		cursor = this.dbA.fetchAllPhotosOfAContainer(skinId, OptionType.PHOTOSKIN);
		assertTrue(cursor.getCount() == 0);

	}

	public void testConnectAPhotoToManyContainers(){
		SortedSet<? extends Container> setAllGroups = this.dbC.getAllContainers(OptionType.GROUP);
		assertTrue("no group. Please add some group and connect this group to some photo for testing!", setAllGroups.isEmpty() == false);

		SortedSet<Photo> setPho = this.dbC.getAllPhoto();
		assertTrue("no photo. Please add a photo and " +
				"connect this photo to some groups and some skin conditions for testing", setPho.isEmpty() == false);
		Photo pho = null;
		for(Photo p : setPho){
			pho = p;
			break;
		}

		SortedSet<? extends Container> setGroups = this.dbC.getAllContainersOfAPhoto(pho.getPhotoId(),OptionType.PHOTOGROUP);
		Set<? extends Container> setDiff = new HashSet<Container>(setAllGroups);
		setDiff.removeAll(setGroups);

		assertTrue("all groups are connected to this photo. Please disconnect a group to test", setDiff.isEmpty() == false);

		Set<Integer> setInt = new HashSet<Integer>();
		for(Container n : setDiff){
			setInt.add(n.getItemId());
		}

		this.dbC.connectAPhotoToManyContainers(pho.getPhotoId(), setInt, OptionType.PHOTOGROUP);
		SortedSet<? extends Container> setGroupsAfter = this.dbC.getAllContainersOfAPhoto(pho.getPhotoId(), OptionType.PHOTOGROUP);
		assertTrue(setGroupsAfter.equals(setAllGroups));
	}

}
