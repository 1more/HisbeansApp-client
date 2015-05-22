package ghost.android.hbapp.register;

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
 * @author HyeonWook Kim
 * @email khw0867@gmail.com
 * @classname Register2Act.java
 * @package ghost.android.hbapp.register
 * @date 2012. 10. 23. 오전 11:53:34
 * @purpose : 회원가입 2번째 Activity Class
 * @comment :
 */

public class Register2Act extends Activity {
	private Intent intent;

	private TextView phoneTv;
	private TextView timerMinTv;
	private TextView timerSecTv;
	private TextView timerMiddleTv;

	private EditText confirmNumEt;

	private Button getNumB;
	private Button checkB;
	private Button cancelB;

	private String id;
	private String name;
	private String pw;
	private String email;
	private String phoneNum;
	private String confirmNum;

	private boolean getNumIng = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_form2);


		// Get Intent Information
		intent = getIntent();
		id = intent.getStringExtra(GlobalVariables.INTENT_REGISTER_ID);
		pw = intent.getStringExtra(GlobalVariables.INTENT_REGISTER_PW);
		name = intent.getStringExtra(GlobalVariables.INTENT_REGISTER_NAME);
		email = intent.getStringExtra(GlobalVariables.INTENT_REGISTER_EMAIL);
		phoneNum = intent.getStringExtra(GlobalVariables.INTENT_REGISTER_PHONE);



		/************ Find View and Setting ************/

		/*** Edit Text Setting ***/

		phoneTv = (TextView) findViewById(R.id.register_phoneTv);
		phoneTv.setText(phoneNum);

		confirmNumEt = (EditText) findViewById(R.id.register_numEt);
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

		/*** Time Count and Display for Vaildate ***/

		timerMinTv = (TextView) findViewById(R.id.register_timerMinTv);
		timerSecTv = (TextView) findViewById(R.id.register_timerSecTv);
		timerMiddleTv = (TextView) findViewById(R.id.register_timerMiddleTv);
		timerMinTv.setText("03");
		timerSecTv.setText("00");
		timerMiddleTv.setText(" : ");

		/*** Button Setting ***/

		getNumB = (Button) findViewById(R.id.register_getNumB);
		getNumB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(
						Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, Register2Act.this);

				// 인터넷환경이 원활 할 경우 인증번호 받기
				if (netConDlgBuilder == null) {
					// 인증번호 발급
					Map<String, String> param = new HashMap<String, String>();
					param.put("id", id);
					param.put("phoneNum", phoneNum);

					new ValidateConfirmNum().execute(param);
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Display
				else {
					if(!Register2Act.this.isFinishing())
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

		checkB = (Button) findViewById(R.id.register_okB);
		checkB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에, 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(
						Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, Register2Act.this);

				// 인터넷환경이 원활 할 경우.
				if (netConDlgBuilder == null) {
					confirmNum = confirmNumEt.getText().toString();

					// 인증번호를 받았을 때에만 인증 진행
					if(getNumIng){
						// null check
						// number check
						// Check Confirm Number
						if (confirmNum.equals("")) {
							if(!Register2Act.this.isFinishing())
								showDialog(1);
						}
						else if (!RegExpression.checkPattern(
								RegExpression.Patterns.NUMBER, confirmNum)) {
							confirmNumEt.setText("");

							if(!Register2Act.this.isFinishing())
								showDialog(6);
						}
						else {
							Map<String, String> param = new HashMap<String, String>();
							param.put("id", id);
							param.put("confirmNum", confirmNum);

							new CheckConfirmNum().execute(param);
						}
					}
					// 인증번호를 받지 않았을 경우 안내 Dialog
					else{
						if(!Register2Act.this.isFinishing())
							showDialog(8);
					}
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Display
				else {
					if(!Register2Act.this.isFinishing())
						netConDlgBuilder.show();
				}
			}
		});
		checkB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if(!confirmNumEt.getText().toString().equals("")){
					if (event.getAction() == MotionEvent.ACTION_DOWN)
						v.setBackgroundResource(drawable.find_btn_certi_press);
					if (event.getAction() == MotionEvent.ACTION_UP)
						v.setBackgroundResource(drawable.find_btn_certi_normal);
				}
				
				return false;
			}
		});
		checkB.setClickable(false);

		cancelB = (Button) findViewById(R.id.register_cancelB);
		cancelB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(Register2Act.this, LoginAct.class);
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


	/************ Get ConfirmNum By Phone Info Progress Class with AsyncTask ************/

	// params (id, phoneNum)
	private class ValidateConfirmNum extends AsyncTask<Map<String, String>, Void, Boolean> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(Register2Act.this, "알림",
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

			// confirm number를 성공적으로 받아오면 Time Text Reset and 인증번호 받기
			if (res != null && res == true) {
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
						if (!Register2Act.this.isFinishing()) {
							timerSecTv.setText("00");
							getNumIng = false;
							showDialog(4);
						}
					}
				}.start();

				getNumIng = true;
				if(!Register2Act.this.isFinishing())
					showDialog(2);
			}
			// confirm number를 성공적으로 받아지 못하면 실패 Dialog Display
			else if (res != null && res == false) {
				if(!Register2Act.this.isFinishing())
					showDialog(5);
			}
			// Fail to get information
			else if(res == null){
				if(!Register2Act.this.isFinishing()){
					AlertDialog.Builder netConDlgBuilder = GlobalMethods.NetworkProblemDialog(Register2Act.this);
					netConDlgBuilder.show();
				}
			}
		}

		@Override
		protected void onCancelled() {
			// loading dialog 사라지게 하기
			super.onCancelled();
			loadingDlg.dismiss();
		}
	}

	/************ Check Confirm Num Progress Class with AsyncTask ************/

	// params (id)
	private class CheckConfirmNum extends
	AsyncTask<Map<String, String>, Void, Boolean> {
		private MultiValueMap<String, String> formData;
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(Register2Act.this, "알림",
					"잠시만 기다려 주세요", true);
		}

		@Override
		protected Boolean doInBackground(Map<String, String>... params) {
			// Populate the data to POST
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
			// loading dialog 사라지게 하기
			super.onPostExecute(res);
			loadingDlg.dismiss();

			// confirm number 인증에 성공하면 회원가입
			if (res != null && res == true) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("id", id);
				param.put("pw", GlobalMethods.md5(pw));
				param.put("name", name);
				param.put("email", email);
				param.put("phone", phoneNum);

				new RegisterRequest().execute(param);
			}
			// confirm number을 인증에 실패하면
			else if (res != null && res == false) {
				confirmNumEt.setText("");
				if(!Register2Act.this.isFinishing())
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

	/************ Register Request Class with AsyncTask ************/

	private class RegisterRequest extends AsyncTask<Map<String, String>, Void, Integer> {
		private MultiValueMap<String, String> formData;
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(Register2Act.this, "알림",
					"잠시만 기다려 주세요", true);
		}

		@Override
		protected Integer doInBackground(Map<String, String>... params) {
			// Populate the data to POST
			formData = new LinkedMultiValueMap<String, String>();
			formData.add("id", params[0].get("id"));
			formData.add("password", params[0].get("pw"));
			formData.add("email", params[0].get("email"));
			formData.add("name", params[0].get("name"));
			formData.add("phoneNum", params[0].get("phone"));

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate(true);
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Integer> response = restTemplate.postForObject(
					GlobalVariables.registerURL, formData, Map.class);

			// Return the response body to display to the user
			return response.get(GlobalVariables.KEY_SIGNUP);
		}

		@Override
		protected void onPostExecute(Integer res) {
			// loading dialog 사라지게 하기
			super.onPostExecute(res);
			loadingDlg.dismiss();

			// 회원 가입에 성공하면 완료 Activity로 이동
			if (res != null && res == 1) {
				confirmNumEt.setText("");

				intent = new Intent(Register2Act.this,
						RegisterDoneAct.class);
				intent.putExtra("name", name);
				intent.putExtra("id", id);
				intent.putExtra("pw", pw);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
			// 회원 가입에 실패하면 Warning Dialog Display
			else if (res != null && res != 1) {
				confirmNumEt.setText("");
				if(!Register2Act.this.isFinishing())
					showDialog(7);
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
			builder = new AlertDialog.Builder(Register2Act.this)
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
			builder = new AlertDialog.Builder(Register2Act.this)
			.setTitle("알림")
			.setMessage(R.string.validate)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			return builder.create();
		case 3:
			builder = new AlertDialog.Builder(Register2Act.this)
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
			builder = new AlertDialog.Builder(Register2Act.this)
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
			builder = new AlertDialog.Builder(Register2Act.this)
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
			builder = new AlertDialog.Builder(Register2Act.this)
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
		case 7:
			builder = new AlertDialog.Builder(Register2Act.this)
			.setTitle("알림")
			.setMessage(R.string.register_err)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 8:
			builder = new AlertDialog.Builder(Register2Act.this)
			.setTitle("알림")
			.setMessage("인증번호를 받아주세요.")
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
