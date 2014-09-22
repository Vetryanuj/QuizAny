/*
 * Multiple Choice Activity:
 * Provide function for student to select answers from a b c d;
 * User can simply tap submit to upload their choices.
 * 
 */

package com.example.uitest;

import ws.local.SessionManager;

import java.io.*;
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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class MChoiceActivity extends Activity implements OnClickListener {
	
	private RadioGroup  radioGroup=null;
    private RadioButton  radioButton_A,radioButton_B, radioButton_C, radioButton_D;
    private Button submit;
    private String result;
    private String ip = null;
	final private int port=1820;
	SessionManager session;
	private String name;
	private String andrewid;
	private String message;
	ProgressDialog dialog;
	
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mchoice);
		
		ip = DataStore.getInstance().getIP();
		if(ip == null)
		{
			Toast message = Toast.makeText(getApplicationContext(), "IP not set! Please set IP first!", Toast.LENGTH_LONG);
			message.show();
		}
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		radioGroup=(RadioGroup)findViewById(R.id.radioGroup1);
		
		radioButton_A=(RadioButton)findViewById(R.id.radio0);
	    radioButton_B=(RadioButton)findViewById(R.id.radio1);
	    radioButton_C=(RadioButton)findViewById(R.id.radio2);
	    radioButton_D=(RadioButton)findViewById(R.id.radioButton1);
	    
	    radioGroup.setOnCheckedChangeListener(listen);
	    
	 // Session class instance
        session = new SessionManager(getApplicationContext());
        
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        andrewid = user.get(SessionManager.KEY_EMAIL);
	    submit = (Button) findViewById(R.id.button1);
        submit.setOnClickListener(this);
	}

	 protected OnCheckedChangeListener  listen = new OnCheckedChangeListener() 
	 {
	          public void onCheckedChanged(RadioGroup group, int checkedId) 
	          {
	               
	               switch (group.getCheckedRadioButtonId()) 
	               {
	                   case R.id.radio0:
	    	                result = radioButton_A.getText().toString();
	    	                System.out.println(result);
                            break;
	    	           case R.id.radio1:
	    	                result = radioButton_B.getText().toString();
	    	                break;
	    	           case R.id.radio2:
	    	                result = radioButton_C.getText().toString();
                           break;
	    	           case R.id.radioButton1:
	    	                result = radioButton_D.getText().toString();
	    	                break;
	    	           default:
	                        result = null;
	    	                break;
	    	       }
	    	   }
	   };
	   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mchoice, menu);
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
	 public void onClick(View bt)
    {
		SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd hh:mm:ss",java.util.Locale.getDefault());       
		String    date    =    sDateFormat.format(new    java.util.Date());  
		
    	message = "quiz  "+andrewid +"  "+name+"  "+result+"  "+date+ "\n";
		try 
		{
			
			dialog = ProgressDialog.show(this, "Submitting", "Connecting and Submitting...");
			
			    Thread thread=new Thread(new Runnable()  
		        {  
		            @Override  
		            public void run()  
		            {  
		        		try {
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
    public void SendMsg(String ip,int port,String msg) throws UnknownHostException, IOException
    {    	
		try {
			Socket socket = new Socket(ip, port);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			writer.write(msg);
			writer.flush();
			writer.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
