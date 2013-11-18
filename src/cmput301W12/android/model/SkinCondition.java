package cmput301W12.android.model;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import cmput301W12.android.project.controller.DbAdapter;
import cmput301W12.android.project.controller.FController;

/**
 * @author Hieu Ngo
 * @date Mar 15, 2012
 * 
 * Data class, stores information of skin condition
 */
public class SkinCondition extends Container implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(Object o) {
		if(o instanceof SkinCondition == false){
			return false;
		}else{
			return this.getItemId() == ((SkinCondition) o).getItemId() ||
					this.getName() == ((SkinCondition) o).getName();
		}
	}

	public SkinCondition(String name) {
		super(name);
	}

	public SkinCondition(String name, Set<Integer> photos) {
		super(name, photos);
	}

	@Override
	public SortedSet<Photo> getPhotos(Context context) {
		FController controller = SkinObserverApplication.getSkinObserverController(context);
		if (getItemId() != DbAdapter.INVALID_ID)
		{
			SortedSet<Photo> photoList = (SortedSet<Photo>) controller.getAllPhotoOfAContainer(getItemId(), OptionType.PHOTOSKIN);
			return photoList;
		}
		else
			return new TreeSet<Photo>();
	}

}
