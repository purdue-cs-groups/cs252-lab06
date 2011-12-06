package edu.purdue.cs252.lab06;
import java.io.IOException;
import java.util.ArrayList;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CallActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);
        
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");
        
        TextView tV = null;
        tV = (TextView) findViewById(R.id.callerName); 
        tV.setText(username);
    }
}
