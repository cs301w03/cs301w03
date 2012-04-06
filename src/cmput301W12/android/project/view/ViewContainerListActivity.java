package cmput301W12.android.project.view;

import java.util.Set;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import cmput301W12.android.model.Container;
import cmput301W12.android.model.DbController;
import cmput301W12.android.model.Group;
import cmput301W12.android.model.OptionType;
import cmput301W12.android.model.SkinCondition;
import cmput301W12.android.model.SkinObserverApplication;
import cmput301W12.android.project.R;
import cmput301W12.android.project.controller.FController;
import cmput301W12.android.project.view.helper.ContainerArrayAdapter;
import cmput301W12.android.project.view.helper.SkinObserverIntent;

/**
 * 
 * @author MinhTri
 * @date Mar 15, 2012
 * 
 * An activity to display all groups/skin conditions
 */
public class ViewContainerListActivity extends ListActivity implements FView<DbController>{
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_DELETE=1;
	private static final int ACTIVITY_EDIT=1;
	
	//Menu id
	private static final int CREATE_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int CONNECT_ID = Menu.FIRST + 2;
	private static final int DISCONNECT_ID = Menu.FIRST + 3;
	private static final int RENAME_ID = Menu.FIRST + 4;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_text_view);
		fillList();

		registerForContextMenu(getListView());
	}


	private void fillList() {        
		Container[] array = null;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			FController controller = SkinObserverApplication.getSkinObserverController(this);
			Set<? extends Container> setCon = null;
			if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
			{
				setCon = controller.getAllContainers(OptionType.GROUP);
				array = new Group[setCon.size()];
				setCon.toArray(array);
			}
			else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
			{
				setCon = controller.getAllContainers(OptionType.SKINCONDITION);
				array = new SkinCondition[setCon.size()];
				setCon.toArray(array);
			}

			ContainerArrayAdapter conAdapter = new ContainerArrayAdapter(this, array);
			setListAdapter(conAdapter);
		}
		else
		{
			//Not legitimate, go back
			//not implemented
		}
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);     

		Container cont  = (Container) l.getAdapter().getItem(position);

		Intent newIntent = new Intent(this, PhotoListActivity.class);
		Bundle bundle = getIntent().getExtras();
		newIntent.putExtras(bundle);
		if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
		{
			newIntent.putExtra(SkinObserverIntent.DATA_GROUP,cont);
		}
		else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
		{
			newIntent.putExtra(SkinObserverIntent.DATA_SKIN_CONDITION,cont);
		}
		startActivity(newIntent);
	}    


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, CREATE_ID, 0, "Add New");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		case CREATE_ID:
			createNewGroup();
			return true;
		}



		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * 
	 * 
	 */
	private void createNewGroup() {
		Intent i = new Intent(this, CreateContainerActivity.class);
		i.putExtras(getIntent().getExtras());
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if ( requestCode == ACTIVITY_CREATE || requestCode == ACTIVITY_DELETE 
				|| requestCode == ACTIVITY_EDIT)
			fillList();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
		menu.add(0, CONNECT_ID, 0, "Connect photos");
		menu.add(0, DISCONNECT_ID, 0, "Disconnect photos");
		menu.add(0, RENAME_ID, 0, "Rename");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();               
		final Container cont = (Container) getListAdapter().getItem(info.position);
		switch(item.getItemId()) {
		case DELETE_ID:
			deleteContainer(cont);
			fillList();
			return true;
		case CONNECT_ID:
			connectContainer(cont);
			fillList();
			return true;
		case DISCONNECT_ID:
			disconnectContainer(cont);
			fillList();
			return true;
		case RENAME_ID:
			//Pop up window and rename container.
			AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
			popupBuilder.setTitle("Name");
			final EditText name = new EditText(this);
			name.setSingleLine();
			name.setText(cont.getName());
			popupBuilder.setView(name);
			popupBuilder.setNeutralButton("Confirm", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					cont.setName(name.getText().toString());
					String newName = cont.getName();
					FController fcontrol = SkinObserverApplication.getSkinObserverController(getApplicationContext());
					if(newName.trim().length() > 0){
						try{
							if (cont instanceof SkinCondition){
								fcontrol.updateSkin(cont.getItemId(), cont.getName());
							} else if (cont instanceof Group){
								fcontrol.updateGroup(cont.getItemId(), cont.getName());
							}
						}
						catch(Exception SQLiteConstraintException){
							Toast.makeText(getApplicationContext(), "Cannot update the name. There exists another item + " +
									"with the same new name", Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "The name can't be an empty String. It can't just have whitespaces either!", 
								Toast.LENGTH_LONG);
					}
					fillList();
					//<------------- SAVE THE NEW NAME TO THE DB!
				}
			});
			popupBuilder.create();
			popupBuilder.show();

			return true;
		}
		return super.onContextItemSelected(item);
	}



	protected void deleteContainer(Container cont)
	{
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
			if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
				skinObserverController.deleteAContainer(cont, OptionType.GROUP);
			else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
				skinObserverController.deleteAContainer(cont, OptionType.SKINCONDITION);
		}
	}

	protected void connectContainer(Container cont)
	{
		Intent newIntent = new Intent(this, PhotoListActivity.class);
		Bundle bundle = getIntent().getExtras();
		newIntent.putExtras(bundle);
		newIntent.putExtra(PhotoListActivity.CONNECT, PhotoListActivity.CONNECT);

		if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
		{
			newIntent.putExtra(SkinObserverIntent.DATA_GROUP,cont);
		}
		else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
		{
			newIntent.putExtra(SkinObserverIntent.DATA_SKIN_CONDITION,cont);
		}
		
		startActivityForResult(newIntent, ACTIVITY_EDIT);
	}

	protected void disconnectContainer(Container cont)
	{
		Intent newIntent = new Intent(this, PhotoListActivity.class);
		Bundle bundle = getIntent().getExtras();
		newIntent.putExtras(bundle);
		newIntent.putExtra(PhotoListActivity.DISCONNECT, PhotoListActivity.DISCONNECT);

		if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
		{
			newIntent.putExtra(SkinObserverIntent.DATA_GROUP,cont);
		}
		else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
		{
			newIntent.putExtra(SkinObserverIntent.DATA_SKIN_CONDITION,cont);
		}
		
		startActivityForResult(newIntent, ACTIVITY_EDIT);
	}

	@Override
	public void update(DbController model)
	{
		// TODO Auto-generated method stub

	}

}
