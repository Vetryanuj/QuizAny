/*
 * Attendance Activity:
 * Using google map API, to find students' current location, mark it on the map,
 * student can submit their locations by tapping submit button; 
 * 
 */

package com.example.uitest;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import ws.local.SessionManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Data.DataStore;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class AttendaceActivity extends Activity implements OnClickListener{
	GoogleMap map;
	Intent intent;
	static double lat=1,lng=1;
	static String loc;
	private Button submit;
	private String ip = null;
	private final int port =1820;
	SessionManager session;
	private String name;
	private String andrewid;
	private String message;
	ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendace);
        
        submit = (Button) findViewById(R.id.button1);
        submit.setOnClickListener(this);
        
        ip = DataStore.getInstance().getIP();
		if(ip == null)
		{
			Toast message = Toast.makeText(getApplicationContext(), "IP not set! Please set IP first!", Toast.LENGTH_LONG);
			message.show();
		//	submit.setEnabled(false);
		}
        
        ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
		LocationManager lm= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener llisten =new mylocationlistener();
		
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		// choose location service from network provider and gps provider
		if (mWifi.isConnected()){
		if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, llisten);
		else lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, llisten);
		}
		else lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, llisten);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        session = new SessionManager(getApplicationContext());
        
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        andrewid = user.get(SessionManager.KEY_EMAIL);
			
		
    }
    private class mylocationlistener implements LocationListener{
    	Marker pos =null;
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@SuppressLint("NewApi")
		@Override
		public void onLocationChanged(Location location) {
			if(location!=null){
				Log.d("LOCATION CHANGED", location.getLatitude()+"");
				Log.d("LOCATION CHANGED", location.getLongitude()+"");
				lat= location.getLatitude();
				lng=location.getLongitude();
				loc= "My location is: Latitud= "+location.getLatitude()+" Longtitud= "+location.getLongitude();
				TextView printText=(TextView)findViewById(R.id.textView1);
				printText.setText(loc);
				LatLng current = new LatLng(lat,lng);
			       
		        
		        if (map!=null){
		           if (pos==null){
		            pos= map.addMarker(new MarkerOptions().position(current)
		                .title("Fking Home"+lat+lng));
		            }   
		        }
		        else {
		        	pos.setPosition(current);
		        }
		       
		        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current,20));}
		}
		

		@Override
		public void onProviderDisabled(String arg0) {
			Toast.makeText(getApplicationContext(), "GPS is disabled.\n Please turn it on!", Toast.LENGTH_LONG).show();
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
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
	@Override
	public void onClick(View v) {
		
		try 
		{
			dialog = ProgressDialog.show(this, "Submitting", "Connecting and Submitting...");
			
			
			    Thread thread=new Thread(new Runnable()  
		        {  
			    	
		            @Override  
		            public void run()  
		            {  
		        		try {		        			
		        			SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd hh:mm:ss",java.util.Locale.getDefault());       
		        			String date = sDateFormat.format(new java.util.Date());  
		        			
		        	    	message = "attendance  "+andrewid +"  "+name+"  "+lat+"  "+lng+"  "+date+ "\n";
		        			Socket socket=new Socket(ip,port);
		        			System.out.println("2");
		        			
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
		        								"Your location has been submitted!",
		        								Toast.LENGTH_SHORT).show();
		        		          }
		        			});
		        			
		        		} catch (UnknownHostException e) {
		        			e.printStackTrace();
		        			dialog.dismiss();
		        		
		        			
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
