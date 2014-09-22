/*
 * RecordActivity:
 * Allow student to use video recorder to record the lecture;
 * student can play back the recent video
 * and also can access the folder where stores all the notes; 
 * 
 */

package com.example.uitest;

import java.io.File;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class RecordActivity extends Activity implements SurfaceHolder.Callback{
	
	private MediaRecorder recorder = null;
	private Camera camera;
	private static final String OUTPUT_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath()+"/quizany_notes";
	private static final String filename= "video_classnote";
	private static final String TAG = "RecordVideo";
	private VideoView videoView = null;
	private Button startBtn = null;
	private int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
		startBtn = (Button) findViewById(R.id.bgnBtn);
		Button endBtn = (Button) findViewById(R.id.stpBtn);
		Button playRecordingBtn = (Button) findViewById(R.id.playRecordingBtn);
		Button stpPlayBtn= (Button) findViewById(R.id.stpPlay);
		Button openBtn = (Button) findViewById(R.id.openfolder);
		
		videoView = (VideoView)this.findViewById(R.id.videoView);
		
		final SurfaceHolder holder = videoView.getHolder();
		holder.addCallback(this);
	
		startBtn.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View view) 
		{
			try 
			{
				beginRecording(holder);
			} catch (Exception e) 
			{
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
		}
	});

	endBtn.setOnClickListener(new OnClickListener() 
	{
		@Override
		public void onClick(View view) 
		{
			try 
			{
				stopRecording();
			} 
			catch (Exception e) 
			{
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
		}
	});

	playRecordingBtn.setOnClickListener(new OnClickListener() 
	{
		@Override
		public void onClick(View view) 
		{
			try 
			{
				playRecording();
			} 
			catch (Exception e) 
			{
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
		}
	});
	stpPlayBtn.setOnClickListener(new OnClickListener() 
	{
		@Override
		public void onClick(View view) 
		{
			try 
			{
				stopPlayingRecording();
			} 
			catch (Exception e) 
			{
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
		}
	});
	openBtn.setOnClickListener(new OnClickListener() 
	{
		@Override
		public void onClick(View view) 
		{
			try 
			{
				openfolder();
			} 
			catch (Exception e) 
			{
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
		}
	});
	
}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		Log.v(TAG, "Width x Height = " + width + "x" + height);
	}
	
	private void playRecording() {
		
		
		MediaController mc = new MediaController(this);
		videoView.setMediaController(mc);
		videoView.setVideoPath(OUTPUT_FOLDER+filename+i+".mp4");
		videoView.start();
	}
	
	private void stopPlayingRecording() {
		videoView.stopPlayback();
	}
	
	private void stopRecording() throws Exception {
		
			recorder.stop();
			recorder.reset();
			camera.release();
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (recorder != null) 
		{
			recorder.release();
		}
	}
	
	private void beginRecording(SurfaceHolder holder) throws Exception 
	{
		if(recorder!=null)
		{
			//recorder.stop();
			camera.release();
			recorder.reset();
		}
		File direc = new File(OUTPUT_FOLDER);
		if(!direc.exists())
		{
			direc.mkdirs();			
		}
		File outfile = new File(OUTPUT_FOLDER+filename+i+".mp4");
		
		if (outfile.exists()){
			i++;
		}
		try 
		{
			camera =Camera.open();
			camera.setDisplayOrientation(90);
			camera.unlock();
			
			recorder = new MediaRecorder();
			recorder.setCamera(camera);
			recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
			recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setVideoSize(640, 480);
			recorder.setVideoFrameRate(30);
			recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.setPreviewDisplay(holder.getSurface());
			recorder.setOutputFile(OUTPUT_FOLDER+filename+i+".mp4");
			recorder.setOrientationHint(90);
			recorder.prepare();
			recorder.start();
		}
		catch(Exception e) 
		{
			Log.e(TAG, e.toString());
			e.printStackTrace();
		}
		
	}
	private void openfolder() {
		File file = new File(Environment.getExternalStorageDirectory(),
			    "quizany_notes");

			Log.d("path", file.toString());

			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setDataAndType(Uri.fromFile(file), "*/*");
			startActivity(intent);
	}
}
