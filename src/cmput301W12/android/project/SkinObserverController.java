package cmput301W12.android.project;

import java.sql.Timestamp;
import java.util.Set;
import java.util.SortedSet;
import android.content.Context;

/**
 * 
 * @author Hieu Ngo
 * @date Mar 14, 2012
 * 
 * System's controller
 */
public class SkinObserverController implements FController {
	DbControllerInterface dbCon = null;

	public SkinObserverController(Context ctx){
		this.dbCon = DbController.getDbController(ctx);
	}

	@Override
	/**
	 * Close database connection
	 */
	public void close() {
		dbCon.close();
	}

	@Override
	/*
	 * Add a photo to database
	 * Return a photo if success
	 * If not, return null
	 */
	public Photo addPhoto(Photo photo) {
		return dbCon.addPhoto(photo);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see cmput301W12.android.project.FController#addContainObj(cmput301W12.android.project.Container)
	 * Add a new Container and its list of photos to database
	 * Return a Container and its list of photos if success
	 * If not, return null
	 */
	public Container addContainObj(Container container) {
		return dbCon.addContainObj(container);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see cmput301W12.android.project.FController#getAllPhoto()
	 * Load all photos from database 
	 * Return a SortedSet of Photo
	 */
	public SortedSet<Photo> getAllPhoto() {
		return dbCon.getAllPhoto();
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see cmput301W12.android.project.FController#getAllPhotoOfAContainer(int, cmput301W12.android.project.OptionType)
	 * Load all photos that belongs to a same container 
	 * Return a SortedSet of Photo
	 */
	public SortedSet<Photo> getAllPhotoOfAContainer(int itemId, OptionType option) {
		return dbCon.getAllPhotoOfAContainer(itemId, option);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see cmput301W12.android.project.FController#getAllContainersOfAPhoto(int, cmput301W12.android.project.OptionType)
	 * Load all containers of a photo
	 * Return a Set of Container
	 */
	public SortedSet<? extends Container> getAllContainersOfAPhoto(int photoId,
			OptionType option) {
		return dbCon.getAllContainersOfAPhoto(photoId, option);
	}

	/*
	 * (non-Javadoc)
	 * @see cmput301W12.android.project.FController#getAllContainers(cmput301W12.android.project.OptionType)
	 * Load all containers based on OptionType: Group/SkinCondition
	 * Return of Set of Container
	 */
	public SortedSet<? extends Container> getAllContainers(OptionType option){
		return dbCon.getAllContainers(option);
	}

	@Override
	public int deleteAPhoto(Photo PhoObj) {
		return dbCon.deleteAPhoto(PhoObj);
	}

	@Override
	public int deleteAContainer(int containerId, OptionType option) {
		return dbCon.deleteAContainer(containerId, option);
	}

	@Override
	public int deleteAContainer(Container containerObj, OptionType option) {
		return dbCon.deleteAContainer(containerObj, option);
	}

	@Override
	public int disconnectAPhotoFromManyContainers(int photoId, OptionType option) {
		return dbCon.disconnectAPhotoFromManyContainers(photoId, option);
	}

	@Override
	public int disconnectAPhotoFromManyContainers(int photoId,
			Set<Integer> setOfIDs, OptionType option) {
		return dbCon.disconnectAPhotoFromManyContainers(photoId, setOfIDs, option);
	}

	@Override
	public int disconnectAContainerFromManyPhotos(int containerId,
			OptionType option) {
		return dbCon.disconnectAContainerFromManyPhotos(containerId, option);
	}

	@Override
	public int disconnectAContainerFromManyPhotos(int containerId,
			Set<Integer> setOfIDs, OptionType option) {
		return dbCon.disconnectAContainerFromManyPhotos(containerId, setOfIDs, option);
	}

	@Override
	public int connectAPhotoToManyContainers(int photoId, OptionType option) {
		return dbCon.connectAPhotoToManyContainers(photoId, option);
	}

	@Override
	public int connectAPhotoToManyContainers(int photoId, Set<Integer> setOfIDs,
			OptionType option) {
		return dbCon.connectAPhotoToManyContainers(photoId, setOfIDs, option);
	}

	@Override
	public int connectAContainerToManyPhotos(int containerId, OptionType option) {
		return dbCon.connectAContainerToManyPhotos(containerId, option);
	}

	@Override
	public int connectAContainerToManyPhotos(int containerId,
			Set<Integer> setOfIDs, OptionType option) {
		return dbCon.connectAContainerToManyPhotos(containerId, setOfIDs, option);
	}

	@Override
	public int updatePhoto(int photoId, String newLocation,
			Timestamp newTimeStamp, String newName, String newAnnotation) {
		return dbCon.updatePhoto(photoId, newLocation, newTimeStamp, newName, newAnnotation);
	}

	@Override
	public int updateGroup(int groupId, String newName) {
		return dbCon.updateGroup(groupId, newName);
	}

	@Override
	public int updateSkin(int skinId, String newName) {
		return dbCon.updateSkin(skinId, newName);
	}

	@Override
	public Alarm addAlarm(Alarm alarm) {
		// TODO Auto-generated method stub
		return dbCon.addAlarm(alarm);
	}

	@Override
	public SortedSet<Alarm> getAllAlarms() {
		return this.dbCon.getAllAlarms();
	}

	@Override
	public int deleteAnAlarm(int alarmId) {
		return this.dbCon.deleteAnAlarm(alarmId);
	}

	@Override
	public int updateAlarm(int alarmId, Timestamp newTime, String newNote) {
		return this.dbCon.updateAlarm(alarmId, newTime, newNote);
	}

}
