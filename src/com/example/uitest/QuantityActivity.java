/*
 * Quantity Activity:
 * Allow student to type in a short answer and submit the string on to server
 * 
 */


package com.example.uitest;

import ws.local.SessionManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import Data.DataStore;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuantityActivity extends Activity implements OnClickListener 
{
	//private EditText raw = null;
	//private String result = null;
	private Button submit;
	private String ip = null;
	private final int port =1820;
	SessionManager session;
	private String name;
	private String andrewid;
	private String message;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quantity);
		
		ip = DataStore.getInstance().getIP();
		if(ip == null)
		{
			Toast message = Toast.makeText(getApplicationContext(), "IP not set! Please set IP first!", Toast.LENGTH_LONG);
			message.show();
		}
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		//raw = (EditText)findViewById(R.id.editText1);
		//result = raw.getText().toString();
				
		// Session class instance
        session = new SessionManager(getApplicationContext());
        
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        andrewid = user.get(SessionManager.KEY_EMAIL);
		submit = (Button) findViewById(R.id.button1);
        submit.setOnClickListener(this);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quantity, menu);
		return true;
	}
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
           
            case R.id.action_setting:
            	Intent intent = new Intent(this, SettingActivity.class);
        		startActivity(intent);
            	return true;
        }
        return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onClick(View arg0) {
		
		
		
		// TODO Auto-generated method stub
		try 
		{
    		
			dialog = ProgressDialog.show(this, "Submitting", "Connecting and Submitting...");
			    Thread thread=new Thread(new Runnable()  
		        {  
		            @Override  
		            public void run()  
		            {  
		        		try {
		        			String result = ((EditText)findViewById(R.id.quantityAnswer)).getText().toString();
		        			
		        			SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd hh:mm:ss",java.util.Locale.getDefault());       
		        			String    date    =    sDateFormat.format(new    java.util.Date());  
		        			
		        	    	message = "quiz  "+andrewid +"  "+name+"  "+result+"  "+date+ "\n";
		        	    	
		        			Socket socket=new Socket(ip,port);
		        			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
		        					socket.getOutputStream()));
		        			writer.write(message);
		        			writer.flush();
		        			writer.close();
		        			socket.close();
		        			dialog.dismiss();
		        			runOnUiThread(new Runnable(){
		        		          public void run() {
		        		        	  Toast.makeText(getApplicationContext(),
		        								"Your answer has been submitted!",
		        								Toast.LENGTH_SHORT).show();
		        		          }
		        			});
		        		} catch (UnknownHostException e) {
		        			e.printStackTrace();
		        		} catch (IOException e) {
		        			e.printStackTrace();
		        			dialog.dismiss();
		        			runOnUiThread(new Runnable(){
		        		          public void run() {
		        		        	  Toast.makeText(getApplicationContext(),
		        								"Connection Fail! Please Check the IP or Turn ON the Server!!",
		        								Toast.LENGTH_SHORT).show();
		        		                }
		        		     });
		        		}	            	
		            }  
		        });  
		        thread.start();  
			
			}
			
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}
	}

}
