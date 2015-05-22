package ghost.android.hbapp.common;

import java.util.regex.Pattern;

 /**
 * @author Sooyeon Kim
 * @email sooyeon.ksy@gmail.com
 * @classname RegExpression.java
 * @package ghost.android.hbapp.common
 * @date 2012. 7. 31.
 * @purpose : Edit 정보 Grammer & Pattern Check Class
 *
 */

public class RegExpression {
	public static boolean checkPattern(String pattern, String string){
		return Pattern.matches(pattern, string);
	}
	
	public class Patterns{
		public final static String LOWERCASE = "^[a-z]*$";
		public final static String UPPERCASE = "^[A-Z]*$";
		public final static String ALPHABET = "^[a-zA-Z]*$";
		public final static String KOREAN = "^[\uAC00-\uD7A3]*$";
		public final static String KOR_ALPHA = "^[a-zA-Z\uAC00-\uD7A3]*$";
		public final static String NUMBER = "^[0-9]*$";
		public final static String ALPHA_NUM = "^[a-zA-Z0-9]*$";
		public final static String EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		public final static String PHONE = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
		public final static String PHONE_ONLYNUM = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
	}
}
