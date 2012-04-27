package com.floppyinfant.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.EditText;

public class Form extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
    }

	/**
	 * Zwischenspeichern von Eingaben, falls die Activity unterbrochen wird.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		
		final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString("" + R.id.form_edit_text, "" + ((EditText) findViewById(R.id.form_edit_text)).getText());
		editor.putString("" + R.id.form_edit_text2, "" + ((EditText) findViewById(R.id.form_edit_text2)).getText());
		editor.commit();
	}
    
    
}
