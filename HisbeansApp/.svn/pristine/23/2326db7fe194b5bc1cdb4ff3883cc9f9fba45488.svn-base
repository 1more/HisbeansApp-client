package ghost.android.hbapp.setting;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentHisbeansAct;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.giftcard.GiftCardMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname SettingAccountInfoAct.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 3:49:57
 * @purpose : 계정 정보 Activity Class
 * @comment :
 */

public class SettingAccountInfoAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private TextView nameText;
	private TextView idText;
	private TextView phoneText;
	private TextView emailText;

	private Button changeBtn;
	private Button passwordBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_account_info);

		// Bottom Tab Setup
		setUp();

		// SPF에서 Informaion 가져오기
		SharedPreferences spf = getSharedPreferences(GlobalVariables.SPF_NAME_LOGIN, 0);
		String name = spf.getString(GlobalVariables.SPF_LOGIN_NAME, "NONE_NAME");
		String id = spf.getString(GlobalVariables.SPF_LOGIN_ID, "NONE_ID");
		String phone = spf.getString(GlobalVariables.SPF_LOGIN_PHONE, "NONE_PHONE");
		String email = spf.getString(GlobalVariables.SPF_LOGIN_EMAIL, "NONE_EMAIL");



		/************ Find View and Setting ************/

		/*** TextView Setting ***/
		nameText = (TextView) findViewById(R.id.setting_account_info_name);
		nameText.setText(name);

		idText = (TextView) findViewById(R.id.setting_account_info_id);
		idText.setText(id);

		phoneText = (TextView) findViewById(R.id.setting_account_info_phone);
		phoneText.setText(phone);

		emailText = (TextView) findViewById(R.id.setting_account_info_email);
		emailText.setText(email);

		/*** Button Setting ***/

		// information change
		changeBtn = (Button) findViewById(R.id.setting_account_info_change_btn);
		changeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingAccountInfoAct.this, 
						SettingAccountInfoChangeAct.class);
				startActivity(intent);
			}
		});
		changeBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.setting_account_info_change_btn_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.setting_account_info_change_btn_normal);

				return false;
			}
		});

		// password change
		passwordBtn = (Button) findViewById(R.id.setting_account_info_password_btn);
		passwordBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingAccountInfoAct.this, 
						SettingAccountInfoPasswordAct.class);
				startActivity(intent);
			}
		});
		passwordBtn.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.setting_account_info_password_btn_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.setting_account_info_password_btn_normal);

				return false;
			}
		});
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
			Intent intent = new Intent(SettingAccountInfoAct.this, LoginAct.class);

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
				Intent intent = new Intent(SettingAccountInfoAct.this, StampBarcodeMainAct.class);

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
				Intent intent = new Intent(SettingAccountInfoAct.this, GiftCardMainAct.class);

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
				Intent intent = new Intent(SettingAccountInfoAct.this, DepartmentMainAct.class);

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
