package cmput301W12.android.project;

import com.log.R;

import android.app.ListActivity;
import android.os.Bundle;


public class PhotoListActivity extends ListActivity
{

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listphotoview_activity);
	 }
	 
	 public void fillupList(void) {
		 
	 }
	 
	 //Kalen add context menu, remember we are creating a context menu
	 //of options like "add photo", or whatever is given in the UI,
	 //we are not creating a context menu of list photo, because 
	 //the list is being handled by the listactivity here.
}
