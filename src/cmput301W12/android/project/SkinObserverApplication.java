package cmput301W12.android.project;

import android.app.Application;
import android.content.Context;

public class SkinObserverApplication extends Application {
	// Singleton
	transient private static DbControllerInterface dbController = null;

	public static DbControllerInterface getDbController(Context ctx) {
		if (dbController == null) {
			dbController = DbController.getDbController(ctx);
		}
		return dbController;
	}

}
