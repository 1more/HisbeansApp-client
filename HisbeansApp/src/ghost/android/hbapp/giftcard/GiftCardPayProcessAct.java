package ghost.android.hbapp.giftcard;

import ghost.android.hbapp.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

/**
 * @author HyeonWook Kim
 * @email khw0867@gmail.com
 * @classname GiftCardPayProcessAct.java
 * @package ghost.android.hbapp.giftcard
 * @date 2012. 10. 28. 오후 8:17:03
 * @purpose : 결제화면 연결할 Activity Class - 안드로이드용 결제모듈 부재로 미완성
 * @comment :
 */

public class GiftCardPayProcessAct extends Activity {
	private WebView webView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.giftcard_payprocess);

        webView = (WebView) findViewById(R.id.payWebView);
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
