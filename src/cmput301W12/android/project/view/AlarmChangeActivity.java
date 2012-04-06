package cmput301W12.android.project.view;

import java.sql.Timestamp;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import cmput301W12.android.model.Alarm;
import cmput301W12.android.model.SkinObserverApplication;
import cmput301W12.android.project.R;
import cmput301W12.android.project.controller.FController;
import cmput301W12.android.project.view.helper.OneTimeAlarmReceiver;
import cmput301W12.android.project.view.helper.RepeatingAlarmReceiver;


public class AlarmChangeActivity extends Activity
{


	private static final String MEDIA_PLAYER = null;
	Toast mToast;
	Timestamp timestamp;
	TextView alarmRepeat_Info;
	Button alarmdelete;
	EditText alarmNote_editText;
	EditText alarmRepeatConstant_editText;
	Button alarmtime;
	Button alarmdate;
	Button alarmupdate;
	Button createalarm;
	Spinner alarmRepeat_Spinner;
	Spinner alarmType_spinner;

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

	/**
	 * This method overrides onCreate method of activity. This
	 * method is responsible for getting alarm object and setting
	 * alarmId from intent.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.alarm_controller_edit);

		Alarm alarm = (Alarm) getIntent().getSerializableExtra(RemindersListActivity.ALARM);        
		alarmId = alarm.getAlarmId();
		this.initializeResources();

		updateTimeDateDialogs(alarm.getAlarmTime(), alarm.getAlarmNote());
		this.setListeners();

	}
	
	/**
	 * Sets different listeners for different view objects in 
	 * alarm_layout.xml. The view objects will now respond to clicks
	 * and selections by users.
	 */
	private void setListeners()
	{

	    ArrayAdapter<CharSequence> r_adapter = ArrayAdapter.createFromResource(
                this, R.array.repeat_terms, android.R.layout.simple_spinner_item);
        r_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alarmRepeat_Spinner.setAdapter(r_adapter);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.alarm_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alarmType_spinner.setAdapter(adapter);
        
        alarmType_spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
		alarmRepeat_Spinner.setOnItemSelectedListener(new RepeatItemSelectedListener());
        
		alarmupdate.setOnClickListener(updatealarmListener);
        alarmtime.setOnClickListener(invokeTimePicker);
        alarmdate.setOnClickListener(invokeDatePicker);
        alarmdelete.setOnClickListener(deleteAlarmListener);
	}

	/**
	 * Initializes view objects by their id from generated R file of project
	 */
	private void initializeResources()
	{

		alarmType_spinner = (Spinner) findViewById(R.id.spinner);
		alarmtime = (Button)findViewById(R.id.timePicker);
        alarmdate = (Button)findViewById(R.id.datepicker);
		alarmdelete = (Button)findViewById(R.id.delete_alarm_button);
		alarmupdate = (Button) findViewById(R.id.update_alarm_button);
        alarmNote_editText = (EditText)findViewById(R.id.alarm_text_editview);
        alarmRepeatConstant_editText = (EditText)findViewById(R.id.alarm_number_editview);
        alarmRepeat_Spinner = (Spinner) findViewById(R.id.repeat_spinner);
		alarmRepeat_Info  = (TextView)findViewById(R.id.alarm_repeat_info);
	}
    
	/**
	 * This method updates all the fields of the timestamp 
	 * and editText alarmNote_editText, getting parameters
	 * t and s from alarm object that was received by the
	 * activity.
	 * @param t
	 * @param s
	 */
	private void updateTimeDateDialogs(Timestamp t, String s) {

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTimeInMillis(t.getTime());

		theYear = cal.get(Calendar.YEAR);
		theMonth = cal.get(Calendar.MONTH);
		theDay = cal.get(Calendar.DAY_OF_MONTH);
		theHour = cal.get(Calendar.HOUR);
		theMinute = cal.get(Calendar.MINUTE);

		alarmRepeat_Info.setText(this.getFactoredString(s));
		alarmNote_editText.setText(alarmnote); 
		timestamp = t;
	}

