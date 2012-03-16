package cmput301W12.android.project;

/* ContainerArrayAdapter.class is an adapter that connects containers
 * to a row view in a list view. It updates the information in containers
 * with a layout template and then populates the row with the specific container that
 * has been passed. It will be used in a listview where list items will be updated
 * according to the layout presented here. Here the textview in the layout
 * will receive the name of the container, and that the name will be shown as a
 * list item of the listview that uses this adapter.
 */

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
