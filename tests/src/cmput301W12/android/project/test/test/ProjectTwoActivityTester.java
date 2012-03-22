package cmput301W12.android.project.test.test;

import android.test.ActivityInstrumentationTestCase2;
import cmput301W12.android.project.view.ProjectTwoActivity;

/**
 * 
 * @author Stephen Romansky
 * Date: March 15th, 2012
 */


public class ProjectTwoActivityTester extends
        ActivityInstrumentationTestCase2<ProjectTwoActivity>
{

    public ProjectTwoActivityTester()
    {
        super("cmput301W12.android.project.view.ProjectTwoActivity", ProjectTwoActivity.class);
    }

    /* (non-Javadoc)
     * @see android.test.ActivityInstrumentationTestCase2#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {

        // TODO Auto-generated method stub
        super.setUp();
        fail("The group needs to finish implementing this!");
    }
    
    
    public void testPreconditions()
    {
        assertTrue(1 == 1);
    }
    
    /*
     * Add application use sequences
     */

    
}
