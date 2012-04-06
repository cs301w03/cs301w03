package cmput301W12.android.project;

import cmput301W12.android.project.view.ProjectTwoActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * RepeatingAlarmReceiver inherits from BroadcastReceiver class,
 * and it is responsible for receiving scheduled repeating alarms
 * and playing a ringtone for the user at the set time.
 * @author Tanvir Sajed
 *
 */
public class RepeatingAlarmReceiver extends BroadcastReceiver
{

	/**
	 * This method overrides the onReceive method of BroadCastReceiver.
	 * This method is called when an alarm is sent off. The Receiver receives
	 * the pendingIntent set up for the alarm. Here, a ringtone is generated 
	 * from mobile phones ringtones, and it is played as an alarm for some
	 * time. Application also gets started as soon as the alarm is received. This
	 * receiver receives repeating alarms only. The note for the alarmId
	 * that initiated the alarm, is received from the intent and that note
	 * is displayed in a toast for some time.
	 */
	@Override
	public void onReceive(Context context, Intent intent)
	{


		
		String note = (String) intent.getCharSequenceExtra(AlarmController.NOTE);
		
		Uri defaultRingtonUri = RingtoneManager.getValidRingtoneUri(context);
		Ringtone rtone = RingtoneManager.getRingtone(context, defaultRingtonUri);
		
		if (rtone == null) {
			Toast.makeText(context, "No ringtone detected", Toast.LENGTH_SHORT).show();
		}
		
		else {
			rtone.play();	        
		}
		
		Intent newintent = new Intent(context, ProjectTwoActivity.class);
		newintent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		for (int i=0; i < 2; i++){ 
			Toast.makeText(context, "Repeating Alarm Received\nNote : " + note, Toast.LENGTH_LONG).show(); 
		}
		
		context.startActivity(newintent);
	
	}
	
}
