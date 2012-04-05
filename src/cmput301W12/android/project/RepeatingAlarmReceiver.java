package cmput301W12.android.project;

import cmput301W12.android.project.view.ProjectTwoActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;


public class RepeatingAlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{

		// TODO Auto-generated method stub
		Toast.makeText(context, "Repeating Alarm Received", Toast.LENGTH_SHORT).show();
		
		/*Intent downloader = new Intent(context, ProjectTwoActivity.class);
        
        context.startService(downloader);*/
		
		 
		 MediaPlayer mp = new MediaPlayer();

	}

}
