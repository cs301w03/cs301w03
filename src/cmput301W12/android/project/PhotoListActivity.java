package cmput301W12.android.project;


import java.util.SortedSet;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import cmput301W12.android.project.view.PhotoEditorActivity;

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
	 private SortedSet<Photo> currentPhotoSet = null;
	 private Container cont = null;
	 
	 public static final String CONNECT = "CONNECT";
	 public static final String DISCONNECT = "DISCONNECT";
	 public static final String VIEW = "VIEW";
	 public static final String ADD_PHOTO = "Add Photo";
     public static final String PHOTO = "PHOTO";
     public static final int ACTIVITY_VIEW_PHOTO = 0;
     public static final int ACTIVITY_EDIT_PHOTO = 1;
     
     public static final int CONNECT_ID = Menu.FIRST;
     public static final int DISCONNECT_ID = Menu.FIRST +1;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.photoviewlist_activity);
	                	     	       	        
	        registerForContextMenu(getListView());                
	        fillList();

	 }
	 
	 protected void fillList()
	 {
		 Bundle bundle = getIntent().getExtras();
		 if (bundle != null)
		 {
			 getContainer(bundle);
	         if (bundle.containsKey(CONNECT))
	        	 fillUnseletedList(bundle);
	         else
	        	 currentPhotoSet =  getAllCurrentPhoto(bundle);
		 }
		 else
			 currentPhotoSet =  getAllCurrentPhoto(null);
         displayList();
	 }
	 
	 protected void getContainer(Bundle bundle)
	 {
		 if (bundle != null)
		 {
			 if (bundle.containsKey(SkinObserverIntent.DATA_GROUP) )
			 {
				 cont = (Container) bundle.getSerializable(SkinObserverIntent.DATA_GROUP);	 
			 }
			 else if (bundle.containsKey(SkinObserverIntent.DATA_SKIN_CONDITION))
			 {
				 cont = (Container) bundle.getSerializable(SkinObserverIntent.DATA_SKIN_CONDITION);
			 }	 
		 }
	 }
	 
	 
	 protected SortedSet<Photo> getAllCurrentPhoto(Bundle bundle) {
		 FController controller =  SkinObserverApplication.getSkinObserverController(this);
		 if (cont != null)
		 {
			 return cont.getPhotos(this);
		 }
		 else
			 return controller.getAllPhoto();
	 }
	 
	 
	 
	 protected void displayList()
	 {
		 if (currentPhotoSet != null)
		 {
			 Photo[] photoArray = new Photo[currentPhotoSet.size()];
			 currentPhotoSet.toArray(photoArray);
			 //Photo[] photosArray1 = (Photo[]) photos.toArray();
			 PhotoListArrayAdapter caAdapter = new PhotoListArrayAdapter(this, photoArray);
			 
			 setListAdapter(caAdapter);
		 }
	 }
	 
	 
	 protected void fillUnseletedList(Bundle bundle){
		 currentPhotoSet = getAllCurrentPhoto(bundle);
		 
		 //Pass null to get all photos from database
		 SortedSet<Photo> allPhotoSet = getAllCurrentPhoto(null);
		 
		 //Get the complement of currentPhotoSet in allPhotoSet
		 
		 for (Photo photo: currentPhotoSet)
		 {
			 if (allPhotoSet.contains(photo))
			 {
				 allPhotoSet.remove(photo);
			 }
		 }
		 currentPhotoSet = allPhotoSet;
	 }
	 	    
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu,  v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.ctxmenu1, menu);
	
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    Bundle bundle = getIntent().getExtras();
	    if (bundle.containsKey(CONNECT))
	    	menu.add(0, CONNECT_ID, 0, "Connect");
	    if (bundle.containsKey(CONNECT))
	    	menu.add(0, DISCONNECT_ID, 0, "Disconnect");
	    
	    return true;
	}
	    	 
	 
	 // if a user picks an item from the context menu, this function is then called.
	    protected void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(CONNECT) || bundle.containsKey(DISCONNECT))
            {
            	View row = l.getChildAt(position);
            	changeSelection(row);
            }
            else
            {
            	ListAdapter la = l.getAdapter();
            	Photo photo  = (Photo) la.getItem(position);
            	viewPhoto(photo);
            }
	     
	    }
	    
	    protected void viewPhoto(Photo photo)
	    {
            Intent i = new Intent(this, ViewPhotoActivity.class);
            i.putExtra(PHOTO, photo);
            startActivityForResult(i, ACTIVITY_VIEW_PHOTO);
	    }
	    
	    protected void changeSelection(View row)
	    {
	    	Drawable drawable = row.getBackground();
	    	if (drawable != null)
            {
            	PaintDrawable paintDrawable = (PaintDrawable) drawable;
    	        if (paintDrawable != SelectiveColor.getSelectedDrawable())
    	        	row.setBackgroundDrawable(SelectiveColor.getSelectedDrawable());
    	        else
    	        	row.setBackgroundDrawable(SelectiveColor.getUnselectedDrawable());
            }
            else
            {
            	row.setBackgroundDrawable(SelectiveColor.getSelectedDrawable());
            }
	    }
	        
	    //Opens the context menu and gives the option edit, or delete, and reacts accordingly.
	    public boolean onContextItemSelected(MenuItem item){
	    	
	    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();               
	    	Photo photo = (Photo) getListAdapter().getItem(info.position);
	    	
	        switch (item.getItemId()) {
	            case R.id.menuedit:
	            	Intent editPhoto = new Intent(this, PhotoEditorActivity.class);
	            	editPhoto.putExtra("Photo", photo);
	        		startActivityForResult(editPhoto, ACTIVITY_EDIT_PHOTO);
	        		return true;
	            case R.id.menudelete:
	            	deletePhoto(photo);
	            	fillList();              
	                return true;
	                
	            case R.id.menucompare:
	            	
	            	return true;
	            			
	            default:
	                return super.onContextItemSelected(item);
	        	}
	 
	        }

	    protected void deletePhoto(Photo photo)
		{
			Bundle bundle = getIntent().getExtras();
			if (bundle != null)
			{
				FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
				skinObserverController.deleteAPhoto(photo);
			}
		}
	    
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        super.onActivityResult(requestCode, resultCode, intent);
//	        if ( requestCode == ACTIVITY_EDIT_PHOTO )
//	        	fillSelectableList();
	        fillList();
	    }
	    
		@Override
		public void update(DbController model)
		{

			//fillSelectableList();
			// TODO Auto-generated method stub
			
		}




}
