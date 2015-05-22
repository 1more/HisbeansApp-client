package ghost.android.hbapp.giftcard;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.setting.SettingMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;

/**
 * @author HyeonWook Kim
 * @email khw0867@gmail.com
 * @classname GetGiftBox.java
 * @package ghost.android.hbapp.giftcard
 * @date 2012. 7. 30.
 * @purpose : 받은 선물함 관련 Activity
 *
 */

public class GiftCardGetboxAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private Intent intent;

	private ExpandableListView giftCardExpanList;
	
	ImageView giftcardIv;
	Bitmap giftcardImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.giftcard_getbox);

		// Bottom Tab Setup
		setUp();

		giftCardExpanList = (ExpandableListView) findViewById(R.id.getGiftList);

		// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		AlertDialog.Builder netConDlgBuilder = GlobalMethods.checkInternet(cm,
				GiftCardGetboxAct.this);

		// 인터넷환경이 원활 할 경우 정보 받아 setting
		if (netConDlgBuilder == null) {
			// SPF에서 Informaion 가져오기
			SharedPreferences spf = getSharedPreferences(
					GlobalVariables.SPF_NAME_LOGIN, 0);
			String phone = spf.getString(GlobalVariables.SPF_LOGIN_PHONE, "NONE_PHONE");

			Map<String, String> param = new HashMap<String, String>();
			param.put("phoneNum", phone);

			new GetGiftBoxListProgress().execute(param);
		}
		// 인터넷환경이 원활하지 않을 경우 setting with local information
		else {
			netConDlgBuilder.show().setOnDismissListener(
					new OnDismissListener() {
						public void onDismiss(DialogInterface dialog) {
							finish();
						}
					});
		}
	}

	/********** DB에서 보낸 선물 읽어오는 Thread ***********/

	private class GetGiftBoxListProgress extends
			AsyncTask<Map<String, String>, Void, GiftCardModel> {
		private ProgressDialog loadingDlg;
		private GiftCardModel obj;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(GiftCardGetboxAct.this, "알림",
					"잠시만 기다려 주세요...", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected GiftCardModel doInBackground(Map<String, String>... param) {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			GiftCardModel response = restTemplate.getForObject(
					GlobalVariables.getGiftCardsAsReceiverURL,
					GiftCardModel.class, param[0]);
			
			response.setImages();

			return response;
		}

		@Override
		protected void onPostExecute(GiftCardModel response) {
			// dialog 사라지게 하기
			loadingDlg.dismiss();
			obj = response;

			// 정보를 성공적으로 받아왔을 때 gift box ListView Set
			if (obj != null) {
				// setting epandable list
				giftCardExpanList.setAdapter(new GiftCardGetListAdapter(
						GiftCardGetboxAct.this, obj));
				giftCardExpanList
						.setOnGroupExpandListener(new OnGroupExpandListener() {
							@Override
							public void onGroupExpand(int groupPosition) {
								for (int i = 0; i < obj.getSize(); i++) {
									if (i != groupPosition)
										giftCardExpanList.collapseGroup(i);
								}
							}
						});
			}
			// Exception이 생겼을 때 Warning Dialog Display 후 Activity 종료
			else {
				if (!GiftCardGetboxAct.this.isFinishing()) {
					AlertDialog.Builder netConDlgBuilder = GlobalMethods
							.NetworkProblemDialog(GiftCardGetboxAct.this);
					netConDlgBuilder.show().setOnDismissListener(
							new OnDismissListener() {
								public void onDismiss(DialogInterface dialog) {
									finish();
								}
							});
				}
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}

	/*** Create Option Menu ***/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Menu.FIRST, Menu.NONE, "로그아웃");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		// 로그아웃
		case Menu.FIRST:
			SharedPreferences spf = getSharedPreferences(
					GlobalVariables.SPF_NAME_LOGIN, 0);
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

			// Launch Stamp_main Screen
			intent = new Intent(GiftCardGetboxAct.this, LoginAct.class);

			// Close all views before launching Stamp_main
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			// Close Login Screen
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	/*** Self Method ***/

	// Bottom Tab Setup
	private void setUp() {
		tab_stampBtn = (Button) findViewById(R.id.tab_stamp);
		tab_stampBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_stampBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch Stamp_main Screen
				Intent intent = new Intent(GiftCardGetboxAct.this, StampBarcodeMainAct.class);

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
				Intent intent = new Intent(GiftCardGetboxAct.this, DepartmentMainAct.class);

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
				Intent intent = new Intent(GiftCardGetboxAct.this, SettingMainAct.class);

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
