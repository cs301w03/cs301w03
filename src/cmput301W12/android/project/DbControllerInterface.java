package cmput301W12.android.project;

import java.util.Set;

public interface DbControllerInterface {
	public void close();
	public PhotoObj addPhotoObject(PhotoObj phoObj);
	/**
	 * Fetch all photos of a group or skin condition with the itemId.
	 * option = OptionType.GROUP to fetch from the group.
	 * option = OptionType.SKINCONDITION to fetch from the skin condition.
	 * @param itemId
	 * @param option
	 * @return
	 */
	public Set<PhotoObj> fetchAllPhotoObj(int itemId, OptionType option);
	
	/**
	 * Fetch all containers (groups or skins conditions) of the photo with the provided photoId.
	 * If the photoId = DbAdapter.INVALID_ID then fetch all containers(groups or skin conditions)
	 * option = OptionType.GROUP for fetching all groups
	 * option = OptionType.SKINCONDITION for fetching all skin conditions.
	 * @param option
	 * @return
	 */
	public Set<? extends ContainObj> fetchAllContainers(int photoId, OptionType option);
	
	public 
}
