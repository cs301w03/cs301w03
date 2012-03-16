package cmput301W12.android.project;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class GroupArrayAdapter extends ArrayAdapter<Group> {
		private final Context context;
		private final Group[] values;

		public GroupArrayAdapter(Context context, Group[] values) {
			super(context, R.layout.list_text_view, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_text_view, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.textLabel);
			if ( textView != null)
			if (values.length > 0)
			if (position < values.length)
			{
				textView.setText(values[position].getName());
			}
			return rowView;
		}
	}
