/*
 * AudioActivity:
 * Provide audio recorder function for student to record the lecture.
 * Student can playback the recent recordings;
 * Also, student can access the folder where stores all kinds of notes on sdcard. 
 * 
 */

package com.example.uitest;

import java.io.File;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;

public class AudioActivity extends Activity {

	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private static final String OUTPUT_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath()+"/quizany_notes";
	private static final String filename="audio_classnote";
	private int i;
	private ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		Button startBtn = (Button) findViewById(R.id.bgnBtn);
		Button endBtn = (Button) findViewById(R.id.stpBtn);
		Button playRecordingBtn = (Button) findViewById(R.id.playRecordingBtn);
		Button stpPlayingRecordingBtn = (Button) findViewById(R.id.stpPlay);
		Button openBtn =(Button) findViewById(R.id.openfolder);
		pb= (ProgressBar) findViewById(R.id.progressBar1);
		final Chronometer counter = (Chronometer) findViewById(R.id.chronometer1);
	
		startBtn.setOnClickListener(new OnClickListener() 
		{
				@Override
				public void onClick(View view) 
				{
					try {
							pb.setVisibility(View.VISIBLE);
				            counter.setBase(SystemClock.elapsedRealtime());
							counter.start();
							beginRecording();
					} catch (Exception e) {
							e.printStackTrace();
					}
				}
		});
		
		endBtn.setOnClickListener(new OnClickListener() 
		{
		@Override
		public void onClick(View view) {
			try {
					stopRecording();
					pb.setVisibility(View.INVISIBLE);
					counter.stop();
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
		});

		playRecordingBtn.setOnClickListener(new OnClickListener() 
		{
		@Override
		public void onClick(View view) {
			try {
					playRecording();
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
		});
		
		

		stpPlayingRecordingBtn.setOnClickListener(new OnClickListener() 
		{
		@Override
			public void onClick(View view) {
			try {
					stopPlayingRecording();
			} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		openBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				try{
					openfile();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
		
	private void beginRecording() throws Exception 
	{
		killMediaRecorder();
		File direc = new File(OUTPUT_FOLDER);
		if(!direc.exists())
		{
			direc.mkdirs();			
		}
		File outfile = new File(OUTPUT_FOLDER+filename+i+".3gpp");
		if (outfile.exists()){
			i++;
		}
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(OUTPUT_FOLDER + filename+ i+".3gpp");
		recorder.prepare();
		recorder.start();
	}
	
	private void stopRecording() throws Exception 
	{
		if (recorder != null) 
		{
			recorder.stop();
		}
	}

	private void killMediaRecorder() 
	{
		if (recorder != null) 
		{
			recorder.release();
		}
	}
	
	private void killMediaPlayer() 
	{
		if (mediaPlayer != null) 
		{
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void playRecording() throws Exception {
		killMediaPlayer();
		
		killMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(OUTPUT_FOLDER+filename+i+".3gpp");
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	private void stopPlayingRecording() throws Exception 
	{
		if(mediaPlayer!=null)
		{
			mediaPlayer.stop();
		}
	}
	
	private void openfile() throws Exception 
	{
		File file = new File(Environment.getExternalStorageDirectory(),
		    "quizany_notes");

		Log.d("path", file.toString());

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setDataAndType(Uri.fromFile(file), "*/*");
		startActivity(intent);
	}

	@Override
	protected void onDestroy() 
	{
			super.onDestroy();
			killMediaRecorder();
			killMediaPlayer();
	}
}
