package com.cs252.lab06;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DirectoryScreen extends ListActivity implements OnItemClickListener{
	private ProgressDialog connectDialog;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directoryscreen);
        
        connectDialog = new ProgressDialog(this);
        connectDialog.setMessage("Connecting...");
		connectDialog.setCancelable(true);
		connectDialog.show();
		
        Bundle extras = getIntent().getExtras(); // Get user item from CS252lab06Activity
        String username = extras.getString("username");
        
	    setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , loginProcess(username)));
	    connectDialog.dismiss();
	    
	    final ListView lv1 = getListView();
        lv1.setTextFilterEnabled(true);
        lv1.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        	AlertDialog.Builder adb=new AlertDialog.Builder(DirectoryScreen.this);
        	adb.setTitle("LVSelectedItemExample");
        	adb.setMessage("Selected Item is = " + lv1.getItemAtPosition(position));
        	adb.setPositiveButton("Ok", null);
        	adb.setNegativeButton("No", null);
        	adb.show();
        	}
        	});
	    
    }

	public String[] loginProcess(String username){
		try {
        	DirectoryClient dc = new DirectoryClient("lore.cs.purdue.edu");                 
          	
        	dc.connect();
          	dc.addUser(username);  // IP address of user?
                  
            ArrayList<User> directory = dc.getDirectory();
            int i = 0;
            for (User u : directory) {
                i++;
            }  // Stupid... 
            String [] directoryElement = new String [i];
            i = 0; 
            for (User u : directory) {
            	directoryElement [i++] = u.getUsername();
            }
            return directoryElement;
        } catch (Exception e){
        	//Show error pop up window and return to previous activity
        	finishActivity(0);
        }
		finishActivity(0);
		return null;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}


	
}
