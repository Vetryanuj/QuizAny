/*
 * Navigate four different kinds of notes that user can make
 * 
 */

package com.example.uitest;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class TakeNotesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_notes);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_notes, menu);
		return true;
	}
	
	public void goToRecord(View view){
		Intent intent = new Intent(this, RecordActivity.class);
		startActivity(intent);
		}
	public void goToAudio(View view){
		Intent intent = new Intent(this, AudioActivity.class);
		startActivity(intent);
		}
	public void goToText(View view){
		Intent intent = new Intent(this, TextActivity.class);
		startActivity(intent);
		}
	public void goToCamera(View view){
		Intent intent= new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(intent,Activity.DEFAULT_KEYS_DIALER);
	}

}
