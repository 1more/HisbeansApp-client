package ghost.android.hbapp.giftcard;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.giftcard.GiftCardMenuModel.GiftCardMenuInfo;
import ghost.android.hbapp.setting.SettingMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author HyeonWook Kim
 * @email khw0867@gmail.com
 * @classname GiftCardSend.java
 * @package ghost.android.hbapp.giftcard
 * @date 2012. 7. 26.
 * @purpose : 선물카드 보내기 Activity Class
 *
 */

public class GiftCardSendAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private EditText receiveNameEdit;
	private EditText sendNameEdit;
	private EditText messageEdit;
	private EditText phoneEdit;

	private TextView priceText;

	private Spinner cateSpin;
	private Spinner menuSpin;
	private Spinner quantitySpin;

	private ArrayAdapter<String> cateAdapter;
	private ArrayAdapter<String> menuAdapter;
	private ArrayAdapter<String> quantityAdapter;

	private Button okBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.giftcard_send);

		// Bottom Tab Setup
		setUp();



		/************ Find View and Setting ************/

		/*** EditText Setting ***/
		receiveNameEdit = (EditText) findViewById(R.id.giftcard_send_receive_name_edit);
		receiveNameEdit.addTextChangedListener(new TextWatcher() {
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
				if (checkAllFull()) {
					okBtn.setClickable(true);
					okBtn.setBackgroundResource(drawable.giftcard_send_ok_btn);
				} else {
					okBtn.setClickable(false);
					okBtn.setBackgroundResource(drawable.giftcard_send_ok_btn_press);
				}
			}
		});

		sendNameEdit = (EditText) findViewById(R.id.giftcard_send_send_name_edit);
		sendNameEdit.addTextChangedListener(new TextWatcher() {
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
				if (checkAllFull()) {
					okBtn.setClickable(true);
					okBtn.setBackgroundResource(drawable.giftcard_send_ok_btn);
				} else {
					okBtn.setClickable(false);
					okBtn.setBackgroundResource(drawable.giftcard_send_ok_btn_press);
				}
			}
		});

		messageEdit = (EditText) findViewById(R.id.giftcard_send_message_edit);
		messageEdit.addTextChangedListener(new TextWatcher() {
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
				if (checkAllFull()) {
					okBtn.setClickable(true);
					okBtn.setBackgroundResource(drawable.giftcard_send_ok_btn);
				} else {
					okBtn.setClickable(false);
					okBtn.setBackgroundResource(drawable.giftcard_send_ok_btn_press);
				}
			}
		});

		phoneEdit = (EditText) findViewById(R.id.giftcard_send_phone_edit);
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
				if (checkAllFull()) {
					okBtn.setClickable(true);
					okBtn.setBackgroundResource(drawable.giftcard_send_ok_btn);
				} else {
					okBtn.setClickable(false);
					okBtn.setBackgroundResource(drawable.giftcard_send_ok_btn_press);
				}
			}
		});

		/*** Spinner Setting ***/

		// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
		ConnectivityManager cm = (ConnectivityManager) getSystemService(
				Context.CONNECTIVITY_SERVICE);
		AlertDialog.Builder netConDlgBuilder = GlobalMethods.checkInternet(cm,
				GiftCardSendAct.this);

		// 인터넷환경이 원활 할 경우 Menu 정보 받아와 spinner setting
		if (netConDlgBuilder == null) {
			// Get Menu Information
			new GetAllMenuAndSettingSpin().execute();
		}
		// 인터넷환경이 원활 하지 않은 경우
		else{
			// do nothing
		}

		/*** price Setting ***/
		priceText = (TextView) findViewById(R.id.giftcard_send_price_text);

		/*** Button Setting ***/

		okBtn = (Button) findViewById(R.id.giftcard_send_ok_btn);
		okBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(
						Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods.checkInternet(cm,
						GiftCardSendAct.this);

				// 인터넷환경이 원활 할 경우 정보 intent에 담아 확인 화면으로 이동
				if (netConDlgBuilder == null && checkAllFull()) {
					Intent intent = new Intent(GiftCardSendAct.this,
							GiftCardSendVerifyAct.class);
					intent.putExtra("receiveName", receiveNameEdit.getText().toString());
					intent.putExtra("sendName", sendNameEdit.getText().toString());
					intent.putExtra("message", messageEdit.getText().toString());
					intent.putExtra("phone", phoneEdit.getText().toString());
					intent.putExtra("cate", cateSpin.getSelectedItem().toString());
					intent.putExtra("menu", menuSpin.getSelectedItem().toString());
					intent.putExtra("quantity", quantitySpin.getSelectedItem().toString());
					intent.putExtra("price", priceText.getText().toString());

					startActivity(intent);
				}
				// 인터넷환경이 원활 하지 않은 경우 Warning dialog display
				else{
					if(!GiftCardSendAct.this.isFinishing())
						netConDlgBuilder.show();
				}
			}
		});
		okBtn.setClickable(false);
	}



	/*** Get All Menus and Setting Spinner Progress with AsyncTask ***/

	private class GetAllMenuAndSettingSpin extends AsyncTask<Void, Void, GiftCardMenuModel> {
		private ProgressDialog loadingDlg;
		private List<GiftCardMenuInfo> coffeeMenu;
		private List<GiftCardMenuInfo> herbTeaMenu;
		private List<GiftCardMenuInfo> beverageMenu;
		private List<GiftCardMenuInfo> sideMenu;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(GiftCardSendAct.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected GiftCardMenuModel doInBackground(Void... barcodeNum) {
			// server에서 Menu 정보 가져오기
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			GiftCardMenuModel menus = restTemplate.getForObject(
					GlobalVariables.getAllMenuURL , GiftCardMenuModel.class);

			return menus;
		}

		@Override
		protected void onPostExecute(GiftCardMenuModel response) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// local variables 선언
			List<GiftCardMenuInfo> menus = response.getMenu();
			coffeeMenu = new ArrayList<GiftCardMenuInfo>();
			herbTeaMenu = new ArrayList<GiftCardMenuInfo>();
			beverageMenu = new ArrayList<GiftCardMenuInfo>();
			sideMenu = new ArrayList<GiftCardMenuInfo>();

			// setting local variables
			for(int i=0 ; i<menus.size() ; i++){
				GiftCardMenuInfo menu = menus.get(i);

				if(menu.getType().equals("Coffee")){
					coffeeMenu.add(new GiftCardMenuInfo(
							menu.getMenu_ko(), menu.getPrice()));
				}
				else if(menu.getType().equals("Herb & Tea")){
					herbTeaMenu.add(new GiftCardMenuInfo(
							menu.getMenu_ko(), menu.getPrice()));
				}
				else if(menu.getType().equals("Beverage")){
					beverageMenu.add(new GiftCardMenuInfo(
							menu.getMenu_ko(), menu.getPrice()));
				}
				else if(menu.getType().equals("Side Menu")){
					sideMenu.add(new GiftCardMenuInfo(
							menu.getMenu_ko(), menu.getPrice()));
				}
			}

			// 각 Spinner Contents 채우기 위한 Resources
			Resources res = getResources();

			// 카테고리 Setting
			String[] categoryItems = res.getStringArray(R.array.category);
			cateSpin = (Spinner) findViewById(R.id.giftcard_send_cate_spin);
			cateAdapter = new ArrayAdapter<String>(
					GiftCardSendAct.this, android.R.layout.simple_spinner_item, categoryItems);
			cateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cateSpin.setAdapter(cateAdapter);

			// 메뉴 Setting
			String[] menuItems = new String[coffeeMenu.size()];
			for(int i=0 ; i<coffeeMenu.size() ; i++)
				menuItems[i] = coffeeMenu.get(i).getMenu_ko();
			menuSpin = (Spinner) findViewById(R.id.giftcard_send_menu_spin);
			menuAdapter = new ArrayAdapter<String>(
					GiftCardSendAct.this, android.R.layout.simple_spinner_item, menuItems);
			menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			menuSpin.setAdapter(menuAdapter);

			// 수량 Setting - 최대 10개
			String[] quantityItems = res.getStringArray(R.array.quantity);
			quantitySpin = (Spinner) findViewById(R.id.giftcard_send_quantity_spin);
			quantityAdapter = new ArrayAdapter<String>(
					GiftCardSendAct.this, android.R.layout.simple_spinner_item, quantityItems);
			quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			quantitySpin.setAdapter(quantityAdapter);

			// price setting
			priceText.setText(""+coffeeMenu.get(0).getPrice());

			// 카테고리에 따라 Menu 다르게 표시
			cateSpin.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// 커피 메뉴
					if(position == 0){
						String[] menuItems = new String[coffeeMenu.size()];
						for(int i=0 ; i<coffeeMenu.size() ; i++)
							menuItems[i] = coffeeMenu.get(i).getMenu_ko();
						menuSpin = (Spinner) findViewById(R.id.giftcard_send_menu_spin);
						menuAdapter = new ArrayAdapter<String>(
								GiftCardSendAct.this, android.R.layout.simple_spinner_item, menuItems);
						menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						menuSpin.setAdapter(menuAdapter);
					} 
					// 허브 티 메뉴
					else if(position == 1){
						String[] menuItems = new String[herbTeaMenu.size()];
						for(int i=0 ; i<herbTeaMenu.size() ; i++)
							menuItems[i] = herbTeaMenu.get(i).getMenu_ko();
						menuSpin = (Spinner) findViewById(R.id.giftcard_send_menu_spin);
						menuAdapter = new ArrayAdapter<String>(
								GiftCardSendAct.this, android.R.layout.simple_spinner_item, menuItems);
						menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						menuSpin.setAdapter(menuAdapter);
					} 
					// 음료 메뉴
					else if(position == 2){
						String[] menuItems = new String[beverageMenu.size()];
						for(int i=0 ; i<beverageMenu.size() ; i++)
							menuItems[i] = beverageMenu.get(i).getMenu_ko();
						menuSpin = (Spinner) findViewById(R.id.giftcard_send_menu_spin);
						menuAdapter = new ArrayAdapter<String>(
								GiftCardSendAct.this, android.R.layout.simple_spinner_item, menuItems);
						menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						menuSpin.setAdapter(menuAdapter);
					} 
					// 사이드 메뉴
					else if(position == 3){
						String[] menuItems = new String[sideMenu.size()];
						for(int i=0 ; i<sideMenu.size() ; i++)
							menuItems[i] = sideMenu.get(i).getMenu_ko();
						menuSpin = (Spinner) findViewById(R.id.giftcard_send_menu_spin);
						menuAdapter = new ArrayAdapter<String>(
								GiftCardSendAct.this, android.R.layout.simple_spinner_item, menuItems);
						menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						menuSpin.setAdapter(menuAdapter);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// do nothing
				}
			});

			// menu에 따라 가격 다르게 설정
			menuSpin.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					String category = cateSpin.getSelectedItem().toString();
					String quantity = quantitySpin.getSelectedItem().toString();

					if(category.equals("Coffee")){
						priceText.setText(""+
								coffeeMenu.get(position).getPrice()*Integer.parseInt(quantity));
					}
					else if(category.equals("Herb_Tea")){
						priceText.setText(""+
								herbTeaMenu.get(position).getPrice()*Integer.parseInt(quantity));
					}
					else if(category.equals("Beverage")){
						priceText.setText(""+
								beverageMenu.get(position).getPrice()*Integer.parseInt(quantity));
					}
					else if(category.equals("Side Menu")){
						priceText.setText(""+
								sideMenu.get(position).getPrice()*Integer.parseInt(quantity));
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// do nothing
				}
			});

			// 수량에 따라 가격 다르게 설정
			quantitySpin.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					String category = cateSpin.getSelectedItem().toString();
					int quantity = position+1;

					if(category.equals("Coffee")){
						priceText.setText(""+
								coffeeMenu.get(menuSpin.getSelectedItemPosition()).getPrice()*quantity);
					}
					else if(category.equals("Herb_Tea")){
						priceText.setText(""+
								herbTeaMenu.get(menuSpin.getSelectedItemPosition()).getPrice()*quantity);
					}
					else if(category.equals("Beverage")){
						priceText.setText(""+
								beverageMenu.get(menuSpin.getSelectedItemPosition()).getPrice()*quantity);
					}
					else if(category.equals("Side Menu")){
						priceText.setText(""+
								sideMenu.get(menuSpin.getSelectedItemPosition()).getPrice()*quantity);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// do nothing
				}
			});
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
			Intent intent = new Intent(GiftCardSendAct.this, LoginAct.class);

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
				Intent intent = new Intent(GiftCardSendAct.this, StampBarcodeMainAct.class);

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
				Intent intent = new Intent(GiftCardSendAct.this, DepartmentMainAct.class);

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
				Intent intent = new Intent(GiftCardSendAct.this, SettingMainAct.class);

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

	// check every edit-text form blank or not
	private boolean checkAllFull() {
		if (receiveNameEdit.getText().toString().equals("")) {
			return false;
		} else if (sendNameEdit.getText().toString().equals("")) {
			return false;
		} else if (messageEdit.getText().toString().equals("")) {
			return false;
		} else if (phoneEdit.getText().toString().equals("")) {
			return false;
		} else if (priceText.getText().toString().equals("")) {
			return false;
		} else if (cateSpin.getSelectedItem().toString().equals("")) {
			return false;
		} else if (menuSpin.getSelectedItem().toString().equals("")) {
			return false;
		} else if (quantitySpin.getSelectedItem().toString().equals("")) {
			return false;
		}
		return true;
	}
}
