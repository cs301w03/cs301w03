package cmput301W12.android.project;

/* PhotoListArrayAdapter.class is responsible for connecting the information
 * contained in a list item with the layout designed for the list item. The layout
 * defines the way the information will be displayed in each row of the list. The
 * information or data objects that are passed to this object, will be used by this
 * class to fill the rows of a listview. Here photo objects are passed, and the image
 * from a photo object is used to fill the imageview in the layout template, and the
 * textview of the layout template is filled with the name of the photo/photos. These
 * two information will be shown in each list item of the list view.
 */

import java.net.URI;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PhotoListArrayAdapter extends ArrayAdapter<Photo> {
		private final Context context;
		private final Photo[] photo_array;

		public PhotoListArrayAdapter(Context context, Photo[] photos) {
			super(context, R.layout.list_photo_view);
			this.context = context;
			this.photo_array = photos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_photo_view, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.textViewPhotoLabel1);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewPhotoThumbnail);
			if ( textView != null && imageView != null)
				if (photo_array.length > 0)
					if (position < photo_array.length)
					{
						textView.setText(photo_array[position].getName());
						Uri imgUri = Uri.parse(photo_array[position].getLocation());
						imageView.setImageURI( imgUri );
					}
			return rowView;
		}
	}
