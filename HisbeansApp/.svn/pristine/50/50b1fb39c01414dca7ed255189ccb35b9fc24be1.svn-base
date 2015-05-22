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
import android.widget.ImageView;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname LookforPwMainAct.java
 * @package ghost.android.hbapp.lookforpw
 * @date 2012. 7. 26.
 * @purpose : Password 찾기 Main Activity Class
 * 
 */

public class LookforPwMainAct extends Activity {
	private Intent intent;

	private ImageView eOrPIv;

	private EditText idEt;
	private EditText nameEt;
	private EditText infoEt;

	private String id;
	private String name;
	private String info;

	private Button nextB;
	private Button cancelB;
	private Button byEB;
	private Button byPB;

	private String emailOrPhone = "email";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lookforpw_main);



		/************ Find View and Setting ************/

		/*** Edit Text Setting ***/
		idEt = (EditText) findViewById(R.id.pw_mainIDEt);
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
				if (!idEt.getText().toString().equals("")
						&& !nameEt.getText().toString().equals("")
						&& !infoEt.getText().toString().equals("")) {
					nextB.setClickable(true);
					nextB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					nextB.setClickable(false);
					nextB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		nameEt = (EditText) findViewById(R.id.pw_mainNameEt);
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
				if (!idEt.getText().toString().equals("")
						&& !nameEt.getText().toString().equals("")
						&& !infoEt.getText().toString().equals("")) {
					nextB.setClickable(true);
					nextB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					nextB.setClickable(false);
					nextB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		infoEt = (EditText) findViewById(R.id.pw_mainCheckInfoEt);
		infoEt.addTextChangedListener(new TextWatcher() {
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
				if (!idEt.getText().toString().equals("")
						&& !nameEt.getText().toString().equals("")
						&& !infoEt.getText().toString().equals("")) {
					nextB.setClickable(true);
					nextB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					nextB.setClickable(false);
					nextB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});



		/*** Button Setting ***/

		eOrPIv = (ImageView) findViewById(R.id.pw_mainEorPIv);
		byEB = (Button) findViewById(R.id.pw_mainByEB);
		byEB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				idEt.setText("");
				nameEt.setText("");
				infoEt.setText("");
				emailOrPhone = "email";

				eOrPIv.setImageResource(drawable.find_txt_mail);
				byEB.setBackgroundResource(drawable.find_switch_a_selected);
				byPB.setBackgroundResource(drawable.find_switch_b_unselected);
			}
		});
		byEB.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.find_switch_a_selected);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.find_switch_a_unselected);

				return false;
			}
		});

		byPB = (Button) findViewById(R.id.pw_mainByPB);
		byPB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				idEt.setText("");
				nameEt.setText("");
				infoEt.setText("");
				emailOrPhone = "phone";

				eOrPIv.setImageResource(drawable.find_txt_phone);
				byPB.setBackgroundResource(drawable.find_switch_b_selected);
				byEB.setBackgroundResource(drawable.find_switch_a_unselected);
			}
		});
		byPB.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.find_switch_b_selected);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.find_switch_b_unselected);

				return false;
			}
		});



		/********* Button Click Listeners *********/

		nextB = (Button) findViewById(R.id.pw_mainNextB);
		nextB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(
						Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LookforPwMainAct.this);

				// 인터넷환경이 원활 할 경우 Grammer Check 후 비밀번호 찾기 진행
				if (netConDlgBuilder == null) {
					// get information
					id = idEt.getText().toString();
					name = nameEt.getText().toString();
					info = infoEt.getText().toString();

					// null check
					// ID format check
					// name format check
					// email or phone format check
					if (name.equals("") || info.equals("") || id.equals("")) {
						nameEt.setText("");
						infoEt.setText("");
						idEt.setText("");

						if(!LookforPwMainAct.this.isFinishing())
							showDialog(1);
					} 
					else if (!RegExpression.checkPattern(
							RegExpression.Patterns.ALPHA_NUM, id)) {
						nameEt.setText("");
						infoEt.setText("");
						idEt.setText("");

						if(!LookforPwMainAct.this.isFinishing())
							showDialog(5);
					} 
					else if (!RegExpression.checkPattern(
							RegExpression.Patterns.KOR_ALPHA, name)) {
						nameEt.setText("");
						infoEt.setText("");
						idEt.setText("");

						if(!LookforPwMainAct.this.isFinishing())
							showDialog(3);
					} 
					else if (!(RegExpression.checkPattern(RegExpression.Patterns.EMAIL, info) 
							|| RegExpression.checkPattern(RegExpression.Patterns.PHONE_ONLYNUM, info))) {
						nameEt.setText("");
						infoEt.setText("");
						idEt.setText("");

						if(!LookforPwMainAct.this.isFinishing())
							showDialog(4);
					} 
					else {
						Map<String, String> param = new HashMap<String, String>();
						param.put("id", id);
						param.put("name", name);
						param.put("info", info);

						// find pw by info
						new CheckPwInfo().execute(param);

						nameEt.setText("");
						infoEt.setText("");
						idEt.setText("");
					}
				}
				// 인터넷 연결이 원할하지 않을 경우 Warning Dialog Display
				else {
					idEt.setText("");
					nameEt.setText("");
					infoEt.setText("");

					if(!LookforPwMainAct.this.isFinishing())
						netConDlgBuilder.show();
				}
			}
		});
		nextB.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (!idEt.getText().toString().equals("")
						&& !nameEt.getText().toString().equals("")
						&& !infoEt.getText().toString().equals("")) {
					if (event.getAction() == MotionEvent.ACTION_DOWN)
						v.setBackgroundResource(drawable.find_btn_next_press);
					if (event.getAction() == MotionEvent.ACTION_UP)
						v.setBackgroundResource(drawable.find_btn_next_normal);
				}

				return false;
			}
		});
		nextB.setClickable(false);

		cancelB = (Button) findViewById(R.id.pw_mainCancelB);
		cancelB.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				intent = new Intent(LookforPwMainAct.this, LoginAct.class);
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



	/************ Look for PW By Email Info Progress Class with AsyncTask ************/

	// params (checkEorP, id, name, checkInfo)
	private class CheckPwInfo extends AsyncTask<Map<String, String>, Void, Boolean> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(LookforPwMainAct.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(Map<String, String>... params) {
			String url = null;
			String tag = null;

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			if (emailOrPhone.equals("email")) {
				url = GlobalVariables.lookforPasswordByEURL;
				tag = GlobalVariables.KEY_BY_ID_NAME_EMAIL;
			} else {
				url = GlobalVariables.lookforPasswordByPURL;
				tag = GlobalVariables.KEY_BY_ID_NAME_PHONE;
			}

			Map<String, Boolean> response = restTemplate.getForObject(
					url , Map.class, params[0]);

			return response.get(tag);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// check pw info가 성공하면 비밀번호 찾기 진행
			if(res == true){
				idEt.setText("");
				nameEt.setText("");
				infoEt.setText("");

				// Email로 찾기
				if (emailOrPhone.equals("email"))
					intent = new Intent(LookforPwMainAct.this, LookforPwByEmailAct.class);
				// Phone으로 찾기
				else
					intent = new Intent(LookforPwMainAct.this, LookforPwByPhoneAct.class);

				// Information을 추후 Activity에 전송 By Intent
				intent.putExtra("checkInfo", info);
				intent.putExtra("id", id);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
			// check pw info가 실패하면 Warning Dialog Display
			else if(res == false){
				idEt.setText("");
				nameEt.setText("");
				infoEt.setText("");

				if(!LookforPwMainAct.this.isFinishing())
					showDialog(2);
			}
			// Fail to get information
			else if(res == null){
				if(!LookforPwMainAct.this.isFinishing()){
					AlertDialog.Builder netConDlgBuilder = GlobalMethods.NetworkProblemDialog(LookforPwMainAct.this);
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



	/************ Create Dialog Method ************/

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;

		switch (id) {
		case 1:
			builder = new AlertDialog.Builder(LookforPwMainAct.this)
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
			builder = new AlertDialog.Builder(LookforPwMainAct.this)
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
			builder = new AlertDialog.Builder(LookforPwMainAct.this)
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
			builder = new AlertDialog.Builder(LookforPwMainAct.this)
			.setTitle("알림")
			.setMessage(R.string.wrongFormat_EorP)
			.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
		case 5:
			builder = new AlertDialog.Builder(LookforPwMainAct.this)
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