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
 * @author Tri Lai
 *
 */
public class CreateContainerActivity extends Activity {

	private EditText containerNameEditText;
	private Button addButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.create_container);
		setTitle(R.string.app_name);

		containerNameEditText = (EditText) findViewById(R.id.editTextNames);
		addButton = (Button) findViewById(R.id.buttonAdd);
		addButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				String contName =containerNameEditText.getText().toString() ;
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
			}
		});

	}



}
