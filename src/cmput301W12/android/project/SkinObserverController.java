package cmput301W12.android.project;

import java.util.Set;
import java.util.SortedSet;

import android.content.Context;
import android.util.Log;

public class SkinObserverController implements FController {
	DbControllerInterface dbCon = null;
	
	public SkinObserverController(Context ctx){
	    Log.d("SOController", "Trail part 4");
		this.dbCon = DbController.getDbController(ctx);
	}

	@Override
	public void close() {
		dbCon.close();
	}

	@Override
	public Photo addPhoto(Photo photo) {
	    Log.d("SKINOBSERVER", "Long as trail is making me tired of walking");
		return dbCon.addPhoto(photo);
	}

	@Override
	public Container addContainObj(Container container) {
		return dbCon.addContainObj(container);
	}

	@Override
	public Container storeNewContainer(String name, OptionType option) {
		return dbCon.storeNewContainer(name, option);
	}

	@Override
	public SortedSet<Photo> getAllPhoto() {
		return dbCon.getAllPhoto();
	}

	@Override
	public SortedSet<Photo> getAllPhotoOfAContainer(int itemId, OptionType option) {
		return dbCon.getAllPhotoOfAContainer(itemId, option);
	}

	@Override
	public Set<? extends Container> getAllContainersOfAPhoto(int photoId,
			OptionType option) {
		return dbCon.getAllContainersOfAPhoto(photoId, option);
	}

}
