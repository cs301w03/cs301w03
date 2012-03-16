package cmput301W12.android.project;

/* ComparePhotos.class will be used later to compare between photos that the user
 * has already selected before in some other activities. One of them is 
 * PhotoViewActivity where you can choose to compare photos from the context menu.
 * The user will see both the photos and will be able to compare between them. The 
 * choice of two is best for the user to compare photos effectively between two photos.
 */

import android.app.Activity;
import android.os.Bundle;


public class ComparePhotos extends Activity
{

	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.photoviewlist_activity);
	}
}
