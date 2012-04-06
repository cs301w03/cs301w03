package cmput301W12.android.project.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import cmput301W12.android.model.Photo;
import cmput301W12.android.project.R;
import cmput301W12.android.project.view.helper.CameraPreview;

/**
 * Customized camera with a transparent layer.
 * This camera provide better method to capture consistent photo
 * 
 * Reuse the camera here:
 * http://developer.android.com/reference/android/hardware/Camera.html
 * @author Tri Lai
 *
 */
public class SpecialTakePhotoActivity extends Activity {

	public static final String TRANSPARENT_LAYER = "TRANSPARENT_LAYER";
	
	private Camera camera;
    private CameraPreview cameraPreview;
    private Photo transparentLayer = null;
    
    private static final String TAG = "SpecialTakePhotoActivity";

    protected PictureCallback pictureCallback = new PictureCallback() {
    	
    	
    	
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
        	Log.d("special callback","back back");
        	Uri photoURI = null;
        	
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(MediaStore.EXTRA_OUTPUT)){
            	photoURI = (Uri) bundle.get(MediaStore.EXTRA_OUTPUT);
            	Log.d("specialtakephoto", photoURI.getPath());
            }

            try {
                FileOutputStream fos = new FileOutputStream(photoURI.getPath());
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
            
            setResult(RESULT_OK);
            finish();
        }
    };
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_a_photo);

        
        Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			if (bundle.containsKey(TRANSPARENT_LAYER))
				transparentLayer = (Photo) bundle.get(TRANSPARENT_LAYER);
		}
		
		Uri uri = Uri.parse(transparentLayer.getLocation());
		Drawable transparentLayerDrawable = Drawable.createFromPath(uri.getPath());
        // Create an instance of Camera
        camera = getCameraInstance();
        
        // Create our Preview view and set it as the content of our activity.
        cameraPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

        //Drawable transparentLayer = new PaintDrawable(Color.GREEN);
        
        transparentLayerDrawable.setAlpha(100);
        transparentLayerDrawable.setBounds(preview.getLeft(), 
				preview.getTop(), 
				preview.getRight(), 
				preview.getBottom());
        
        preview.setMinimumHeight(preview.getWidth()*3/4);
        preview.setForeground(transparentLayerDrawable);
        preview.addView(cameraPreview);
        
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get an image from the camera
                    camera.takePicture(null, null, pictureCallback);
                }
            }
        );
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }


    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

}
