package cmput301W12.android.project;

import java.util.Set;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;

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
	private static final int CREATE_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int CONNECT_ID = Menu.FIRST + 2;
	private static final int DISCONNECT_ID = Menu.FIRST + 3;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_text_view);
        fillData();
        
        registerForContextMenu(getListView());
    }
    
//    // Temporary method to demo
//    public void fillData() {  
//    	Container[] array = new Container[3];
//    	Bundle bundle = getIntent().getExtras();
//    	if (bundle != null)
//		{
//			if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
//			{
//				array[0] = new Group("Head");
//				array[1] = new Group("LeafHand");
//				array[2] = new Group("Chest");
//			}
//			else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
//			{
//				array[0] = new SkinCondition("Mole");
//				array[1] = new SkinCondition("Acne");
//				array[2] = new SkinCondition("Unknown");
//			}
//	    	ContainerArrayAdapter conAdapter = new ContainerArrayAdapter(this, array);
//	    	setListAdapter(conAdapter);
//		}
//	    
//    }
    
    
    public void fillData() {        
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
        
        ListAdapter adapter = l.getAdapter();
        Container cont  = (Container) adapter.getItem(position);
        
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
        Intent i = new Intent(this, ViewCreateContainerActivity.class);
        i.putExtras(getIntent().getExtras());
        startActivityForResult(i, ACTIVITY_CREATE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if ( requestCode == ACTIVITY_CREATE || requestCode == ACTIVITY_DELETE 
        		|| requestCode == ACTIVITY_EDIT)
        	fillData();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        menu.add(0, CONNECT_ID, 0, "Connect photos");
        menu.add(0, DISCONNECT_ID, 0, "Disconnect photos");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();               
        Container cont = (Container) getListAdapter().getItem(info.position);
        switch(item.getItemId()) {
            case DELETE_ID:
                deleteContainer(cont);
                fillData();
                return true;
            case CONNECT_ID:
                connectContainer(cont);
                fillData();
                return true;
            case DISCONNECT_ID:
            	disconnectContainer(cont);
            	fillData();
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
