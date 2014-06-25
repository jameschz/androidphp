package com.app.demos.special.demo;

import java.util.ArrayList;

import com.app.demos.special.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DemoVoice extends Activity {
	
	private static final int VOICE_RESULT_REQUEST_CODE = 1001;
	
	Button btnStartRecord;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_voice);
		
		btnStartRecord = (Button) this.findViewById(R.id.demo_voice_btn_start_record);
		btnStartRecord.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Record");
					startActivityForResult(intent, VOICE_RESULT_REQUEST_CODE);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(DemoVoice.this, "Can not find device", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RESULT_REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			StringBuffer sb = new StringBuffer();
			int resultCount = results.size();
			for (int i = 0; i < resultCount; i++) {
				sb.append(results.get(i));
			}
			Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}