package ghost.android.hbapp.setting;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.giftcard.GiftCardMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;

import java.util.ArrayList;

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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname SettingQNAAct.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 8:02:16
 * @purpose : 사용자 도움말 - QNA Activity Class
 * @comment :
 */

public class SettingQNAAct extends Activity {
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private ExpandableListView expanListView;

	private ArrayList<String> mGroupList = null;
	private ArrayList<ArrayList<String>> mChildList = null;
	private ArrayList<String> mChildListContent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_qna);

		// Bottom Tab Setup
		setUp();
		
		// Setting the Array List that will be ListView content
		mGroupList = new ArrayList<String>();
		mChildList = new ArrayList<ArrayList<String>>();

		mGroupList.add("히즈빈스 앱은 어떤 앱인가요?");
		mGroupList.add("쿠폰 스탬프는 무엇인가요?");
		mGroupList.add("선물 카드는 어떤 서비스 인가요?");

		mChildListContent = new ArrayList<String>();
		mChildListContent.add("히즈빈스 앱은 카페 히즈빈스에서 운영하는 안드로이드폰 사용자를 위한 앱으로, " +
				"앱을 이용해 히즈빈스 쿠폰 스탬프를 모으고 기존에 오프라인으로만 보내던 선물카드를 안드로이드폰을 통해서 주고받을 수 있습니다.\n\n" +
				"히즈빈스 앱에서 만드신 계정은 웹상의 히즈빈스 홈페이지에서 동일하게 사용가능합니다.");
		mChildList.add(mChildListContent);

		mChildListContent = new ArrayList<String>();
		mChildListContent.add("쿠폰 스탬프는 일종의 마일리지 제도로서, " +
				"히즈빈스 카페 이용시 앱 상의 회원바코드를 직원에게 보여주시면 주문하신 음료의 수만큼 쿠폰 스탬프를 적립해 드립니다.\n\n" +
				"스탬프 10개를 모으시면 자동적으로 완성쿠폰이 생성되며 완성쿠폰은 아메리카노 1잔으로 교환 가능합니다.");
		mChildList.add(mChildListContent);

		mChildListContent = new ArrayList<String>();
		mChildListContent.add("선물카드는 히즈빈스 카페에서 오프라인으로 시행해왔던 서비스로, " +
				"사랑하는 사람들에게 고객님의 소중한 마음을 전할 수 있도록 히즈빈스에서 준비한 선물입니다.\n\n" +
				"앱 상에서 히즈빈스의 메뉴를 주문 결제하신 후 간단한 메시지를 작성주시면 문자와 메일등을 통해 선물카드를 전송해드리며 " +
				"히즈빈스 앱 또는 홈페이지 이용고객께서는 선물카드 상의 메시지를 메일뿐만이 아닌 앱과 홈페이지 상의 받은 선물함에서 확인하실 수 있습니다.\n\n" +
				"선물받은 선물카드의 코드번호나 선물카드 이미지를 갖고 가까운 히즈빈스 매장을 찾아주시면 해당 메뉴를 받으실 수 있습니다.");
		mChildList.add(mChildListContent);

		// Find view and setting Expandable List View
		expanListView = (ExpandableListView) findViewById(R.id.setting_qna_expan_list);
		expanListView.setAdapter(new SettingQNAExpanListAdapter(
				SettingQNAAct.this, mGroupList, mChildList));
		expanListView.setOnGroupExpandListener(new OnGroupExpandListener(){

			@Override
			public void onGroupExpand(int groupPosition) {
				for(int i=0 ; i<mGroupList.size() ; i++){
					if(i != groupPosition)
						expanListView.collapseGroup(i);
				}
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
			Intent intent = new Intent(SettingQNAAct.this, LoginAct.class);

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
				Intent intent = new Intent(SettingQNAAct.this, StampBarcodeMainAct.class);

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
				Intent intent = new Intent(SettingQNAAct.this, GiftCardMainAct.class);

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
				Intent intent = new Intent(SettingQNAAct.this, DepartmentMainAct.class);

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
