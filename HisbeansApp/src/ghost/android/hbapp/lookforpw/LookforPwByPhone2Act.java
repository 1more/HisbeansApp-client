package ghost.android.hbapp.lookforpw;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;
import ghost.android.hbapp.common.RegExpression;

import java.util.HashMap;
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
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname LookforPwByPhone2Act.java
 * @package ghost.android.hbapp.lookforpw
 * @date 2012. 8. 7.
 * @purpose : Phone으로 비밀번호 찾기 2번째 Activity Class
 * 
 */

public class LookforPwByPhone2Act extends Activity {
	private Intent intent;

	private TextView p2PhoneTv;
	private TextView timerMinTv;
	private TextView timerSecTv;
	private TextView timerMiddleTv;

	private EditText confirmNumEt;

	private Button getNumB;
	private Button checkB;
	private Button cancelB;

	private String id;
	private String phoneNum;
	private String confirmNum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lookforpw_byphone2);

		// Get Intent Information
		intent = getIntent();
		id = intent.getStringExtra("id");
		phoneNum = intent.getStringExtra("phoneNum");



		/************ Find View and Setting ************/

		/*** Edit Text setting ***/
		
		p2PhoneTv = (TextView) findViewById(R.id.pw_byP2PhoneTv);
		p2PhoneTv.setText(phoneNum);

		confirmNumEt = (EditText) findViewById(R.id.pw_byP2NumEt);
		confirmNumEt.addTextChangedListener(new TextWatcher() {
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
				if (!confirmNumEt.getText().toString().equals("")) {
					checkB.setClickable(true);
					checkB.setBackgroundResource(drawable.find_btn_certi_normal);
				} else {
					checkB.setClickable(false);
					checkB.setBackgroundResource(drawable.find_btn_certi_off);
				}
			}
		});

		/*** Button Setting ***/
		getNumB = (Button) findViewById(R.id.pw_byP2GetNumB);
		getNumB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LookforPwByPhone2Act.this);

				// 인터넷환경이 원활 할 경우 인증번호 받기
				if (netConDlgBuilder == null) {
					/*** 인증번호 발급 ***/
					Map<String, String> param = new HashMap<String, String>();
					param.put("id", id);
					param.put("phoneNum", phoneNum);

					new ValidateConfirmNum().execute(param);
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Display
				else {
					netConDlgBuilder.show();
				}
			}
		});
		getNumB.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.find_btn_num_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.find_btn_num_normal);

				return false;
			}
		});

		checkB = (Button) findViewById(R.id.pw_byP2OkB);
		checkB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LookforPwByPhone2Act.this);

				// 인터넷환경이 원활 할 경우 인증 받기
				if (netConDlgBuilder == null) {
					confirmNum = confirmNumEt.getText().toString();

					// null check
					// number check
					// Check Confirm Number
					if (confirmNum.equals("")) {
						if(!LookforPwByPhone2Act.this.isFinishing())
							showDialog(1);
					}
					else if (!RegExpression.checkPattern(
							RegExpression.Patterns.NUMBER, confirmNum)) {
						confirmNumEt.setText("");

						if(!LookforPwByPhone2Act.this.isFinishing())
							showDialog(6);
					}
					else {
						Map<String, String> param = new HashMap<String, String>();
						param.put("id", id);
						param.put("confirmNum", confirmNum);

						new CheckConfirmNum().execute(param);
					}
				}
				// 인터넷 연결이 원할하지 않을 경우.
				else {
					netConDlgBuilder.show();
				}
			}
		});
		checkB.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (!confirmNumEt.getText().toString().equals("")) {
					if (event.getAction() == MotionEvent.ACTION_DOWN)
						v.setBackgroundResource(drawable.find_btn_certi_press);
					if (event.getAction() == MotionEvent.ACTION_UP)
						v.setBackgroundResource(drawable.find_btn_certi_normal);
				}

				return false;
			}
		});
		checkB.setClickable(false);

		cancelB = (Button) findViewById(R.id.pw_byP2CancelB);
		cancelB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LookforPwByPhone2Act.this, LoginAct.class);
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



		/********* Time Count and Display for Vaildate *********/

		timerMinTv = (TextView) findViewById(R.id.pw_byP2TimerMinTv);
		timerSecTv = (TextView) findViewById(R.id.pw_byP2TimerSecTv);
		timerMiddleTv = (TextView) findViewById(R.id.pw_byP2TimerMiddleTv);
		timerMinTv.setText("03");
		timerSecTv.setText("00");
		timerMiddleTv.setText(" : ");

		// 3분동안 1초 간격으로 카운트
		// 정해진 시간 지나면 안내메세지
		new CountDownTimer(60000 * 3, 1000) {
			public void onTick(long millisUntilFinished) {
				if (timerSecTv.getText().toString().equals("00")) {
					Integer min = Integer.parseInt(timerMinTv.getText()
							.toString());
					timerMinTv.setText(String.format("%02d", --min));
					timerSecTv.setText("59");
				} else {
					Integer sec = Integer.parseInt(timerSecTv.getText()
							.toString());
					timerSecTv.setText(String.format("%02d", --sec));
				}
			}

			public void onFinish() {
				if (!LookforPwByPhone2Act.this.isFinishing()) {
					timerSecTv.setText("00");
					showDialog(4);
				}
			}
		}.start();
	}

	/************ Get ConfirmNum By Phone Info Progress Class with AsyncTask ************/

	// params (id, phoneNum)
	private class ValidateConfirmNum extends AsyncTask<Map<String, String>, Void, Boolean> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// Progress Dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(LookforPwByPhone2Act.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(Map<String, String>... params) {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.getForObject(
					GlobalVariables.validateforPhoneURL, Map.class, params[0]);

			return response.get(GlobalVariables.KEY_VALIDATE_PHONE);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// confirm number를 성공적으로 받아오면 Time Text Reset
			// 성공 Dialog 띄우기
			if (res != null && res == true) {
				timerMinTv.setText("03");
				timerSecTv.setText("00");
				timerMiddleTv.setText(" : ");

				new CountDownTimer(60000 * 3, 1000) {
					public void onTick(long millisUntilFinished) {
						if (timerSecTv.getText().toString().equals("00")) {
							Integer min = Integer.parseInt(timerMinTv.getText()
									.toString());
							timerMinTv.setText(String.format("%02d", --min));
							timerSecTv.setText("59");
						} else {
							Integer sec = Integer.parseInt(timerSecTv.getText()
									.toString());
							timerSecTv.setText(String.format("%02d", --sec));
						}
					}

					public void onFinish() {
						if (!LookforPwByPhone2Act.this.isFinishing()) {
							timerSecTv.setText("00");
							showDialog(4);
						}
					}
				}.start();

				if(!LookforPwByPhone2Act.this.isFinishing())
					showDialog(2);
			}
			// confirm number를 성공적으로 받아지 못하면
			else if (res != null && res == false) {
				if(!LookforPwByPhone2Act.this.isFinishing())
					showDialog(5);
			}
			// fail to get information
			else if(res == null){
				if(!LookforPwByPhone2Act.this.isFinishing()){
					AlertDialog.Builder netConDlgBuilder = GlobalMethods.NetworkProblemDialog(LookforPwByPhone2Act.this);
					netConDlgBuilder.show();
				}
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();

			// loading dialog 사라지게 하기
			loadingDlg.dismiss();
		}
	}

	/************ Get ConfirmNum By Email Info Progress Class with AsyncTask ************/

	// params (id)
	private class CheckConfirmNum extends
	AsyncTask<Map<String, String>, Void, Boolean> {
		private MultiValueMap<String, String> formData;
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();

			loadingDlg = ProgressDialog.show(LookforPwByPhone2Act.this, "알림",
					"잠시만 기다려 주세요", true);
		}

		@Override
		protected Boolean doInBackground(Map<String, String>... params) {
			// populate the data to post
			formData = new LinkedMultiValueMap<String, String>();
			formData.add("id", params[0].get("id"));
			formData.add("validCode", params[0].get("confirmNum"));

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.postForObject(
					GlobalVariables.validateURL, formData, Map.class);

			// Return the response body to display to the user
			return response.get(GlobalVariables.KEY_VALIDATE);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			super.onPostExecute(res);

			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// confirm number 인증에 성공하면
			if (res != null && res == true) {
				confirmNumEt.setText("");

				intent = new Intent(LookforPwByPhone2Act.this,
						LookforPwDoneAct.class);
				intent.putExtra("id", id);
				intent.putExtra("confirmNum", confirmNum);
				startActivity(intent);
			}
			// confirm number을 인증에 실패하면
			else if (res != null && res == false) {
				confirmNumEt.setText("");

				showDialog(3);
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
			builder = new AlertDialog.Builder(LookforPwByPhone2Act.this)
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
			builder = new AlertDialog.Builder(LookforPwByPhone2Act.this)
			.setTitle("알림")
			.setMessage(R.string.reValidate)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			});

			return builder.create();
		case 3:
			builder = new AlertDialog.Builder(LookforPwByPhone2Act.this)
			.setTitle("알림")
			.setMessage(R.string.noMatch_valiNum)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		case 4:
			builder = new AlertDialog.Builder(LookforPwByPhone2Act.this)
			.setTitle("알림")
			.setMessage(R.string.overInputTime)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		case 5:
			builder = new AlertDialog.Builder(LookforPwByPhone2Act.this)
			.setTitle("알림")
			.setMessage(R.string.waitThreeMin)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		case 6:
			builder = new AlertDialog.Builder(LookforPwByPhone2Act.this)
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
		}
		return null;
	}
}
