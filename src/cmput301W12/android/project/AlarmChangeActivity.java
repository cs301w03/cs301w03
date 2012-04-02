package cmput301W12.android.project;

import java.sql.Timestamp;
import java.util.Calendar;

import cmput301W12.android.project.AlarmController.MyOnItemSelectedListener;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class AlarmChangeActivity extends Activity
{
	private static final String MEDIA_PLAYER = null;
	Toast mToast;
	MediaPlayer mp;
	Timestamp timestamp;
	EditText alarmtext;
	Button alarmtime;
	Button alarmdate;
	Button alarmdelete;

	private int alarm_type = 0 ;
	private int theYear;
	private int theMonth;
	private int theDay;
	private int theHour;
	private int theMinute;
	private int alarmId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.alarm_controller_edit);

		Alarm alarm = (Alarm) getIntent().getSerializableExtra(RemindersListActivity.ALARM);        
		alarmId = alarm.getAlarmId();
		alarmtime = (Button)findViewById(R.id.timePicker);
		alarmdate = (Button)findViewById(R.id.datepicker);
		alarmdelete = (Button)findViewById(R.id.delete_alarm_button);
		alarmtext = (EditText)findViewById(R.id.alarm_text_editview);

		updateTimeDateDialogs(alarm.getAlarmTime(), alarm.getAlarmNote());

		Button button = (Button)findViewById(R.id.update_alarm_button);
		button.setOnClickListener(updatealarmListener);


		Spinner spinner = (Spinner) findViewById(R.id.spinner);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.alarm_type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		alarmtime.setOnClickListener(invokeTimePicker);
		alarmdate.setOnClickListener(invokeDatePicker);
		alarmdelete.setOnClickListener(deleteAlarmListener);

		/* mp = new MediaPlayer();
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE); 
        try {
			mp.setDataSource(this, alert);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		// Watch for button clicks

		/*button = (Button)findViewById(R.id.start_repeating);
        button.setOnClickListener(mStartRepeatingListener);
        button = (Button)findViewById(R.id.stop_repeating);
        button.setOnClickListener(mStopRepeatingListener);*/

	}

	private void updateTimeDateDialogs(Timestamp t, String s) {

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTimeInMillis(t.getTime());

		theYear = cal.get(Calendar.YEAR);
		theMonth = cal.get(Calendar.MONTH);
		theDay = cal.get(Calendar.DAY_OF_MONTH);
		theHour = cal.get(Calendar.HOUR);
		theMinute = cal.get(Calendar.MINUTE);

		alarmtext.setText(s); 
		timestamp = t;
	}

	private OnDateSetListener odsListener = new OnDateSetListener()
	{

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub

			theYear = year;
			theMonth = monthOfYear;
			theDay = dayOfMonth;

			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(theYear, theMonth, theDay, theHour, theMinute, 0);
			long time = cal.getTimeInMillis();

			timestamp = new Timestamp(time);
		}
	};

	private OnTimeSetListener otsListener = new OnTimeSetListener() 
	{

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub

			theHour = hourOfDay;
			theMinute = minute;


			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(theYear, theMonth, theDay, theHour, theMinute, 0);
			long time = cal.getTimeInMillis();

			timestamp = new Timestamp(time);
		}



	};

	private OnClickListener updatealarmListener = new OnClickListener() {

		@Override
		public void onClick(View v)
		{

			// TODO Auto-generated method stub
			if(alarmtext.getText().toString() == "") {
				mToast = Toast.makeText(AlarmChangeActivity.this, "Stopping Repeating Shots",
						Toast.LENGTH_LONG);
				mToast.show();
			}

			else {

				FController skinObserverController = SkinObserverApplication.getSkinObserverController(AlarmChangeActivity.this);
				skinObserverController.updateAlarm(alarmId, timestamp, alarmtext.getText().toString());

				if(alarm_type == 0) {
					setonetimeAlarm();
				}

				else {
					setrepeatingAlarm();
				}

				mToast = Toast.makeText(AlarmChangeActivity.this, timestamp.toString() ,
						Toast.LENGTH_LONG);
				mToast.show();

				finish();
			}
		}

	};

	private OnClickListener invokeDatePicker = new OnClickListener() {
		public void onClick(View v) {

			DatePickerDialog dpDialog = new DatePickerDialog(AlarmChangeActivity.this,
					odsListener, theYear, theMonth, theDay);
			dpDialog.show();
		}
	};

	private OnClickListener invokeTimePicker = new OnClickListener() {
		public void onClick(View v) {

			TimePickerDialog tpDialog = new TimePickerDialog(AlarmChangeActivity.this,
					otsListener, theHour, theMinute, false);
			tpDialog.show();
		}
	};

	private OnClickListener deleteAlarmListener = new OnClickListener() {

		@Override
		public void onClick(View v)
		{

			// TODO Auto-generated method stub
			FController skinObserverController = SkinObserverApplication.getSkinObserverController(AlarmChangeActivity.this);
			skinObserverController.deleteAnAlarm(alarmId);

			finish();

		}

	};

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent,
				View view, int pos, long id) {

			if (pos == 0) {
				alarm_type = 0;
			}

			else if (pos == 1) {
				alarm_type = 1;
			}
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	private void setonetimeAlarm() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this, OneTimeAlarmReceiver.class);
		Bundle bundle = new Bundle();
		//bundle.putParcelable(MEDIA_PLAYER, (Parcelable) mp);
		//intent.putExtras(bundle);
		PendingIntent sender = PendingIntent.getBroadcast(this,
				0, intent, 0);

		// We want the alarm to go off 30 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 30);

		// Schedule the alarm!
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

		// Tell the user about what we did.
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = Toast.makeText(AlarmChangeActivity.this, "Testing One Shot",
				Toast.LENGTH_LONG);
		mToast.show();

	}

	private void setrepeatingAlarm() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this, RepeatingAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(this,
				0, intent, 0);

		// We want the alarm to go off 30 seconds from now.
		long firstTime = SystemClock.elapsedRealtime();
		firstTime += 15*1000;

		// Schedule the alarm!
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				firstTime, 15*1000, sender);

		// Tell the user about what we did.
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = Toast.makeText(this, "Testing Repeating Shots",
				Toast.LENGTH_LONG);
		mToast.show();

	}

	private void stopRepeatingListener() {

		// Create the same intent, and thus a matching IntentSender, for
		// the one that was scheduled.
		Intent intent = new Intent(this, OneTimeAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(this,
				0, intent, 0);

		// And cancel the alarm.
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.cancel(sender);

		// Tell the user about what we did.
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = Toast.makeText(this, "Stopping Repeating Shots",
				Toast.LENGTH_LONG);
		mToast.show();
	}

}
