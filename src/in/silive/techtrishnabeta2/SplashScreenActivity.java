package in.silive.techtrishnabeta2;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashScreenActivity extends Activity {

	private int DELAY_TIME = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent i;
				i = new Intent(

				"in.silive.techtrishnabeta2.MAINACTIVITY");

				startActivity(i);
				overridePendingTransition(R.anim.lr, R.anim.rl);
				
				finish();
			}
		}, DELAY_TIME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
