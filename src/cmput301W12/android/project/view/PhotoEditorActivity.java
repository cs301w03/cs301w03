package cmput301W12.android.project.view;

//import android.R;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import cmput301W12.android.project.CheckBoxArrayAdapter;
import cmput301W12.android.project.Container;
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
	
	private Container[] groupArray = null;
	private Container[] conditionArray = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_editor);
		
		Button confirm = (Button) this.findViewById(R.id.confirm);
		confirm.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// I want to output what things have been selected
				for(Container cont: groupArray)
				{
					if (cont.isSelected())
						System.err.print("Hello, I am " + cont.getName() + "\n");
				}
				/*
				for(Container cont2: conditionArray)
				{
					if (cont2.isSelected())
						System.err.print("Hello, I am " + cont2.getName() + "\n");
				}*/
				
			}
		});
		
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

			
    	CheckBoxArrayAdapter conAdapterGroup = new CheckBoxArrayAdapter(this, groupArray);
    	CheckBoxArrayAdapter conAdapterCondition = new CheckBoxArrayAdapter(this, conditionArray);
    	groupList.setAdapter(conAdapterGroup);
    	conditionList.setAdapter(conAdapterCondition);
	}

}
