package ghost.android.hbapp.lookforpw;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.common.GlobalMethods;
import ghost.android.hbapp.common.GlobalVariables;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname LookforPwByPhoneAct.java
 * @package ghost.android.hbapp.lookforpw
 * @date 2012. 8. 6.
 * @purpose : Phone으로 비밀번호 찾기 1번째 Activity Class
 * 
 */
public class LookforPwByPhoneAct extends Activity {
	private Intent intent;

	private TextView pPhoneTv;

	private String id;
	private String phoneNum;

	private Button getNumB;
	private Button cancelB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lookforpw_byphone);

		// Get Intent Information
		intent = getIntent();
		id = intent.getStringExtra("id");
		phoneNum = intent.getStringExtra("checkInfo");



		/************ Find View and Setting ************/

		pPhoneTv = (TextView) findViewById(R.id.pw_byPPhoneTv);
		pPhoneTv.setText(phoneNum);

		/*** Button Setting ***/
		getNumB = (Button) findViewById(R.id.pw_byPGetNumB);
		getNumB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 현재 device가 인터넷이 되고 있는지 검사 한 후에,
				// 인터넷이 되지 않을 경우, error dialog 표시
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				AlertDialog.Builder netConDlgBuilder = GlobalMethods
						.checkInternet(cm, LookforPwByPhoneAct.this);

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

		cancelB = (Button) findViewById(R.id.pw_byPCancelB);
		cancelB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LookforPwByPhoneAct.this, LoginAct.class);
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



	/************ Get ConfirmNum By Email Info Progress Class with AsyncTask ************/

	// params (phoneNum, id)
	private class ValidateConfirmNum extends AsyncTask<Map<String, String>, Void, Boolean> {
		private ProgressDialog loadingDlg;

		@Override
		protected void onPreExecute() {
			// Progress Dialog 띄우기
			super.onPreExecute();
			loadingDlg = ProgressDialog.show(LookforPwByPhoneAct.this, "알림",
					"잠시만 기다려 주세요", true);
			loadingDlg.setCancelable(true);
		}

		@Override
		protected Boolean doInBackground(Map<String, String>... params) {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

			Map<String, Boolean> response = restTemplate.getForObject(
					GlobalVariables.validateforPhoneURL , Map.class, params[0]);

			return response.get(GlobalVariables.KEY_VALIDATE_PHONE);
		}

		@Override
		protected void onPostExecute(Boolean res) {
			// dimiss Dialog
			loadingDlg.dismiss();

			// confirm number를 성공적으로 받아오면 성공 Dialog Display
			if(res != null && res == true){
				// success getting validate num and setting intent
				intent = new Intent(LookforPwByPhoneAct.this, LookforPwByPhone2Act.class);
				intent.putExtra("phoneNum", phoneNum);
				intent.putExtra("id", id);

				if(!LookforPwByPhoneAct.this.isFinishing())
					showDialog(1);
			}
			// confirm number를 성공적으로 받지 못하면 실패 Dialog Display
			else if(res != null && res == false){
				if(!LookforPwByPhoneAct.this.isFinishing())
					showDialog(2);
			}
			// Fail to get information
			else if(res == null){
				if(!LookforPwByPhoneAct.this.isFinishing()){
					AlertDialog.Builder netConDlgBuilder = GlobalMethods.NetworkProblemDialog(LookforPwByPhoneAct.this);
					netConDlgBuilder.show();
				}
			}
		}

		@Override
		protected void onCancelled() {
			// dimiss Dialog
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
			builder = new AlertDialog.Builder(LookforPwByPhoneAct.this)
			.setTitle("알림")
			.setMessage(R.string.validate)
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
		case 2:
			builder = new AlertDialog.Builder(LookforPwByPhoneAct.this)
			.setTitle("알림")
			.setMessage(R.string.noConnInternet)
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
