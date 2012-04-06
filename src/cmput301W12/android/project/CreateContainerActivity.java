package cmput301W12.android.project;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Activity for adding a new container(either a group(tag) or a skin condition).
 * @author hieungo
 *
 */
public class CreateContainerActivity extends Activity {

	private EditText mNameText;
	private Button mAddButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.create_container);
		setTitle(R.string.app_name);

		mNameText = (EditText) findViewById(R.id.editTextNames);
		mAddButton = (Button) findViewById(R.id.buttonAdd);
		mAddButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mNameText.getText().toString() != "")
				{
					storeContainer();
				}
			}
		});

	}

	protected void storeContainer()
	{
		Container cont = null;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			if (bundle.getString(SkinObserverIntent.DATA_GROUP) != null)
				cont = new Group(mNameText.getText().toString());
			else if (bundle.getString(SkinObserverIntent.DATA_SKIN_CONDITION) != null)
				cont = new SkinCondition(mNameText.getText().toString());

			FController skinObserverController = SkinObserverApplication.getSkinObserverController(this);
			cont = skinObserverController.addContainObj(cont);
			Log.d("container id", cont.getItemId() + "");
			setResult(RESULT_OK);
			finish();
		}
	}


}
