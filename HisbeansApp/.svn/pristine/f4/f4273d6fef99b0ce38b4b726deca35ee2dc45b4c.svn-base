package ghost.android.hbapp.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

/**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname CommonFunctions.java
 * @package ghost.android.hbapp.common
 * @date 2012. 7. 31.
 * @purpose : Global하게 사용되는 Methods 모음 Class
 *
 */

public class GlobalMethods {
	// 비밀번호 암호화 Function
	public static String md5(String inpara){
		byte[] bpara = new byte[inpara.length()];
		byte[] rethash;
		int i;

		for (i=0; i < inpara.length(); i++)
			bpara[i] = (byte)(inpara.charAt(i) & 0xff );

		try {
			MessageDigest md5er = MessageDigest.getInstance("MD5");
			rethash = md5er.digest(bpara);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}

		StringBuffer r = new StringBuffer(32);
		for (i=0; i < rethash.length; i++) {
			String x = Integer.toHexString(rethash[i] & 0xff).toLowerCase();
			if (x.length()<2)
				r.append("0");
			r.append(x);
		}

		return r.toString();
	}

	// 인터넷 연결상태 확인한 후에, 연결이 나쁘면 미리 생성된 AlertDialog return 한다.
	public static AlertDialog.Builder checkInternet(ConnectivityManager cm, Context con){
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();

		if(isWifiConn==false && isMobileConn==false){
			AlertDialog.Builder builder = new AlertDialog.Builder(con)
			.setMessage("네트워크 연결이 원활하지 않습니다. Wifi나 3G를 확인해주세요.")
			.setCancelable(false)
			.setPositiveButton("확인", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					dialog.dismiss();
				}
			});
			return builder;
		}
		return null;		
	}

	// Network Problem Warning Dialog
	public static AlertDialog.Builder NetworkProblemDialog(Context con){
		AlertDialog.Builder builder = new AlertDialog.Builder(con)
		.setMessage("네트워크 연결이 원활하지 않습니다. Wifi나 3G를 확인해주세요.")
		.setCancelable(false)
		.setPositiveButton("확인", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				dialog.dismiss();
			}
		});
		return builder;	
	}

	// Save Barcode Image at SDCard
	public static boolean saveImgAtSdcard(String which, Bitmap barcode){
		try {
			// get SDCard Path and create file and folder
			String sdPath = ""+Environment.getExternalStorageDirectory();
			File dir = new File(sdPath, "hisbeans");
			dir.mkdir();

			// FileOutputStream 생성 및 Bitmap 저장
			OutputStream fos = new FileOutputStream(new File(dir, which + ".bmp"));
			barcode.compress(Bitmap.CompressFormat.PNG, 100, fos);

			// 스트림 닫기
			fos.close();
		}catch (IOException e) {
			Log.e("IOException error", e.toString());
			return false;
		}

		return true;
	}
	
	public static boolean checkSpecialCharacterIn(String str){
		if(str.indexOf('\'') != -1){
			return true;
		}
		if(str.indexOf(';') != -1){
			return true;
		}
		if(str.indexOf('-') != -1){
			return true;
		}
		
		return false;
	}
}
