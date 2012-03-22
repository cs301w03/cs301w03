package cmput301W12.android.project;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class ComparePhotoActivity extends Activity {
	private ImageView imageView;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compare_photo);
		setTitle(R.string.app_name);
		
		
		Resources r = getResources();
		Drawable[] layers = new Drawable[2];
		Drawable layer = r.getDrawable(android.R.drawable.stat_sys_phone_call_forward);
		layer.setBounds(0, 0, 50, 50);
		layer.setAlpha(255);
		layers[0] = layer;
		Drawable layer2 = r.getDrawable(android.R.drawable.stat_sys_download_done);
		layer2.setBounds(25, 25, 60, 60);
		layer2.setAlpha(30);
		layers[1] = layer2;
		LayerDrawable layers2 = new LayerDrawable(layers);
		imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageDrawable(layers2);
		imageView.setAlpha(255);

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
}
