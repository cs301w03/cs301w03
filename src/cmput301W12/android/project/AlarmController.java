package cmput301W12.android.project;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


public class AlarmController extends Activity implements FView<DbController>
{
	private static final String MEDIA_PLAYER = null;
	Toast mToast;
	MediaPlayer mp;
	Timestamp timestamp;
	EditText alarmtext;
	EditText alarmrepeat;
	Button alarmtime;
	Button alarmdate;
	Spinner repeat_spinner;
	
	private int alarm_type = 1 ;
	private int theYear = -1;
	private int theMonth = -1;
	private int theDay = -1;
	private int theHour = -1;
	private int theMinute = -1;
	private int repeat_type = 0;
	private int repeatMillisec = 60000;
	private int alarmId;
	
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.alarm_controller);
        	
        Button button = (Button)findViewById(R.id.create_alarm_button);
        button.setOnClickListener(createalarmListener);
        
        
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

        // Watch for button clicks.
        
        alarmtime = (Button)findViewById(R.id.timePicker);
        alarmdate = (Button)findViewById(R.id.datepicker);
        alarmtext = (EditText)findViewById(R.id.alarm_text_editview);
        alarmrepeat = (EditText)findViewById(R.id.alarm_number_editview);
        
        alarmtime.setOnClickListener(invokeTimePicker);
        alarmdate.setOnClickListener(invokeDatePicker);
        
        /*button = (Button)findViewById(R.id.start_repeating);
        button.setOnClickListener(mStartRepeatingListener);
        button = (Button)findViewById(R.id.stop_repeating);
        button.setOnClickListener(mStopRepeatingListener);*/
        
    }
    
    OnDateSetListener odsListener = new OnDateSetListener()
    {

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

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			
			theHour = hourOfDay;
			theMinute = minute;
			Calendar cal = Calendar.getInstance();
			
			cal.set(Calendar.HOUR, theHour);
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
        public void onClick(View v) {
            
        	if(theYear == -1 || theMonth == -1 || theDay == -1 || theHour == -1 || theMinute == -1) {
        		mToast = Toast.makeText(AlarmController.this, "Stopping Repeating Shots",
                        Toast.LENGTH_LONG);
                mToast.show();
        	}
        	
        	else if(alarmtext.getText().toString() == "") {
        		mToast = Toast.makeText(AlarmController.this, "Stopping Repeating Shots",
                        Toast.LENGTH_LONG);
                mToast.show();
        	}
        	
        	else {
        		
        		String s = getFactoredString(alarmtext.getText().toString());
        		
        		Alarm alarm = new Alarm(timestamp, s);
        		
        		FController controller =  SkinObserverApplication.getSkinObserverController(AlarmController.this);
        		alarm = controller.addAlarm(alarm);
        		alarmId = alarm.getAlarmId();
        		
        		
        		if(alarm_type == 0) {
					setonetimeAlarm();
				}
				
				else {
					setrepeatingAlarm();
				}
        		
        		
        		
        		finish();
        	}
        }
    };

    private OnClickListener invokeDatePicker = new OnClickListener() {
        public void onClick(View v) {
        
        	Calendar cal = Calendar.getInstance();
        	DatePickerDialog dpDialog = new DatePickerDialog(AlarmController.this,
        			odsListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 
        			cal.get(Calendar.DAY_OF_MONTH));
        			dpDialog.show();
        }
    };
    
    private OnClickListener invokeTimePicker = new OnClickListener() {
        public void onClick(View v) {
        
        	Calendar cal = Calendar.getInstance();
        	TimePickerDialog tpDialog = new TimePickerDialog(AlarmController.this,
        			otsListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
        			tpDialog.show();
        }
    };

    
    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          
          if (pos == 0) {
        	  alarm_type = 0;
        	  
        	  repeat_spinner.setVisibility(Spinner.INVISIBLE);
        	  alarmrepeat.setVisibility(EditText.INVISIBLE);
          }
          
          else if (pos == 1) {
        	  alarm_type = 1;
        	  
        	  repeat_spinner.setVisibility(Spinner.VISIBLE);
        	  alarmrepeat.setVisibility(EditText.VISIBLE);
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
        
        //PendingIntent pintent = 

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
        mToast = Toast.makeText(AlarmController.this, "Testing One Shot: " + (int)alarm_time,
                Toast.LENGTH_LONG);
        mToast.show();
		
	}

	private void setrepeatingAlarm() {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(this, RepeatingAlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this,
                alarmId, intent, 0);

        // We want the alarm to go off 30 seconds from now.
        //long firstTime = SystemClock.elapsedRealtime();
        //firstTime += 15*1000;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        
        long alarm_time = timestamp.getTime() - calendar.getTimeInMillis();
        calendar.add(Calendar.MILLISECOND, (int)alarm_time);
        
        mToast = Toast.makeText(AlarmController.this, "Testing Multiple Shot: " + (int)alarm_time,
                Toast.LENGTH_LONG);
        mToast.show();
        
        String s = alarmrepeat.getText().toString();
        int repeat = Integer.parseInt(s);
        repeat = repeat * repeatMillisec;
        

        // Schedule the alarm!
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), repeat, sender);

        // Tell the user about what we did.
        if (mToast != null) {
            mToast.cancel();
        }
        /*mToast = Toast.makeText(this, "Testing Repeating Shots",
                Toast.LENGTH_LONG);
        mToast.show();*/
		
	}
	
	private String getFactoredString(String note) {
	
		if(alarm_type == 0) {
			note = note.concat("0");
			return note;
		}
		
		else {
			note = note.concat("~");
			note = note.concat(alarmrepeat.getText().toString());
			note = note.concat("~");
			note = note + repeat_type;
			note = note.concat("~");
			
			return note;
		}
	}
	

	@Override
	public void update(DbController model) {
		// TODO Auto-generated method stub
		
	}
    
    

}
