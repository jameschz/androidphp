package com.app.demos.special.demo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.app.demos.special.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DemoMedia extends Activity {
	
	private static final String MUSIC_DIR = "/sdcard/";
	private List<String> musicList = new ArrayList<String>();
	private boolean musicIsPaused = false;
	private int musicNo = 0;
	
	private MediaPlayer mediaPlayer;
	private ListView musicListView;
	private Button btnPlayStart;
	private Button btnPlayStop;
	private Button btnPlayPrev;
	private Button btnPlayNext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_media);
		
		musicList();
		
		mediaPlayer = new MediaPlayer();
		
		btnPlayStart = (Button) this.findViewById(R.id.demo_media_btn_play_start);
		btnPlayStart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (musicIsPaused) {
					mediaPlayer.start();
					musicIsPaused = false;
				} else {
					musicPlay(MUSIC_DIR + musicList.get(musicNo));
				}
			}
		});
		
		btnPlayStop = (Button) this.findViewById(R.id.demo_media_btn_play_stop);
		btnPlayStop.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
					musicIsPaused = true;
				}
			}
		});
		
		btnPlayPrev = (Button) this.findViewById(R.id.demo_media_btn_play_prev);
		btnPlayPrev.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				musicPlayPrev();
			}
		});
		
		btnPlayNext = (Button) this.findViewById(R.id.demo_media_btn_play_next);
		btnPlayNext.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				musicPlayNext();
			}
		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mediaPlayer != null) {
			switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
					mediaPlayer.stop();
					mediaPlayer.release();
					mediaPlayer = null;
					break;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void musicPlay(String path) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
			mediaPlayer.start();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener(){
				@Override
				public void onCompletion(MediaPlayer mp) {
					musicPlayNext();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void musicPlayNext() {
		if (++musicNo >= musicList.size()) {
			musicNo = 0;
		}
		musicPlay(MUSIC_DIR + musicList.get(musicNo));
	}
	
	private void musicPlayPrev() {
		if (--musicNo < 0) {
			musicNo = musicList.size() - 1;
		}
		musicPlay(MUSIC_DIR + musicList.get(musicNo));
	}
	
	private void musicList() {
		File musicDir = new File(MUSIC_DIR);
		File[] musicFiles = musicDir.listFiles(new MusicFilter());
		if (musicFiles.length > 0) {
			for (File file : musicFiles) {
				musicList.add(file.getName());
			}
			musicListView = (ListView) this.findViewById(R.id.demo_media_list_music);
			ArrayAdapter<String> musicListAdapter = new ArrayAdapter<String>(this, 
					R.layout.list_music_item, 
					R.id.list_music_item_text_name, 
					musicList);
			musicListView.setAdapter(musicListAdapter);
		}
	}
	
	private class MusicFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String filename) {
			return filename.endsWith(".mp3");
		}
	}
}