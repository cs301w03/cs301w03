package cmput301W12.android.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context arg0, Intent arg1)
	{

		// TODO Auto-generated method stub
		Toast.makeText(context, R.string.repeating_received, Toast.LENGTH_SHORT).show();

		
	}

}
