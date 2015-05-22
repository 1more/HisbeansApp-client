package ghost.android.hbapp.register;

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
 * @classname RegisterTermsMainAct.java
 * @package ghost.android.hbapp.register
 * @date 2012. 10. 23. 오후 12:01:06
 * @purpose :	회원가입 약관동의 Main Activity Class
 * @comment :
 */

public class RegisterTermsMainAct extends Activity {
	private Button term1B;
	private Button term2B;
	private Button nextB;

	private TextView terms1T;
	private TextView terms2T;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_terms_main);



		/************ Find View and Setting ************/

		terms1T = (TextView) findViewById(R.id.register_terms_text1);
		terms1T.setText(R.string.register_term1);

		terms2T = (TextView) findViewById(R.id.register_terms_text2);
		terms2T.setText(R.string.register_term2);

		term1B = (Button) findViewById(R.id.service_term_btn);
		term1B.setTag("off");
		term1B.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if( ((String)term1B.getTag()).equals("off") ){
					term1B.setBackgroundResource(R.drawable.check_on);
					term1B.setTag("on");

					if(term2B.getTag().equals("on")){
						nextB.setClickable(true);
						nextB.setBackgroundResource(drawable.find_btn_next);
					}
				} else {
					term1B.setBackgroundResource(R.drawable.check_off);
					term1B.setTag("off");

					nextB.setClickable(false);
					nextB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		term2B = (Button) findViewById(R.id.personal_term_btn);
		term2B.setTag("off");
		term2B.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if( ((String)term2B.getTag()).equals("off") ){
					term2B.setBackgroundResource(R.drawable.check_on);
					term2B.setTag("on");

					if(term1B.getTag().equals("on")){
						nextB.setClickable(true);
						nextB.setBackgroundResource(drawable.find_btn_next);
					}
				} else {
					term2B.setBackgroundResource(R.drawable.check_off);
					term2B.setTag("off");

					nextB.setClickable(false);
					nextB.setBackgroundResource(drawable.find_btn_next_off);
				}
			}
		});

		// '다음'버튼 클릭 시 동작하는 listener
		nextB = (Button) findViewById(R.id.register_next_button);
		nextB.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//다음 회원가입 페이지로 이동
				//동시에 기존 activity는 종료시켜서 '뒤로'버튼으로 돌아오는일 방지
				Intent intent = new Intent(RegisterTermsMainAct.this, Register1Act.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		nextB.setClickable(false);
	}
}