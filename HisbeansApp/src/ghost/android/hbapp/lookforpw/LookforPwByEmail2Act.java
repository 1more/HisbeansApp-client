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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname LookforPwByEmail2Act.java
 * @package ghost.android.hbapp.lookforpw
 * @date 2012. 8. 7.
 * @purpose : Email로 비밀번호 찾기 2번째 Activity Class
 * 
 */

public class LookforPwByEmail2Act extends Activity {
	private Intent intent;

	private TextView e2EmailTv;
	private TextView timerMinTv;
	private TextView timerSecTv;
	private TextView timerMiddleTv;

	private EditText confirmNumEt;

	private Button getNumB;
	private Button checkB;
	private Button cancelB;

	private String id;
	private String email;
	private String confirmNum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lookforpw_byemail2);

		// Get Intent Information
		intent = getIntent();
		id = intent.getStringExtra("id");
		email = intent.getStringExtra("email");

		
		
		/************ Find View and Setting ************/
		
		/*** Edit Text setting ***/
		e2EmailTv = (TextView) findViewById(R.id.pw_byE2EmailTv);
		e2EmailTv.setText(email);

		confirmNumEt = (EditText) findViewById(R.id.pw_byE2NumEt);
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
		// 인증번호 다시 받기 버튼
		getNumB = (Button) findViewById(R.id.pw_byE2GetNumB);
		getNumB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LookforPwByEmail2Act.this);

				// 인터넷환경이 원활 할 경우 인증번호 받기
				if (netConDlgBuilder == null) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("id", id);
					param.put("email", email);

					// get validate confirm number
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

		// 인증번호 입력 완료 버튼
		checkB = (Button) findViewById(R.id.pw_byE2OkB);
		checkB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LookforPwByEmail2Act.this);

				// 인터넷환경이 원활 할 경우 인증받기
				if (netConDlgBuilder == null) {
					confirmNum = confirmNumEt.getText().toString();

					// null check
					// number check
					// Check Confirm Number
					if (confirmNum.equals("")) {
						if(!LookforPwByEmail2Act.this.isFinishing())
							showDialog(1);
					}
					else if (!RegExpression.checkPattern(
							RegExpression.Patterns.NUMBER, confirmNum)) {
						confirmNumEt.setText("");
						if(!LookforPwByEmail2Act.this.isFinishing())
							showDialog(6);
					}
					else {
						Map<String, String> param = new HashMap<String, String>();
						param.put("id", id);
						param.put("confirmNum", confirmNum);

						new CheckConfirmNum().execute(param);
					}
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Dislplay
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

		cancelB = (Button) findViewById(R.id.pw_byE2CancelB);
		cancelB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LookforPwByEmail2Act.this, LoginAct.class);
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

		timerMinTv = (TextView) findViewById(R.id.pw_byE2TimerMinTv);
		timerSecTv = (TextView) findViewById(R.id.pw_byE2TimerSecTv);
		timerMiddleTv = (TextView) findViewById(R.id.pw_byE2TimerMiddleTv);
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
				if (!LookforPwByEmail2Act.this.isFinishing()) {
					timerSecTv.setText("00");
					showDialog(4);
				}
			}
		}.start();
	}

	
	
	/************ Get ConfirmNum By Email Info Progress Class with AsyncTask ************/

	// params (id, email)
	private class ValidateConfirmNum extends AsyncTask<Map<String, String>, Void, Boolean> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// Progress Dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(LookforPwByEmail2Act.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(Map<String, String>... params) {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.getForObject(
					GlobalVariables.validateforMailURL, Map.class, params[0]);

			return response.get("validateForMail");
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 정보를 성공적으로 받아오면 성공 Dialog Display
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
						if (!LookforPwByEmail2Act.this.isFinishing()) {
							timerSecTv.setText("00");
							showDialog(4);
						}
					}
				}.start();

				if(!LookforPwByEmail2Act.this.isFinishing())
					showDialog(2);
			}
			// Match하는 ID가 없으면 실패 Dialog Display
			else if (res != null && res == false) {
				if(!LookforPwByEmail2Act.this.isFinishing())
					showDialog(5);
			}
			// Fail to get information
			else if(res == null){
				if(!LookforPwByEmail2Act.this.isFinishing()){
					AlertDialog.Builder netConDlgBuilder = GlobalMethods.NetworkProblemDialog(LookforPwByEmail2Act.this);
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

	
	
	/************ Check ConfirmNum Progress Class with AsyncTask ************/

	// params (email)
	private class CheckConfirmNum extends AsyncTask<Map<String, String>, Void, Boolean> {
		private MultiValueMap<String, String> formData;
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(LookforPwByEmail2Act.this, "알림",
					"잠시만 기다려 주세요", true);
		}

		@Override
		protected Boolean doInBackground(Map<String, String>... params) {
			// Populate the data to post
			formData = new LinkedMultiValueMap<String, String>();
			formData.add("id", params[0].get("id"));
			formData.add("validCode", params[0].get("confirmNum"));

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String,Boolean> response = restTemplate.postForObject(
					GlobalVariables.validateURL, formData, Map.class);

			// Return the response body to display to the user
			return response.get(GlobalVariables.KEY_VALIDATE);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 정보를 성공적으로 받아오면 완료 Acitivity로 이동
			if (res != null && res == true) {
				confirmNumEt.setText("");

				intent = new Intent(LookforPwByEmail2Act.this,
						LookforPwDoneAct.class);
				intent.putExtra("id", id);
				intent.putExtra("confirmNum", confirmNum);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
			// confirm number을 인증에 실패하면 Warning Dialog Display
			else if (res != null && res == false) {
				confirmNumEt.setText("");
				if(!LookforPwByEmail2Act.this.isFinishing())
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
			builder = new AlertDialog.Builder(LookforPwByEmail2Act.this)
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
			builder = new AlertDialog.Builder(LookforPwByEmail2Act.this)
			.setTitle("알림")
			.setMessage(R.string.reValidate)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();

					startActivity(intent);
					finish();
				}
			});

			return builder.create();
		case 3:
			builder = new AlertDialog.Builder(LookforPwByEmail2Act.this)
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
			builder = new AlertDialog.Builder(LookforPwByEmail2Act.this)
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
			builder = new AlertDialog.Builder(LookforPwByEmail2Act.this)
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
			builder = new AlertDialog.Builder(LookforPwByEmail2Act.this)
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
