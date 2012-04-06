package cmput301W12.android.project;

import cmput301W12.android.project.view.ProjectTwoActivity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;


public class RepeatingAlarmReceiver extends BroadcastReceiver
{


	@Override
	public void onReceive(Context context, Intent intent)
	{

		// TODO Auto-generated method stub
		
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
