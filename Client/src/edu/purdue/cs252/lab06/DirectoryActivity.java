package edu.purdue.cs252.lab06;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DirectoryActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);
        
        setupBindings();
    }
    
    public void setupBindings()
	{
		Button loginButton = (Button)findViewById(R.id.button1);
		loginButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v)
			{
				EditText username = (EditText) findViewById(R.id.editText1);
				
				if (!username.getText().toString().equals(""))
				{
					Intent i = new Intent(DirectoryActivity.this, DirectoryActivity.class);
					i.putExtra("username",username.getText().toString());
					
					startActivity(i);
				}
				else
				{
				   AlertDialog.Builder adb = new AlertDialog.Builder(DirectoryActivity.this);
				   adb.setTitle("Error");
				   adb.setMessage("Please input your user ID");
				   adb.setPositiveButton("Ok", null);
				   adb.show();
				}	       
	        }
        });
	}
    
    public void updateDirectory(ArrayList<User> directory)
    {
    	// TODO: implement this method
    }
    
    public void displayIncomingCall(String username, String ipAddress)
    {
    	// TODO: implement this method
    }
    
    public void displayHangup()
    {
    	// TODO: implement this method
    }
    
    public void displayBusy()
    {
    	// TODO: implement this method
    }
}