package com.floppyinfant.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Form extends Activity {
	
	private EditText mET1;
	private EditText mET2;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        
        mET1 = (EditText) findViewById(R.id.form_edit_text);
        mET2 = (EditText) findViewById(R.id.form_edit_text2);
    }

	/**
	 * Zwischenspeichern von Eingaben, falls die Activity unterbrochen wird.
	 * 
	 * @see /ApiDemos/src/com/example/android/apis/app/PersistentState.java
	 */
	@Override
	protected void onPause() {
		super.onPause();
		
		final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString("" + R.id.form_edit_text, mET1.getText().toString());
		editor.putString("" + R.id.form_edit_text2, mET2.getText().toString());
		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		String text1 = prefs.getString("" + R.id.form_edit_text, null);
		if (text1 != null) {
			mET1.setText(text1, TextView.BufferType.EDITABLE);
		}
		String text2 = prefs.getString("" + R.id.form_edit_text2, null);
		if (text2 != null) {
			mET2.setText(text2, TextView.BufferType.EDITABLE);
		}
	}
    
    
}
