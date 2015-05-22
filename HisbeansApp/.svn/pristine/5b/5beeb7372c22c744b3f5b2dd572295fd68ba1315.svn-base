package ghost.android.hbapp.setting;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.giftcard.GiftCardMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname SettingAccountInfoChangeAct.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 3:52:30
 * @purpose : 계정 정보 변경 Activity Class
 * @comment :
 */

public class SettingAccountInfoChangeAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private TextView nameText;
	private TextView idText;
	private EditText phoneEdit;
	private EditText emailEdit;

	private Button okBtn;
	private Button cancelBtn;

	private String name;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_account_info_change);

		// Bottom Tab Setup
		setUp();
		
		// SPF에서 Informaion 가져오기
		SharedPreferences spf = getSharedPreferences(GlobalVariables.SPF_NAME_LOGIN, 0);
		name = spf.getString(GlobalVariables.SPF_LOGIN_NAME, "NONE_NAME");
		id = spf.getString(GlobalVariables.SPF_LOGIN_ID, "NONE_ID");



		/************ Find View and Setting ************/

		/*** Setting TextView ***/
		
		nameText = (TextView) findViewById(R.id.setting_account_info_change_name);
		nameText.setText(name);

		idText = (TextView) findViewById(R.id.setting_account_info_change_id);
		idText.setText(id);

		/*** Setting Edit Text ***/
		
		phoneEdit = (EditText) findViewById(R.id.setting_account_info_change_phone_edit);
		phoneEdit.addTextChangedListener(new TextWatcher() {
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
				if (!phoneEdit.getText().toString().equals("")
						&& !emailEdit.getText().toString().equals("")) {
					okBtn.setClickable(true);
					okBtn.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					okBtn.setClickable(false);
					okBtn.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		emailEdit = (EditText) findViewById(R.id.setting_account_info_change_email_edit);
		emailEdit.addTextChangedListener(new TextWatcher() {
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
				if (!phoneEdit.getText().toString().equals("")
						&& !emailEdit.getText().toString().equals("")) {
					okBtn.setClickable(true);
					okBtn.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					okBtn.setClickable(false);
					okBtn.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		/*** Setting Button ***/
		
		// information change button
		okBtn = (Button) findViewById(R.id.setting_account_info_change_ok_btn);
		okBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(phoneEdit.getText().length() < 10
						|| phoneEdit.getText().length() > 11){
					Toast.makeText(getApplicationContext(), "전화번호 형식이 잘못되었습니다.",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String[] idAndHost = emailEdit.getText().toString().split("@");
				if(idAndHost.length != 2){
					Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String[] hostSplit = idAndHost[1].split("\\.");
				if(hostSplit.length < 2){
					Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.",
							Toast.LENGTH_SHORT).show();
					return;
				}

				new InfoChangeProgress().execute(id,
						emailEdit.getText().toString(), phoneEdit.getText().toString());
			}
		});
		okBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!phoneEdit.getText().toString().equals("")
						&& !emailEdit.getText().toString().equals("")) {
					if (event.getAction() == MotionEvent.ACTION_DOWN)
						v.setBackgroundResource(drawable.find_btn_next_press);
					if (event.getAction() == MotionEvent.ACTION_UP)
						v.setBackgroundResource(drawable.find_btn_next_normal);
				}

				return false;
			}
		});
		okBtn.setClickable(false);

		// cancel button
		cancelBtn = (Button) findViewById(R.id.setting_account_info_change_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// back
				Intent intent = new Intent(SettingAccountInfoChangeAct.this,
						SettingAccountInfoAct.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		cancelBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.find_btn_cancel_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.find_btn_cancel_normal);

				return false;
			}
		});
	}



	/************ InfoChangeProgress Class with AsyncTask ************/

	private class InfoChangeProgress extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog loadingDlg;

		private String id;
		private String email;
		private String phoneNum;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(SettingAccountInfoChangeAct.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			id = params[0];
			email = params[1];
			phoneNum = params[2];

			Map<String, String> param = new HashMap<String, String>();
			param.put("id", id);
			param.put("email", email);
			param.put("phoneNum", phoneNum);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.getForObject(
					GlobalVariables.modifyUserURL, Map.class, param);

			// Return the response body to display to the user
			return response.get(GlobalVariables.KEY_MODIFY_USER);
		}

		@Override
		protected void onPostExecute(Boolean response) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 변경이 성공하면 정보 저장 후 성공 Dialog 띄우기
			if (response != null && response == true) {
				emailEdit.setText("");
				phoneEdit.setText("");

				SharedPreferences spf = getSharedPreferences(
						GlobalVariables.SPF_NAME_LOGIN, 0);
				SharedPreferences.Editor spfEditor = spf.edit();

				// 성공 아이디 SPF에 저장하기
				spfEditor.putString(GlobalVariables.SPF_LOGIN_EMAIL, email);
				spfEditor.putString(GlobalVariables.SPF_LOGIN_PHONE, phoneNum);
				spfEditor.commit();

				if(!SettingAccountInfoChangeAct.this.isFinishing())
					showDialog(2);
			}
			// 변경이 실패하면 실패 Dialog Display
			else if (response != null && response == false) {
				emailEdit.setText("");
				phoneEdit.setText("");

				if(!SettingAccountInfoChangeAct.this.isFinishing())
					showDialog(3);
			}
			// Fail to get information
			else if(response == null){
				if(!SettingAccountInfoChangeAct.this.isFinishing())
					GlobalMethods.NetworkProblemDialog(SettingAccountInfoChangeAct.this).show();
			}
		}

		@Override
		protected void onCancelled() {
			// Dismiss Progress Dialog
			super.onCancelled();
			loadingDlg.dismiss();
		}
	}

	

	/*** Create Dialog ***/

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;

		switch (id) {
		case 2:
			builder = new AlertDialog.Builder(SettingAccountInfoChangeAct.this)
			.setTitle("알림")
			.setMessage("정보를 성공적으로 변경하였습니다.")
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();

					// move activity
					Intent intent = new Intent(SettingAccountInfoChangeAct.this,
							SettingAccountInfoAct.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			});

			return builder.create();
		case 3:
			builder = new AlertDialog.Builder(SettingAccountInfoChangeAct.this)
			.setTitle("알림")
			.setMessage("비밀번호를 변경하는데 실패했습니다. 네트워크를 확인해주세요.")
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
			Intent intent = new Intent(SettingAccountInfoChangeAct.this, LoginAct.class);

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
				Intent intent = new Intent(SettingAccountInfoChangeAct.this, StampBarcodeMainAct.class);

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
				Intent intent = new Intent(SettingAccountInfoChangeAct.this, GiftCardMainAct.class);

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
				Intent intent = new Intent(SettingAccountInfoChangeAct.this, DepartmentMainAct.class);

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
