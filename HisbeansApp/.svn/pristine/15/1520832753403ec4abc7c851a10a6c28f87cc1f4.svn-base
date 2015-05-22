package ghost.android.hbapp.giftcard;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.setting.SettingMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
 * @classname GiftCardSendVerifyAct.java
 * @package ghost.android.hbapp.giftcard
 * @date 2012. 10. 28. 오후 8:16:50
 * @purpose : 선물카드 보내기 확인 Activity Class - 미완성
 * @comment :
 */

public class GiftCardSendVerifyAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private String receiveName; 
	private String sendName; 
	private String message; 
	private String phone; 
	private String cate; 
	private String menu; 
	private String quantity; 
	private String price;
	
	private Button okBtn;
	private Button cancelBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.giftcard_send_verify);

		// Bottom Tab Setup
		setUp();

		// Get Intent Information
		Intent intent = getIntent();
		receiveName = intent.getStringExtra("receiveName");
		sendName = intent.getStringExtra("sendName");
		message = intent.getStringExtra("message");
		phone = intent.getStringExtra("phone");
		cate = intent.getStringExtra("cate");
		menu = intent.getStringExtra("menu");
		quantity = intent.getStringExtra("quantity");
		price = intent.getStringExtra("price");
		
		
		
		/************ Find View and Setting ************/
		
		/*** Button Setting ***/

		okBtn = (Button) findViewById(R.id.giftcard_send_verify_ok_btn);
		okBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(
						Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods.checkInternet(cm,
						GiftCardSendVerifyAct.this);

				// 인터넷환경이 원활 할 경우 정보 intent에 담아 결제 화면으로 이동
				if (netConDlgBuilder == null) {
					// launch 결제 화면
					Intent intent = new Intent(GiftCardSendVerifyAct.this,
							GiftCardPayProcessAct.class);
					intent.putExtra("receiveName", receiveName);
					intent.putExtra("sendName", sendName);
					intent.putExtra("message", message);
					intent.putExtra("phone", phone);
					intent.putExtra("cate", cate);
					intent.putExtra("menu", menu);
					intent.putExtra("quantity", quantity);
					intent.putExtra("price", price);
					
					startActivity(intent);
				}
				// 인터넷환경이 원활 하지 않은 경우 Warning dialog display
				else{
					if(!GiftCardSendVerifyAct.this.isFinishing())
						netConDlgBuilder.show();
				}
			}
		});
		
		cancelBtn = (Button) findViewById(R.id.giftcard_send_verify_cancel_btn);
		cancelBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// launch 선물카드 화면
				Intent intent = new Intent(GiftCardSendVerifyAct.this,
						GiftCardSendAct.class);

				// Close all views before launching Stamp_main
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
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
			Intent intent = new Intent(GiftCardSendVerifyAct.this, LoginAct.class);

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
				Intent intent = new Intent(GiftCardSendVerifyAct.this, StampBarcodeMainAct.class);

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
		tab_giftBtn.setBackgroundResource(drawable.menu_b_on);

		tab_departmentBtn = (Button) findViewById(R.id.tab_department);
		tab_departmentBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_departmentBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch Depart_main Screen
				Intent intent = new Intent(GiftCardSendVerifyAct.this, DepartmentMainAct.class);

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
		tab_settingBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch Setting_main Screen
				Intent intent = new Intent(GiftCardSendVerifyAct.this, SettingMainAct.class);

				// Close all views before launching Stamp_main
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		tab_settingBtn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.menu_d_on);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.menu_d_off);

				return false;
			}
		});
	}
}
