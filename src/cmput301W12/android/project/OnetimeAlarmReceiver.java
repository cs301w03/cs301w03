package cmput301W12.android.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

	public class OnetimeAlarmReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
		}
		}

