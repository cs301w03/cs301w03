package cmput301W12.android.project.view.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;
import cmput301W12.android.project.view.ProjectTwoActivity;


public class RepeatingAlarmReceiver extends BroadcastReceiver
{

	/**
	 * This method overrides the onReceive method of BroadCastReceiver.
	 * This method is called when an alarm is sent off. The Receiver receives
	 * the pendingIntent set up for the alarm. Here, a ringtone is generated 
	 * from mobile phones ringtones, and it is played as an alarm for some
	 * time. This receiver receives repeating alarms only. The note for the alarmId
	 * that initiated the alarm, is received from the intent and that note
	 * is displayed in a toast for some time.
	 */
	@Override
	public void onReceive(Context context, Intent intent)
	{
		// TODO Auto-generated method stub


		Uri defaultRingtoneUri = RingtoneManager.getValidRingtoneUri(context);	        
		Ringtone rtone = RingtoneManager.getRingtone(context, defaultRingtoneUri);

		if(rtone == null){
			Toast.makeText(context, "No such ringtone", Toast.LENGTH_SHORT).show();
		} else {
			rtone.play();
		}

		for (int i=0; i < 6; i++){ 
			Toast.makeText(context, "Repeating Alarm Received\nYou need to take new photos of your skin", Toast.LENGTH_LONG).show(); 
		}
		
		startApplication(context);
	}
	/**
	 * Starts the Skin Observer Application whenever the alarm is
	 * received by the receiver
	 * @param context
	 */
	private void startApplication(Context context)
	{

		Intent newintent = new Intent(context, ProjectTwoActivity.class);
		newintent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		context.startActivity(newintent);
	}

}
