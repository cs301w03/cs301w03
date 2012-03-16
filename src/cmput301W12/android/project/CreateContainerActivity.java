package cmput301W12.android.project;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//This is a test merge.
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
		Log.d("CreateContainer","1");
		mAddButton.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Log.d("CreateContainer","2...");
				if (mNameText.getText().toString()== "")
				{
					//Toast.makeText(PhotoListActivity.this, "Please enter a name!", Toast.LENGTH_SHORT).show();
					Log.d("CreateContainer","null string");
				}
				else
				{
					Log.d("CreateContainer","not null...");
					storeContainer();
				}
			}
		});
		
	}
	
	protected void storeContainer()
	{
		Container cont;
		Log.d("CreateContainer","creating...");
		if (getIntent() ==null )
			Log.d("createcontainer","intent is null");
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
				cont = new Group(mNameText.getText().toString());
			else
				cont = new SkinCondition(mNameText.getText().toString());
			
			FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
			cont = skinObserverController.addContainObj(cont);
			Log.d("container id", cont.getItemId() + "");
			setResult(RESULT_OK);
			finish();
		}
	}

	
}
