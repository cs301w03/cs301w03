package cmput301W12.android.project;

import android.app.Application;
import android.content.Context;
import android.util.Log;
/**
 * 
 * @author Hieu Ngo
 * @date Mar 14, 2012
 * 
 * Application to initialize the state of the whole system
 */
public class SkinObserverApplication extends Application {
	// Singleton
	transient private static FController skinObserverController = null;

	public static FController getSkinObserverController(Context ctx) {
		if (skinObserverController == null)
		{
			skinObserverController = new SkinObserverController(ctx);
		}
		return skinObserverController;
	}

}
