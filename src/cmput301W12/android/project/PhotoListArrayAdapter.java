package cmput301W12.android.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author MinhTri
 * @date Mar 16, 2012
 * 
 * Extends ArrayAdaper to display a list of rows,
 * each consists a photo thumbnail and name
 */
public class PhotoListArrayAdapter extends ArrayAdapter<Photo> {
	private final Context context;
	private final Photo[] photoArray;

	public PhotoListArrayAdapter(Context context, Photo[] photos) {
		super(context, R.layout.list_photo_view, photos);
		this.context = context;
		this.photoArray = photos;
	}

	//This method is called by the adapter to set the View of a row in a ListView
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_photo_view, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.textViewPhotoLabel1);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewPhotoThumbnail);

		if ( textView != null && imageView != null)
			if (photoArray.length > 0)
				if (position < photoArray.length)
				{
					textView.setText(photoArray[position].getName());
					String location = photoArray[position].getLocation();
					Uri uri = Uri.parse(location);
					Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
					imageView.setImageBitmap(bitmap);

				}
		return rowView;
	}
}
