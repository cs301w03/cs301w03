package cmput301W12.android.project;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * @author: Tri Lai 
 * @date: Mar 24, 2012
 * 
 * This is an activity that will let the users to compare two photos
 */
public class ComparePhotoActivity extends Activity {
	private ImageView imageView;
	private SeekBar seekBarOpacity;
	
	public static final String BACKGROUND = "BACKGROUND";
	public static final String SURFACE = "SURFACE";

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compare_photo);
		setTitle(R.string.app_name);
		
		imageView = (ImageView) findViewById(R.id.imageView1);
		seekBarOpacity = (SeekBar) findViewById(R.id.seekBarOpacity);

		Bundle bundle = getIntent().getExtras();
		
		try {
			Object backgroundObject = bundle.getSerializable(ComparePhotoActivity.BACKGROUND);
			Object surfaceObject = bundle.getSerializable(ComparePhotoActivity.SURFACE);
			if (backgroundObject != null && surfaceObject != null)
			{
				Photo backgroundPhoto = (Photo) backgroundObject;
				String location = backgroundPhoto.getLocation();
				Uri uri = Uri.parse(location);
				Drawable backgroundDrawable =  Drawable.createFromPath(uri.getPath());
				
				Photo surfacePhoto = (Photo) surfaceObject;
				location = surfacePhoto.getLocation();
				uri = Uri.parse(location);
				Drawable surfaceDrawable =  Drawable.createFromPath(uri.getPath());
				
				setupTransparentImage(backgroundDrawable,surfaceDrawable);
				setupSeekBar();
				//This is the last version
			}
			else
			{
				//Toast: Cannot load the images. Please press back and try again
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	private void setupTransparentImage()
	{
		//Test code
		Resources r = getResources();
		Drawable background = r.getDrawable(android.R.drawable.stat_sys_download_done);
		Drawable surface = r.getDrawable(android.R.drawable.stat_sys_phone_call_forward);

		imageView.setBackgroundDrawable(background);
		imageView.setImageDrawable(surface);
	}
	
	private void setupTransparentImage(Drawable background, Drawable surface)
	{
		imageView.setBackgroundDrawable(background);
		imageView.setImageDrawable(surface);
	}
	
	private void setupSeekBar()
	{
		seekBarOpacity.setEnabled(true);
		seekBarOpacity.setFocusable(true);
		seekBarOpacity.setClickable(true);

		seekBarOpacity.setMax(255);		
		
		seekBarOpacity.setProgress(70);
		imageView.setAlpha(70);
		
		seekBarOpacity.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				imageView.setAlpha(progress);
			}
		});
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
