package cmput301W12.android.project;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


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
public class ViewCreateContainerActivity extends Activity implements FView<DbController>{

	private EditText mNameText;
	private Button mAddButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.create_container);
		setTitle(R.string.app_name);

		mNameText = (EditText) findViewById(R.id.editTextNames);
		mAddButton = (Button) findViewById(R.id.buttonAdd);
		mAddButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v){
				String contName = mNameText.getText().toString().trim();
				if (contName.length() > 0)
				{
					storeContainer(contName);
				}
				else
				{
					Toast.makeText(getApplicationContext(),"The group's name cannot be empty!", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	private void storeContainer(String contName)
	{
		Container cont = null;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
				cont = new Group(contName);
			else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
				cont = new SkinCondition(contName);

			FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
			cont = skinObserverController.addContainObj(cont);
			setResult(RESULT_OK);
			finish();
		}
	}

	@Override
	public void update(DbController model) {
	}


}
