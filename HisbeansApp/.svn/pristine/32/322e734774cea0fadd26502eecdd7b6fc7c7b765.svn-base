package ghost.android.hbapp.stamp;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.department.DepartmentMainAct;
import ghost.android.hbapp.giftcard.GiftCardMainAct;
import ghost.android.hbapp.setting.SettingMainAct;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import android.widget.Toast;

/**
 * @author MinJi Kim
 * @email Minstar777@gmail.com
 * @classname StampMainAct.java
 * @package ghost.android.hbapp.stamp
 * @date 2012. 8. 16.
 * @purpose : 스탬프 카드 첫화면 - 바코드 & 스탬프카드정보보기 버튼 있는 부분 Activity Class
 * 
 */

public class StampBarcodeMainAct extends Activity {
	private Intent intent;

	private Button viewStampCouponBtn;
	private Button tab_stampBtn;
	private Button tab_giftBtn;
	private Button tab_departmentBtn;
	private Button tab_settingBtn;

	private Bitmap barcode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stamp_barcode_main);
		
		// Bottom Tab Setup
		setUp();
		
		// Get SPF Information and Make Toast with ID
		SharedPreferences spf = getSharedPreferences(
				GlobalVariables.SPF_NAME_LOGIN, 0);
		Toast.makeText(StampBarcodeMainAct.this, "현재 아이디 : "
						+ spf.getString(GlobalVariables.SPF_LOGIN_ID, "NONE_ID"),
						Toast.LENGTH_SHORT).show();

		// 스탬프 카드로 넘어가는 listener
		// 나의 쿠폰 스탬프 클릭하면 StampCouponAct.java로 이동
		viewStampCouponBtn = (Button) findViewById(R.id.showStampCouponImageBtn);
		viewStampCouponBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch Stamp_main Screen
				intent = new Intent(StampBarcodeMainAct.this,
						StampCouponAct.class);
				startActivity(intent);
			}
		});
		viewStampCouponBtn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.coupon_btn_a_press);
				if (event.getAction() == MotionEvent.ACTION_UP){
					v.setBackgroundResource(drawable.coupon_btn_a_on);
				}

				return false;
			}
		});

		// get information from spf and set param
		String barcodeNum = spf.getString(GlobalVariables.SPF_LOGIN_BARCODE, "");
		String barcodeURL = spf.getString(GlobalVariables.SPF_LOGIN_BARCODE_URL, "");
		String[] param = new String[] { barcodeNum, barcodeURL };

		// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
		ConnectivityManager cm = (ConnectivityManager) getSystemService(
				Context.CONNECTIVITY_SERVICE);
		AlertDialog.Builder netConDlgBuilder = GlobalMethods.checkInternet(cm,
				StampBarcodeMainAct.this);

		// 인터넷환경이 원활 할 경우 정보 받아 setting
		if (netConDlgBuilder == null) {
			new ManageStamps().execute(param);
		}
		// 인터넷환경이 원활하지 않을 경우 setting with local information
		else {
			try {
				// SD카드 Path 얻기
				String sdPath = "" + Environment.getExternalStorageDirectory();
				File file = new File(sdPath + "/hisbeans/" + barcodeNum
						+ ".bmp");
				BufferedInputStream bis = null;

				// 파일이 존재하면 사용하기
				if (file.exists()) {
					// 파일 스트림 열기
					InputStream fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis, 512 * 1024);
					barcode = BitmapFactory.decodeStream(bis);

					fis.close();
					bis.close();
				}
				// 파일 없으면 Barcode Image는 빈칸(null)으로 setting
				else {
					barcode = null;
				}
			} catch (FileNotFoundException e) {
				Log.e("FileNotFoundException error", e.toString());
			} catch (IOException e) {
				Log.e("IOException error", e.toString());
			}

			// barcode setting
			ImageView barcodeImg = (ImageView) findViewById(R.id.barcodeImageView);
			barcodeImg.setImageBitmap(barcode);
		}
	}

	
	
	/************ Get Stamps Info Progress Class with AsyncTask ************/

	private class ManageStamps extends AsyncTask<String, Void, Bitmap> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(StampBarcodeMainAct.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Bitmap doInBackground(String... barcodeNum) {

			// get barcode img and save at sd card
			try {
				// SD카드 Path 얻기
				String sdPath = "" + Environment.getExternalStorageDirectory();
				File file = new File(sdPath + "/hisbeans/" + barcodeNum[0]
						+ ".bmp");
				BufferedInputStream bis = null;

				// 파일이 존재하면 사용하기
				if (file.exists()) {
					// 파일 스트림 열기
					InputStream fis = new FileInputStream(file);

					bis = new BufferedInputStream(fis, 512 * 1024);
					barcode = BitmapFactory.decodeStream(bis);
					bis.close();
				}
				// 파일이 없으면 서버에서 받아와 저장하기
				else {
					// Populate the data to POST
					MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
					formData.add("barcodeURL", barcodeNum[1]);

					// Create a new RestTemplate instance
					RestTemplate restTemplate = new RestTemplate(true);
					restTemplate.getMessageConverters().add(
							new ByteArrayHttpMessageConverter());
					ResponseEntity<byte[]> response = restTemplate
							.postForEntity(GlobalVariables.getUserBarcodeURL,
									formData, byte[].class);

					// 성공적으로 정보를 불러오면 이미지 SD카드에 저장
					if (response.getBody() != null) {
						InputStream fileis = new ByteArrayInputStream(
								response.getBody());

						bis = new BufferedInputStream(fileis, 512 * 1024);
						barcode = BitmapFactory.decodeStream(bis);

						GlobalMethods.saveImgAtSdcard(barcodeNum[0], barcode);
						bis.close();
					}
					// 파일 없으면  Barcode Image는 빈칸(null)으로 setting
					else {
						barcode = null;
					}
				}
			} catch (FileNotFoundException e) {
				Log.e("FileNotFoundException error", e.toString());
			} catch (MalformedURLException e) {
				Log.e("MalformedURLException error", e.toString());
			} catch (IOException e) {
				Log.e("IOException error", e.toString());
			}

			return barcode;
		}

		@Override
		protected void onPostExecute(Bitmap barcode) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// barcode setting
			ImageView barcodeImg = (ImageView) findViewById(R.id.barcodeImageView);
			barcodeImg.setImageBitmap(barcode);

			if (barcode == null) {
				Toast.makeText(StampBarcodeMainAct.this, "네트워크 상태가 원할하지 않습니다.",
						Toast.LENGTH_SHORT).show();
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

			// Launch Login Screen
			intent = new Intent(StampBarcodeMainAct.this, LoginAct.class);

			// Close all views before launching Stamp_main
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}

		return super.onOptionsItemSelected(item);
	}


	
	/*** Exit App process ***/
	@Override
	public void onBackPressed() {
		String alertTitle = getResources().getString(R.string.app_name);
		String buttonMessage = getResources().getString(R.string.notifyExit);
		String buttonYes = getResources().getString(android.R.string.yes);
		String buttonNo = getResources().getString(android.R.string.no);

		new AlertDialog.Builder(StampBarcodeMainAct.this)
		.setTitle(alertTitle)
		.setMessage(buttonMessage)
		.setPositiveButton(buttonYes,
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				dialog.dismiss();

				moveTaskToBack(true);
				finish();
			}
		})
		.setNegativeButton(buttonNo, null).show();
	}



	/*** Activity 다시 시작 ***/

	@Override
	protected void onResume() {
		super.onResume();
		Display display = getWindowManager().getDefaultDisplay();
		GlobalVariables.bottomTabWidth = display.getWidth() / 4;
		setUp();
	}

	
	
	/*** Self Method ***/

	// Bottom Tab Setup
	private void setUp() {
		tab_stampBtn = (Button) findViewById(R.id.tab_stamp);
		tab_stampBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_stampBtn.setBackgroundResource(drawable.menu_a_on);

		tab_giftBtn = (Button) findViewById(R.id.tab_gift);
		tab_giftBtn.setWidth(GlobalVariables.bottomTabWidth);
		tab_giftBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Launch GiftCard_main Screen
				Intent intent = new Intent(StampBarcodeMainAct.this,
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
				Intent intent = new Intent(StampBarcodeMainAct.this,
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
				Intent intent = new Intent(StampBarcodeMainAct.this,
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
