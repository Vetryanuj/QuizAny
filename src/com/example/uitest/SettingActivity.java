/*
 * Setting Activity:
 * Set up server's ip address
 *  
 */


package com.example.uitest;

import Data.DataStore;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

public class SettingActivity extends Activity implements OnClickListener
{
	private Button ok;
	private String ip;
	private Toast message;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener(this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		ip = ((EditText)findViewById(R.id.ip_professor)).getText().toString();
		DataStore.getInstance().setIP(ip);
		message = Toast.makeText(getApplicationContext(), "IP Setted", Toast.LENGTH_LONG);
		message.show();
	}

}
