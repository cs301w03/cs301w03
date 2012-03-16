package cmput301W12.android.project;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ViewContainerListActivity extends ListActivity implements FView<DbController>{
	private static final int ACTIVITY_CREATE=0;
	private static final int CREATE_ID = Menu.FIRST;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_text_view);
        fillData();
        
        registerForContextMenu(getListView());
    }
    
    private void fillData() {
        
//        Group g1 = new Group("abc");
//        Group g2 = new Group("Feet");
//        Group g3 = new Group("Right");
//        Group[] gl = {g1,g2,g3};
//        ContainerArrayAdapter conAdapter = new ContainerArrayAdapter(this, gl);
//        setListAdapter(conAdapter);
    	
    	FController controller = SkinObserverApplication.getSkinObserverController(this);
    	controller.getAllPhotoOfAContainer(Photo.INVALID_ID, OptionType.GROUP);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ListAdapter adapter = l.getAdapter();
        Container cont  = (Container) adapter.getItem(position);
        Bundle bundle = getIntent().getExtras();
        
        Intent newIntent = new Intent(this, CreateContainerActivity.class);
        newIntent.putExtras(bundle);
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
        if ( requestCode == ACTIVITY_CREATE)
        	fillData();
    }

	@Override
	public void update(DbController model)
	{

		// TODO Auto-generated method stub
		
	}
    
}
