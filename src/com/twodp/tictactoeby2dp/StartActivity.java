package com.twodp.tictactoeby2dp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class StartActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

		   public void run() {
			   Intent intent = new Intent(StartActivity.this, MenuActivity.class);
			   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			   startActivity(intent);
			   finish();
		   }
		}, 1500);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	@Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
	
	
}
