package ghost.android.hbapp.register;

import ghost.android.hbapp.LoginAct;
import ghost.android.hbapp.R;
import ghost.android.hbapp.R.drawable;
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
 * @author HyeonWook Kim
 * @email khw0867@gmail.com
 * @classname RegisterDoneAct.java
 * @package ghost.android.hbapp.register
 * @date 2012. 10. 23. 오후 12:00:19
 * @purpose : 회원가입 완료 Activity Class
 * @comment :
 */

public class RegisterDoneAct extends Activity {
	private TextView msgTv;
	private Button finBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_form_done);

		// Get Intent Information
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");



		/************ Find View and Setting ************/

		msgTv = (TextView) findViewById(R.id.register_doneTv1);
		msgTv.setText(name);

		finBtn = (Button) findViewById(R.id.register_doneB);
		finBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RegisterDoneAct.this, LoginAct.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		finBtn.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					v.setBackgroundResource(drawable.btn_start_press);
				if (event.getAction() == MotionEvent.ACTION_UP)
					v.setBackgroundResource(drawable.btn_start_on);

				return false;
			}
		});
	}
}
