package cmput301W12.android.project;

import java.sql.Timestamp;
import java.util.Calendar;

import cmput301W12.android.project.AlarmController.MyOnItemSelectedListener;
import cmput301W12.android.project.AlarmController.RepeatItemSelectedListener;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
	EditText alarmrepeat;
	TextView repeatinfo;
	Button alarmtime;
	Button alarmdate;
	Button alarmdelete;
	Spinner repeat_spinner;

	private int alarm_type = 0 ;
	private int theYear;
	private int theMonth;
	private int theDay;
	private int theHour;
	private int theMinute;
	private int repeat_type = 0;
	private int repeatMillisec = 60000;
	private int alarmId;
	private String alarmnote;

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
		alarmrepeat = (EditText)findViewById(R.id.alarm_number_editview);
		repeatinfo  = (TextView)findViewById(R.id.alarm_repeat_info);

		updateTimeDateDialogs(alarm.getAlarmTime(), alarm.getAlarmNote());

		Button button = (Button)findViewById(R.id.update_alarm_button);
		button.setOnClickListener(updatealarmListener);


		Spinner alarmtype_spinner = (Spinner) findViewById(R.id.spinner);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.alarm_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alarmtype_spinner.setAdapter(adapter);
        
        alarmtype_spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        repeat_spinner = (Spinner) findViewById(R.id.repeat_spinner);
        
        ArrayAdapter<CharSequence> r_adapter = ArrayAdapter.createFromResource(
                this, R.array.repeat_terms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeat_spinner.setAdapter(r_adapter);
        
        repeat_spinner.setOnItemSelectedListener(new RepeatItemSelectedListener());
        
        
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

		repeatinfo.setText(this.getFactoredString(s));
		alarmtext.setText(alarmnote); 
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
				
				String s = setFactoredString(alarmtext.getText().toString());

				FController skinObserverController = SkinObserverApplication.getSkinObserverController(AlarmChangeActivity.this);
				skinObserverController.updateAlarm(alarmId, timestamp, s);
				
				stopRepeatingAlarm();

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
			stopRepeatingAlarm();

			finish();

		}

	};

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent,
				View view, int pos, long id) {

			if (pos == 0) {
				
				alarm_type = 0;
				
				repeat_spinner.setVisibility(Spinner.INVISIBLE);
	        	alarmrepeat.setVisibility(EditText.INVISIBLE);
	        	
	        	//repeatinfo.setText("One Shot Alarm");
			}

			else if (pos == 1) {
				alarm_type = 1;
				
				repeat_spinner.setVisibility(Spinner.VISIBLE);
	        	alarmrepeat.setVisibility(EditText.VISIBLE);
	        	
	        	repeatinfo.setText("Repeat Every : ");
			}
		}


		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
			
	}
	
	public class RepeatItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          
          if (pos == 0) {
        	  repeat_type = 0;
        	  repeatMillisec = 1000*60;
          }
          
          else if (pos == 1) {
        	  repeat_type = 1;
        	  repeatMillisec = 3600*1000*24;
          }
          
          else if (pos == 2) {
        	  
        	  repeat_type = 2;
        	  repeatMillisec = 3600*1000*24*7;
          }
          
          else if (pos == 3) {
        	  
        	  repeat_type = 3;
        	  repeatMillisec = 3600*1000*24*30 ;
          }
          
          else {
        	  repeat_type = 4;
        	  repeatMillisec = 3600*1000*24*365;
          }
        }

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{

			// TODO Auto-generated method stub
			
		}
    }

	private void setonetimeAlarm() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this, OneTimeAlarmReceiver.class);
		Bundle bundle = new Bundle();
		//bundle.putParcelable(MEDIA_PLAYER, (Parcelable) mp);
		//intent.putExtras(bundle);
		PendingIntent sender = PendingIntent.getBroadcast(this,
				alarmId, intent, 0);

		// We want the alarm to go off 30 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		long alarm_time = timestamp.getTime() - calendar.getTimeInMillis();
        calendar.add(Calendar.MILLISECOND, (int)alarm_time);

		// Schedule the alarm!
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

		// Tell the user about what we did.
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = Toast.makeText(AlarmChangeActivity.this, "Testing One Shot :" + (int)alarm_time,
				Toast.LENGTH_LONG);
		mToast.show();

	}

	private void setrepeatingAlarm() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this, RepeatingAlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this,
                alarmId, intent, 0);

        //We want the alarm to go off 30 seconds from now.
        long firstTime = SystemClock.elapsedRealtime();
        //firstTime += 15*1000;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        
        long alarm_time = timestamp.getTime() - calendar.getTimeInMillis();
        //calendar.add(Calendar.MILLISECOND, (int)alarm_time);
        
        firstTime += alarm_time;
        
        String s = alarmrepeat.getText().toString();
        int repeat = Integer.parseInt(s);
        repeat = repeat * repeatMillisec;
        

        // Schedule the alarm!
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				firstTime, repeat, sender);

        // Tell the user about what we did.
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, "Testing Repeating Shots" + (int)alarm_time + repeat,
                Toast.LENGTH_LONG);
        mToast.show();

	}

	private void stopRepeatingAlarm() {

		// Create the same intent, and thus a matching IntentSender, for
		// the one that was scheduled.
		Intent intent = new Intent(this, OneTimeAlarmReceiver.class);
		Intent intent2 = new Intent(this, RepeatingAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(this,
				alarmId, intent, 0);
		
		PendingIntent sender2 = PendingIntent.getBroadcast(this, alarmId, intent2, 0);

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
	
	private String setFactoredString(String note) {
		
		if(alarm_type == 0) {
			note = note.concat("~");
			note = note.concat("5");
			return note;
		}
		
		else {
			note = note.concat("~");
			note = note.concat(alarmrepeat.getText().toString());
			note = note.concat("~");
			note = note + repeat_type;
			//note = note.concat("~");
			
			return note;
		}
	}
	
	private String getFactoredString(String note) {
		
		if (note.charAt(note.length() - 1) == '5') {
			
			String[] command = note.split("~");
			alarmnote = command[0];
			alarm_type = 0;
			
			return "One Shot Alarm";
		}
		
		String[] command = note.split("~");
		String s = "Repeat Every " + command[1] + " " ;
		
		if(command[2].equals("0")){
			s = s + "Hour(s)";
		}
		
		if(command[2].equals("1")){
			s = s + "Day(s)";
		}
		
		if(command[2].equals("2")){
			s = s + "Week(s)";
		}
		
		if(command[2].equals("3")){
			s = s + "Month(s)";
		}
		
		if(command[2].equals("4")){
			s = s + "Year(s)";
		}
		
		alarm_type = 1;
		
		alarmnote = command[0];
		
		return s;
	}

}
