package cmput301W12.android.project;

import java.sql.Timestamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReminderListArrayAdapter extends ArrayAdapter<Alarm> {

	private final Context context;
	private final Alarm[] alarms;
	
	public ReminderListArrayAdapter(Context context, Alarm[] objects) {
		super(context, R.layout.list_reminder_view, objects);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.alarms = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_reminder_view, parent, false);
		
		TextView remindertext = (TextView) rowView.findViewById(R.id.reminder_text);
		TextView reminderdate = (TextView) rowView.findViewById(R.id.reminder_timestamp);
		
		if ( remindertext != null && reminderdate != null)
			if (alarms.length > 0)
				if (position < alarms.length)
				{
					remindertext.setText(alarms[position].getAlarmNote());
					
					Timestamp t = (alarms[position].getAlarmTime());
					reminderdate.setText(t.toString());

				}
		return rowView;
	}

}
