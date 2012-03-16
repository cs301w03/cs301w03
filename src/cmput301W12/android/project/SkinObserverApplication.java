package cmput301W12.android.project;

import android.app.Application;
import android.content.Context;

public class SkinObserverApplication extends Application {
	// Singleton
	transient private static FController skinObserverController = null;

	public static FController getSkinObserverController(Context ctx) {
		if (skinObserverController == null) {
			skinObserverController = new SkinObserverController(ctx);
		}
		return skinObserverController;
	}

}
