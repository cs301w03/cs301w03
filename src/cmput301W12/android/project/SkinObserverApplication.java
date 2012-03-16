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
//		new SkinObserverController(this);
	//transient private static FController skinObserverController = new SkinObserverController(this);
	
	public static FController getSkinObserverController(Context ctx) {
//		Log.d("FController", "trail part2");
		if (skinObserverController == null)
		{
			skinObserverController = new SkinObserverController(ctx);
		}
		return skinObserverController;
	}

}


//package cmput301W12.android.project;
//
//import android.app.Application;
//import android.content.Context;
//
//public class SkinObserverApplication extends Application {
//	// Singleton
//	transient private static FController skinObserverController = new SkinObserverController(this);
//	//transient private static FController skinObserverController = new SkinObserverController(this);
//	
//	public static FController getSkinObserverController() {
//		return skinObserverController;
//	}
//
//}