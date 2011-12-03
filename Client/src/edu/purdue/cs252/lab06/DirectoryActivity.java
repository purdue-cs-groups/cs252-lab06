package edu.purdue.cs252.lab06;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
        
        connectToServer();
    }
    
    public void connectToServer()
    {
    	Bundle extras = getIntent().getExtras();
    	String serverAddress = extras.getString("serverAddress");
    	String username = extras.getString("username");
        
        try
        {
            DirectoryClient dc = new DirectoryClient(serverAddress, this);
              
            dc.connect();
            dc.addUser(username);
        }
        catch (Exception ex)
        {
        	AlertDialog.Builder adb = new AlertDialog.Builder(DirectoryActivity.this);
			   
		   adb.setTitle("Error");
		   adb.setMessage("Could not connect to server. Please try again later!");
		   adb.setPositiveButton("OK", null);
		   adb.show();
        }
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
