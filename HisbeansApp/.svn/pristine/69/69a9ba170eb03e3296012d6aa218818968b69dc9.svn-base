package ghost.android.hbapp.lookforid;

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
 * @classname LookforIdMainAct.java
 * @package ghost.android.hbapp.lookforid
 * @date 2012. 8. 14.
 * @purpose : 아이디 찾기 Activity Class
 * @comment :
 */

public class LookforIdMainAct extends Activity {
	private Intent intent;

	private ImageView eOrPIv;

	private EditText nameEt;
	private EditText infoEt;

	private Button nextB;
	private Button cancelB;
	private Button byEB;
	private Button byPB;

	private String emailOrPhone = "email";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lookforid_main);



		/************ Find View and Setting ************/

		/*** Edit Text Setting ***/
		nameEt = (EditText) findViewById(R.id.id_mainNameEt);
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
				if (!nameEt.getText().toString().equals("")
						&& !infoEt.getText().toString().equals("")) {
					nextB.setClickable(true);
					nextB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					nextB.setClickable(false);
					nextB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		infoEt = (EditText) findViewById(R.id.id_mainCheckInfoEt);
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
				if (!nameEt.getText().toString().equals("")
						&& !infoEt.getText().toString().equals("")) {
					nextB.setClickable(true);
					nextB.setBackgroundResource(drawable.find_btn_next_normal);
				} else {
					nextB.setClickable(false);
					nextB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		/*** Email or Phone Button Setting ***/
		eOrPIv = (ImageView) findViewById(R.id.id_mainEorPIv);
		byEB = (Button) findViewById(R.id.id_mainByEB);
		byEB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
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

		byPB = (Button) findViewById(R.id.id_mainByPB);
		byPB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
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
		
		/*** Button Setting ***/
		nextB = (Button) findViewById(R.id.id_mainNextB);
		nextB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(
						Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LookforIdMainAct.this);

				// 인터넷환경이 원활 할 경우.
				if (netConDlgBuilder == null) {
					// get information
					String name = nameEt.getText().toString();
					String info = infoEt.getText().toString();

					// null check
					// name format check
					// email or phone format check
					if (name.equals("") || info.equals("")) {
						nameEt.setText("");
						infoEt.setText("");

						showDialog(1);
					} 
					else if (!RegExpression.checkPattern(
							RegExpression.Patterns.KOR_ALPHA, name)) {
						nameEt.setText("");
						infoEt.setText("");

						showDialog(3);
					} 
					else if (!(RegExpression.checkPattern(
							RegExpression.Patterns.EMAIL, info) || RegExpression
							.checkPattern(RegExpression.Patterns.PHONE_ONLYNUM,
									info))) {
						nameEt.setText("");
						infoEt.setText("");

						showDialog(4);
					} 
					else {
						Map<String, String> param = new HashMap<String, String>();
						param.put("name", name);
						param.put("info", info);

						// find id by info
						new CheckIdInfo().execute(param);

						nameEt.setText("");
						infoEt.setText("");
					}
				}
				// 인터넷 연결이 원할하지 않을 경우 Dialog Show
				else {
					nameEt.setText("");
					infoEt.setText("");

					netConDlgBuilder.show();
				}
			}
		});
		nextB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (!nameEt.getText().toString().equals("")
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

		cancelB = (Button) findViewById(R.id.id_mainCancelB);
		cancelB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LookforIdMainAct.this, LoginAct.class);
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

	
	
	/************ Look for ID By Email Info Progress Class with AsyncTask ************/

	private class CheckIdInfo extends AsyncTask<Map<String, String>, Void, String> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// Progress Dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(LookforIdMainAct.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected String doInBackground(Map<String, String>... params) {
			String url = null;
			String tag = null;

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(
					new MappingJacksonHttpMessageConverter());

			if (emailOrPhone.equals("email")) {
				url = GlobalVariables.lookforIdByEURL;
				tag = GlobalVariables.KEY_BY_NAME_EMAIL;
			} else {
				url = GlobalVariables.lookforIdByPURL;
				tag = GlobalVariables.KEY_BY_NAME_PHONE;
			}

			Map<String, String> response = restTemplate.getForObject(url,
					Map.class, params[0]);

			return response.get(tag);
		}

		@Override
		protected void onPostExecute(String res) {
			// loading dialog 사라지게 하기
			loadingDlg.dismiss();

			// 정보를 성공적으로 받아오면
			if (res != null && !res.equals("false")) {
				intent = new Intent(LookforIdMainAct.this,
						LookforIdDoneAct.class);
				intent.putExtra("id", res.toString());
				startActivity(intent);
			}
			// Match하는 ID가 없으면
			else if (res != null && res.equals("false")) {
				if(!LookforIdMainAct.this.isFinishing())
					showDialog(2);
			}
			// Fail to get information
			else if(res == null){
				if(!LookforIdMainAct.this.isFinishing()){
					AlertDialog.Builder netConDlgBuilder = GlobalMethods.NetworkProblemDialog(LookforIdMainAct.this);
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

	
	
	/********* Create Dialog *********/

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;

		switch (id) {
		case 1:
			builder = new AlertDialog.Builder(LookforIdMainAct.this)
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
			builder = new AlertDialog.Builder(LookforIdMainAct.this)
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
			builder = new AlertDialog.Builder(LookforIdMainAct.this)
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
			builder = new AlertDialog.Builder(LookforIdMainAct.this)
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
		}
		return null;
	}
}