package cmput301W12.android.project;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;

import android.content.Context;

/**
 * @author Hieu Ngo
 * @date Mar 15, 2012
 * 
 * Data class, stores information of group
 * 
 * Note: Expect to implement Proxy design pattern
 */
public class Group extends Container implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public Group(String name, Set<Integer> photos) {
		super(name, photos);
		// TODO Auto-generated constructor stub
	}

	public Group(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cmput301W12.android.project.ContainObj#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Group == false){
			return false;
		}else{
			return this.getItemId() == ((Group) o).getItemId() ||
									this.getName() == ((Group) o).getName();
		}

	}

	@Override
	public SortedSet<Photo> getPhotos(Context context) {
		FController controller = SkinObserverApplication.getSkinObserverController(context);
		if (getItemId() != DbAdapter.INVALID_ID)
		{
			SortedSet<Photo> photoList = (SortedSet<Photo>) controller.getAllPhotoOfAContainer(getItemId(), OptionType.PHOTOGROUP);
			return photoList;
		}
		else
			return null;
	}
	



}
