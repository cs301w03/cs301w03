package cmput301W12.android.project;

import java.util.Set;

public interface FController {
	
	public void close();
	
	public Photo addPhoto(Photo photo);
	
	public Container addContainObj(Container container);
	
	public Container storeNewContainer(String name, OptionType option);

	public Set<Photo> getAllPhoto();
	
	public Set<Photo> getAllPhotoOfAContainer(int itemId, OptionType option);
	
	public Set<? extends Container> getAllContainersOfAPhoto(int photoId, OptionType option);



}
