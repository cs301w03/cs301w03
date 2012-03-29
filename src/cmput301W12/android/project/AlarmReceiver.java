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


public class AlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{

		// TODO Auto-generated method stub
		Toast.makeText(context, "Alarm Received", Toast.LENGTH_SHORT).show();
		
		 
		 MediaPlayer mp = new MediaPlayer();
	        
		
	}

}
