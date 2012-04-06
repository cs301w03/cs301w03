package cmput301W12.android.project.view;


import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import cmput301W12.android.project.FController;
import cmput301W12.android.project.Group;
import cmput301W12.android.project.Photo;
import cmput301W12.android.project.SkinCondition;
import cmput301W12.android.project.SkinObserverApplication;

/**
 * The TakeAPhotoActivity allows the user to capture images. When an image is captured a time stamp
 * is created. If the user chooses to keep the photo, the time stamp is then associated with the
 * saved image.
 * 
 * Images are stored in the following directory of the android phone.
 *      /Pictures/SKINOBSERVER
 *      
 * @author Stephen Romansky
 *
 */

/*
 * Useful links and tutorials:
 *      For implementing a camera activity
 *      http://developer.android.com/guide/topics/media/camera.html
 *      http://developer.android.com/reference/android/hardware/Camera.html
 *      
 *      The following are for storing media
 *      http://developer.android.com/guide/topics/media/camera.html#saving-media
 */

public class TakeAPhotoActivity extends Activity
{
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int GET_IMAGE_INFO_REQUEST_CODE = 1250;

	private static Uri photoUri;
	private static Timestamp time;

	//TAG METHODS
	Set<Integer> groups = null;
	Set<Integer> skinConditions = null;
	/* Make a photo object here */

	//MAKE PHOTO OBJECT

	String name = null;
	Photo newestPhoto = null;


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.take_a_photo);

		getPhoto(); // Calls camera activity and gets the user to take a picture.

	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) 
		{
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				/*if the program gets here, a picture was succesfully captured, call the method to tag the photo here */

				/* Construct a photo object from data */
				newestPhoto = new Photo(photoUri.toString(), time, name, groups, skinConditions, null);
				getTags(); // Ask the user to input data about the photo
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				Toast.makeText(this, "The user canceled capturing a photo\n", Toast.LENGTH_LONG).show();
				Log.d("SKINOBSERVER", "User canceled taking a photo.");
			} else {
				// Image capture failed, advise user
				Log.d("SKINOBSERVER", "There was an error capturing the photo.");
			}
		}
		else if (requestCode == GET_IMAGE_INFO_REQUEST_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				if (data != null ) 
				{
					Bundle PhotoInfo = data.getExtras();
					newestPhoto = (Photo) PhotoInfo.getSerializable("Photo");
				}
			}
			//put into db
			FController controller = SkinObserverApplication.getSkinObserverController(this);
			controller.addPhoto(newestPhoto);
		}
	}

	private void getTags() 
	{
		/* send the photo object to Hieu's DB */
		Intent getPhotoTags = new Intent(TakeAPhotoActivity.this, PhotoEditorActivity.class);
		getPhotoTags.putExtra("Photo", newestPhoto);
		startActivityForResult(getPhotoTags, GET_IMAGE_INFO_REQUEST_CODE);
	}



	private void getPhoto()
	{
		/*
		 * This calls an existing camera application that returns an intent with a URI for our
		 * group to manipulate.
		 * I think I can code a custom camera for the project. For the prototype this sample
		 * camera should be sufficient.
		 */

		//Create an intent to take a picture
		Intent sCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		photoUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);     // Create a file to store the image
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm"); 
		Date dDate = new Date();        // get a timestamp too!
		time = new Timestamp(dDate.getTime());

		sCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);    // Sets the image file name


		startActivityForResult(sCamera, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE); // Calls the camera application to get a Photo
	}




	/* Create a file Uri for saving an image, this has been taken from the Android Camera tutorial. */
	private static Uri getOutputMediaFileUri(int type)
	{
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/* Create a File for saving an image, this has been taken from the android Camera tutorial and heavily modified for
	 * Android 2.1
	 */
	private static File getOutputMediaFile(int type){
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but all we need
			//  to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}




		//We are using API level 7, we cannot use the lovely DIRECTORY_PICTURES variable.
		//We store images in pictures/SKINOBSERVER
		File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures/SKINOBSERVER");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist

		if (! mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				Log.d("SKINOBSERVER", " " + mediaStorageDir);
				Log.d("SKINOBSERVER", "failed to create directory");
				return null;
			}
		}

		Log.d("SKINOBSERVER", " " + mediaStorageDir);

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_"+ timeStamp + ".jpg");

			Log.d("SKINOBSERVER", " " + " " + mediaFile);
		} else {
			Log.d("SKINOBSERVER", "unknown type in creating file");
			return null;
		}

		return mediaFile;
	}

}




