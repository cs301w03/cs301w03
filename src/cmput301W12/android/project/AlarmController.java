package cmput301W12.android.project;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class AlarmController extends Activity
{
	Toast mToast;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alarm_controller);

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.one_shot);
        button.setOnClickListener(mOneShotListener);
        button = (Button)findViewById(R.id.start_repeating);
        button.setOnClickListener(mStartRepeatingListener);
        button = (Button)findViewById(R.id.stop_repeating);
        button.setOnClickListener(mStopRepeatingListener);
    }

    private OnClickListener mOneShotListener = new OnClickListener() {
        public void onClick(View v) {
            // When the alarm goes off, we want to broadcast an Intent to our
            // BroadcastReceiver.  Here we make an Intent with an explicit class
            // name to have our own receiver (which has been published in
            // AndroidManifest.xml) instantiated and called, and then create an
            // IntentSender to have the intent executed as a broadcast.
            Intent intent = new Intent(AlarmController.this, OneShotAlarm.class);
            PendingIntent sender = PendingIntent.getBroadcast(AlarmController.this,
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
            mToast = Toast.makeText(AlarmController.this, R.string.one_shot_scheduled,
                    Toast.LENGTH_LONG);
            mToast.show();
        }
    };

    private OnClickListener mStartRepeatingListener = new OnClickListener() {
        public void onClick(View v) {
            // When the alarm goes off, we want to broadcast an Intent to our
            // BroadcastReceiver.  Here we make an Intent with an explicit class
            // name to have our own receiver (which has been published in
            // AndroidManifest.xml) instantiated and called, and then create an
            // IntentSender to have the intent executed as a broadcast.
            // Note that unlike above, this IntentSender is configured to
            // allow itself to be sent multiple times.
            Intent intent = new Intent(AlarmController.this, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(AlarmController.this,
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
            mToast = Toast.makeText(AlarmController.this, R.string.repeating_scheduled,
                    Toast.LENGTH_LONG);
            mToast.show();
        }
    };

    private OnClickListener mStopRepeatingListener = new OnClickListener() {
        public void onClick(View v) {
            // Create the same intent, and thus a matching IntentSender, for
            // the one that was scheduled.
            Intent intent = new Intent(AlarmController.this, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(AlarmController.this,
                    0, intent, 0);

            // And cancel the alarm.
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.cancel(sender);

            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(AlarmController.this, R.string.repeating_unscheduled,
                    Toast.LENGTH_LONG);
            mToast.show();
        }
    };

}
