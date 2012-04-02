package cmput301W12.android.project;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;

import android.content.Context;

/**
 * @author Hieu Ngo
 * @date Mar 15, 2012
 * 
 * Data class, stores information of skin condition
 * 
 * Note: Expect to implement Proxy design pattern
 */
public class SkinCondition extends Container implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see cmput301W12.android.project.ContainObj#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof SkinCondition == false){
			return false;
		}else{
			return this.getItemId() == ((SkinCondition) o).getItemId() ||
									this.getName() == ((SkinCondition) o).getName();
		}
	}

	public SkinCondition(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SkinCondition(String name, Set<Integer> photos) {
		super(name, photos);
		// TODO Auto-generated constructor stub
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
			return null;
	}
	
}
