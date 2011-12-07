package edu.purdue.cs252.lab06;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CallActivity extends Activity
{
	public static Handler UIhandler;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);
        
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");
        final String userAddress = extras.getString("userAddress");
        String serverAddress = extras.getString("serverAddress");
        
        Button btn = null;
        btn = (Button) findViewById(R.id.widget33); 
        btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				DirectoryClient dc = new DirectoryClient(DirectoryClient._socket, UIhandler);
				
				try
				{
					dc.hangUp(userAddress);
					displayHangup();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        });
        
        TextView tV = null;
        tV = (TextView) findViewById(R.id.callerName); 
        tV.setText(username);
        
        setupHandler();
        
        
        /*IntentFilter filter = new IntentFilter();
        filter.addAction("hangup.the.fucking.phone");
        registerReceiver(cr, filter);*/
        
        // socket is available at `DirectoryClient._socket;`
    }
	
	public void setupHandler()
    {
    	UIhandler = new Handler() {    		
    		public void handleMessage(Message msg)
    		{
    			if (msg.what == 2)
    			{
    				displayHangup();
    			}
    		}
    	};
    	
    	Log.i("CallActivity", this.toString());
    	CallReceiver cr = new CallReceiver(UIhandler);
    }
	
	public void displayHangup()
    {
    	finish();
    }
}
