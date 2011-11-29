package com.cs252.lab06;
//Written by Junyoung Justin Kim

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CS252lab06Activity extends Activity {
	private Button loginButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        buttonSetup();
    }
    
    public void buttonSetup(){
    	loginButton = (Button)findViewById(R.id.loginButton);// Login button
    	loginButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) {
    			EditText username = (EditText) findViewById(R.id.loginID);
    			if (!username.getText().toString().equals("")) { // If the id field is not empty
					Intent i = new Intent(CS252lab06Activity.this, DirectoryScreen.class);
					i.putExtra("username",username.getText().toString());
					startActivity(i);
				} else {
					AlertDialog.Builder adb=new AlertDialog.Builder(CS252lab06Activity.this);
		        	adb.setTitle("Error");
		        	adb.setMessage("Please input your user ID");
		        	adb.setPositiveButton("Ok", null);
		        	adb.show();
				}
    			
    		}
    	});
    }
}