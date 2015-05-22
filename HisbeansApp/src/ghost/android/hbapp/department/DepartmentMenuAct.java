package ghost.android.hbapp.department;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.giftcard.GiftCardMainAct;
import ghost.android.hbapp.setting.SettingMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname DepartmentMenuAct.java
 * @package ghost.android.hbapp.department
 * @date 2012. 10. 23. 오전 10:33:56
 * @purpose : 매뉴 소개 Activity Class
 * @comment :
 */

public class DepartmentMenuAct extends Activity {
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
		setContentView(R.layout.department_menu);

		// Bottom Tab Setup
		setUp();
		
		// Setting the Array List that will be ListView content
		mGroupList = new ArrayList<String>();
		mChildList = new ArrayList<ArrayList<String>>();

		mGroupList.add("커피");
		mGroupList.add("허브&티");
		mGroupList.add("음료");
		mGroupList.add("와플");

		mChildListContent = new ArrayList<String>();
		mChildListContent.add("커피01");
		mChildListContent.add("커피02");
		mChildListContent.add("커피03");
		mChildListContent.add("커피04");
		mChildListContent.add("커피05");
		mChildListContent.add("커피06");
		mChildListContent.add("커피07");
		mChildListContent.add("커피08");
		mChildListContent.add("커피09");
		mChildListContent.add("커피10");
		mChildListContent.add("커피11");
		mChildListContent.add("커피12");
		mChildList.add(mChildListContent);

		mChildListContent = new ArrayList<String>();
		mChildListContent.add("허브&티01");
		mChildListContent.add("허브&티02");
		mChildListContent.add("허브&티03");
		mChildListContent.add("허브&티04");
		mChildListContent.add("허브&티05");
		mChildListContent.add("허브&티06");
		mChildListContent.add("허브&티07");
		mChildListContent.add("허브&티08");
		mChildListContent.add("허브&티09");
		mChildList.add(mChildListContent);

		mChildListContent = new ArrayList<String>();
		mChildListContent.add("음료01");
		mChildListContent.add("음료02");
		mChildListContent.add("음료03");
		mChildListContent.add("음료04");
		mChildListContent.add("음료05");
		mChildListContent.add("음료06");
		mChildListContent.add("음료07");
		mChildListContent.add("음료08");
		mChildListContent.add("음료09");
		mChildList.add(mChildListContent);

		mChildListContent = new ArrayList<String>();
		mChildListContent.add("와플01");
		mChildListContent.add("와플02");
		mChildListContent.add("와플03");
		mChildListContent.add("와플04");
		mChildListContent.add("와플05");
		mChildListContent.add("와플06");
		mChildListContent.add("와플07");
		mChildList.add(mChildListContent);

		// find view and setting expandable list view
		expanListView = (ExpandableListView) findViewById(R.id.depart_menu_expan_list);
		expanListView.setAdapter(new DepartmentMenuExpanListAdapter(
				DepartmentMenuAct.this, mGroupList, mChildList));
		expanListView.setChildDivider(getResources().getDrawable(R.color.item_normal));
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
			Intent intent = new Intent(DepartmentMenuAct.this, LoginAct.class);

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
				Intent intent = new Intent(DepartmentMenuAct.this, StampBarcodeMainAct.class);

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
				// Launch Gift_card Screen
				Intent intent = new Intent(DepartmentMenuAct.this, GiftCardMainAct.class);

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
		tab_departmentBtn.setBackgroundResource(drawable.menu_c_on);

		tab_settingBtn = (Button) findViewById(R.id.tab_setting);
		tab_settingBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_settingBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch Setting_main Screen
				Intent intent = new Intent(DepartmentMenuAct.this, SettingMainAct.class);

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
