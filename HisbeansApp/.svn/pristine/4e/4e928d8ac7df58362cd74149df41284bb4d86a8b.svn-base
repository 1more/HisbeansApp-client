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
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname SettingSignOutAct.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 8:08:07
 * @purpose : 탈퇴 Activity Class
 * @comment :
 */

public class SettingSignOutAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private Button signOutBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_signout);

		// Bottom Tab Setup
		setUp();

		// Find view and setting Button
		signOutBtn = (Button) findViewById(R.id.setting_signout_btn);
		signOutBtn.setOnClickListener(new OnClickListener(){
			// after finished activity show warning dialog
			@Override
			public void onClick(View v) {
				if(!SettingSignOutAct.this.isFinishing())
					showDialog(1);
			}
		});
		signOutBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.setting_signout_btn_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.setting_signout_btn_normal);

				return false;
			}
		});
	}



	/************ Sign Up Progress Class with AsyncTask ************/

	private class SignOutProgress extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(SettingSignOutAct.this, "알림",
					"잠시만 기다려주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// get id successfully
			if(!params[0].equals("NONE_ID")){
				// 탈퇴하기
				RestTemplate restTemplate = new RestTemplate(true);
				restTemplate.getMessageConverters().add(
						new MappingJacksonHttpMessageConverter());

				Map<String, Boolean> response = restTemplate.getForObject(
						GlobalVariables.signOutURL, Map.class, params[0]);

				return response.get(GlobalVariables.KEY_SIGNOUT);
			}
			// getting id error
			else{
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// dismiss progress dialog
			loadingDlg.dismiss();

			// after finished activity
			if(!SettingSignOutAct.this.isFinishing()){
				// show sign out success dialog
				if(res == true){
					showDialog(2);
				}
				// show sign out fail or server error dialog
				else{
					GlobalMethods.NetworkProblemDialog(SettingSignOutAct.this);
				}
			}
		}

		@Override
		protected void onCancelled() {
			// dismiss progress dialog
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
			builder = new AlertDialog.Builder(SettingSignOutAct.this)
			.setTitle("알림")
			.setMessage("정말 탈퇴하시겠습니까? 모든 데이터가 삭제되며 복구할 수 없습니다.")
			.setPositiveButton("예",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();

					// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
					ConnectivityManager cm = (ConnectivityManager) getSystemService(
							Context.CONNECTIVITY_SERVICE);
					AlertDialog.Builder netConDlgBuilder = GlobalMethods.checkInternet(cm, SettingSignOutAct.this);

					// 인터넷환경이 원활 할 경우 sign out
					if (netConDlgBuilder == null){
						// get id
						SharedPreferences spf = getSharedPreferences(
								GlobalVariables.SPF_NAME_LOGIN, 0);
						// do sign out progress
						new SignOutProgress().execute(
								spf.getString(GlobalVariables.SPF_LOGIN_ID, "NONE_ID"));
					}
					//인터넷환경이 원활하지 않을 경우 setting with local information
					else{
						if(!SettingSignOutAct.this.isFinishing())
							netConDlgBuilder.show();
					}
				}
			})
			.setNegativeButton("아니요", 
					new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		case 2:
			builder = new AlertDialog.Builder(SettingSignOutAct.this)
			.setTitle("알림")
			.setMessage("탈퇴하였습니다. 모든 데이터가 삭제되어 복구할 수 없습니다.")
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();

					// init information
					SharedPreferences spf = getSharedPreferences(GlobalVariables.SPF_NAME_LOGIN, 0);
					SharedPreferences.Editor spfEditor = spf.edit();

					spfEditor.putString(GlobalVariables.SPF_LOGIN_ID, null);
					spfEditor.putString(GlobalVariables.SPF_LOGIN_BARCODE, null);
					spfEditor.putString(GlobalVariables.SPF_LOGIN_PHONE, null);
					spfEditor.commit();

					// Launch Login Screen
					Intent intent = new Intent(SettingSignOutAct.this, LoginAct.class);

					// Close all views before launching Stamp_main
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
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
			Intent intent = new Intent(SettingSignOutAct.this, LoginAct.class);

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
				Intent intent = new Intent(SettingSignOutAct.this, StampBarcodeMainAct.class);

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
				Intent intent = new Intent(SettingSignOutAct.this, GiftCardMainAct.class);

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
				Intent intent = new Intent(SettingSignOutAct.this, DepartmentMainAct.class);

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
