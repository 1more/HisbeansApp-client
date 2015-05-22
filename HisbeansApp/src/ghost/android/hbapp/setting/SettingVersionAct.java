package ghost.android.hbapp.setting;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.giftcard.GiftCardMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;

import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname SettingVersionAct.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 8:16:14
 * @purpose : Version Info Activity Class
 * @comment :
 */

public class SettingVersionAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private TextView versionCurText;
	private TextView versionLastText;

	private Button updateBtn;

	private String versionCur;
	private String versionLast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_version);

		// get current version
		try {
			Context context = getApplicationContext();
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			versionCur = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			Log.e("error", "SettingVersionAct : " + e.toString());
		}

		// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
		ConnectivityManager cm = (ConnectivityManager) getSystemService(
				Context.CONNECTIVITY_SERVICE);
		AlertDialog.Builder netConDlgBuilder = GlobalMethods.checkInternet(cm, SettingVersionAct.this);

		// 인터넷환경이 원활 할 경우 get last version
		if (netConDlgBuilder == null){
			new GetNotice().execute();
		}
		//인터넷환경이 원활하지 않을 경우 setting with local information
		else{
			versionLast = versionCur;
			updateBtn.setBackgroundResource(drawable.setting_version_update_btn_press);
			updateBtn.setClickable(false);
		}

		// setting cur version text
		versionCurText = (TextView) findViewById(R.id.setting_version_text_cur);
		versionCurText.setText(versionCur + "     ");

		// setting update button
		updateBtn = (Button) findViewById(R.id.setting_version_update_btn);
		updateBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!SettingVersionAct.this.isFinishing())
					showDialog(1);
			}
		});
		updateBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.setting_version_update_btn_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.setting_version_update_btn_normal);

				return false;
			}
		});
	}



	/************ GetNotice Class with AsyncTask ************/

	private class GetNotice extends AsyncTask<Void, Void, String> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(SettingVersionAct.this, "알림", "잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected String doInBackground(Void... params) {
			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, String> response = restTemplate.getForObject(
					GlobalVariables.getVersionURL, Map.class);

			// Return the response body to display to the user
			return response.get(GlobalVariables.KEY_GETVERSION);
		}

		@Override
		protected void onPostExecute(String response) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// get last version successfully
			if(!response.equals("") && response != null)
				versionLast = response;
			// fail to get last version
			else
				versionLast = versionCur;

			// cur version과 last version이 같다면 button click 불가
			if(versionLast.equals(versionCur)){
				updateBtn.setBackgroundResource(drawable.setting_version_update_btn_press);
				updateBtn.setClickable(false);
			}

			// setting last version text
			versionLastText = (TextView) findViewById(R.id.setting_version_text_last);
			versionLastText.setText(versionLast + "     ");
		}

		@Override
		protected void onCancelled() {
			// Progress Dialog Dismiss
			super.onCancelled();
			loadingDlg.dismiss();
		}
	}


	/************ Create Dialog Method ************/

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;

		switch (id) {
		case 1:
			builder = new AlertDialog.Builder(SettingVersionAct.this)
			.setTitle("알림")
			.setMessage("Hisbeans App을 업데이트 하시겠습니까?")
			.setPositiveButton("예",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();

					// update
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("market://details?id=ghost.android.hbapp"));
					startActivity(intent);
				}
			})
			.setNegativeButton("아니요",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		}
		return null;
	}



	/*** Create Option Menu ***/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Menu.FIRST, Menu.NONE , "로그아웃");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){

		// 로그아웃
		case Menu.FIRST :
			SharedPreferences spf = getSharedPreferences(GlobalVariables.SPF_NAME_LOGIN, 0);
			SharedPreferences.Editor spfEditor = spf.edit();

			// 정보 지우기
			spfEditor.putString(GlobalVariables.SPF_LOGIN_ID, null);
			spfEditor.putString(GlobalVariables.SPF_LOGIN_NAME, null);
			spfEditor.putString(GlobalVariables.SPF_LOGIN_NICK_NAME, null);
			spfEditor.putString(GlobalVariables.SPF_LOGIN_EMAIL, null);
			spfEditor.putString(GlobalVariables.SPF_LOGIN_PHONE, null);
			spfEditor.putString(GlobalVariables.SPF_LOGIN_BARCODE, null);
			spfEditor.putString(GlobalVariables.SPF_LOGIN_BARCODE_URL, null);
			spfEditor.commit();

			// Launch Login Screen
			Intent intent = new Intent(SettingVersionAct.this, LoginAct.class);

			// Close all views before launching Stamp_main
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}

		return super.onOptionsItemSelected(item);
	}
	
	
	/*** Activity 다시 시작 ***/

	@Override
	protected void onResume() {
		super.onResume();
		Display display = getWindowManager().getDefaultDisplay();
		GlobalVariables.bottomTabWidth = display.getWidth()/4;
		setUp();
	}



	/*** Self Method ***/

	// Bottom Tab Setup
	private void setUp(){
		tab_stampBtn = (Button) findViewById(R.id.tab_stamp);
		tab_stampBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_stampBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch Stamp_main Screen
				Intent intent = new Intent(SettingVersionAct.this, StampBarcodeMainAct.class);

				// Close all views before launching Stamp_main
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		tab_stampBtn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.menu_a_on);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.menu_a_off);

				return false;
			}
		});


		tab_giftBtn = (Button) findViewById(R.id.tab_gift);
		tab_giftBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_giftBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch GiftCard_main Screen
				Intent intent = new Intent(SettingVersionAct.this, GiftCardMainAct.class);

				// Close all views before launching Stamp_main
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		tab_giftBtn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.menu_b_on);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.menu_b_off);

				return false;
			}
		});

		tab_departmentBtn = (Button) findViewById(R.id.tab_department);
		tab_departmentBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_departmentBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch Depart_main Screen
				Intent intent = new Intent(SettingVersionAct.this, DepartmentMainAct.class);

				// Close all views before launching Stamp_main
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		tab_departmentBtn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.menu_c_on);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.menu_c_off);

				return false;
			}
		});

		tab_settingBtn = (Button) findViewById(R.id.tab_setting);
		tab_settingBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_settingBtn.setBackgroundResource(drawable.menu_d_on);
	}
}