	private OnDateSetListener odsListener = new OnDateSetListener()
	{

    	/**
    	 * This method overrides the onDateSet method of OnDateSetListener.
    	 * When the user changes the date and clicks on set button of
    	 * a date dialog, all the fields of timestamp will be changed
    	 * according to the parameters specified by this method.
    	 * 
    	 */
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

		/**
		 * This method overrides the onTimeSet method of OnTimeSetListener.
		 * Once the user confirms the time selection based on a time
		 * dialog, the fields of timestamp will be updated according
		 * to the parameters in this method.
		 * 
		 */
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

		/**
		 * This method updates an alarm pertaining to a specific alarmId
		 * in the alarm table of the database. It stops the previous alarm
		 * for the alarmId, and creates a new one according to what the user has
		 * updated. 
		 */
		@Override
		public void onClick(View v)
		{

			// TODO Auto-generated method stub
			Calendar cal = Calendar.getInstance();
			
		    if((alarmNote_editText.getText().toString()).contentEquals("")) {
        		mToast = Toast.makeText(AlarmChangeActivity.this, "Stopping Repeating Shots",
                        Toast.LENGTH_LONG);
                mToast.show();
        	}
        	
        	else if (cal.getTimeInMillis() > timestamp.getTime()) {
				mToast = Toast.makeText(AlarmChangeActivity.this, timestamp.toString() ,
						Toast.LENGTH_LONG);
				mToast.show();
        	}
        	
        	else if(alarm_type == 1 && (alarmRepeatConstant_editText.getText().toString()).contentEquals("")) {
				mToast = Toast.makeText(AlarmChangeActivity.this, timestamp.toString() ,
						Toast.LENGTH_LONG);
				mToast.show();
        	}

			else {
				
				String s = setFactoredString(alarmNote_editText.getText().toString());

				FController skinObserverController = SkinObserverApplication.getSkinObserverController(AlarmChangeActivity.this);
				skinObserverController.updateAlarm(alarmId, timestamp, s);
				
				stopScheduledAlarm();

				if(alarm_type == 0) {
					setOneTimeAlarm();
				}

				else {
					setRepeatingAlarm();
				}

				mToast = Toast.makeText(AlarmChangeActivity.this, timestamp.toString() ,
						Toast.LENGTH_LONG);
				mToast.show();

				finish();
			}
		}

	};

	private OnClickListener invokeDatePicker = new OnClickListener() {
		
		/**
    	 * This method overrides the onClick method of OnClickListener.
    	 * The DatePicker Dialog is initialized for user to choose his
    	 * own date for alarm through the dialog.
    	 */
    	@Override
		public void onClick(View v) {

			DatePickerDialog dpDialog = new DatePickerDialog(AlarmChangeActivity.this,
					odsListener, theYear, theMonth, theDay);
			dpDialog.show();
		}
	};

	private OnClickListener invokeTimePicker = new OnClickListener() {
		
	 	/**
    	 * The method overrides the onClick method of OnClickListener.
    	 * The TimePicker Dialog is initialized for user to choose his
    	 * own specific time for alarm through the dailog.
    	 */
    	@Override
		public void onClick(View v) {

			TimePickerDialog tpDialog = new TimePickerDialog(AlarmChangeActivity.this,
					otsListener, theHour, theMinute, false);
			tpDialog.show();
		}
	};

