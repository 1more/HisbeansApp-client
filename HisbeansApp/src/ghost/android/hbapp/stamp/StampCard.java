package ghost.android.hbapp.stamp;

/**
 * @author MinJi Kim
 * @email Minstar777@gmail.com
 * @classname StampCard.java
 * @package ghost.android.hbapp.stamp
 * @date 2012. 8. 16.
 * @purpose : 스탬프카드 Model Class
 *
 */

public class StampCard {
	public static String index_tag = "index";
	public static String userBarcodeNum_tag = "userBarcodeNum";
	public static String counts_tag = "counts";
	public static String usedDate_tag = "usedDate";
	public static String usedFlag_tag = "usedFlag";
	
	private int index;
	private String userBarcodeNum;
	private int counts;
	private String usedDate;
	private int usedFlag;
	
	
	
	/*******  Constructor  ********/
	public StampCard(int index, String userBarcodeNum, int counts, String usedDate, int usedFlag) {
		super();
		this.index = index;
		this.userBarcodeNum = userBarcodeNum;
		this.counts = counts;
		this.usedDate = usedDate;
		this.usedFlag = usedFlag;
	}

	
	
	/******   Getters AND Setters  ******/
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getuserBarcodeNum() {
		return userBarcodeNum;
	}
	public void setuserBarcodeNum(String userBarcodeNum) {
		this.userBarcodeNum = userBarcodeNum;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public String getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(String usedDate){
		this.usedDate = usedDate;
	}
	public int getUsedFlag() {
		return usedFlag;
	}
	public void setUsedFlag_tag(int usedFlag) {
		this.usedFlag = usedFlag;
	}
}
