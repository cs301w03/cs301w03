package cmput301W12.android.project;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateContainerActivity extends Activity {

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
		
		Activity activity = this;
		
		mAddButton.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if (mNameText.getText().toString().trim().length() > 0)
					;//Toast.makeText(PhotoListActivity.this, "Please enter a name!", Toast.LENGTH_SHORT).show();
				else
				{
					storeContainer();
				}
			}
		});
		
	}
	
	protected void storeContainer()
	{
		Container cont;
		
		Bundle bundle = getIntent().getExtras();
		if (bundle.getString(SkinObserverIntent.DATA_GROUP) == 
								SkinObserverIntent.DATA_GROUP)
			cont = new Group(mNameText.getText().toString());
		else
			cont = new SkinCondition(mNameText.getText().toString());
		
		FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
		skinObserverController.addContainObj(cont);
		
		setResult(RESULT_OK);
		finish();
	}
	
	public void start(){
		Container cont;
		
		Bundle bundle = getIntent().getExtras();
		if (bundle.getString(SkinObserverIntent.DATA_GROUP) == 
								SkinObserverIntent.DATA_GROUP)
			cont = new Group(mNameText.getText().toString());
		else
			cont = new SkinCondition(mNameText.getText().toString());
		
		//FController skinObserverController = SkinObserverApplication.getSkinObserverController(activity);
		//skinObserverController.addContainObj(cont);
		
		setResult(RESULT_OK);
		finish();
	}
	
}
