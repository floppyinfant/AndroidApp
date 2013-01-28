package com.floppyinfant.android.data;

import com.floppyinfant.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DataEditActivity extends Activity {
	
	private EditText mTitle;
	private EditText mText;
	private Long mRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_edit);
        
        mTitle = (EditText) findViewById(R.id.title);
        mText = (EditText) findViewById(R.id.text);

        Button ok = (Button) findViewById(R.id.confirm);

        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(DatabaseManager.KEY_NAME);
            String text = extras.getString(DatabaseManager.KEY_TEXT);
            mRowId = extras.getLong(DatabaseManager.KEY_ROWID);

            if (title != null) {
                mTitle.setText(title);
            }
            if (text != null) {
                mText.setText(text);
            }
        }

        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString(DatabaseManager.KEY_NAME, mTitle.getText().toString());
                bundle.putString(DatabaseManager.KEY_TEXT, mText.getText().toString());
                if (mRowId != null) {
                    bundle.putLong(DatabaseManager.KEY_ROWID, mRowId);
                }

                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
    }
}
