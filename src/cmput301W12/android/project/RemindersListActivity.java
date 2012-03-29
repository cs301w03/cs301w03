package cmput301W12.android.project;

import java.util.SortedSet;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class RemindersListActivity extends ListActivity implements FView<DbController> {

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.photoviewlist_activity);
	        
	        Button menubutton = (Button) this.findViewById(R.id.menubutton);
	        	        
	       	        
	        registerForContextMenu(getListView());
	        registerForContextMenu(menubutton);
	        
	        fillRemindersList();
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,  v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ctxmenu2, menu);
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	    	
        super.onListItemClick(l, v, position, id);
        
        ListAdapter adapter = l.getAdapter();
        Alarm alarm  = (Alarm) adapter.getItem(position);
        Intent i = new Intent(this, AlarmController.class);
        /*i.putExtra(PHOTO, photo);
        startActivityForResult(i, VIEW_PHOTO);*/
        
        //editor.putExtra(PHOTO, value ); PRODUCE INTENT WITH PHOTO OBJECT to view

	}
	
	public boolean onContextItemSelected(MenuItem item){
        
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
	       case R.id.menuedit:
	           
	       	Intent intent = new Intent(this, AlarmController.class);
	       	startActivity(intent);
	           return true;
	       case R.id.menudelete:
	       	
	       	FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
	       	//int x = skinObserverController.deleteEntry(info.id, OptionType.PHOTO);
	           
	           return true;
	       			
	       default:
	           return super.onContextItemSelected(item);
		}
	}
	
	private void fillRemindersList() {
		
		FController controller =  SkinObserverApplication.getSkinObserverController(this);
		SortedSet<Alarm> alarms =  controller.getAllAlarms();
		
		if(alarms != null) {
			Alarm[] new_alarms = new Alarm[alarms.size()];
			alarms.toArray(new_alarms);
			
			ReminderListArrayAdapter rAdapter = new ReminderListArrayAdapter(this, new_alarms);
			setListAdapter(rAdapter);
		}
	}
	
	@Override
	public void update(DbController model) {
		// TODO Auto-generated method stub
		
	}

}
