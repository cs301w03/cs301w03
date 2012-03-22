import cmput301W12.android.project.*;
import android.test.ActivityInstrumentationTestCase2;

/* Author : Tanvir Sajed
 * Date : 16 - 03 - 2012
 * 
 * DatabaseTest.class ensures that database is working properly and the
 * DbAdapter is changing and updating the database as the application
 * intends it to.
 */


public class DatabaseTest extends ActivityInstrumentationTestCase2<T>
{
	public DatabaseTest() {
		
		super("cmput301W12.android.project.DbAdapter", DbAdapter.class);
	}
}
