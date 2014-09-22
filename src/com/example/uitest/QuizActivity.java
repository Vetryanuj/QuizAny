/*
 * Navigate two different kinds of quiz;
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

public class QuizActivity extends Activity {
	Button mChoice;
	Button quantity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
		mChoice=(Button) findViewById(R.id.button1);
		quantity=(Button) findViewById(R.id.button2);
		mChoice.setOnClickListener(new OnClickListenermChoice());
		quantity.setOnClickListener(new OnClickListenerquantity());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
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
	private class OnClickListenermChoice implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
		//	submit.setBackgroundColor(RESULT_OK);
			Intent intent=new Intent();
			intent.setClass(QuizActivity.this, MChoiceActivity.class);
			QuizActivity.this.startActivity(intent);
			// TODO Auto-generated method stub
			
		}
	}
	private class OnClickListenerquantity implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
		//	submit.setBackgroundColor(RESULT_OK);
			Intent intent=new Intent();
			intent.setClass(QuizActivity.this, QuantityActivity.class);
			QuizActivity.this.startActivity(intent);
			// TODO Auto-generated method stub
			
		}
	}

}
