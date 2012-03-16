package cmput301W12.android.project;

import com.log.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;


public class PhotoListActivity extends ListActivity
{

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listphotoview_activity);
	        
	        
	        //set up the listview to enable a context menu for editting logs/deleting
	        registerForContextMenu(getListView());  // calls oncontextmenucreate with the ListView,
	                                                // binding the listview, so the onListItemClick function works
	 }
	 
	 public void fillupList() {
		 
	 }
	 
	 //Kalen add context menu, remember we are creating a context menu
	 //of options like "add photo", or whatever is given in the UI,
	 //we are not creating a context menu of list photo, because 
	 //the list is being handled by the listactivity here.

	 
	 
	 
	 
	 
	 
	 
	    // places the data in ctxmenu into the build the context menu for Petrol manager
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	        super.onCreateContextMenu(menu,  v, menuInfo);
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.ctxmenu, menu);

	    }	 
	 
	 
	 
	 // if a user picks an item from the context menu, this function is then called.
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	                super.onListItemClick(l, v, position, id);
	                xid = id;
	                this.openContextMenu(l);                                  // opens up the context menu
	        }
	        
	    //Opens the xontext menu and gives the option edit, or delete, and reacts accordingly.
	    public boolean onContextItemSelected(MenuItem item){
//	              String edit = "Edit Entry";
//	              String delete = "Delete Entry";

	                //ADD HERE what you would like to do when a list item is selected.
	                
//	              if (edit.compareTo(item.getTitle().toString()) == 0){
//	                      //will have to use a bundle to pass the rowid to Entry_Editor to enable simpler code
//	                      Intent editor = new Intent(this, Entry_Editor.class);
//	                      editor.putExtra("_id", Long.toString(xid));
//	                      startActivityForResult(editor, EDIT_ENTRY);
//	              }
//	              else if(delete.compareTo(item.getTitle().toString()) == 0) {
//	                      mydb.deleteEntry(xid);
//	                      refreshList();
//	              }
	                return super.onContextItemSelected(item);
	        }




}
