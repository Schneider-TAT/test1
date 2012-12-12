package com.example.testapp;	//テストアプリ

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final String RECORDED_AUDIO = "sound.3gp";
	private MediaPlayer mPlayer;
	private MediaRecorder mRecorder;
	private File mSound;
	private Button recordButton;
	private Button playButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSound = new File(getCacheDir(), RECORDED_AUDIO);
		recordButton = (Button) findViewById(R.id.recordbutton);
		playButton = (Button) findViewById(R.id.playbutton);
		recordButton.setOnClickListener(new RecordButtonClickListener());
		playButton.setOnClickListener(new PlayButtonClickListener());
		if (mSound.exists()) {
			playButton.setEnabled(true);
		} else {
			playButton.setEnabled(false);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mPlayer = new MediaPlayer();
		mRecorder = new MediaRecorder();
		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				playButton.setText(R.string.Play_Voice);

			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPlayer.release();
		mRecorder.release();

	}

	public class RecordButtonClickListener implements OnClickListener {

		public void onClick(View v) {
			if (mSound.exists()) {
				playButton.setEnabled(true);
			} else {
				playButton.setEnabled(false);
			}

			if (recordButton.getText().equals(getString(R.string.Record_Voice))) {

				recordButton.setText(R.string.Record_Stop);

				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				mRecorder.setOutputFile(mSound.getAbsolutePath());
				try {
					mRecorder.prepare();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}

				mRecorder.start();
			} else {
				recordButton.setText(R.string.Record_Voice);
				mRecorder.stop();
			}
		}

	}

	public class PlayButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (playButton.getText().equals(getString(R.string.Play_Voice))) {
				try {
					mPlayer.setDataSource(mSound.getAbsolutePath());
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					try {
						mPlayer.prepare();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				mPlayer.start();
			} else {
				playButton.setText(R.string.Play_Voice);
				mPlayer.stop();

			}

		}

	}

}
