package cmput301W12.android.project;

import java.util.Set;

public class SkinConditionObj extends ContainObj {

	/* (non-Javadoc)
	 * @see cmput301W12.android.project.ContainObj#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof SkinConditionObj == false){
			return false;
		}else{
			return this.getItemId() == ((SkinConditionObj) o).getItemId() ||
									this.getName() == ((SkinConditionObj) o).getName();
		}
	}

	public SkinConditionObj(int skinConditionId, String name) {
		super(skinConditionId, name);
		// TODO Auto-generated constructor stub
	}

	public SkinConditionObj(int skinConditionId, String name, Set<Integer> photos) {
		super(skinConditionId, name, photos);
		// TODO Auto-generated constructor stub
	}
	
}
