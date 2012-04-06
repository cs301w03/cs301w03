package cmput301W12.android.project;

import java.sql.Timestamp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * The adapter for the list of alarms.
 * @author Tanvir Sajed
 *
 */
public class ReminderListArrayAdapter extends ArrayAdapter<Alarm> {

	private static final String REPEAT = "Repeat Every ";
	private static final String ONE_SHOT_ALARM = "One Shot Alarm";
	private final Context context;
	private final Alarm[] alarms;
	private String alarmnote;
	
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
		TextView reminderrepeat = (TextView) rowView.findViewById(R.id.reminder_repeat);
		
		if ( remindertext != null && reminderdate != null && reminderrepeat != null)
			if (alarms.length > 0)
				if (position < alarms.length)
				{
					reminderrepeat.setText(this.getFactoredString(alarms[position].getAlarmNote()));
					remindertext.setText("Text : " + alarmnote);
					
					Timestamp t = (alarms[position].getAlarmTime());
					reminderdate.setText("Timestamp : " + t.toString());

				}
		return rowView;
	}
	
	private String getFactoredString(String note) {
		
		if (note.charAt(note.length() - 1) == '5') {
			
			String[] command = note.split("~");
			alarmnote = command[0];
			
			return ONE_SHOT_ALARM;
		}
		
		String[] command = note.split("~");
		String s = REPEAT + command[1] + " " ;
		
		if(command[2].equals("0")){
			s = s + "Hour(s)";
		}
		
		if(command[2].equals("1")){
			s = s + "Day(s)";
		}
		
		if(command[2].equals("2")){
			s = s + "Week(s)";
		}
		
		if(command[2].equals("3")){
			s = s + "Month(s)";
		}
		
		if(command[2].equals("4")){
			s = s + "Year(s)";
		}
		
		alarmnote = command[0];
		
		return s;
	}

}
