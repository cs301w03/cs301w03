package cmput301W12.android.project;

import android.app.Application;
import android.content.Context;

public class SkinObserverApplication extends Application {
	// Singleton
	transient private static FController skinObserverController = null;
//		new SkinObserverController(this);
	//transient private static FController skinObserverController = new SkinObserverController(this);
	
	public static FController getSkinObserverController(Context ctx) {
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