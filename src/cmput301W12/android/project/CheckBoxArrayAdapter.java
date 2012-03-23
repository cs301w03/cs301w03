package cmput301W12.android.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

	/**
	 * 
	 * @author romansky march 22nd
	 *
	 *	This tutorial was used to help setup this Adapter 
	 *  http://www.vogella.de/articles/AndroidListView/article.html#overview_listview
	 */
public class CheckBoxArrayAdapter extends ArrayAdapter<Group> {
	private final Context context;
	private final Group[] values;
	
	public CheckBoxArrayAdapter(Context context, Group[] values) {
		super(context, R.layout.list_text_view, values);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.values = values;
	}
	
	static class ViewHolder {
		protected TextView name;
		protected CheckBox ckBox;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_checkbox_view, parent, false);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) rowView.findViewById(R.id.textLabel);
			viewHolder.ckBox = (CheckBox) rowView.findViewById(R.id.check);
	//		viewHolder.ckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	//			
	//				@Override
	//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	//					// TODO Auto-generated method stub
	//					Group element = (Group) viewHolder.ckBox.getTag();
	//					element. .setSelected(buttonView.isChecked());
	//				}
	//			});
		}
		else if ( convertView != null)
			if (values.length > 0)
				if (position < values.length)
				{
					textView.setText(values[position].getName());
				}

		return rowView;
	}
	
}
