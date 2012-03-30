package cmput301W12.android.project;

import java.util.SortedSet;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class RemindersListActivity extends ListActivity implements FView<DbController> {

	public static final String ALARM = "alarm";
	public static final String ALARM_ID = "alarm_id";
	private static final int CREATE_ALARM = 0;
	Toast mToast;

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.photoviewlist_activity);
	       	        	             	        
	        registerForContextMenu(getListView());
	        
	        fillRemindersList();
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,  v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ctxmenu2, menu);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, CREATE_ALARM, 0, "Create");
        return true;
    }
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	    	
        super.onListItemClick(l, v, position, id);
        
        ListAdapter adapter = l.getAdapter();
        Alarm alarm  = (Alarm) adapter.getItem(position);
        Intent i = new Intent(this, AlarmChangeActivity.class);
        
        i.putExtra(ALARM, alarm);
        
        String s = Long.toString(id);
        int id_int = Integer.valueOf(s);
        i.putExtra(ALARM_ID, id_int);
        
        mToast = Toast.makeText(RemindersListActivity.this, "ID : " + id_int + " position : " +position,
                Toast.LENGTH_LONG);
        mToast.show();
        
        this.startActivityForResult(i, 0);
        

	}
	
	public boolean onContextItemSelected(MenuItem item){
        
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
	       case R.id.menuedit:
	           
	       	Intent intent = new Intent(this, AlarmChangeActivity.class);
	       	startActivity(intent);
	           return true;
	       case R.id.menudelete:
	       	
	       	FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
	       	//int x = skinObserverController.deleteEntry(info.id, OptionType.PHOTO);
	       	skinObserverController.deleteAnAlarm(info.position);
	       	
	       	fillRemindersList();
	           
	           return true;
	       			
	       default:
	           return super.onContextItemSelected(item);
		}
	}
	
	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case CREATE_ALARM:
            	Intent i = new Intent(this, AlarmController.class);
            	this.startActivityForResult(i, 0);
            	
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
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
