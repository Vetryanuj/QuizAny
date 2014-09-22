/*
 * Main Manu Activity:
 * Navigate all the functions the app may provided; 
 * 
 */

package com.example.uitest;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainManuActivity extends Activity {
	Button quiz=null;
	Button att=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_manu);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		quiz=(Button) findViewById(R.id.button1);
		att=(Button) findViewById(R.id.button2);
		quiz.setOnClickListener(new OnClickListener2());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_manu, menu);
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
	
	private class OnClickListener2 implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
		//	submit.setBackgroundColor(RESULT_OK);
			Intent intent=new Intent();
			intent.setClass(MainManuActivity.this, QuizActivity.class);
			MainManuActivity.this.startActivity(intent);
			// TODO Auto-generated method stub
			
		}
	}
	public void goToOthersMenu(View view){
		Intent intent = new Intent(this, OthersActivity.class);
		startActivity(intent);
		}
	public void goToAttendaceMenu(View view){
		Intent intent = new Intent(this, AttendaceActivity.class);
		startActivity(intent);
		}
	

}
