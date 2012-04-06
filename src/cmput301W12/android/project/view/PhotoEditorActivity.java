package cmput301W12.android.project.view;

//import android.R;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import cmput301W12.android.project.CheckBoxArrayAdapter;
import cmput301W12.android.project.Container;
import cmput301W12.android.project.FController;
import cmput301W12.android.project.Group;
import cmput301W12.android.project.OptionType;
import cmput301W12.android.project.Photo;
import cmput301W12.android.project.R;
import cmput301W12.android.project.SkinCondition;
import cmput301W12.android.project.SkinObserverApplication;
import cmput301W12.android.project.SkinObserverIntent;
import cmput301W12.android.project.ViewContainerListActivity;

/**
 * This class currently allows you to tag a new photo with Skin conditions and Groups. 
 * I would like to implement an add group, and add condition button. I would
 * also like to make the image in the activity display the recently taken photo.
 * @author romansky march 22nd
 *
 */

/* Add annotation button 
 * http://i.thiyagaraaj.com/articles/android-articles/customdialogboxpopupusinglayoutinandroid
 */

public class PhotoEditorActivity extends  Activity {
	private ListView groupList;
	private ListView conditionList;

	private ImageView newImg;

	private Container[] groupArray = null;
	private Container[] conditionArray = null;

	private Photo editingPhoto = null;

	public static final int ACTIVITY_CREATE_GROUP = 0;
	public static final int ACTIVITY_CREATE_SKINCONDITION = 1;

	public static final int ID_CREATE_GROUP = Menu.FIRST;
	public static final int ID_CREATE_SKINCONDITION = Menu.FIRST +1;
	public static final int ID_CREATE_ANNOTATION = Menu.FIRST + 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_editor);
		editingPhoto = (Photo) getIntent().getSerializableExtra("Photo");

		newImg = (ImageView) this.findViewById(R.id.recentlyTakenImage);
		Uri uri = Uri.parse(editingPhoto.getLocation());
		Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
		newImg.setImageBitmap(bitmap);

		Button confirm = (Button) this.findViewById(R.id.confirm);

		confirm.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				Set<Integer> groups = new HashSet<Integer>();
				Set<Integer> skinConditions = new HashSet<Integer>();
				for(Container cont: groupArray)
				{
					if (cont.isSelected())
					{
						groups.add(cont.getItemId());
					}
				}

				for(Container cont2: conditionArray)
				{
					if (cont2.isSelected())
					{
						System.err.print("Hello, I am " + cont2.getName() + "\n");

						skinConditions.add(cont2.getItemId());
					}
				}
				editingPhoto.setGroups(groups);
				editingPhoto.setSkinConditions(skinConditions);
				Bundle returnTags = new Bundle();
				returnTags.putSerializable("Photo", editingPhoto);
				Intent i = new Intent();
				i.putExtras(returnTags);
				setResult(RESULT_OK, i);
				//Intent returnTags = new Intent();
				//returnTags.putExtra("cmput301W12.android.project.Photo.Photo", newestPhoto);
				//if (getParent() == null) {
				//		            setResult(Activity.RESULT_OK, returnTags);
				//		        } else {
				//		            getParent().setResult(Activity.RESULT_OK, returnTags);
				//		        }

				finish();
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
		switch(item.getItemId()) {
		case ID_CREATE_GROUP:
			createNewGroup();
			return true;
		case ID_CREATE_SKINCONDITION:
			createNewSkinCondition();
			return true;
		case ID_CREATE_ANNOTATION:
			editingPhoto.editAnnotation(this);
			//getAnnotation();
			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	// Fill the list with items from the database
	protected void fillLists() {

		Set<? extends Container> setGroup = null;
		Set<? extends Container> setCondition = null;

		/* Set up setGroup with group names */
		if (editingPhoto.isNew())
		{
			FController controller = SkinObserverApplication.getSkinObserverController(this);
			setGroup = controller.getAllContainers(OptionType.GROUP);
			groupArray = new Group[setGroup.size()];
			setGroup.toArray(groupArray);

			/* Set up setCondition with skin condition names */
			setCondition = controller.getAllContainers(OptionType.SKINCONDITION);
			conditionArray = new SkinCondition[setCondition.size()];
			setCondition.toArray(conditionArray);
		}
		else
		{
			setGroup = editingPhoto.getGroups(this);
			groupArray = new Group[setGroup.size()];
			setGroup.toArray(groupArray);

			setCondition = editingPhoto.getSkinConditions(this);
			conditionArray = new SkinCondition[setCondition.size()];
			setCondition.toArray(conditionArray);

		}

		CheckBoxArrayAdapter conAdapterGroup = new CheckBoxArrayAdapter(this, groupArray);
		CheckBoxArrayAdapter conAdapterCondition = new CheckBoxArrayAdapter(this, conditionArray);
		groupList.setAdapter(conAdapterGroup);
		conditionList.setAdapter(conAdapterCondition);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ID_CREATE_ANNOTATION, 0, "Add Photo Annotation");
		menu.add(0, ID_CREATE_GROUP, 0, "Add Tag");
		menu.add(0, ID_CREATE_SKINCONDITION, 0, "Add Skin Condition");
		return true;
	}

	protected void createNewGroup()
	{
		Intent iCreateGroup = new Intent(this, ViewContainerListActivity.class);
		iCreateGroup.putExtra(SkinObserverIntent.DATA_GROUP, SkinObserverIntent.DATA_GROUP);
		startActivityForResult(iCreateGroup, ACTIVITY_CREATE_GROUP);
	}

	protected void createNewSkinCondition()
	{
		Intent iCreateSC = new Intent(this, ViewContainerListActivity.class);
		iCreateSC.putExtra(SkinObserverIntent.DATA_SKIN_CONDITION, SkinObserverIntent.DATA_SKIN_CONDITION);
		startActivityForResult(iCreateSC, ACTIVITY_CREATE_SKINCONDITION);
	}

	protected void getAnnotation()
	{
		AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
		popupBuilder.setTitle("Annotation");
		final EditText annotation = new EditText(this);
		annotation.setSingleLine();
		annotation.setText("");
		popupBuilder.setView(annotation);
		popupBuilder.setNeutralButton("Confirm", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//When click confirm save the annotation into the photo?
				editingPhoto.setAnnotation(annotation.getText().toString());

			}
		});
		popupBuilder.create();
		popupBuilder.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if ( requestCode == ID_CREATE_GROUP)
			fillLists();
		if ( requestCode == ID_CREATE_SKINCONDITION)
			fillLists();

	}
}