	private OnClickListener deleteAlarmListener = new OnClickListener() {

		/**
		 * This method deletes a particular alarm from database
		 * and stops the alarm that was set for the particular
		 * alarmId.
		 */
		@Override
		public void onClick(View v)
		{

			// TODO Auto-generated method stub
			FController skinObserverController = SkinObserverApplication.getSkinObserverController(AlarmChangeActivity.this);
			skinObserverController.deleteAnAlarm(alarmId);
			stopScheduledAlarm();

			finish();

		}

	};

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		/**
    	 * This method overrides the onItemSelected method for OnItemSelectedListener.
    	 * It responds to the item selection of alarmType_spinner where user
    	 * can selected a one time alarm or a repeating alarm.
    	 */
    	@Override
		public void onItemSelected(AdapterView<?> parent,
				View view, int pos, long id) {

			if (pos == 0) {
				
				alarm_type = 0;
				
				alarmRepeat_Spinner.setVisibility(Spinner.INVISIBLE);
	        	alarmRepeatConstant_editText.setVisibility(EditText.INVISIBLE);
	        	
	        	//repeatinfo.setText("One Shot Alarm");
			}

			else if (pos == 1) {
				alarm_type = 1;
				
				alarmRepeat_Spinner.setVisibility(Spinner.VISIBLE);
	        	alarmRepeatConstant_editText.setVisibility(EditText.VISIBLE);
	        	
	        	alarmRepeat_Info.setText("Repeat Every : ");
			}
		}


		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
			
	}
	
	public class RepeatItemSelectedListener implements OnItemSelectedListener {

		/**
    	 * This method overrides the onItemSelected method for OnItemSelectedListener.
    	 * It responds to the item selection of alarmRepeat_spinner where user
    	 * can select one of five repeating constants from Month, Year, Day, Hour, Week.
    	 */
    	@Override
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


    /**
     * This method uses Alarm Manager to create a one shot alarm from a pendingIntent.
     * The alarm is sent to a BroadCastReceiver which plays the alarm when
     * the time for the alarm goes off, as set by the user.
     */
	private void setOneTimeAlarm() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this, OneTimeAlarmReceiver.class);

		intent.setAction(AlarmController.INTENT_ACTION_ONESHOT);
		intent.putExtra(AlarmController.NOTE, alarmNote_editText.getText().toString());

		PendingIntent sender = PendingIntent.getBroadcast(this,
				alarmId, intent, 0);
        

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        
        long alarm_time = timestamp.getTime() - calendar.getTimeInMillis();
        calendar.add(Calendar.MILLISECOND, (int)alarm_time);
       
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



	/**
     * This method uses Alarm Manager to create a repeating alarm from a pendingIntent.
     * The alarm is sent to a BroadCastReceiver which plays the alarm when
     * the time for the alarm goes off, as set by the user. After that the 
     * alarm repeats at regular intervals according to the repeat_type constant set 
     * by the user.
     */
	private void setRepeatingAlarm() {
		// TODO Auto-generated method stub


		Intent intent = new Intent(this, RepeatingAlarmReceiver.class);
		intent.setAction(AlarmController.INTENT_ACTION_REPEAT);
		intent.putExtra(AlarmController.NOTE, alarmNote_editText.getText().toString());
		
        PendingIntent sender = PendingIntent.getBroadcast(this,
                alarmId, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        
        long alarm_time = timestamp.getTime() - calendar.getTimeInMillis();
        
        firstTime += alarm_time;
        
        String s = alarmRepeatConstant_editText.getText().toString();
        float repeat = Float.parseFloat(s);
        repeat = repeat * repeatMillisec;
        

        // Schedule the alarm!
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				firstTime, (long)repeat, sender);

        // Tell the user about what we did.
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, "Testing Repeating Shots" + (int)alarm_time + " " + (long)repeat,
                Toast.LENGTH_LONG);
        mToast.show();

	}

	/**
	 * This method stops the scheduled alarm for a particular alarmId
	 */
	private void stopScheduledAlarm() {

		// Create the same intent, and thus a matching IntentSender, for
		// the one that was scheduled.
		Intent intent = new Intent(this, OneTimeAlarmReceiver.class);
		intent.setAction(AlarmController.INTENT_ACTION_ONESHOT);
		
		Intent intent2 = new Intent(this, RepeatingAlarmReceiver.class);
		intent2.setAction(AlarmController.INTENT_ACTION_REPEAT);
		
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
	
	/**
	 * This method changes the text for the alarm in a specific way
	 * to add information about its alarm_type and repeat_type since
	 * that information is not added to the database. The factored
	 * string is returned to be stored in the database and later
	 * extracted to get the actual text set by user.
	 * @param note
	 * @return String
	 */
	private String setFactoredString(String note) {
		
		if(alarm_type == 0) {
			note = note.concat("~");
			note = note.concat("5");
			return note;
		}
		
		else {
			note = note.concat("~");
			note = note.concat(alarmRepeatConstant_editText.getText().toString());
			note = note.concat("~");
			note = note + repeat_type;
			//note = note.concat("~");
			
			return note;
		}
	}
	
	/**
	 * This method creates the actual string that was set by the user
	 * when he wrote the text for the alarm. the string is stored in
	 * alarmnote. Confirmation of alarm_type is returned as a string.
	 * @param note
	 * @return
	 */
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
