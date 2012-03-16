package cmput301W12.android.project.test.test;

//import android.R;
import java.sql.Timestamp;

import junit.framework.Assert;

import android.content.Intent;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import cmput301W12.android.project.view.TakeAPhotoActivity;

/**
 * This is a unit test for the take a photo activity. It covers the following tests.
 *      --
 *
 * 
 * @author Stephen Romansky
 * Date: March 15th, 2012
 */

public class takeAPhotoTest extends ActivityInstrumentationTestCase2<TakeAPhotoActivity>
{
    private TakeAPhotoActivity testThis;
    private static Uri testPhotoUri;
    private static Timestamp testTime;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int SUCCESFUL_IMAGE_CAPTURE = -1;
    
    //calls the super constructor with parameters that tell the framework what Android application should be tested. 
    public takeAPhotoTest()
    {
        super("cmput301W12.android.project.view.TakeAPhotoActivity", TakeAPhotoActivity.class);
    }

    
    
    
    /* (non-Javadoc)
     * use setUp() to initialize variables and prepare the test environment.
     * 
     * @see android.test.ActivityInstrumentationTestCase2#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        setActivityInitialTouchMode(false);
        
        testThis = getActivity();
        testPhotoUri = null;
        testTime = null;
        

        
        
    }

    
    public void testPreConditions()
    {
       
        //assertNotNull(testThis);
        assertNull(testPhotoUri);
        assertNull(testTime);
        fail("There is an error in this test case..");
    }


//    public void testOnCreateBundle()
//    {
//
//        fail("Not yet implemented");
//    }

    public void testOnActivityResult(int requestCode, int resultCode, Intent data)
    {
        assertEquals(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE, requestCode);
        assertEquals(SUCCESFUL_IMAGE_CAPTURE, resultCode);
        assertNull(data);
    }

}
