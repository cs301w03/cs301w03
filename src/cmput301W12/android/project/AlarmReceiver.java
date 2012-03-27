package cmput301W12.android.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{

		// TODO Auto-generated method stub
		Toast.makeText(context, "Alarm Received", Toast.LENGTH_SHORT).show();

		
	}

}
