package cmput301W12.android.project.view;

import java.util.SortedSet;

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
import cmput301W12.android.model.Alarm;
import cmput301W12.android.model.DbController;
import cmput301W12.android.model.SkinObserverApplication;
import cmput301W12.android.project.R;
import cmput301W12.android.project.controller.FController;
import cmput301W12.android.project.view.helper.OneTimeAlarmReceiver;
import cmput301W12.android.project.view.helper.ReminderListArrayAdapter;
import cmput301W12.android.project.view.helper.RepeatingAlarmReceiver;

/**
 * RemindersListActivity inherits ListActivity, and is responsible for 
 * updating a listview with all the alarms queried from the alarm
 * table in the database. Each item in the list shows the text
 * for an alarm, timestamp and alarm_type.
 * @author Tanvir Sajed
 *
 */
public class RemindersListActivity extends ListActivity implements FView<DbController> {

	public static final String ALARM = "alarm";
	public static final String ALARM_ID = "alarm_id";
	private static final int CREATE_ALARM = 0;
	private static final int CREATEALARM_ACTIVITY = 0;
	Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoviewlist_activity);

		registerForContextMenu(getListView());

		Button Menu = (Button) findViewById(R.id.menubutton);
		Menu.setVisibility(Button.INVISIBLE);

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

		mToast = Toast.makeText(RemindersListActivity.this, "ID : " + alarm.getAlarmId() + " position : " +position,
				Toast.LENGTH_LONG);
		mToast.show();

		this.startActivityForResult(i, 0);


	}

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

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		case CREATE_ALARM:
			Intent i = new Intent(this, AlarmController.class);
			this.startActivityForResult(i, CREATEALARM_ACTIVITY);

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
		intent.setAction(AlarmController.INTENT_ACTION_ONESHOT);
		
		Intent intent2 = new Intent(this, RepeatingAlarmReceiver.class);
		intent2.setAction(AlarmController.INTENT_ACTION_REPEAT);
		
		PendingIntent sender = PendingIntent.getBroadcast(this,
				id, intent, 0);
		
		PendingIntent sender2 = PendingIntent.getBroadcast(this, id, intent2, 0);

		// And cancel the alarm.
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.cancel(sender);
		am.cancel(sender2);

		// Tell the user about what we did.
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = Toast.makeText(this, "Stopping Repeating Shots",
				Toast.LENGTH_LONG);
		mToast.show();
	}




	@Override
	public void update(DbController model) {
	}

}
