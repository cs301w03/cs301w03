package cmput301W12.android.project;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;


public class OneTimeAlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{

		// TODO Auto-generated method stub
		Toast.makeText(context, "One Shot Alarm Received", Toast.LENGTH_SHORT).show();
		Uri defaultRingtoneUri = RingtoneManager.getValidRingtoneUri(context);	        
		Ringtone rtone = RingtoneManager.getRingtone(context, defaultRingtoneUri);
		
		if(rtone == null){
			Toast.makeText(context, "No such ringtone", Toast.LENGTH_SHORT).show();
		} else {
			rtone.play();
		}
		
	}

}
