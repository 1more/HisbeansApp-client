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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname SettingAccountInfoPasswordAct.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 3:54:39
 * @purpose : 계정 정보 - 비밀번호 변경 Activity Class
 * @comment :
 */

public class SettingAccountInfoPasswordAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private EditText newPwEt;
	private EditText checkPwEt;

	private Button loginB;
	private Button cancelB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_account_info_password);

		// Bottom Tab Setup
		setUp();

		
		
		/************ Find View and Setting ************/

		/*** Edit Text Setting ***/

		newPwEt = (EditText) findViewById(R.id.setting_account_info_password_new_edit);
		newPwEt.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable arg0) {
				// ignore
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// ignore
			}

			// text가 채워져 있을 때만 버튼 활성화
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!newPwEt.getText().toString().equals("")
						&& !checkPwEt.getText().toString().equals("")) {
					loginB.setClickable(true);
					loginB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					loginB.setClickable(false);
					loginB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		checkPwEt = (EditText) findViewById(R.id.setting_account_info_password_check_edit);
		checkPwEt.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable arg0) {
				// ignore
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// ignore
			}

			// text가 채워져 있을 때만 버튼 활성화
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!newPwEt.getText().toString().equals("")
						&& !checkPwEt.getText().toString().equals("")) {
					loginB.setClickable(true);
					loginB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					loginB.setClickable(false);
					loginB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		/*** Setting Button ***/

		loginB = (Button) findViewById(R.id.setting_account_info_password_ok_btn);
		loginB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, SettingAccountInfoPasswordAct.this);

				// 인터넷환경이 원활 할 경우.
				if (netConDlgBuilder == null) {
					String newPw = newPwEt.getText().toString();
					String checkPw = checkPwEt.getText().toString();

					// null check
					if (newPw.equals("") || checkPw.equals("")) {
						if(!SettingAccountInfoPasswordAct.this.isFinishing())
							showDialog(1);
					}
					// 비밀번호 일치하면 변경 후 activity 이동
					else if (newPw.equals(checkPw)) {	
						// SPF에서 Informaion 가져오기
						SharedPreferences spf = getSharedPreferences(GlobalVariables.SPF_NAME_LOGIN, 0);
						String id = spf.getString(GlobalVariables.SPF_LOGIN_ID, "NONE_ID");

						new ChangePasswordTask().execute(id, GlobalMethods.md5(newPw));
					}
					// 비밀번호 비일치하면 Error Dialog
					else {
						newPwEt.setText("");
						checkPwEt.setText("");

						if(!SettingAccountInfoPasswordAct.this.isFinishing())
							showDialog(4);
					}
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Display
				else {
					if(!SettingAccountInfoPasswordAct.this.isFinishing())
						netConDlgBuilder.show();
				}
			}
		});
		loginB.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				if (!newPwEt.getText().toString().equals("")
						&& !checkPwEt.getText().toString().equals("")) {
					if (event.getAction() == MotionEvent.ACTION_DOWN)
						v.setBackgroundResource(drawable.find_btn_next_press);
					if (event.getAction() == MotionEvent.ACTION_UP)
						v.setBackgroundResource(drawable.find_btn_next_normal);
				}

				return false;
			}
		});
		loginB.setClickable(false);

		cancelB = (Button) findViewById(R.id.setting_account_info_password_cancel_btn);
		cancelB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// back
				Intent intent = new Intent(SettingAccountInfoPasswordAct.this,
						SettingAccountInfoAct.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		cancelB.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.find_btn_cancel_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.find_btn_cancel_normal);

				return false;
			}
		});
	}



	/************ Change Passoword Progress Class with AsyncTask ************/

	// params (id, phoneNum, confirmNum)
	private class ChangePasswordTask extends AsyncTask<String, Void, Boolean> {
		private MultiValueMap<String, String> formData;
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(SettingAccountInfoPasswordAct.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// populate the data to post
			formData = new LinkedMultiValueMap<String, String>();
			formData.add("id", params[0]);
			formData.add("newPassword", params[1]);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.postForObject(
					GlobalVariables.modifyPasswordWithNoneValidURL, formData, Map.class);

			// Return the response body to display to the user
			return response.get(GlobalVariables.KEY_MODIFY_PASSWORD_WITH_NONE_VALID);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 비밀번호 변경이 성공하면 변경 후 성공 Dialog Display
			if (res != null && res == true) {
				newPwEt.setText("");
				checkPwEt.setText("");

				if(!SettingAccountInfoPasswordAct.this.isFinishing())
					showDialog(2);
			}
			// 비밀번호 변경이 실패하면 실패 Dialog Display
			else if (res != null && res == false) {
				newPwEt.setText("");
				checkPwEt.setText("");

				if(!SettingAccountInfoPasswordAct.this.isFinishing())
					showDialog(3);
			}
			// Fail to get information
			else if(res == null){
				if(!SettingAccountInfoPasswordAct.this.isFinishing())
					GlobalMethods.NetworkProblemDialog(SettingAccountInfoPasswordAct.this).show();
			}
		}

		@Override
		protected void onCancelled() {
			// loading dialog 사라지게 하기
			super.onCancelled();			
			loadingDlg.dismiss();
		}
	}



	/*** Create Dialog ***/

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;

		switch (id) {
		case 1:
			builder = new AlertDialog.Builder(SettingAccountInfoPasswordAct.this)
			.setTitle("알림")
			.setMessage(R.string.noEmpty)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		case 2:
			builder = new AlertDialog.Builder(SettingAccountInfoPasswordAct.this)
			.setTitle("알림")
			.setMessage(R.string.modiPwDone)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();

					// move activity
					Intent intent = new Intent(SettingAccountInfoPasswordAct.this,
							SettingAccountInfoAct.class);

					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

					finish();
				}
			});

			return builder.create();
		case 3:
			builder = new AlertDialog.Builder(SettingAccountInfoPasswordAct.this)
			.setTitle("알림")
			.setMessage(R.string.errModiPw)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		case 4:
			builder = new AlertDialog.Builder(SettingAccountInfoPasswordAct.this)
			.setTitle("알림")
			.setMessage(R.string.noMatch_pw)
			.setPositiveButton("확인",
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
			Intent intent = new Intent(SettingAccountInfoPasswordAct.this, LoginAct.class);

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
				Intent intent = new Intent(SettingAccountInfoPasswordAct.this, StampBarcodeMainAct.class);

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
				Intent intent = new Intent(SettingAccountInfoPasswordAct.this, GiftCardMainAct.class);

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
				Intent intent = new Intent(SettingAccountInfoPasswordAct.this, DepartmentMainAct.class);

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
