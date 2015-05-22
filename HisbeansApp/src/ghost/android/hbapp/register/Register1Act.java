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
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author HyeonWook Kim
 * @email khw0867@gmail.com
 * @classname Register1Act.java
 * @package ghost.android.hbapp.register
 * @date 2012. 10. 23. 오전 11:44:07
 * @purpose : 회원가입 1번째 Activity Class
 * @comment :
 */

public class Register1Act extends Activity {
	private EditText nameEt;
	private EditText idEt;
	private EditText pwEt;
	private EditText confPwEt;
	private EditText phoneEt;
	private EditText emailEt;

	private Button nextBtn;
	private Button cancelBtn;
	private Button dupCheckBtn;

	private String name;
	private String id;
	private String pw;
	private String confPw;
	private String phone;
	private String email;

	private boolean checkDupIdCheck = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_form);

		/************ Find View and Setting ************/

		/*** Edit Text Setting ***/

		nameEt = (EditText) findViewById(R.id.register_nameEt);
		nameEt.addTextChangedListener(new TextWatcher() {
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
				if (checkAllEditTextsFull()) {
					nextBtn.setClickable(true);
					nextBtn.setBackgroundResource(drawable.find_btn_next);
				} else {
					nextBtn.setClickable(false);
					nextBtn.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		idEt = (EditText) findViewById(R.id.register_idEt);
		idEt.addTextChangedListener(new TextWatcher() {
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
				// id text가 변경되면 다시 중복체크를 해야하도록 설정.
				checkDupIdCheck = false;

				if (checkAllEditTextsFull()) {
					nextBtn.setClickable(true);
					nextBtn.setBackgroundResource(drawable.find_btn_next);
				} else {
					nextBtn.setClickable(false);
					nextBtn.setBackgroundResource(drawable.find_btn_next_off);
				}

				if (idEt.getText().toString().equals("")) {
					dupCheckBtn.setClickable(false);
					dupCheckBtn.setBackgroundResource(drawable.dupcheck_btn_off);
				} else {
					dupCheckBtn.setClickable(true);
					dupCheckBtn.setBackgroundResource(drawable.dupcheck_btn);
				}
			}
		});

		pwEt = (EditText) findViewById(R.id.register_pwEt);
		pwEt.addTextChangedListener(new TextWatcher() {
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
				if (checkAllEditTextsFull()) {
					nextBtn.setClickable(true);
					nextBtn.setBackgroundResource(drawable.find_btn_next);
				} else {
					nextBtn.setClickable(false);
					nextBtn.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		confPwEt = (EditText) findViewById(R.id.register_pwConfEt);
		confPwEt.addTextChangedListener(new TextWatcher() {
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
				if (checkAllEditTextsFull()) {
					nextBtn.setClickable(true);
					nextBtn.setBackgroundResource(drawable.find_btn_next);
				} else {
					nextBtn.setClickable(false);
					nextBtn.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		phoneEt = (EditText) findViewById(R.id.register_phoneEt);
		phoneEt.addTextChangedListener(new TextWatcher() {
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
				if (checkAllEditTextsFull()) {
					nextBtn.setClickable(true);
					nextBtn.setBackgroundResource(drawable.find_btn_next);
				} else {
					nextBtn.setClickable(false);
					nextBtn.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		emailEt = (EditText) findViewById(R.id.register_mailEt);
		emailEt.addTextChangedListener(new TextWatcher() {
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
				if (checkAllEditTextsFull()) {
					nextBtn.setClickable(true);
					nextBtn.setBackgroundResource(drawable.find_btn_next);
				} else {
					nextBtn.setClickable(false);
					nextBtn.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		/*** Button Setting ***/

		nextBtn = (Button) findViewById(R.id.register_mainNextBtn);
		nextBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, Register1Act.this);

				// 인터넷환경이 원활 할 경우 회원가입 진행
				if (netConDlgBuilder == null) {
					// get information
					name = nameEt.getText().toString();
					id = idEt.getText().toString();
					pw = pwEt.getText().toString();
					confPw = confPwEt.getText().toString();
					phone = phoneEt.getText().toString();
					email = emailEt.getText().toString();

					// null check
					// name format check
					// email or phone format check
					if (!checkAllEditTextsFull()) {
						if (!Register1Act.this.isFinishing())
							showDialog(1);
					} else if (!RegExpression.checkPattern(
							RegExpression.Patterns.KOR_ALPHA, name)) {
						if (!Register1Act.this.isFinishing())
							showDialog(3);
					} else if (!(RegExpression.checkPattern(
							RegExpression.Patterns.EMAIL, email))) {
						if (!Register1Act.this.isFinishing())
							showDialog(4);
					} else if (!(RegExpression.checkPattern(
							RegExpression.Patterns.PHONE_ONLYNUM, phone))) {
						if (!Register1Act.this.isFinishing())
							showDialog(5);
					} else if (!(pw.equals(confPw))) {
						pwEt.setText("");
						confPwEt.setText("");

						if (!Register1Act.this.isFinishing())
							showDialog(6);
					} else if (checkDupIdCheck == false) {
						if (!Register1Act.this.isFinishing())
							showDialog(9);
					} else if (!(RegExpression.checkPattern(
							RegExpression.Patterns.ALPHABET,
							id.charAt(0) + ""))) {
						if (!Register1Act.this.isFinishing())
							showDialog(11);
					} else if (!(RegExpression.checkPattern(
							RegExpression.Patterns.ALPHA_NUM, id))) {
						if (!Register1Act.this.isFinishing())
							showDialog(12);
					} else if (GlobalMethods.checkSpecialCharacterIn(pw)) {
						pwEt.setText("");
						confPwEt.setText("");
						
						if (!Register1Act.this.isFinishing())
							showDialog(13);
					} else {
						Map<String, String> param = new HashMap<String, String>();
						param.put("email", email);
						param.put("phone", phone);
						new CheckEmailPhoneExist().execute(param);
					}
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Display
				else {
					nameEt.setText("");
					idEt.setText("");
					pwEt.setText("");
					confPwEt.setText("");
					emailEt.setText("");
					phoneEt.setText("");

					if (!Register1Act.this.isFinishing())
						netConDlgBuilder.show();
				}
			}
		});
		nextBtn.setClickable(false);

		cancelBtn = (Button) findViewById(R.id.register_mainCancelBtn);
		cancelBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Register1Act.this, LoginAct.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});

		dupCheckBtn = (Button) findViewById(R.id.register_dupCheckBtn);
		dupCheckBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// ID길이가 너무 짧은지 체크 
				if(idEt.getText().length() < 2){
					showDialog(11);
					return;
				}

				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, Register1Act.this);

				// 인터넷환경이 원활 할 경우 중복 체크
				if (netConDlgBuilder == null) {
					// get information
					String id = idEt.getText().toString();

					// null check
					if (idEt.getText().toString().equals("")) {
						if (!Register1Act.this.isFinishing())
							showDialog(1);
					} else {
						new CheckIdExist().execute(id);
					}
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Display
				else {
					if (!Register1Act.this.isFinishing())
						netConDlgBuilder.show();
				}
			}
		});
		dupCheckBtn.setClickable(false);

		// 단말기의 전화번호를 가지고 와서 자동으로 입력.
		TelephonyManager phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String pNum = phoneManager.getLine1Number().replace("+82", "0");
		phoneEt.setText(pNum);
	}

	/************ Look for ID in Database or not ************/

	private class CheckIdExist extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(Register1Act.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			String url = GlobalVariables.checkDupIdURL;
			String tag = GlobalVariables.KEY_ID_DUPCHECK;

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.getForObject(url,
					Map.class, params[0]);

			return response.get(tag);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 정보를 성공적으로 받아오지 못하면 실패 Dialog Display
			if (res != true) {
				idEt.setText("");

				if (!Register1Act.this.isFinishing())
					showDialog(7);
			}
			// 정보를 성공적으로 받아오면 중복체크 여부 True 후 성공 Dialog Display
			else {
				checkDupIdCheck = true;

				if (!Register1Act.this.isFinishing())
					showDialog(8);
			}
		}

		@Override
		protected void onCancelled() {
			// loading dialog 사라지게 하기
			super.onCancelled();
			loadingDlg.dismiss();
		}
	}

	/************ Look for Phone and Email in Database or not ************/

	private class CheckEmailPhoneExist extends
	AsyncTask<Map<String, String>, Void, Boolean> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(Register1Act.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(Map<String, String>... params) {
			String url = GlobalVariables.checkDupPhoneEmailURL;
			String tag = GlobalVariables.KEY_PHONE_EMAIL_DUPCHECK;

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.getForObject(url,
					Map.class, params[0]);

			return response.get(tag);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 정보를 성공적으로 받지 못하면 실패 Dialog Display
			if (res != true) {
				if (!Register1Act.this.isFinishing())
					showDialog(10);
			}
			// 정보를 성공적으로 받아오면 회원가입 진행
			else {
				Intent intent = new Intent(Register1Act.this,
						Register2Act.class);
				intent.putExtra(GlobalVariables.INTENT_REGISTER_NAME, name);
				intent.putExtra(GlobalVariables.INTENT_REGISTER_ID, id);
				intent.putExtra(GlobalVariables.INTENT_REGISTER_PW, pw);
				intent.putExtra(GlobalVariables.INTENT_REGISTER_EMAIL, email);
				intent.putExtra(GlobalVariables.INTENT_REGISTER_PHONE, phone);

				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		}

		@Override
		protected void onCancelled() {
			// loading dialog 사라지게 하기
			super.onCancelled();
			loadingDlg.dismiss();
		}
	}

	/********* Create Dialog *********/

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;

		switch (id) {
		case 1:
			builder = new AlertDialog.Builder(Register1Act.this)
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
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.noMatchInfo)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 3:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.wrongFormat_name)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 4:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.wrongFormat_email)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 5:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.wrongFormat_phone)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 6:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.wrongFormat_password)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 7:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.dup_id)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 8:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.non_dup_id)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 9:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.check_dup_id)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 10:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.dup_phoneemail)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 11:
			builder = new AlertDialog.Builder(Register1Act.this)
			.setTitle("알림")
			.setMessage(R.string.invalid_id)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 12:
			builder = new AlertDialog.Builder(Register1Act.this)
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
		case 13:
			builder = new AlertDialog.Builder(Register1Act.this)
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



	/*** Self method ***/

	// check every edit-text form blank or not
	private boolean checkAllEditTextsFull() {
		if (idEt.getText().toString().equals("")) {
			return false;
		} else if (nameEt.getText().toString().equals("")) {
			return false;
		} else if (pwEt.getText().toString().equals("")) {
			return false;
		} else if (confPwEt.getText().toString().equals("")) {
			return false;
		} else if (phoneEt.getText().toString().equals("")) {
			return false;
		} else if (emailEt.getText().toString().equals("")) {
			return false;
		}

		return true;
	}
}
