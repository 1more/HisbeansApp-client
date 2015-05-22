package ghost.android.hbapp.lookforpw;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;

import java.util.Map;

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
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname LookforPwDoneAct.java
 * @package ghost.android.hbapp.lookforpw
 * @date 2012. 7. 26.s
 * @purpose : Password 찾기 완료 Activity Class
 * 
 */

public class LookforPwDoneAct extends Activity {
	private Intent intent;

	private EditText newPwEt;
	private EditText checkPwEt;

	private Button loginB;
	private Button cancelB;

	private String id;
	private String confirmNum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lookforpw_done);

		// Get Intent Information
		intent = getIntent();
		id = intent.getStringExtra("id");
		confirmNum = intent.getStringExtra("confirmNum");

		
		
		/************ Find View and Setting ************/

		/*** Edit Text Setting ***/
		
		newPwEt = (EditText) findViewById(R.id.pw_doneNewPWEt);
		newPwEt.addTextChangedListener(new TextWatcher() {
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
				if (!newPwEt.getText().toString().equals("")
						&& !checkPwEt.getText().toString().equals("")) {
					loginB.setClickable(true);
					loginB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					loginB.setClickable(false);
					loginB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		checkPwEt = (EditText) findViewById(R.id.pw_doneCheckPWEt);
		checkPwEt.addTextChangedListener(new TextWatcher() {
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
				if (!newPwEt.getText().toString().equals("")
						&& !checkPwEt.getText().toString().equals("")) {
					loginB.setClickable(true);
					loginB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					loginB.setClickable(false);
					loginB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		
		/*** Button Setting ***/
		
		loginB = (Button) findViewById(R.id.pw_doneLoginB);
		loginB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LookforPwDoneAct.this);

				// 인터넷환경이 원활 할 경우 비밀번호 변경 후 Login Activity로 이동
				if (netConDlgBuilder == null) {
					String newPw = newPwEt.getText().toString();
					String checkPw = checkPwEt.getText().toString();

					// null check
					if (newPw.equals("") || checkPw.equals("")) {
						if(!LookforPwDoneAct.this.isFinishing())
							showDialog(1);
					}
					// 비밀번호 일치하면 변경 후 activity 이동
					else if (newPw.equals(checkPw)) {
						new ChangePasswordTask().execute(
								id, GlobalMethods.md5(newPw), confirmNum);
					}
					// 비밀번호 비일치하면 Error Dialog Display
					else {
						newPwEt.setText("");
						checkPwEt.setText("");

						if(!LookforPwDoneAct.this.isFinishing())
							showDialog(4);
					}
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Display
				else {
					if(!LookforPwDoneAct.this.isFinishing())
						netConDlgBuilder.show();
				}
			}
		});
		loginB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				if (!newPwEt.getText().toString().equals("")
						&& !checkPwEt.getText().toString().equals("")) {
					if (event.getAction() == MotionEvent.ACTION_DOWN)
						v.setBackgroundResource(drawable.find_btn_next_press);
					if (event.getAction() == MotionEvent.ACTION_UP)
						v.setBackgroundResource(drawable.find_btn_next_normal);
				}

				return false;
			}
		});
		loginB.setClickable(false);

		cancelB = (Button) findViewById(R.id.pw_doneCancelB);
		cancelB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LookforPwDoneAct.this,
						LoginAct.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		cancelB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.find_btn_cancel_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.find_btn_cancel_normal);

				return false;
			}
		});
	}



	/************ Change Passoword Progress Class with AsyncTask ************/

	// params (id, phoneNum, confirmNum)
	private class ChangePasswordTask extends AsyncTask<String, Void, Boolean> {
		private MultiValueMap<String, String> formData;
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(LookforPwDoneAct.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// Populate the data to POST
			formData = new LinkedMultiValueMap<String, String>();
			formData.add("id", params[0]);
			formData.add("newPassword", params[1]);
			formData.add("validCode", params[2]);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.postForObject(
					GlobalVariables.modifyPasswordURL, formData, Map.class);

			// Return the response body to display to the user
			return response.get(GlobalVariables.KEY_MODIFY_PASSWORD);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 비밀번호 변경이 성공하면 성공 Dialog Display
			if (res != null && res == true) {
				newPwEt.setText("");
				checkPwEt.setText("");

				if(!LookforPwDoneAct.this.isFinishing())
					showDialog(2);
			}
			// 비밀번호 변경이 실패하면 Warning Dialog Display
			else if (res != null && res == false) {
				newPwEt.setText("");
				checkPwEt.setText("");

				if(!LookforPwDoneAct.this.isFinishing())
					showDialog(3);
			}
			// Fail to get information
			else if(res == null){
				if(!LookforPwDoneAct.this.isFinishing())
					GlobalMethods.NetworkProblemDialog(LookforPwDoneAct.this);
			}
		}

		@Override
		protected void onCancelled() {
			// loading dialog 사라지게 하기
			super.onCancelled();
			loadingDlg.dismiss();
		}
	}



	/************ Create Dialog Method ************/

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;

		switch (id) {
		case 1:
			builder = new AlertDialog.Builder(LookforPwDoneAct.this)
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
			builder = new AlertDialog.Builder(LookforPwDoneAct.this)
			.setTitle("알림")
			.setMessage(R.string.modiPwDone)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();

					// move activity
					intent = new Intent(LookforPwDoneAct.this,
							LoginAct.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			});

			return builder.create();
		case 3:
			builder = new AlertDialog.Builder(LookforPwDoneAct.this)
			.setTitle("알림")
			.setMessage(R.string.errModiPw)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		case 4:
			builder = new AlertDialog.Builder(LookforPwDoneAct.this)
			.setTitle("알림")
			.setMessage(R.string.noMatch_pw)
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
}
