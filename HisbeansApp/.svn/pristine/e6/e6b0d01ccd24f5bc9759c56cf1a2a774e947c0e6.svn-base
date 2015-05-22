package ghost.android.hbapp.lookforid;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
import ghost.android.hbapp.lookforpw.LookforPwMainAct;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname LookforIdDoneAct.java
 * @package ghost.android.hbapp.lookforid
 * @date 2012. 8. 14.
 * @purpose : 아이디 찾기 완료 Activity Class
 * @comment :
 */

public class LookforIdDoneAct extends Activity {
	private Intent intent;

	private Button doneB;
	private Button lookforPwB;

	private String id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lookforid_done);

		// Get Intent Information
		intent = getIntent();
		id = intent.getStringExtra("id");



		/************ Find View and Setting ************/

		/*** Text View Setting ***/
		TextView idTv = (TextView)findViewById(R.id.id_doneIdTv);
		idTv.setText(id);

		/*** Button Setting ***/
		doneB = (Button)findViewById(R.id.id_doneOkB);
		doneB.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				intent = new Intent(LookforIdDoneAct.this, LoginAct.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

				finish();
			}
		});
		doneB.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.btn_ok_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.btn_ok_on);
				return false;
			}
		});

		lookforPwB = (Button)findViewById(R.id.id_doneLookforPWB);
		lookforPwB.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				intent = new Intent(LookforIdDoneAct.this, LookforPwMainAct.class);
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
	}
}
