
package cmput301W12.android.project;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author: Hieu Ngo 
 * @date: Mar 16, 2012
 * 
 * This is an activity that will let the users to compare their photos
 */
public class ComparePhotos extends Activity
{

	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.photoviewlist_activity);
	}
}
