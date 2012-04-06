package cmput301W12.android.project;


import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cmput301W12.android.project.view.ComparePhotosActivity;
import cmput301W12.android.project.view.PhotoEditorActivity;

/**
 * 
 * @author MinhTri & Tanvir Sajed
 * @date Mar 16, 2012
 * 
 * This class is an activity used to display a list
 * of photo thumbnails and names
 */
public class PhotoListActivity extends ListActivity implements FView<DbController>
{
	

	public static final String CONNECT = "CONNECT";
	public static final String DISCONNECT = "DISCONNECT";
	public static final String VIEW = "VIEW";
	public static final String ADD_PHOTO = "ADD_PHOTO";
	public static final String PHOTO = "PHOTO";
	
	public static final int ACTIVITY_VIEW_PHOTO = 0;
	public static final int ACTIVITY_EDIT_PHOTO = 1;

	//Menu ID
	public static final int CONNECT_ID = Menu.FIRST;
	public static final int DISCONNECT_ID = Menu.FIRST +1;

	private SortedSet<Photo> currentPhotoSet = new TreeSet<Photo>();
	private SortedSet<Photo> selectedPhotoSet;
	
	private Photo photoCompare;
	private int compareMode = 0;
	private Container cont = null;
	private FController fcontroller;

	private Button confirmButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.photoviewlist_activity);
		confirmButton = (Button) this.findViewById(R.id.menubutton);
		registerForContextMenu(getListView());
		
		fcontroller = SkinObserverApplication.getSkinObserverController(this);
		 
		Bundle bundle = getIntent().getExtras(); 

		if (bundle != null && (bundle.containsKey(CONNECT) || bundle.containsKey(DISCONNECT))){
			
			if (bundle.containsKey(CONNECT)) {
				confirmButton.setText(R.string.connect);
			}
			else{ 
				confirmButton.setText(R.string.disconnect);
			}
			
			selectedPhotoSet = new TreeSet<Photo>();
			confirmButton.setOnClickListener(new View.OnClickListener()
			{   
				@Override
				public void onClick(View v)
				{
					updateContainer();
					finish();
				}
			});
		}
		//In view photo mode
		else {
			confirmButton.setVisibility(View.GONE);
		}
		
		fillList();
	}


	private void updateContainer(){
		OptionType op = null;
		Bundle bundle = this.getIntent().getExtras();

		if(cont instanceof Group){
			op = OptionType.PHOTOGROUP;
		} else if (cont instanceof SkinCondition){
			op = OptionType.PHOTOSKIN;
		}

		if (bundle.containsKey(CONNECT))
		{

			Set<Integer> setInt = new HashSet<Integer>();
			for(Photo p : selectedPhotoSet){
				setInt.add(p.getPhotoId());
			}
			fcontroller.connectAContainerToManyPhotos(cont.getItemId(), setInt, op);
		}  else if (bundle.containsKey(DISCONNECT)){
			Set<Integer> setInt = new HashSet<Integer>();
			for(Photo p : selectedPhotoSet){
				setInt.add(p.getPhotoId());
			}
			fcontroller.disconnectAContainerFromManyPhotos(cont.getItemId(), setInt, op);
		}


	}

	private void fillList()
	{
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			getContainer(bundle);
			if (bundle.containsKey(CONNECT))
				fillUnseletedList(bundle);
			else
				currentPhotoSet =  getAllCurrentPhoto();
		}
		else{
			currentPhotoSet =  getAllCurrentPhoto();
		}
		displayList();
	}

	private void getContainer(Bundle bundle)
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


	private SortedSet<Photo> getAllCurrentPhoto() {
		if (cont != null)
		{
			return cont.getPhotos(this);
		}
		else
		{
			return fcontroller.getAllPhoto();
		}
	}



	private void displayList()
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


	private void fillUnseletedList(Bundle bundle){
		currentPhotoSet = getAllCurrentPhoto();
		//Pass null to get all photos from database
		SortedSet<Photo> allPhotoSet = fcontroller.getAllPhoto();

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
//		Bundle bundle = getIntent().getExtras();
//		if (bundle.containsKey(CONNECT))
//			menu.add(0, CONNECT_ID, 0, "Connect");
//		if (bundle.containsKey(CONNECT))
//			menu.add(0, DISCONNECT_ID, 0, "Disconnect");

		return true;
	}


	// if a user picks an item from the context menu, this function is then called.
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Bundle bundle = getIntent().getExtras();
		ListAdapter la = l.getAdapter();
		Photo photo  = (Photo) la.getItem(position);

		if(bundle != null){
			if (bundle.containsKey(CONNECT) || bundle.containsKey(DISCONNECT))
			{
				View row = l.getChildAt(position);
				changeSelection(row, photo);
				return;
			}
		}

		if(compareMode == 0){
			viewPhoto(photo);
		} else if (compareMode == 1){
			Intent i = new Intent(this, ComparePhotoActivity.class);

			i.putExtra(ComparePhotoActivity.BACKGROUND_PHOTO, photoCompare);
			i.putExtra(ComparePhotoActivity.SURFACE_PHOTO, photo);


			compareMode = 0;

			startActivityForResult(i, 0);
		} else if (compareMode == 2){
			Intent comparePhotos = new Intent(this, ComparePhotosActivity.class);
			comparePhotos.putExtra("PhotoOne", photoCompare);
			comparePhotos.putExtra("PhotoTwo", photo);
			compareMode = 0;
			startActivity(comparePhotos);
		}
	}


	private void viewPhoto(Photo photo)
	{
		Intent i = new Intent(this, ViewPhotoActivity.class);
		i.putExtra(PHOTO, photo);
		startActivityForResult(i, ACTIVITY_VIEW_PHOTO);
	}

	private void changeSelection(View row, Photo photo)
	{
		Drawable drawable = row.getBackground();

		//Initially, drawble is null because row.getBackground() returns null.
		if (drawable != null){
			PaintDrawable paintDrawable = (PaintDrawable) drawable;
			if (paintDrawable != SelectiveColor.getSelectedDrawable()){
				row.setBackgroundDrawable(SelectiveColor.getSelectedDrawable());
				selectedPhotoSet.add(photo);
			}
			else{
				row.setBackgroundDrawable(SelectiveColor.getUnselectedDrawable());
				selectedPhotoSet.remove(photo);
			}
		}
		else {
			row.setBackgroundDrawable(SelectiveColor.getSelectedDrawable());
			selectedPhotoSet.add(photo);
		}
	}

	//Opens the context menu and gives the option edit, or delete, and reacts accordingly.
	public boolean onContextItemSelected(MenuItem item){

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();               
		Photo photo = (Photo) getListAdapter().getItem(info.position);

		switch (item.getItemId()) {
//		case R.id.menuedit:
//			Intent editPhoto = new Intent(this, PhotoEditorActivity.class);
//			editPhoto.putExtra("Photo", photo);
//			startActivityForResult(editPhoto, ACTIVITY_EDIT_PHOTO);
//			return true;
		case R.id.menudelete:
			fcontroller.deleteAPhoto(photo);
			fillList();              
			return true;
		case R.id.menucompare:
			photoCompare = photo;
			compareMode = 1;
			CharSequence text = "Click at another photo to compare with this chosen photo!";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(this, text, duration);
			toast.show();
			return true;

		case R.id.menucompare2:
			photoCompare = photo;
			compareMode = 2;
			CharSequence text2 = "Click at another photo to compare with this chosen photo!";
			int duration2 = Toast.LENGTH_SHORT;
			Toast toast2 = Toast.makeText(this, text2, duration2);
			toast2.show();
			return true;
		default:
			return super.onContextItemSelected(item);
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
	}

}
