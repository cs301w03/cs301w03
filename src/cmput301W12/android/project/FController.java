package cmput301W12.android.project;

import java.sql.Timestamp;
import java.util.Set;
import java.util.SortedSet;

/**
 * @author Hieu Ngo
 * @date Mar 14, 2012
 * 
 * Interface for Controller of the system in MVC model.
 * Every controller should implement this interface
 */
public interface FController {

	public void close();

	/**
	 * Add this newly created photo to the database. Update the association of this photo with
	 * any group or skin condition.
	 * @param phoObj
	 * @return
	 */
	public Photo addPhoto(Photo photo);

	/**
	 * Add the newly created container to the relevant tables.
	 * @return a container ( group or skin condition ). If the ItemId of the new container is INVALID_ID
	 * the new container is not added to the database but all IDs of photos connected to this container
	 * will be added to the appropriate table.
	 */
	public Container addContainObj(Container container);

	/**
	 * Add the newly creaed alarm to the database.
	 * @param alarm
	 * @return the alarm with alarm ID being modified to reflect the ID assigned to this alarm.
	 */
	public Alarm addAlarm(Alarm alarm);

	public SortedSet<Photo> getAllPhoto();

	/**
	 * Fetch all photos of a group or skin condition with the itemId.
	 * If itemId = DbAdapter.INVALID_ID then fetch all photos of all containers
	 * of particular type ( GROUP or SKINCONDITION).
	 * option = OptionType.PHOTOGROUP to fetch from the group.
	 * option = OptionType.PHOTOSKIN to fetch from the skin condition.
	 * @param itemId
	 * @param option
	 * @return 
	 */

	public SortedSet<Photo> getAllPhotoOfAContainer(int itemId, OptionType option);

	/**
	 * Fetch all containers (groups or skins conditions) of the photo with the provided photoId.
	 * If the photoId = DbAdapter.INVALID_ID then fetch all containers(groups or skin conditions)
	 * option = OptionType.GROUP for fetching all groups
	 * option = OptionType.SKINCONDITION for fetching all skin conditions.
	 * @param option
	 * @return the Cursor has two columns : GROUPID and GROUPNAME or SKINCONDITIONID and
	 * SKINNAME. 
	 */

	public SortedSet<? extends Container> getAllContainersOfAPhoto(int photoId, OptionType option);

	/**
	 * Get all containers.
	 * @param option OptionType.GROUP for getting all groups or OptionType.SKINCONDITION 
	 * for getting all skin conditions.
	 * @return
	 */
	public SortedSet<? extends Container> getAllContainers(OptionType option);

	/**
	 * Fetch all alarms.
	 * @return
	 */
	public SortedSet<Alarm> getAllAlarms();


	/**
	 * NOTICE: this method will delete the photo with the provided photoId in the photo table
	 * and all tuples containing this photoId in the PhotoGroup and PhotoSkinCondition tables.
	 * Also delete the photo in the memory.
	 * @param PhoObj the photo object corresponding to the target photo.
	 * @return number of rows that have been deleted.
	 */
	public int deleteAPhoto(Photo PhoObj);

	/**
	 * Delete the container along with all tuples that represent the association between
	 * the container and photos.
	 * @param containerId
	 * @param option either OptionType.GROUP to delete group or OptionType.SKINCONDITION to 
	 * delete a skin condition.
	 * @return number of rows that have been deleted.
	 */
	public int deleteAContainer(int containerId, OptionType option);



	/**
	 * Delete the container along with all tuples that represent the association between
	 * the container and photos.
	 * @param containerObj the container object corresponding to the container.
	 * @param option either OptionType.GROUP to delete group or OptionType.SKINCONDITION to 
	 * delete a skin condition.
	 * @return number of rows that have been deleted.
	 */
	public int deleteAContainer(Container containerObj, OptionType option);

	/**
	 * Delete the alarm given the alarmId;
	 * @param alarmId the id of the alarm to be deleted.
	 * @return number of alarms that have been deleted. 
	 */
	public int deleteAnAlarm(int alarmId);


	/**
	 * disconnect a photo from all containers 
	 * @param photoId
	 * @param option option OptionType.PHOTOGROUP if disconnecting groups or OptionType.PHOTOSKIN 
	 * if disconnecting skin conditions.
	 * @return number of containers that have been dissociated from the photo.
	 */
	public int disconnectAPhotoFromManyContainers(int photoId, OptionType option);

