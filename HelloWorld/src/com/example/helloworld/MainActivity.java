package com.example.helloworld;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "LifeCycleMethods";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		TextView tv = new TextView(this); 
		tv.setText("Hello World"); 
		setContentView(tv); 
		Log.i(TAG, "OnCreate Method Called");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "OnPause Method Called");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.i(TAG, "OnRestart Method Called");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "OnResume Method Called");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.i(TAG, "OnStart Method Called");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, "OnStop Method Called");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i(TAG, "OnDestroy Method Called");
		super.onDestroy();
	}
	
	
}
