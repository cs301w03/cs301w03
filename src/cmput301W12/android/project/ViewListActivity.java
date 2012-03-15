package cmput301W12.android.project;

import android.app.ListActivity;
import android.os.Bundle;


public class ViewListActivity extends ListActivity
{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listphotoview_activity);
        
	}
	
	public void fillList(void) {
		
	}
	
}