	/**
	 * disconnect a photo from many containers
	 * @param photoId 
	 * @param setOfIDs set of Container IDs that are disconnected 
	 * @param option OptionType.PHOTOGROUP if disconnecting groups or OptionType.PHOTOSKIN 
	 * if disconnecting skin conditions.
	 * @return number of containers that have been dissociated from the photo
	 */
	public int disconnectAPhotoFromManyContainers(int photoId, Set<Integer> setOfIDs, OptionType option);

	/**
	 * disconnect a container from all photos.
	 * @param containerId
	 * @param option option OptionType.PHOTOGROUP if disconnecting group or OptionType.PHOTOSKIN 
	 * if disconnecting skin condition.
	 * @return number of photos that have been disconnected from the container.
	 */
	public int disconnectAContainerFromManyPhotos(int containerId, OptionType option);

	/**
	 * disconnect a container from many photos.
	 * @param containerId 
	 * @param setOfIDs set of photo IDs that are disconnected 
	 * @param option OptionType.PHOTOGROUP if disconnecting group or OptionType.PHOTOSKIN 
	 * if disconnecting skin condition.
	 * @return number of photos that have been disconnected from the container.
	 */
	public int disconnectAContainerFromManyPhotos(int containerId, Set<Integer> setOfIDs, OptionType option);

	/**
	 * connect a photo to all containers.
	 * @param photoId the photoId of the photo that is connected to containers.
	 * @param option OptionType.PHOTOGROUP if connecting to the groups or OptionType.PHOTOSKIN
	 * if connecting to the skin conditions.
	 * @return number of groups that are connected to the photo, excluding groups that are connected already.
	 */
	public int connectAPhotoToManyContainers(int photoId, OptionType option);


	/**
	 * connect a photo to many containers.
	 * @param photoId the photoId of the photo that is connected to containers.
	 * @param setOfIDs the set of IDs of the containers to which the photo is connected.
	 * @param option OptionType.PHOTOGROUP if connecting to the groups or OptionType.PHOTOSKIN
	 * if connecting to the skin conditions.
	 * @return number of groups that are connected to the photo.
	 */
	public int connectAPhotoToManyContainers(int photoId, Set<Integer> setOfIDs, OptionType option);

	/**
	 * connect a container to all photos.
	 * @param containerId the container ID of the container that is connected to photos.
	 * @param setOfIDs the set of IDs of the photos to which the container is connected.
	 * @param option OptionType.PHOTOGROUP if connecting to the groups or OptionType.PHOTOSKIN
	 * if connecting to the skin conditions. 
	 * @return number of photos that are connected to the container, excluding photos that are
	 * connected already.
	 */
	public int connectAContainerToManyPhotos(int containerId, OptionType option);


	/**
	 * connect a container to many photos
	 * @param containerId the container ID of the container that is connected to photos.
	 * @param setOfIDs the set of IDs of the photos to which the container is connected.
	 * @param option OptionType.PHOTOGROUP if connecting to the groups or OptionType.PHOTOSKIN
	 * if connecting to the skin conditions. 
	 * @return number of photos that are connected to the container.
	 */
	public int connectAContainerToManyPhotos(int containerId, Set<Integer> setOfIDs, OptionType option);

	/**
	 * Update the photo's information.
	 * @param photoId 
	 * @param newLocation if null , not update the location
	 * @param newTimeStamp if null, not update the timeStamp
	 * @param newName if null, not update the name
	 * @return number of photos that have been disconnected from the container.
	 */
	public int updatePhoto(int photoId, String newLocation, Timestamp newTimeStamp, String newName, String newAnnotation );

	/**
	 * Update the group information.
	 * @param groupId the group ID of the group that is updated.
	 * @param newName new name for the group. If null, name unchanged.
	 * @return number of groups that are updated.
	 * NOTICE: throw SQLiteConstraintException if the newName conflicts with the name of some group.
	 */
	public int updateGroup(int groupId, String newName );

	/**
	 * Update the skin condition information.
	 * @param skinId the skin ID of the skin condition that is updated.
	 * @param newName new name for the skin condition. If null, name unchanged.
	 * @return number of skin conditions that are updated.
	 * NOTICE: throw SQLiteConstraintException if the newName conflicts with the name of some skin condition.
	 */
	public int updateSkin(int skinId, String newName );

	/**
	 * Update the alarm given the alarmId with the newTime and newNote.
	 * @param alarmId the id of the alarm to be updated.
	 * @param newTime the new timestamp for the alarm, passing null retains old value
	 * @param newNote the new note for the alarm, passing null retains old value
	 * @return number of alarms that are updated.
	 */
	public int updateAlarm(int alarmId, Timestamp newTime, String newNote);


}
