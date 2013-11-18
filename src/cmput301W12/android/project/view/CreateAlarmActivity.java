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
import android.widget.TimePicker;
import android.widget.Toast;
import cmput301W12.android.model.Alarm;
import cmput301W12.android.model.DatabaseModel;
import cmput301W12.android.model.SkinObserverApplication;
import cmput301W12.android.project.R;
import cmput301W12.android.project.controller.FController;
import cmput301W12.android.project.view.helper.OneTimeAlarmReceiver;
import cmput301W12.android.project.view.helper.RepeatingAlarmReceiver;

/**
 * AlarmController class is an inherited Activity class, responsible
 * for creating alarms and adding them to the database.
 * @author Tanvir Sajed
 *
 */
public class CreateAlarmActivity extends Activity implements FView<DatabaseModel>
{

	public static final String NOTE ="note";
	public static final String INTENT_ACTION_REPEAT = "cmput301W12.android.project.CUSTOM_INTENT_REPEAT";
	public static final String INTENT_ACTION_ONESHOT = "cmput301W12.android.project.CUSTOM_INTENT_ONESHOT";
	Toast mToast;
	Timestamp timestamp;
	EditText alarmNote_editText;
	EditText alarmRepeatConstant_editText;
	Button alarmtime;
	Button alarmdate;
	Button createalarm;
	Spinner alarmRepeat_Spinner;
	Spinner alarmType_spinner;
	
	private int alarm_type = 1 ;
	private int theYear = -1;
	private int theMonth = -1;
	private int theDay = -1;
	private int theHour = -1;
	private int theMinute = -1;
	private int repeat_type = 0;
	private float repeatMillisec = 3600000;
	private int alarmId;
	
	/**
	 * Activity method when activity is created.
	 * Initializes resources and sets listeners for
	 * activity to continue
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.alarm_controller);
        
        initializeResources();
        setListeners();
 
        
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
        
        alarmtime.setOnClickListener(invokeTimePicker);
        alarmdate.setOnClickListener(invokeDatePicker);
        createalarm.setOnClickListener(createalarmListener);
	}

	/**
	 * Initializes view objects by their id from generated R file of project
	 */
	private void initializeResources()
	{

		alarmType_spinner = (Spinner) findViewById(R.id.spinner);
		alarmtime = (Button)findViewById(R.id.timePicker);
        alarmdate = (Button)findViewById(R.id.datepicker);
        createalarm = (Button)findViewById(R.id.create_alarm_button);
        alarmNote_editText = (EditText)findViewById(R.id.alarm_text_editview);
        alarmRepeatConstant_editText = (EditText)findViewById(R.id.alarm_number_editview);
        alarmRepeat_Spinner = (Spinner) findViewById(R.id.repeat_spinner);
	}
    
    OnDateSetListener odsListener = new OnDateSetListener()
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
			
			cal.set(Calendar.YEAR, theYear);
			cal.set(Calendar.DAY_OF_MONTH, theDay);
			cal.set(Calendar.MONTH, theMonth);
			cal.set(Calendar.SECOND, 0);
			
			if(theHour != -1) {
				cal.set(Calendar.HOUR, theHour);
			}
			
			if(theMinute != -1) {
				cal.set(Calendar.MINUTE, theMinute);
			}
			
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
			
			cal.set(Calendar.HOUR_OF_DAY, theHour);
			cal.set(Calendar.MINUTE, theMinute);
			cal.set(Calendar.SECOND, 0);
			
			
			if(theYear != -1) {
				cal.set(Calendar.YEAR, theYear);
			}
			
			if(theDay != -1) {
				cal.set(Calendar.DAY_OF_MONTH, theDay);
			}
			
			if(theMonth != -1) {
				cal.set(Calendar.MONTH, theMonth);
			}
			
			long time = cal.getTimeInMillis();
			
