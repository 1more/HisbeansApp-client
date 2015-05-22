package ghost.android.hbapp;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname LoginModel.java
 * @package ghost.android.hbapp
 * @date 2012. 10. 23. 오전 10:17:26
 * @purpose : Server로부터 받아온 Login 정보를 받아오는 Model Class
 * @comment : 
 */

public class LoginModel {
	private LoginInfo login;
	
	/* constructor */
	public LoginModel() {
		super();
	}
	public LoginModel(LoginInfo login) {
		super();
		this.login = login;
	}
	
	/* getter and setter */
	public LoginInfo getLogin() {
		return login;
	}
	public void setLogin(LoginInfo login) {
		this.login = login;
	}



	/* inner class for data */
	public class LoginInfo{
		private String id;
		private String email;
		private String name;
		private String nick_name;
		private String phoneNum;
		private String barcodeNum;
		private String barcodeURL;
		
		/* constructors */
		public LoginInfo() {
			super();
		}
		public LoginInfo(String barcodeNum, String id, String email,
				String name, String nick_name, String phoneNum,
				String barcodeURL) {
			super();
			this.barcodeNum = barcodeNum;
			this.id = id;
			this.email = email;
			this.name = name;
			this.nick_name = nick_name;
			this.phoneNum = phoneNum;
			this.barcodeURL = barcodeURL;
		}
		
		// getter and setter
		public String getBarcodeNum() {
			return barcodeNum;
		}
		public void setBarcodeNum(String barcodeNum) {
			this.barcodeNum = barcodeNum;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNick_name() {
			return nick_name;
		}
		public void setNick_name(String nick_name) {
			this.nick_name = nick_name;
		}
		public String getPhoneNum() {
			return phoneNum;
		}
		public void setPhoneNum(String phoneNum) {
			this.phoneNum = phoneNum;
		}
		public String getBarcodeURL() {
			return barcodeURL;
		}
		public void setBarcodeURL(String barcodeURL) {
			this.barcodeURL = barcodeURL;
		}
	}
}
