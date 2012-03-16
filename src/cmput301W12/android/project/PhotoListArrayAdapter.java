package cmput301W12.android.project;

import java.net.URI;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PhotoListArrayAdapter extends ArrayAdapter<Photo> {
		private final Context context;
		private final Photo[] photo_array;
		static int i = 0;

		public PhotoListArrayAdapter(Context context, Photo[] photos) {
			super(context, R.layout.list_photo_view, photos);
			this.context = context;
			this.photo_array = photos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Log.d("Displaying photo", i + "");
			i++;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.list_photo_view, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.textViewPhotoLabel1);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewPhotoThumbnail);
			if ( textView != null && imageView != null)
				if (photo_array.length > 0)
					if (position < photo_array.length)
					{
						Log.d("Displaying photo.Textview", photo_array[position].getLocation());
						textView.setText(photo_array[position].getName());
						Uri imgUri = Uri.parse(photo_array[position].getLocation());
						imageView.setImageURI( imgUri );
					}
			return rowView;
		}
	}
