package cmput301W12.android.project.view;

//import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import cmput301W12.android.project.PhotoListActivity;
import cmput301W12.android.project.R;
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
        Log.d("adapter","begin");
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
                // Add code here to start up camera activity */
                click1();
                Log.d("adapter","end promt");
                //Make a camera controller
                //TakeAPhotoActivity myCamera = new TakeAPhotoActivity();
                //myCamera.getPhoto();
               //startActivity(showphoto);
            	
                
                // TODO Auto-generated method stub
                
            }
        });
        
        
    }
    
    protected void click1()
    {
    	Intent viewGroup = new Intent(this, ViewContainerListActivity.class);
        startActivity(viewGroup);
    }
}
