package cmput301W12.android.project;

import java.util.SortedSet;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * RemindersListActivity shows the current list of alarms
 * from the database in a listview, using a
 * RemindersListArrayAdapter. This class inherits ListActivity.
 * @author Tanvir Sajed
 *
 */
public class RemindersListActivity extends ListActivity implements FView<DbController> {

	public static final String ALARM = "alarm";
	public static final String ALARM_ID = "alarm_id";
	private static final int CREATE_ALARM = 0;
	Toast mToast;

	/**
	 * This method overrides the Activity onCreate method.
	 * Used for initializing resources and filling the
	 * listview with alarm information, taken from
	 * arrayadapter.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoviewlist_activity);

		registerForContextMenu(getListView());

		fillRemindersList();
		
		Button button = (Button) findViewById(R.id.menubutton);
		button.setVisibility(Button.INVISIBLE);
		
	}

	/**
	 * Creates the ContextMenu for each item in the listview.
	 * On long clicking an item in the listview, there will be an option
	 * to delete the alarm selected item in the list.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu,  v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ctxmenu2, menu);
	}

	/**
	 * This method is called on creating the options menu
	 * which is done by pressing the home button. It creates
	 * a button "create" for user to create a new alarm
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, CREATE_ALARM, 0, "Create");
		return true;
	}

	/**
	 * This method overrides onListItemClick from ListViewActivity
	 * It gets the alarm item that is clicked from the adapter.
	 * It starts AlarmChangeActivity which can let you edit an alarm
	 * with an alarmId thrown in the intent
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {


		super.onListItemClick(l, v, position, id);

		ListAdapter adapter = l.getAdapter();
		Alarm alarm  = (Alarm) adapter.getItem(position);
		Intent i = new Intent(this, AlarmChangeActivity.class);

		i.putExtra(ALARM, alarm);

		mToast = Toast.makeText(RemindersListActivity.this, "ID : " + alarm.getAlarmId() + " position : " +position,
				Toast.LENGTH_LONG);
		mToast.show();

		this.startActivityForResult(i, 0);


	}

	/**
	 * This method will do what the user has selected from
	 * the context menu after having selected a list item.
	 * The contextmenu has only the option to delete so
	 * the system will delete the alarm that has been selected.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item){

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {

	       case R.id.menuedit:
	           
	       	Intent intent = new Intent(this, AlarmChangeActivity.class);
	       	startActivity(intent);
	           return true;
	       case R.id.menudelete:
	       	
	       	FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
	       	skinObserverController.deleteAnAlarm(info.position);
	       	
	       	fillRemindersList();
	           
	           return true;
	       			
	       default:
	           return super.onContextItemSelected(item);

		}
	}

	/**
	 * This method will do what the user has selected from
	 * the options menu. There is only one option of 
	 * creating the alarm in the menu, and therefore
	 * the AlarmController activity will start, letting
	 * the user create the alarm.
	 */
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

	/**
	 * The method updates the listview with all the alarm objects
	 * that are currently present in the database. It uses
	 * the RemindLisArrayAdapter to update the list.
	 */
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

	/**
	 * This method will update the listview with the updated
	 * array of alarms once the user has updated alarm, created
	 * an alarm or deleted an alarm from the database.
	 */
	@Override 
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {

		fillRemindersList();
	}




	@Override
	public void update(DbController model) {
	}

}
