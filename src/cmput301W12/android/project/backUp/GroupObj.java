package cmput301W12.android.project.backUp;

import java.util.Set;

public class GroupObj extends ContainObj {

	public GroupObj(int groupId, String name, Set<PhotoObj> photos) {
		super(groupId, name, photos);
		// TODO Auto-generated constructor stub
	}

	public GroupObj(int groupId, String name) {
		super(groupId, name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cmput301W12.android.project.ContainObj#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof GroupObj == false){
			return false;
		}else{
			return this.getItemId() == ((GroupObj) o).getItemId();
		}

	}


}
