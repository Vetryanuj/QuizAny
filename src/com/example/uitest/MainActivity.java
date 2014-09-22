/*
 * Project: QuiZany  TeamName: Narya 
 * Team Member: Yu Wu (yuwu1), Chi Zhang (chizhan1), Jialiang Lin (jialianl)
 * 
 * QuiZany is designed for professors to give out quiz or take attendance on class;
 * Student can submit their quiz answers or current geo-location through their android device.
 * Professor can collect answers using his/her computer. Results can be exported into
 * an Excel file for record and future use.
 * Also, there are couple of extended function for student to use:
 * 1. Take Notes: Student can take notes by using notepad, audio recorder, video recorder or camera
 * 2. Asking Questions: Email Professor to ask questions. 
 * 
 * Main Activity:
 * Provide log in blank, access database to verify the username and password;
 * 
 */

package com.example.uitest;


import costumexception.LoginException;
import ws.local.SessionManager;

import DBLayout.LoginDataBaseAdapter;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	Button buttonSignup = null;
	Button login=null;
	Button cancel=null;
	
	 // Session Manager Class
    SessionManager session;
	
	LoginDataBaseAdapter loginDataBaseAdapter;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// create a instance of SQLite Database
	    loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	    loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	    // Session Manager		
        session = new SessionManager(getApplicationContext());
	
		buttonSignup = (Button)findViewById(R.id.buttonSignUp);
		buttonSignup.setOnClickListener(new OnClickListenerSignUp());
		login=(Button)findViewById(R.id.login);
		login.setOnClickListener(new OnClickListenerl1());
		cancel=(Button) findViewById(R.id.button1);
		cancel.setOnClickListener(new OnClickListener2());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

	private class OnClickListenerl1 implements OnClickListener{
	
	@Override
	public void onClick(View arg0) {
		try {
			signIn(arg0);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	private class OnClickListener2 implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			EditText eText1= (EditText) findViewById(R.id.editText1);
			EditText eText2= (EditText) findViewById(R.id.editText2);
			eText1.setText("");
			eText2.setText("");// TODO Auto-generated method stub
			
		}
		
	}
	
	private class OnClickListenerSignUp implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
			startActivity(intentSignUP);
			
		}
		
	}
	
	// Methos to handleClick Event of Sign In Button
		public void signIn(View V) throws LoginException
		   {
		
			    // get the Refferences of views
			    final  EditText editTextUserName=(EditText)findViewById(R.id.editText1);
			    final  EditText editTextPassword=(EditText)findViewById(R.id.editText2);
			    
				Button btnSignIn=(Button)findViewById(R.id.login);
					
				// Set On ClickListener
				btnSignIn.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v){
						// get The User name and Password
						String userName=editTextUserName.getText().toString();
						String password=editTextPassword.getText().toString();
						
						
						// fetch the Password form database for respective user name
						String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
						String andrewid = loginDataBaseAdapter.getAdrewID(userName);
						
						// check if the Stored password matches with  Password entered by user
						if(password.equals(storedPassword))
						{
							Toast.makeText(MainActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
							
							// create login session
							session.createLoginSession(userName, andrewid);
							
							// start menu activity
							Intent intent= new Intent();
							intent.setClass(MainActivity.this, MainManuActivity.class);
							intent.setAction("service.CheckWifi");
							startService(intent);
							MainActivity.this.startActivity(intent);
						}
						else
						{	
							Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
						}
					}
				});
				
		}
}
