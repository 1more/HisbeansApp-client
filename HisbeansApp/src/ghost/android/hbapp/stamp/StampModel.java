package ghost.android.hbapp.stamp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinJi Kim
 * @email Minstar777@gmail.com
 * @classname StampModel.java
 * @package ghost.android.hbapp.stamp
 * @date 2012. 10. 23. 오후 8:28:51
 * @purpose : Server로부터 받을 Stamp Model Class
 * @comment :
 */

public class StampModel {
	private List<StampInfo> cards;

	/* constructor */
	public StampModel() {
		super();
	}
	public StampModel(List<StampInfo> cards) {
		super();
		this.cards = cards;
	}

	/* getter and setter */
	public List<StampInfo> getCards() {
		return cards;
	}
	public void setCards(List<StampInfo> cards) {
		this.cards = cards;
	}

	/* methods */
	public int getSize(){
		return cards.size();
	}
	public int getUsedSize(){
		return cards.size() - getNonusedFullcountSize() - 1;
	}
	public int getNonusedFullcountSize(){
		int nonUsedCount = 0;

		for(int i=0; i<cards.size(); i++){
			if(cards.get(i).getCounts() == 10 && cards.get(i).getUsedFlag() == 0){
				nonUsedCount++;
			}
		}

		return nonUsedCount;
	}
	public int getCurrentStampcardCount(){
		return cards.get(cards.size()-1).getCounts();
	}



	/* inner class */
	public static class StampInfo{
		private int index;
		private String userBarcodeNum;
		private int counts;
		private String usedDate;
		private int usedFlag;

		public StampInfo() {
		}
		public StampInfo(int index, String userBarcodeNum, int counts,
				String usedDate, int usedFlag) {
			super();
			this.index = index;
			this.userBarcodeNum = userBarcodeNum;
			this.counts = counts;
			this.usedDate = usedDate;
			this.usedFlag = usedFlag;
		}

		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getUserBarcodeNum() {
			return userBarcodeNum;
		}
		public void setUserBarcodeNum(String userBarcodeNum) {
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
		public void setUsedDate(String usedDate) {
			this.usedDate = usedDate;
		}
		public int getUsedFlag() {
			return usedFlag;
		}
		public void setUsedFlag(int usedFlag) {
			this.usedFlag = usedFlag;
		}
	}
}
