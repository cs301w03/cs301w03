package cmput301W12.android.project;

import java.util.Set;
import java.util.SortedSet;

import android.content.Context;
import android.util.Log;

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
	public Set<? extends Container> getAllContainersOfAPhoto(int photoId,
			OptionType option) {
		return dbCon.getAllContainersOfAPhoto(photoId, option);
	}

	/*
	 * (non-Javadoc)
	 * @see cmput301W12.android.project.FController#getAllContainers(cmput301W12.android.project.OptionType)
	 * Load all containers based on OptionType: Group/SkinCondition
	 * Return of Set of Container
	 */
	public Set<? extends Container> getAllContainers(OptionType option){
		return dbCon.getAllContainers(option);
	}

	public int deleteEntry(long rowID, OptionType option) {
		return dbCon.deleteEntry(rowID, option);
	}

}
