package ghost.android.hbapp.stamp;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.giftcard.GiftCardMainAct;
import ghost.android.hbapp.setting.SettingMainAct;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * @author MinJi Kim
 * @email Minstar777@gmail.com
 * @classname StampMainAct.java
 * @package ghost.android.hbapp.stamp
 * @date 2012. 8. 16. 
 * @purpose : 스탬프 카드 개수, 도장 보여주는 부분 Activity Class
 *
 */

public class StampCouponAct extends Activity {
	private Intent intent;

	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stamp_coupon);

		// Bottom Tab Setup
		setUp();

		// Get SPF Information and Set param
		SharedPreferences spf = getSharedPreferences(GlobalVariables.SPF_NAME_LOGIN, 0);
		String barcodeNum = spf.getString(GlobalVariables.SPF_LOGIN_BARCODE, "");

		Map<String, String> param = new HashMap<String, String>();
		param.put("barcodeNum", barcodeNum);

		// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
		ConnectivityManager cm = (ConnectivityManager) getSystemService(
				Context.CONNECTIVITY_SERVICE);
		AlertDialog.Builder netConDlgBuilder = GlobalMethods.checkInternet(cm, StampCouponAct.this);

		//인터넷환경이 원활 할 경우 정보 받아 setting
		if (netConDlgBuilder == null){
			new ManageStamps().execute(param);
		}
		//인터넷환경이 원활하지 않을 경우 setting with local information
		else{
			// do nothing
		}
	}



	/************ Get Stamps Info Progress Class with AsyncTask ************/

	private class ManageStamps extends AsyncTask<Map<String, String>, Void, StampModel> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute(){
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(StampCouponAct.this, "알림", "잠시만 기다려 주세요");
			loadingDlg.setCancelable(true);
		}

		@Override
		protected StampModel doInBackground(Map<String, String>... param) {
			// server에서 해당ID의 stamp정보 가져오기
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			StampModel response = restTemplate.getForObject(
					GlobalVariables.getStampsURL , StampModel.class, param[0]);

			return response;
		}

		@Override
		protected void onPostExecute(StampModel res){
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 성공적으로 stamp 정보를 받아오면 Coupon Setting
			if(res.getSize() != 0){
				// Find Table View
				TableLayout stampCardTable = (TableLayout)findViewById(R.id.stampCardTable);



				/****** 쿠폰 스탬프 보여주는 것 (화면상의 컵 개수) ******/

				int var = res.getSize()-1;		// stamp 10개가 다 찬 카드의 개수
				int isRest = var%5;
				int starRowCount;		//	카드 줄 개수(맨 첫 줄에서 5개가 되면 0줄에서 1줄이 됨)
				int usedVar = res.getUsedSize();		// 사용한 카드 개수

				// 카드가 1장이상 있는 경우
				if(var!=0){		
					// 이미 존재하는(미리 하나 만들어놓은 tableRow)
					TableRow originalTableRow = (TableRow)findViewById(R.id.stampCardBasicTableRow);	
					originalTableRow.removeAllViews();

					// 5로 나눴을 때 5의 배수면 딱 줄 개수가 5개씩 꽉찬 줄 개수가 되고
					if(isRest==0)
						starRowCount = var/5;	
					// 5로 나눴을 때 5의 배수가 아니면 카드가 5개가 아닌 줄이 있으므로 1개 추가
					else
						starRowCount = (var/5) + 1;	

					// 총 카드의 개수가 1줄에 다 들어갈 때는 이미 존재하는(미리 하나 만들어놓은) tableRow에 넣는다.
					if(starRowCount == 1){	
						for( ; var>0 ; var--){
							ImageView cup = new ImageView(StampCouponAct.this);

							if(usedVar>0){
								cup.setImageResource(R.drawable.coupon_cup_used);
								usedVar--;
							}
							else{
								cup.setImageResource(R.drawable.coupon_cup_not_used);
							}

							cup.setVisibility(GlobalVariables.VISIBLE);
							originalTableRow.addView(cup);
						}
					}
					// 총 카드의 개수가 1줄을 넘어서 2줄이상일 경우
					else{
						// 5개는 맨 위에 이미 만들어진 tableRow에 넣음
						for( ; var>0 ; var--){		
							ImageView cup = new ImageView(StampCouponAct.this);

							if(usedVar>0){
								cup.setImageResource(R.drawable.coupon_cup_used);
								usedVar--;
							}
							else{
								cup.setImageResource(R.drawable.coupon_cup_not_used);
							}

							cup.setVisibility(GlobalVariables.VISIBLE);
							originalTableRow.addView(cup);
						}

						// 5개를 제외한 나머지는 tableRow를 동적으로 추가하면서 카드그림 추가
						for(int i=0 ; i<starRowCount-1 ; i++ ){			
							TableRow newTableRow = new TableRow(StampCouponAct.this);

							for( ; var>0 ; var--){
								ImageView cup = new ImageView(StampCouponAct.this);

								if(usedVar>0){
									cup.setImageResource(R.drawable.coupon_cup_used);
									usedVar--;
								}
								else{
									cup.setImageResource(R.drawable.coupon_cup_not_used);
								}

								cup.setVisibility(GlobalVariables.VISIBLE);
								newTableRow.addView(cup);
							}

							stampCardTable.addView(newTableRow);
						}
					}
				}
				// 카드가 한 개도 없다는 에러 그림을 invisible
				else{	
					// do nothing
				}



				/****** 최근의 카드에 찍힌 도장 개수 출력하는 것 (쿠폰 스탬프 윗 부분) ******/

				//제일 최근의 카드의 현재까지 찍힌 도장 개수
				int cnt = res.getCurrentStampcardCount();
				ImageView curStamp = (ImageView)findViewById(R.id.curStampsNumImageView);

				switch(cnt){
				case 0:
					curStamp.setImageResource(R.drawable.coupon_card_a);
					break;
				case 1:
					curStamp.setImageResource(R.drawable.coupon_card_b);
					break;
				case 2:
					curStamp.setImageResource(R.drawable.coupon_card_c);
					break;
				case 3:
					curStamp.setImageResource(R.drawable.coupon_card_d);
					break;
				case 4:
					curStamp.setImageResource(R.drawable.coupon_card_e);
					break;
				case 5:
					curStamp.setImageResource(R.drawable.coupon_card_f);
					break;
				case 6:
					curStamp.setImageResource(R.drawable.coupon_card_g);
					break;
				case 7:
					curStamp.setImageResource(R.drawable.coupon_card_h);
					break;
				case 8:
					curStamp.setImageResource(R.drawable.coupon_card_i);
					break;
				case 9:
					curStamp.setImageResource(R.drawable.coupon_card_j);
					break;
				}
			}
			// 성공적으로 정보를 불러오지 못하면
			else{
				// do nothing
			}
		}

		@Override
		protected void onCancelled() {
			// loading dialog 사라지게 하기
			super.onCancelled();
			loadingDlg.dismiss();
		}
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
			// init information
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

			// back
			intent = new Intent(StampCouponAct.this, LoginAct.class);
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

	// Title 및 하단 Tab View Setting
	private void setUp() {
		tab_stampBtn = (Button) findViewById(R.id.tab_stamp);
		tab_stampBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_stampBtn.setBackgroundResource(drawable.menu_a_on);

		tab_giftBtn = (Button) findViewById(R.id.tab_gift);
		tab_giftBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_giftBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch GiftCard_main Screen
				Intent intent = new Intent(StampCouponAct.this,
						GiftCardMainAct.class);

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
				Intent intent = new Intent(StampCouponAct.this,
						DepartmentMainAct.class);

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
				Intent intent = new Intent(StampCouponAct.this,
						SettingMainAct.class);

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
