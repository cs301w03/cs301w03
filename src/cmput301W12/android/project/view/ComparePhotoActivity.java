package cmput301W12.android.project.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import cmput301W12.android.model.Photo;
import cmput301W12.android.project.R;
/**
 * Compare photo activity. This activity displays two photos with one on top of the other. The 
 * opacity of the front photo is adjusted by a slide bar
 * @author Tri Lai
 *
 */
public class ComparePhotoActivity extends Activity {
	public static final String BACKGROUND_PHOTO = "BACKGROUND_PHOTO";
	public static final String SURFACE_PHOTO = "SURFACE_PHOTO";
	
	private ImageView imageView;
	private SeekBar seekBarOpacity;

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
			Object backgroundObject = bundle.getSerializable(ComparePhotoActivity.BACKGROUND_PHOTO);
			Object surfaceObject = bundle.getSerializable(ComparePhotoActivity.SURFACE_PHOTO);
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
			}
			else
			{
				//Toast: Cannot load the images. Please press back and try again
				CharSequence text = "Cannot load the images. Please try again!";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(this, text, duration);
				toast.show();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}

	private void setupTransparentImage(Drawable background, Drawable surface)
	{
		imageView.setBackgroundDrawable(background);
		imageView.setImageDrawable(surface);
	}

	/**
	 * Set up the slide bar to adjust the opacity of the front image
	 */
	private void setupSeekBar()
	{
		seekBarOpacity.setEnabled(true);
		seekBarOpacity.setFocusable(true);
		seekBarOpacity.setClickable(true);

		// 255 is the maximum opacity of a drawable
		seekBarOpacity.setMax(255);		

		// Set the default opacity to 70
		seekBarOpacity.setProgress(70);
		imageView.setAlpha(70);

		seekBarOpacity.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				imageView.setAlpha(progress);
			}
		});
	}

}
