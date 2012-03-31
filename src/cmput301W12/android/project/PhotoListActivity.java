package cmput301W12.android.project;


import java.util.SortedSet;
import android.app.ListActivity;
import android.content.Intent;
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
import android.widget.Toast;

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
	public static final int COMPARE_PHOTO = 2;

	private static boolean COMPARE_CHOSEN = false;
	private static Photo PHOTO_TO_COMPARE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoviewlist_activity);

		registerForContextMenu(getListView());

		fillSelectableList();

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

		if(PhotoListActivity.COMPARE_CHOSEN == false){
			Intent i = new Intent(this, ViewPhotoActivity.class);
			i.putExtra(PHOTO, photo);
			startActivityForResult(i, VIEW_PHOTO);
		} else {
			Intent i = new Intent(this, ComparePhotoActivity.class);
			i.putExtra(ComparePhotoActivity.BACKGROUND, PhotoListActivity.PHOTO_TO_COMPARE);
			i.putExtra(ComparePhotoActivity.SURFACE, photo);
			COMPARE_CHOSEN = false;
			PHOTO_TO_COMPARE = null;
			startActivityForResult(i, COMPARE_PHOTO);
		}

	}

	//Opens the xontext menu and gives the option edit, or delete, and reacts accordingly.
	public boolean onContextItemSelected(MenuItem item){
		//	             
		switch (item.getItemId()) {
		case R.id.menuedit:


		case R.id.menudelete:

			FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
			//int x = skinObserverController.deleteEntry(info.id, OptionType.PHOTO);

			return true;

		case R.id.menucompare:
			COMPARE_CHOSEN = true;
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			ListAdapter adapter = this.getListAdapter();
			PHOTO_TO_COMPARE = (Photo) adapter.getItem(info.position);

			CharSequence text = "Please click at another photo to compare with the chosen photo.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(this, text, duration);
			toast.show();

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
