package cmput301W12.android.project.view;

//import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cmput301W12.android.project.ComparePhotoActivity;
import cmput301W12.android.project.PhotoListActivity;
import cmput301W12.android.project.R;
import cmput301W12.android.project.RemindersListActivity;
import cmput301W12.android.project.SkinObserverIntent;
import cmput301W12.android.project.ViewContainerListActivity;

    /**
     * The ProjectTwoActivity.java file displays a menu that allows the user
     * to either take a picture, or to view their photo library.
     * 
     * @author Stephen Romansky
     *
     */


public class ProjectTwoActivity extends Activity
{

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button takeAPhoto = (Button) this.findViewById(R.id.takeAPhoto);
        takeAPhoto.setOnClickListener(new View.OnClickListener()
        {   
            @Override
            public void onClick(View v)
            {
                // Add code here to start up camera activity */

                Intent takeAphoto = new Intent(ProjectTwoActivity.this, TakeAPhotoActivity.class);
                startActivity(takeAphoto);
                
                //Make a camera controller
                //TakeAPhotoActivity myCamera = new TakeAPhotoActivity();
                //myCamera.getPhoto();
               //startActivity(showphoto);
            	
                
                // TODO Auto-generated method stub   
            }
        });
        
        Button groupByGroup = (Button) this.findViewById(R.id.groupByGroup);
        groupByGroup.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
            	callViewGroup();
            }
        });
        
        Button groupByCondition = (Button) this.findViewById(R.id.groupByCondition);
        groupByCondition.setOnClickListener(new View.OnClickListener()
    	{
        	@Override
        	public void onClick(View v)
        	{
        		callViewSkinCondition();
        	}
    	});
        
        Button viewAllPhoto = (Button) this.findViewById(R.id.viewAllPhotos);
        viewAllPhoto.setOnClickListener(new View.OnClickListener()
    	{
        	@Override
        	public void onClick(View v)
        	{
        		callViewAllPhoto();
        	}
    	});
        
        Button menu = (Button) this.findViewById(R.id.mainMenu);
        menu.setOnClickListener(new View.OnClickListener()
    	{
        	@Override
        	public void onClick(View v)
        	{
        		comparePhotos();
        	}
    	});
        
        Button reminders = (Button) this.findViewById(R.id.reminders);
        reminders.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				callReminderLists();
			}
		});
        
        Button exit = (Button) this.findViewById(R.id.close_button);
        exit.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
		
				// TODO Auto-generated method stub
				finish();
				
			}
		});
	}
        
    /* The following are utility functions, their names should explain exactly what they do. */
    
    protected void callViewGroup()
    {
    	Intent iViewGroup = new Intent(this, ViewContainerListActivity.class);
    	iViewGroup.putExtra(SkinObserverIntent.DATA_GROUP, SkinObserverIntent.DATA_GROUP);
        startActivity(iViewGroup);
    }

	protected void callViewSkinCondition()
	{
		Intent iViewGroup = new Intent(this, ViewContainerListActivity.class);
		iViewGroup.putExtra(SkinObserverIntent.DATA_SKIN_CONDITION, SkinObserverIntent.DATA_SKIN_CONDITION);
		startActivity(iViewGroup);
	}
	
	protected void callViewAllPhoto()
	{
		Intent iViewGroup = new Intent(this, PhotoListActivity.class);
		startActivity(iViewGroup);
	}
	
	protected void comparePhotos()
	{
		Intent iViewGroup = new Intent(this, ComparePhotoActivity.class);
		startActivity(iViewGroup);
	}
	
	protected void callReminderLists()
	{
		Intent iViewGroup = new Intent(this, RemindersListActivity.class);
		startActivity(iViewGroup);
	}
    
}
