package com.cs252.lab06;

import java.util.*;
import java.net.*;
import java.io.*;

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

public class DirectoryScreen extends ListActivity implements OnItemClickListener
{
    private ProgressDialog connectDialog;
    private ArrayList<String> usernames = new ArrayList<String>();
    private ArrayAdapter<String> aa = null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directoryscreen);
        
        connectDialog = new ProgressDialog(this);
        connectDialog.setMessage("Connecting...");
        connectDialog.setCancelable(true);
        connectDialog.show();
        
        Bundle extras = getIntent().getExtras(); // Get user item from CS252lab06Activity
        String username = extras.getString("username");
        
        
        loginProcess(username);
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames); 
    	setListAdapter(aa);
        
        connectDialog.dismiss();
        
        final ListView lv1 = getListView();
        lv1.setTextFilterEnabled(true);
        lv1.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {
            	AlertDialog.Builder adb=new AlertDialog.Builder(DirectoryScreen.this);
            	adb.setTitle("LVSelectedItemExample");
            	adb.setMessage("Selected Item is = " + lv1.getItemAtPosition(position));
            	adb.setPositiveButton("Ok", null);
            	adb.setNegativeButton("No", null);
            	adb.show();
            }
        });        
    }

    public void loginProcess(String username)
    {
        try
        {
            DirectoryClient dc = new DirectoryClient("lore.cs.purdue.edu", this);                 
              
            dc.connect();
            dc.addUser(username);
            
            //dc.getDirectory();
        }
        catch (Exception ex)
        {
            // TODO: handle this exception
        }
        
        finishActivity(0);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
    	// TODO: implement this method
    }

    public void updateDirectory(ArrayList<User> directory)
    {
    	usernames.clear();
    	for (User u : directory)
    	{
    		usernames.add(u.getUsername());
    	}
    	aa.notifyDataSetChanged();
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
