package cmput301W12.android.project.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import cmput301W12.android.model.Photo;
import cmput301W12.android.project.R;

/**
 * Activity for comparing two photos side-by-side
 * @author hieungo
 *
 */
public class ComparePhotosActivity extends Activity
{
	private Photo photoOne;
	private Photo photoTwo;
	
	private ImageView imageOne;
	private ImageView imageTwo;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * 
     * written by romansky @ April 1st
     * extracts two serializable objects "PhotoOne" and "PhotoTwo" from the intents passed to this activity.
     * It then displays the intents in the ez_compare view.
     * 
     * 
     * 
     * 	The following code should allow you to call compare photos in your classes.
   		Intent comparePhotos = new Intent(this, ComparePhotosActivity.class);
		comparePhotos.putExtra("PhotoOne", <yourFirstPhoto>);
		comparePhotos.putExtra("PhotoTwo", <yourSecondPhoto>);
		startActivity(comparePhotos);
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ez_compare);
        
        photoOne = (Photo) getIntent().getSerializableExtra("PhotoOne");
        photoTwo = (Photo) getIntent().getSerializableExtra("PhotoTwo");
        
		imageOne = (ImageView) this.findViewById(R.id.ez_photo_one);
		Uri uriOne = Uri.parse(photoOne.getLocation());
		Bitmap bitmapOne = BitmapFactory.decodeFile(uriOne.getPath());
		imageOne.setImageBitmap(bitmapOne);
        
		imageTwo = (ImageView) this.findViewById(R.id.ez_photo_two);
		Uri uriTwo = Uri.parse(photoTwo.getLocation());
		Bitmap bitmapTwo = BitmapFactory.decodeFile(uriTwo.getPath());
		imageTwo.setImageBitmap(bitmapTwo);
		
    }
    
}
