package ghost.android.hbapp;

import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.common.RegExpression;
import ghost.android.hbapp.lookforid.LookforIdMainAct;
import ghost.android.hbapp.lookforpw.LookforPwMainAct;
import ghost.android.hbapp.register.RegisterTermsMainAct;
import ghost.android.hbapp.stamp.StampBarcodeMainAct;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname LoginAct.java
 * @package ghost.android.hbapp
 * @date 2012. 8. 9.
 * @purpose : 로그인 Activity Class
 * @comment :
 */

public class LoginAct extends Activity {
	private EditText idEt;
	private EditText pwEt;
	private Intent intent;

	private Button loginB;
	private Button lookforIdB;
	private Button lookforPwB;
	private Button simpleRegB;



	/************ onCreate ************/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_main);



		/************ Find Edit Text and Setting ************/

		idEt = (EditText) findViewById(R.id.login_idEt);
		idEt.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {
				// ignore
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// ignore
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!idEt.getText().toString().equals("")
						&& !pwEt.getText().toString().equals("")) {
					loginB.setClickable(true);
					loginB.setBackgroundResource(drawable.find_btn_login_on);
				} else {
					loginB.setClickable(false);
					loginB.setBackgroundResource(drawable.find_btn_login_off);
				}
			}
		});

		pwEt = (EditText) findViewById(R.id.login_pwEt);
		pwEt.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {
				// ignore
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// ignore
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!idEt.getText().toString().equals("")
						&& !pwEt.getText().toString().equals("")) {
					loginB.setClickable(true);
					loginB.setBackgroundResource(drawable.find_btn_login_on);
				} else {
					loginB.setClickable(false);
					loginB.setBackgroundResource(drawable.find_btn_login_off);
				}
			}
		});

		/************ find button and set action ************/

		// ID 찾기 버튼
		lookforIdB = (Button) findViewById(R.id.login_lookforIDB);
		lookforIdB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LoginAct.this, LookforIdMainAct.class);
				startActivity(intent);
			}
		});
		lookforIdB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.btn_find_id_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.btn_find_id_on);
				return false;
			}
		});

		// 비밀번호 찾기 버튼
		lookforPwB = (Button) findViewById(R.id.login_lookforPWB);
		lookforPwB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LoginAct.this, LookforPwMainAct.class);
				startActivity(intent);
			}
		});
		lookforPwB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.btn_find_pw_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.btn_find_pw_on);
				return false;
			}
		});

		// 간편가입하기 버튼
		simpleRegB = (Button) findViewById(R.id.login_simpleRegB);
		simpleRegB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginAct.this, 
						RegisterTermsMainAct.class);
				startActivity(intent);
			}
		});
		simpleRegB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.btn_join_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.btn_join_on);
				return false;
			}
		});

		// 로그인 버튼
		loginB = (Button) findViewById(R.id.login_loginB);
		loginB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(
						Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LoginAct.this);

				// 인터넷환경이 원활 할 경우.
				if (netConDlgBuilder == null) {
					String id = idEt.getText().toString();
					String password = pwEt.getText().toString();

					// id가 a-z, A-Z, 0-9 범위로 입력되었을 때 로그인체크
					if (id.equals("") || password.equals("")) {
						idEt.setText("");
						pwEt.setText("");

						showDialog(1);
					} else if (!RegExpression.checkPattern(
							RegExpression.Patterns.ALPHA_NUM, id)) {
						idEt.setText("");

						showDialog(3);
					} else if (GlobalMethods.checkSpecialCharacterIn(password)) {
						pwEt.setText("");
						showDialog(4);
					} else {
						idEt.setText("");
						pwEt.setText("");

						new LoginProgress().execute(id, password);
					}
				}
				// 인터넷 연결이 원할하지 않을 경우.
				else {
					idEt.setText("");
					pwEt.setText("");

					netConDlgBuilder.show();
				}
			}
		});
		loginB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (!idEt.getText().toString().equals("")
						&& !pwEt.getText().toString().equals("")) {
					if (event.getAction() == MotionEvent.ACTION_DOWN)
						v.setBackgroundResource(drawable.find_btn_login_press);
					if (event.getAction() == MotionEvent.ACTION_UP)
						v.setBackgroundResource(drawable.find_btn_login_on);
				}
				return false;
			}
		});
		loginB.setClickable(false);
	}



	/************ Login Progress Class with AsyncTask ************/

	private class LoginProgress extends AsyncTask<String, Void, LoginModel> {
		private ProgressDialog loadingDlg;

		private MultiValueMap<String, String> formData;

		private String id;
		private String password;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(LoginAct.this, "알림",
					"로그인을 기다려주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected LoginModel doInBackground(String... params) {
			id = params[0];
			password = GlobalMethods.md5(params[1]);

			// populate the data to post
			formData = new LinkedMultiValueMap<String, String>();
			formData.add("id", id);
			formData.add("password", password);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			LoginModel response = restTemplate.postForObject(
					GlobalVariables.loginURL, formData, LoginModel.class);

			// Return the response body to display to the user
			return response;
		}

		@Override
		protected void onPostExecute(LoginModel res) {
			// dialog 사라지게 하기
			loadingDlg.dismiss();

			// 성공적으로 정보를 불러오면
			if (res.getLogin() != null) {
				// 로그인이 성공하면
				if (!res.getLogin().getBarcodeNum().equals("-1")) {
					SharedPreferences spf = getSharedPreferences(
							GlobalVariables.SPF_NAME_LOGIN, 0);
					SharedPreferences.Editor spfEditor = spf.edit();

					// 성공 아이디 SPF에 저장하기
					spfEditor.putString(GlobalVariables.SPF_LOGIN_ID, id);
					spfEditor.putString(GlobalVariables.SPF_LOGIN_EMAIL, 
							res.getLogin().getEmail());
					spfEditor.putString(GlobalVariables.SPF_LOGIN_NAME, 
							res.getLogin().getName());
					spfEditor.putString(GlobalVariables.SPF_LOGIN_NICK_NAME, 
							res.getLogin().getNick_name());
					spfEditor.putString(GlobalVariables.SPF_LOGIN_PHONE, 
							res.getLogin().getPhoneNum());
					spfEditor.putString(GlobalVariables.SPF_LOGIN_BARCODE, 
							res.getLogin().getBarcodeNum());
					spfEditor.putString(GlobalVariables.SPF_LOGIN_BARCODE_URL,
							res.getLogin().getBarcodeURL());
					spfEditor.commit();

					// Launch Stamp_main Screen
					intent = new Intent(LoginAct.this, StampBarcodeMainAct.class);

					// Close all views before launching Stamp_main
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

					// Close Login Screen
					finish();
				}
				// 로그인이 실패하면
				else {
					if(!LoginAct.this.isFinishing()){
						showDialog(2);
					}
				}
			}
			// 성공적으로 정보를 불러오지 못하면
			else{
				if(!LoginAct.this.isFinishing()){
					AlertDialog.Builder netConDlgBuilder = GlobalMethods.NetworkProblemDialog(LoginAct.this);
					netConDlgBuilder.show();
				}
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			loadingDlg.dismiss();
		}
	}

	/************ dialog 생성하는 method ************/

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;

		switch (id) {
		case 1:
			builder = new AlertDialog.Builder(LoginAct.this)
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
			builder = new AlertDialog.Builder(LoginAct.this)
			.setTitle("알림")
			.setMessage(R.string.wrongIdPw)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 3:
			builder = new AlertDialog.Builder(LoginAct.this)
			.setTitle("알림")
			.setMessage(R.string.wrongFormat_id)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 4:
			builder = new AlertDialog.Builder(LoginAct.this)
			.setTitle("알림")
			.setMessage(R.string.wrongFormat_pw)
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

	/** exit App process **/
	@Override
	public void onBackPressed() {
		String alertTitle = getResources().getString(R.string.app_name);
		String buttonMessage = getResources().getString(R.string.notifyExit);
		String buttonYes = getResources().getString(android.R.string.yes);
		String buttonNo = getResources().getString(android.R.string.no);

		new AlertDialog.Builder(LoginAct.this)
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
}