package cmput301W12.android.project;

import java.util.Set;

public class SkinCondition extends Container {

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

	public SkinCondition(int skinConditionId, String name) {
		super(skinConditionId, name);
		// TODO Auto-generated constructor stub
	}

	public SkinCondition(int skinConditionId, String name, Set<Integer> photos) {
		super(skinConditionId, name, photos);
		// TODO Auto-generated constructor stub
	}
	
}
