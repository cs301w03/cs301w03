package cmput301W12.android.project.view;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cmput301W12.android.model.Container;
import cmput301W12.android.model.DbController;
import cmput301W12.android.model.Group;
import cmput301W12.android.model.SkinCondition;
import cmput301W12.android.model.SkinObserverApplication;
import cmput301W12.android.project.R;
import cmput301W12.android.project.controller.FController;
import cmput301W12.android.project.view.helper.SkinObserverIntent;


/**
 * 
 * @author MinhTri
 * @date Mar 16, 2012
 * 
 * This class is an activity for adding a new Group/Skin Condition
 * into the system.
 * 
 * Issue: Will be implemented Toast to give feedback to user
 * Note: Might be expanding if Group/Skin Condition evolve over time
 * 
 */
public class CreateContainerActivity extends Activity implements FView<DbController>{

	private EditText containerNameEditText;
	private Button addButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.create_container);
		setTitle(R.string.app_name);

		containerNameEditText = (EditText) findViewById(R.id.editTextNames);
		addButton = (Button) findViewById(R.id.buttonAdd);
		addButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v){
				String contName = containerNameEditText.getText().toString().trim();
				if (contName.length() > 0)
				{
					Container cont = null;
					Bundle bundle = getIntent().getExtras();
					if (bundle != null)
					{
						if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
							cont = new Group(contName);
						else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
							cont = new SkinCondition(contName);

						FController skinObserverController = 
							SkinObserverApplication.getSkinObserverController(getApplicationContext());
						
						cont = skinObserverController.addContainObj(cont);
						setResult(RESULT_OK);
						finish();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(),"The group's name cannot be empty!", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}


	@Override
	public void update(DbController model) {
	}


}
