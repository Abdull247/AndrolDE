package org.ide.andriod;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class DebugActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug);

		TextView textview1 = findViewById(R.id.textview1);
		textview1.setTextColor(Color.RED);
		String error = getIntent().getStringExtra("error");
		textview1.setText(error != null ? error : "No error information.");
	}
}
