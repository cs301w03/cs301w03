package cmput301W12.android.project;

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

	public Photo addPhoto(Photo photo);

	public Container addContainObj(Container container);

	public Container storeNewContainer(String name, OptionType option);

	public SortedSet<Photo> getAllPhoto();

	public SortedSet<Photo> getAllPhotoOfAContainer(int itemId, OptionType option);

	public Set<? extends Container> getAllContainersOfAPhoto(int photoId, OptionType option);

	public Set<? extends Container> getAllContainers(OptionType option);


}
