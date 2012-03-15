package cmput301W12.android.project;

import java.util.Set;
import android.content.Context;

public class DbController implements DbControllerInterface {
	DbAdapter dbAdap;
	
	public DbController(Context ct){
		this.dbAdap = new DbAdapter(ct);
		this.dbAdap = this.dbAdap.open();
	}
	
	public void close(){
		this.dbAdap.close();
	}

	@Override
	public PhotoObj addPhotoObject(PhotoObj phoObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PhotoObj> fetchAllPhotoObj(int itemId, OptionType option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends ContainObj> fetchAllContainers(OptionType option) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
