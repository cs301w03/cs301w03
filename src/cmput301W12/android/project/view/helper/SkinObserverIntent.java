package cmput301W12.android.project.view.helper;

/**
 * @author MinhTri
 * @date Mar 16, 2012
 * 
 * Provide system's custom static intent message
 */
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author hieungo
 * Custom Intent for the application
 */
public class SkinObserverIntent extends Intent {

	public static final String ACTION_VIEW_GROUP_LIST = "VIEW_GROUP";
	public static final String ACTION_VIEW_SKIN_CONDITION_LIST = "VIEW_SKIN_CONDITION";
	public static final String DATA_GROUP = "DATA_GROUP";
	public static final String DATA_SKIN_CONDITION = "DATA_SKIN_CONDITION";

	public SkinObserverIntent(Context packageContext, Class<?> cls){
		super(packageContext, cls);
	}

}
