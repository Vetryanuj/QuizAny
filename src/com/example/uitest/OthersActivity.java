/*
 * Others Activity:
 * Navigate extra functions provided by the app.
 * 
 */

package com.example.uitest;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class OthersActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_others);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.others, menu);
		return true;
	}
	public void goToTakingNotesSelectMenu(View view){
		Intent intent = new Intent(this, TakeNotesActivity.class);
		startActivity(intent);
		}
	public void goToContact(View view){
		Intent intent = new Intent(this, ContactActivity.class);
		startActivity(intent);
		}
	public void goToComming(View view){
		Intent intent = new Intent(this, CommingActivity.class);
		startActivity(intent);
		}

}
