package cmput301W12.android.project;

/* SkinCondition.class is a very important class that extends from the main
 * data class of our application, "Container". This data is basically a 
 * container of information , defining the skin condition , which is basically
 * a skin disease. The user can create a skin condition and link photos to a skin
 * condition using other activities in the application. 
 */

import java.io.Serializable;
import java.util.Set;

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
	
}
