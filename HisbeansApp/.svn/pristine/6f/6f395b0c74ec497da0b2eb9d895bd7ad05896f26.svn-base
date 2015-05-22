package ghost.android.hbapp;

import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname MainAct.java
 * @package ghost.android.hbapp
 * @date 2012. 8. 9.
 * @purpose : 첫 Splash Activity Class
 * @comment :
 */

public class MainAct extends Activity {
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		// 화면 크기 구하기
		Display display = getWindowManager().getDefaultDisplay();
		GlobalVariables.bottomTabWidth = display.getWidth() / 4;

		// 750ms 딜레이
		new SleepMinuteAndLogin().execute();
	}



	/************ Sleep Minute And AutoLogin Progress Class with AsyncTask ************/

	private class SleepMinuteAndLogin extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// Sleep
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				Log.e("InterruptedException error", e.toString());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// AutoLogin
			super.onPostExecute(result);
			autoLogin();
		}
	}



	// SPF 정보를 파악 후 Activity 이동
	private void autoLogin() {
		SharedPreferences spf = getSharedPreferences(
				GlobalVariables.SPF_NAME_LOGIN, 0);
		String id = spf.getString(GlobalVariables.SPF_LOGIN_ID, "NONE_ID");

		// SPF에 ID가 등록되 있다면 바로 Stamp_main Activity로 이동
		if (!id.equals("NONE_ID")) 
			intent = new Intent(MainAct.this, StampBarcodeMainAct.class);
		// SPF에 아이디가 등록되있지 않다면 Launch Login Main Screen
		else 
			intent = new Intent(MainAct.this, LoginAct.class);

		// Close all views before launching Login Main
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
}
