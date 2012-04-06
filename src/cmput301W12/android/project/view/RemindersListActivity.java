package cmput301W12.android.project.view;

import java.util.SortedSet;

import cmput301W12.android.model.Alarm;
import cmput301W12.android.model.DatabaseModel;
import cmput301W12.android.model.SkinObserverApplication;
import cmput301W12.android.project.R;
import cmput301W12.android.project.controller.FController;
import cmput301W12.android.project.view.helper.OneTimeAlarmReceiver;
import cmput301W12.android.project.view.helper.ReminderListArrayAdapter;
import cmput301W12.android.project.view.helper.RepeatingAlarmReceiver;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
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
 * RemindersListActivity inherits ListActivity, and is responsible for 
 * updating a listview with all the alarms queried from the alarm
 * table in the database. Each item in the list shows the text
 * for an alarm, timestamp and alarm_type.
 * @author Tanvir Sajed
 *
 */
public class RemindersListActivity extends ListActivity implements FView<DatabaseModel> {

	public static final String ALARM = "alarm";
	public static final String ALARM_ID = "alarm_id";
	private static final int CREATE_ALARM = 0;
	private static final int CREATEALARM_ACTIVITY = 0;
	Toast mToast;

	/**
	 * Method called when Activity created.
	 * It fills the listview with the list of alarms from
	 * the database.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoviewlist_activity);

		registerForContextMenu(getListView());

		Button Menu = (Button) findViewById(R.id.menubutton);
		Menu.setVisibility(Button.INVISIBLE);

		fillRemindersList();
	}

	/**
	 * Method called when contextmenu is created.
	 * When the user long clicks an item in a listview,
	 * the contextmenu pops up with the menu.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu,  v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ctxmenu2, menu);
	}

	/**
	 * Method called when Options menu is created.
	 * It pops up a Create button which lets the user
	 * create new alarms
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, CREATE_ALARM, 0, "Create");
		return true;
	}

	/**
	 * Method called when you click a list item.
	 * The system calls the AlarmChangeActivity.class which lets
	 * the user change a particular alarm that he has selected
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {


		super.onListItemClick(l, v, position, id);

		ListAdapter adapter = l.getAdapter();
		Alarm alarm  = (Alarm) adapter.getItem(position);
		Intent i = new Intent(this, AlarmChangeActivity.class);

		i.putExtra(ALARM, alarm);

		this.startActivityForResult(i, 0);

	}

	/**
	 * This method is called when the user selects a particular
	 * item on context menu. The user can only delete a certain photo
	 * that he has clicked.
	 */
	public boolean onContextItemSelected(MenuItem item){

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()) {

	       case R.id.menudelete:
	       	
	       	FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);

	       	ListAdapter ladapter = this.getListAdapter();
	       	Alarm alarm = (Alarm) ladapter.getItem(info.position);
	       	skinObserverController.deleteAnAlarm(alarm.getAlarmId());
	       	
	       	fillRemindersList();
	       	this.stopScheduledAlarm(alarm.getAlarmId());
	           
	           return true;
	       			
	       default:
	           return super.onContextItemSelected(item);
		}
	}

	/**
	 * This method is called when the item in the options menu
	 * is selected. The user can only create a new alarm in the
	 * options menu, from a AlarmController.java.
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		case CREATE_ALARM:
			Intent i = new Intent(this, CreateAlarmActivity.class);
			this.startActivityForResult(i, CREATEALARM_ACTIVITY);

			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}
	
	/**
	 * This method fills the listview with all the alarm
	 * objects queried from the database. Each item has
	 * a alarm text, alarm timestamp and an alarm type.
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
	 * This method is called when another activity returns to this activity.
	 * Each time an activity returns to this activity, the list is updated with
	 * all the updated alarm objects
	 */
	@Override 
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {

		fillRemindersList();
	}
	
	/**
	 * This method stops the scheduled alarm for a particular alarmId
	 */
	private void stopScheduledAlarm(int id) {

		// Create the same intent, and thus a matching IntentSender, for
		// the one that was scheduled.
		Intent intent = new Intent(this, OneTimeAlarmReceiver.class);
		intent.setAction(CreateAlarmActivity.INTENT_ACTION_ONESHOT);
		
		Intent intent2 = new Intent(this, RepeatingAlarmReceiver.class);
		intent2.setAction(CreateAlarmActivity.INTENT_ACTION_REPEAT);
		
		PendingIntent sender = PendingIntent.getBroadcast(this,
				id, intent, 0);
		
		PendingIntent sender2 = PendingIntent.getBroadcast(this, id, intent2, 0);

		// And cancel the alarm.
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.cancel(sender);
		am.cancel(sender2);

	}




	@Override
	public void update(DatabaseModel model) {
	}

}
