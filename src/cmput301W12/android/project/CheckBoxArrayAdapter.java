package cmput301W12.android.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

	/**
	 * 
	 * @author romansky march 22nd
	 *
	 *	This tutorial was used to help setup this Adapter 
	 *  http://www.vogella.de/articles/AndroidListView/article.html#overview_listview
	 */
public class CheckBoxArrayAdapter extends ArrayAdapter<Container> {
	private final Context context;
	private final Container[] containers;
	View rowView = null;
	
	public CheckBoxArrayAdapter(Context context, Container[] containers) {
		super(context, R.layout.list_text_view, containers);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.containers = containers;
	}
	
	static class ViewHolder {
		protected TextView name;
		protected CheckBox ckBox;
	}
	

		
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		LayoutInflater inflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.list_checkbox_view, parent, false);
//		TextView textView = (TextView) rowView.findViewById(R.id.textLabel);
//		if ( textView != null)
//			if (containers.length > 0)
//				if (position < containers.length)
//				{
//					textView.setText(containers[position].getName());
//				}
//		return rowView;
//	}
//}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.list_checkbox_view, parent, false);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) rowView.findViewById(R.id.textLabel);
			viewHolder.ckBox = (CheckBox) rowView.findViewById(R.id.check);
			viewHolder.ckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						Container element = (Container) viewHolder.ckBox.getTag();
						element.setSelected(buttonView.isChecked());
					}
				});
			rowView.setTag(viewHolder);
			viewHolder.ckBox.setTag(containers[position]); 
			
		}
		else if ( convertView != null)
			if (containers.length > 0)
				if (position < containers.length)
				{
					rowView = convertView;
					((ViewHolder) rowView.getTag()).ckBox.setTag(containers[position]); //What am i doing?! lol
				}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.name.setText(containers[position].getName());
		holder.ckBox.setChecked(containers[position].isSelected());
		return rowView;
	}
	
}
