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
	String serverAddress;
	String username;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);
        
        Bundle extras = getIntent().getExtras();
    	serverAddress = extras.getString("serverAddress");
    	username = extras.getString("username");
        
        connectToServer();
    }
    
    public void connectToServer()
    {
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
			adb.setMessage(ex.getMessage());
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
