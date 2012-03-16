package cmput301W12.android.project;

import java.util.Set;

public interface DbControllerInterface {
	public void close();

	/**
	 * Add this newly created photo to the database. Update the association of this photo with
	 * any group or skin condition.
	 * @param phoObj
	 * @return
	 */
	public Photo addPhoto(Photo photo);

	/**
	 * NOTE: We don't use this method for project part 3...
	 * @param container
	 * @return
	 */
	public Container addContainObj(Container container);
	
	/**
	 * Create a new group in the database. 
	 * @param name
	 * @param option OptionType.GROUP for making a new group and OptionType.SKINCONDITION
	 * for making a new skin condition.
	 * @return a container ( group or skin condition ). If the ItemId of the new container is INVALID_ID
	 * the new group is not added to the database. If the new container is null, the argument option is
	 * not recognized.
	 */
	public Container storeNewContainer(String name, OptionType option);
	
	public Set<Photo> getAllPhoto();
	

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

	public Set<Photo> getAllPhotoOfAContainer(int itemId, OptionType option);
	
	/**
	 * Fetch all containers (groups or skins conditions) of the photo with the provided photoId.
	 * If the photoId = DbAdapter.INVALID_ID then fetch all containers(groups or skin conditions)
	 * option = OptionType.GROUP for fetching all groups
	 * option = OptionType.SKINCONDITION for fetching all skin conditions.
	 * @param option
	 * @return the Cursor has two columns : GROUPID and GROUPNAME or SKINCONDITIONID and
	 * SKINNAME. 
	 */
	
	public Set<? extends Container> getAllContainersOfAPhoto(int photoId, OptionType option);
	//	
	//	/**
	//	 * Fetch all photos in the database.
	//	 * @return
	//	 */
	//	public Cursor fetchAllPhotoObj();
	//	
	//	
//	/**
//		 * Fetch all photos of a group or skin condition with the itemId.
//		 * If itemId = DbAdapter.INVALID_ID then fetch all photos of all containers
//		 * of particular type ( GROUP or SKINCONDITION).
//		 * option = OptionType.GROUP to fetch from the group.
//		 * option = OptionType.SKINCONDITION to fetch from the skin condition.
//		 * @param itemId
//		 * @param option
//		 * @return The Cursor has all the columns of table PHOTO_TABLE: PHOTOID, LOCATION,
//		 * TIMESTAMP and NAME.
//		 */
	//	public Cursor fetchAllPhotoObjConnected(int itemId, OptionType option);
	//	
//		/**
//		 * Fetch all containers (groups or skins conditions) of the photo with the provided photoId.
//		 * If the photoId = DbAdapter.INVALID_ID then fetch all containers(groups or skin conditions)
//		 * option = OptionType.GROUP for fetching all groups
//		 * option = OptionType.SKINCONDITION for fetching all skin conditions.
//		 * @param option
//		 * @return the Cursor has two columns : GROUPID and GROUPNAME or SKINCONDITIONID and
//		 * SKINNAME. 
//		 */
	//	public Cursor fetchAllContainers(int photoId, OptionType option);
	//	
	//	/**
	//	 * 
	//	 * @param option OptionType.GROUP or OptionType.SKINCONDITION to create new
	//	 * group or skin condition.
	//	 * @return the GROUPID or SKINCONDITIONID of the new group or skin
	//	 */

	// NOTICE: The three methods above are enough for project part 3. Specifically,
	// TRI: call fetchAllContainers(DbAdapter.INVALID_ID, OptionType.GROUP) to obtain all the groups.
	// call fetchAllContainers(DbAdapter.INVALID_ID, OptionType.SKINCONDITION) to obtain all the skin conditions.

	// TANVIR: call fetchAllPhotoObj() to obtain all photos in the database
	/// call fetchAllPhotoObjConnected(ID of the group or skin condition, OptionType.GROUP or OptionType.SKIN CONDITION)
	// to obtain all photos associated with some group or skin condition.

	// KALLEN: call addPhotoObj(phoObj) to add the newly created photo along with its association 
	// with any group and skin conditions to the database. 

	// NOTICE you GUYS: we need to have activity to add new group or skin condition. This activity
	// is called when we click "menu" and choose "create new group" within the activity "show all groups" of TRI.
	// or when we click "menu" and choose "create new skin condition" within the activity "show all skin conditions" of TRI.
	// Also it's used within the activity "showing the newly created photo and let user attach it to group or skin conditiion " 
	// of KALLEN in case the user would like to attach it with new group or skin condition.

	// LAST BUT NOT LEAST: we postpone "delete" and "edit" for now...
}
