package cmput301W12.android.project.view;

//import android.R;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import cmput301W12.android.project.Container;
import cmput301W12.android.project.ContainerArrayAdapter;
import cmput301W12.android.project.FController;
import cmput301W12.android.project.Group;
import cmput301W12.android.project.OptionType;
import cmput301W12.android.project.R;
import cmput301W12.android.project.SkinCondition;
import cmput301W12.android.project.SkinObserverApplication;

/**
 * 
 * @author romansky march 22nd
 *
 */

public class PhotoEditorActivity extends  Activity {
	private ListView groupList;// = (ExpandableListView) findViewById(R.id.groupsExpandableList);
	private ListView conditionList;// = (ExpandableListView) findViewById(R.id.conditionExpList);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_editor);
		groupList = (ListView) findViewById(R.id.listGroup);
		conditionList = (ListView) findViewById(R.id.listCondition);
		fillLists();
		groupList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		conditionList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}
	
	public void fillLists() {
		
		Container[] groupArray = null;
		Container[] conditionArray = null;
		
		FController controller = SkinObserverApplication.getSkinObserverController(this);
		Set<? extends Container> setGroup = null;
		Set<? extends Container> setCondition = null;
		
		/* Set up setGroup with group names */
		setGroup = controller.getAllContainers(OptionType.GROUP);
		groupArray = new Group[setGroup.size()];
		setGroup.toArray(groupArray);

		/* Set up setCondition with skin condition names */
		setCondition = controller.getAllContainers(OptionType.SKINCONDITION);
		conditionArray = new SkinCondition[setCondition.size()];
		setCondition.toArray(conditionArray);

			
    	ContainerArrayAdapter conAdapterGroup = new ContainerArrayAdapter(this, groupArray);
    	ContainerArrayAdapter conAdapterCondition = new ContainerArrayAdapter(this, conditionArray);
    	groupList.setAdapter(conAdapterGroup);
    	conditionList.setAdapter(conAdapterCondition);
	}

}
