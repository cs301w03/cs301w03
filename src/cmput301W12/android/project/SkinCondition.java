package cmput301W12.android.project;

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
