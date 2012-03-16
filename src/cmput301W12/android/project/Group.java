package cmput301W12.android.project;

/* Group.class is a very important class that extends from the main
 * data class of our application, "Container". This data is basically a 
 * container of information , defining the group , which is basically
 * a section of the human body. The user can create a group and 
 * link photos to a group using other activities in the application. 
 */

import java.io.Serializable;
import java.util.Set;

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


}
