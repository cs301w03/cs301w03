package cmput301W12.android.model;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import cmput301W12.android.project.controller.DbAdapter;
import cmput301W12.android.project.controller.FController;

import android.content.Context;

/**
 * @author Hieu Ngo
 * @date Mar 15, 2012
 * 
 * Data class for group. This group object keeps a list of photo IDs 
 * of the photos associated with the group
 */
public class Group extends Container implements Serializable {

	private static final long serialVersionUID = 1L;

	public Group(String name, Set<Integer> photos) {
		super(name, photos);
	}

	public Group(String name) {
		super(name);
	}

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
		else{
			return new TreeSet<Photo>();
		}
	}




}
