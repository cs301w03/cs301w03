package cmput301W12.android.project;


import java.util.SortedSet;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @author MinhTri & Tanvir Sajed
 * @date Mar 16, 2012
 * 
 * This class is an activity use to display a list
 * of photo thumbnails and names
 */
public class PhotoListActivity extends ListActivity implements FView<DbController>
{
	
	 private final String edit = "Edit";
     private final String delete = "Delete";
     private final String add_photo = "Add Photo";
     public static final String PHOTO = "PHOTO";
     public static final int VIEW_PHOTO = 0;
     public static final int EDIT_PHOTO = 1;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.photoviewlist_activity);
	        
	        	     	       	        
	        registerForContextMenu(getListView());
	        
	        fillSelectableList();
	        
	        /*final ListView listView = getListView();
	    	listView.setItemsCanFocus(false);
	    	listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); */ 

	        
	        //set up the listview to enable a context menu for editting logs/deleting
	 }
	 
	 protected void fillSelectableList() {
		 
		 FController controller =  SkinObserverApplication.getSkinObserverController(this);
		 Bundle bundle = getIntent().getExtras();
		 Container cont = null;
		 SortedSet<Photo> photoSet = null;
		 if (bundle != null)
		 {
			 if (bundle.containsKey(SkinObserverIntent.DATA_GROUP) )
			 {
				 cont = (Container) bundle.getSerializable(SkinObserverIntent.DATA_GROUP);
				 photoSet = controller.getAllPhotoOfAContainer(cont.getItemId(), OptionType.PHOTOGROUP);	 
			 }
			 else if (bundle.containsKey(SkinObserverIntent.DATA_SKIN_CONDITION))
			 {
				 cont = (Container) bundle.getSerializable(SkinObserverIntent.DATA_SKIN_CONDITION);
				 photoSet = controller.getAllPhotoOfAContainer(cont.getItemId(), OptionType.PHOTOSKIN);
			 }
				 
		 }
		 else
			 photoSet = controller.getAllPhoto();
		 
		 if (photoSet != null)
		 {
			 Photo[] photoArray = new Photo[photoSet.size()];
			 photoSet.toArray(photoArray);
			 //Photo[] photosArray1 = (Photo[]) photos.toArray();
			 PhotoListArrayAdapter caAdapter = new PhotoListArrayAdapter(this, photoArray);
			 
			 setListAdapter(caAdapter);
		 }
		 
	 }
	 
	 protected void fillEditableList() {
		 
	 }
	 
	 
		 
	 
	    // places the data in ctxmenu into the build the context menu for Petrol manager
	    
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	        super.onCreateContextMenu(menu,  v, menuInfo);
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.ctxmenu1, menu);

	    }	
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        super.onCreateOptionsMenu(menu);
	        menu.add(0, Menu.FIRST, 0, "Menu");
	        return true;
	    }
	    	 
	 
	 // if a user picks an item from the context menu, this function is then called.
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	    	
	    	
	    	
	                super.onListItemClick(l, v, position, id);
	                
	                ListAdapter adapter = l.getAdapter();
	                Photo photo  = (Photo) adapter.getItem(position);
	                Intent i = new Intent(this, ViewPhotoActivity.class);
	                i.putExtra(PHOTO, photo);
	                startActivityForResult(i, VIEW_PHOTO);
	                
	                //editor.putExtra(PHOTO, value ); PRODUCE INTENT WITH PHOTO OBJECT to view
	     
	        }
	        
	    //Opens the xontext menu and gives the option edit, or delete, and reacts accordingly.
	    public boolean onContextItemSelected(MenuItem item){
//	             
	    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        switch (item.getItemId()) {
	            case R.id.menuedit:
	                
	         
	            case R.id.menudelete:
	            	
	            	FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
	            	//int x = skinObserverController.deleteEntry(info.id, OptionType.PHOTO);
	                
	                return true;
	                
	            case R.id.menucompare:
	            	
	            	return true;
	            			
	            default:
	                return super.onContextItemSelected(item);
	        	}
	              
	           
	              
	      
	        
	        }

		@Override
		public void update(DbController model)
		{

			//fillSelectableList();
			// TODO Auto-generated method stub
			
		}




}