			timestamp = new Timestamp(time);
		}

		
    	
    };
    

    private OnClickListener createalarmListener = new OnClickListener() {
    	
    	/**
    	 * This method overrides the onClick method of OnClickListener.
    	 * It creates a new alarm object and adds it to the alarm table
    	 * in the database, when the fields of timestamp and the editTextfield
    	 * all have valid entries. A new alarm will be created for the unique
    	 * alarmId for the new alarm generated in database.
    	 * 
    	 */     	 
    	@Override
        public void onClick(View v) {
            
        	Calendar cal = Calendar.getInstance();
        	
        	if(theYear == -1 || theMonth == -1 || theDay == -1 || theHour == -1 || theMinute == -1) {
        		mToast = Toast.makeText(CreateAlarmActivity.this, "You have not set every parameters of Alarm",
                        Toast.LENGTH_SHORT);
                mToast.show();
        	}
        	
        	else if((alarmNote_editText.getText().toString()).contentEquals("")) {
        		mToast = Toast.makeText(CreateAlarmActivity.this, "You have not set every parameters of Alarm",
                        Toast.LENGTH_SHORT);
                mToast.show();
        	}
        	
        	else if (cal.getTimeInMillis() > timestamp.getTime()) {
				mToast = Toast.makeText(CreateAlarmActivity.this, "The current time is ahead of Alarm time. Please select a future Alarm Time.",
						Toast.LENGTH_SHORT);
				mToast.show();
        	}
        	
        	else if(alarm_type == 1 && (alarmRepeatConstant_editText.getText().toString()).contentEquals("")) {
				mToast = Toast.makeText(CreateAlarmActivity.this, "You have not set every parameters of Alarm" ,
						Toast.LENGTH_SHORT);
				mToast.show();
        	}
        	
        	else {
        		
        		String s = setFactoredString(alarmNote_editText.getText().toString());
        		
        		Alarm alarm = new Alarm(timestamp, s);
        		
        		FController controller =  SkinObserverApplication.getSkinObserverController(CreateAlarmActivity.this);
        		alarm = controller.addAlarm(alarm);
        		alarmId = alarm.getAlarmId();
        		
        		
        		if(alarm_type == 0) {
					setOneTimeAlarm();
				}
				
				else {
					setRepeatingAlarm();
				}
        		       		
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
        
        	Calendar cal = Calendar.getInstance();
        	DatePickerDialog dpDialog = new DatePickerDialog(CreateAlarmActivity.this,
        			odsListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 
        			cal.get(Calendar.DAY_OF_MONTH));
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
        
        	Calendar cal = Calendar.getInstance();
        	TimePickerDialog tpDialog = new TimePickerDialog(CreateAlarmActivity.this,
        			otsListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
        			tpDialog.show();
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
        	  
          }
          
          else if (pos == 1) {
        	  alarm_type = 1;
        	  
        	  alarmRepeat_Spinner.setVisibility(Spinner.VISIBLE);
        	  alarmRepeatConstant_editText.setVisibility(EditText.VISIBLE);
        	  
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
        	  repeatMillisec = 3600000;
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
		intent.setAction(CreateAlarmActivity.INTENT_ACTION_ONESHOT);
		intent.putExtra(NOTE, alarmNote_editText.getText().toString());

        PendingIntent sender = PendingIntent.getBroadcast(this,
                alarmId, intent, 0);
        

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        
        long alarm_time = timestamp.getTime() - calendar.getTimeInMillis();
        calendar.add(Calendar.MILLISECOND, (int)alarm_time);
       
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(CreateAlarmActivity.this, "One Time Alarm Created",
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
		intent.setAction(CreateAlarmActivity.INTENT_ACTION_REPEAT);
		intent.putExtra(NOTE, alarmNote_editText.getText().toString());
		
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
        mToast = Toast.makeText(this, "Repeating Alarm Created",
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
			
			return note;
		}
	}
			

	@Override
	public void update(DatabaseModel model) {
		// TODO Auto-generated method stub
		
	}
    
    

}
