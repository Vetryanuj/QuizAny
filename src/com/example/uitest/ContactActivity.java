/*
 * ContactActivity:
 * Allow student to email professor for questions
 * 
 */

package com.example.uitest;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends Activity {

	private EditText nameField,emailField ,mobileField,commentField,pemailField;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
		nameField=(EditText) findViewById(R.id.editTextNaam);
        emailField=(EditText) findViewById(R.id.editTextMail);
        mobileField=(EditText) findViewById(R.id.editTextMobiel);
        commentField=(EditText) findViewById(R.id.editTextBericht);
        pemailField=(EditText) findViewById(R.id.pemail);
        Button send=(Button) findViewById(R.id.buttonSend);
        send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (nameField.getText().toString().length()==0)
					nameField.setError("Please Enter your name");
				else if (emailField.getText().toString().length()==0)
					emailField.setError("Please Enter your Email address");
				else if (mobileField.getText().toString().length()==0)
					mobileField.setError("Please Enter your Mobile Number");
				else if (commentField.getText().toString().length()==0)
					commentField.setError("Please enter your comment");
				else if (pemailField.getText().toString().length()==0)
					pemailField.setError("Please enter professor's email");
				else{
					String body= "Name: "+nameField.getText().toString()+"<br>Moble: "+mobileField.getText().toString()+"<br>Email: "+
								  emailField.getText().toString()+"<br>Comment: "+commentField.getText().toString();
					Intent email =new Intent(Intent.ACTION_SEND);
					
					email.putExtra(Intent.EXTRA_EMAIL, new String[]{pemailField.getText().toString()});
					email.putExtra(Intent.EXTRA_SUBJECT, "Question from QuiZany"); 
					email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
					email.setType("message/rfc822");
		            startActivityForResult(Intent.createChooser(email, "Emailing Prof."),1);
				}
				
			}
        	
        });
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    new AlertDialog.Builder(ContactActivity.this)
	    	.setMessage("Your Question has been Emailed\nThank You")
	    	.setCancelable(false)
	    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) 
	{
	  dialog.cancel();
	    }
	})  
	    .show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact, menu);
		return true;
	}

}
