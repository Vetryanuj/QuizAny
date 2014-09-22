/*
 * CheckWIFI service:
 * The service start when the app is lauched,
 * keep checking if the wifi service is on every minutes.
 * 
 * */


package service;


import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

public class CheckWifi extends Service {
	private boolean quit = false;
	private WifiManager wifiManager;
	
	public void onCreate() {
		super.onCreate();
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				while (!quit) {
					try {
						Thread.sleep(1000*60);	// check every 1 seconds
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
					WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
                                        if (wifiManager.isWifiEnabled() && ipAddress != 0) {

                                        } else {
                                        	Toast.makeText(getApplicationContext(), "Wifi disconnected.\n Please turn it on!", Toast.LENGTH_LONG).show();
                                        	
                                        }
				}
			}
		}.start();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}