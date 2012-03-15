package cmput301W12.android.project;

import java.sql.Timestamp;
import java.util.Set;
import android.content.Context;
import android.database.Cursor;

public class DbController implements DbControllerInterface {
	private DbAdapter mDbAdap;

	private static DbController dbCon = null;

	private DbController(Context ct){
		this.mDbAdap = DbAdapter.getDbAdapter(ct);
		this.mDbAdap = this.mDbAdap.open();
	}

	public static DbController getDbController(Context ct){
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
		// TODO Auto-generated method stub
		String location = phoObj.getLocation();
		Timestamp timeStamp  = phoObj.getTimeStamp();
		String name = phoObj.getName();
		int photoId = ( int) this.mDbAdap.addPhoto(location, timeStamp, name);
		phoObj.setPhotoId(photoId);

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
		// TODO Auto-generated method stub
		String name = containObj.getName();
		Set<Integer> listOfPhotoIds= containObj.getPhotos();


		if(containObj instanceof Group){
			for(Integer id : listOfPhotoIds){
				int groupId= (int) this.mDbAdap.addGroup(name);
				containObj.setItemId(groupId);
				this.mDbAdap.addPhotoGroup(id, groupId );
			}
		} 
		else if(containObj instanceof SkinCondition){
			for(Integer id : listOfPhotoIds){
				int skinId = (int) this.mDbAdap.addSkinCondition(name);
				containObj.setItemId(skinId);
				this.mDbAdap.addPhotoSkinCondition(id, skinId);
			}
		}

		return containObj;
	}

	public 

	private Cursor fetchAllPhotoObj() {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllEntries(OptionType.PHOTO);
	}


	private Cursor fetchAllPhotoObjConnected(int itemId, OptionType option) {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllPhotosOfAContainer(itemId, option);
	}


	private Cursor fetchAllContainers(int photoId, OptionType option) {
		// TODO Auto-generated method stub
		return this.mDbAdap.fetchAllContainersOfAPhoto(photoId, option);
	}

	
	}

