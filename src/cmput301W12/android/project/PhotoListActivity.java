package cmput301W12.android.project;


import java.util.SortedSet;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;


public class PhotoListActivity extends ListActivity implements FView<DbController>
{
	
	 private final String edit = "Edit";
     private final String delete = "Delete";
     private final String add_photo = "Add Photo";
     public static final String PHOTO = "PHOTO";
     public static final int VIEW_PHOTO = 0;
     public static final int EDIT_PHOTO = 1;

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.photoviewlist_activity);
	        fillSelectableList();
	        
	        //set up the listview to enable a context menu for editting logs/deleting
	 }
	 
	 public void fillSelectableList() {
		 
		 FController controller =  SkinObserverApplication.getSkinObserverController(this);
		 Bundle bundle = getIntent().getExtras();
		 Container cont = null;
		 SortedSet<Photo> photos = null;
		 if (bundle == null)
			 photos = controller.getAllPhoto();
		 else
		 {
			 if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
			 {
				 cont = (Container) bundle.getSerializable(SkinObserverIntent.DATA_GROUP);
				 photos = controller.getAllPhotoOfAContainer(cont.getItemId(), OptionType.GROUP);

			 }
			 else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
			 {
				 cont = (Container) bundle.getSerializable(SkinObserverIntent.DATA_SKIN_CONDITION);
				 photos = controller.getAllPhotoOfAContainer(cont.getItemId(), OptionType.SKINCONDITION);
			 }			 
		 }
		 
		 Photo[] newArray = new Photo[photos.size()];
		 photos.toArray(newArray);
		 //Photo[] photosArray1 = (Photo[]) photos.toArray();
		 PhotoListArrayAdapter caAdapter = new PhotoListArrayAdapter(this, newArray);
		 
		 setListAdapter(caAdapter);
		 
	 }
	 
	 public void fillEditableList() {
		 
	 }
	 
	 
		 
	 
	    // places the data in ctxmenu into the build the context menu for Petrol manager
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	        super.onCreateContextMenu(menu,  v, menuInfo);
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.ctxmenu1, menu);

	    }	 
	 
	 
	 
	 // if a user picks an item from the context menu, this function is then called.
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	                super.onListItemClick(l, v, position, id);
	                
	                ListAdapter adapter = l.getAdapter();
	                Photo photo  = (Photo) adapter.getItem(position);
	                /*Intent i = new Intent(this, ShowPhoto.class);
	                i.putExtra(PHOTO, phto);
	                startActivityForResult(i, VIEW_PHOTO);*/
	                
	                //editor.putExtra(PHOTO, value ); PRODUCE INTENT WITH PHOTO OBJECT to view
	     
	        }
	        
	    //Opens the xontext menu and gives the option edit, or delete, and reacts accordingly.
	    public boolean onContextItemSelected(MenuItem item){
//	              String edit = "Edit";
//	              String delete = "Delete";

	                //ADD HERE what you would like to do when a list item is selected.
	                
	              if (edit.compareTo(item.getTitle().toString()) == 0){
//	                      //will have to use a bundle to pass the rowid to Entry_Editor to enable simpler code
//	                      Intent editor = new Intent(this, Entry_Editor.class);
//	                      editor.putExtra("_id", Long.toString(xid));
//	                      startActivityForResult(editor, EDIT_ENTRY);
	              }
	              else if(delete.compareTo(item.getTitle().toString()) == 0) {
//	                      mydb.deleteEntry(xid);
//	                      refreshList();
	              }
	              
	              else if(add_photo.compareTo(item.getTitle().toString()) == 0) {
//                      mydb.deleteEntry(xid);
//                      refreshList();	            	  
	              }
	              
	             /*else if(compare.compareTo(item.getTitle().toString() == 0)) {
	            	  	
                  }*/
	              
	                return super.onContextItemSelected(item);
	        }

		@Override
		public void update(DbController model)
		{

			//fillSelectableList();
			// TODO Auto-generated method stub
			
		}




}
