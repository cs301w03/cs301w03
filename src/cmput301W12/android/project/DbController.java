package cmput301W12.android.project;

import android.content.Context;
import android.database.Cursor;

public class DbController implements DbControllerInterface {
	DbAdapter dbAdap;
	
	private DbController(Context ct){
		this.dbAdap = new DbAdapter(ct);
		this.dbAdap = this.dbAdap.open();
	}
	
	public void close(){
		this.dbAdap.close();
	}

	
	@Override
	public PhotoObj addPhotoObj(PhotoObj phoObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor fetchAllPhotoObj() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor fetchAllPhotoObjConnected(int itemId, OptionType option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor fetchAllContainers(int photoId, OptionType option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createContainer(OptionType option) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
