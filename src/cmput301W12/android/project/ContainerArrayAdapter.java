package cmput301W12.android.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ContainerArrayAdapter extends ArrayAdapter<Container> {
		private final Context context;
		private final Container[] containers;

		public ContainerArrayAdapter(Context context, Container[] containers) {
			super(context, R.layout.list_text_view);
			this.context = context;
			this.containers = containers;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_photo_view, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.textLabel);
			if ( textView != null)
				if (containers.length > 0)
					if (position < containers.length)
					{
						textView.setText(containers[position].getName());
					}
			return rowView;
		}
	}
