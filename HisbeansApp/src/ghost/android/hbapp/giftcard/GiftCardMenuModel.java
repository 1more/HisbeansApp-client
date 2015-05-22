package ghost.android.hbapp.giftcard;

import java.util.List;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname GiftCardMenuModel.java
 * @package ghost.android.hbapp.giftcard
 * @date 2012. 10. 27. 오후 4:22:29
 * @purpose : Gift Card Menu 정보들이 담길 Model Class
 * @comment :
 */

public class GiftCardMenuModel {
	private List<GiftCardMenuInfo> menu;

	/* constructor */
	public GiftCardMenuModel() {
		super();
	}
	public GiftCardMenuModel(List<GiftCardMenuInfo> menu) {
		super();
		this.menu = menu;
	}

	/* getter and setter */
	public List<GiftCardMenuInfo> getMenu() {
		return menu;
	}
	public void setMenu(List<GiftCardMenuInfo> menu) {
		this.menu = menu;
	}

	
	
	/* inner class */
	public static class GiftCardMenuInfo{
		private int index;
		private String menu_ko;
		private String menu_en;
		private int price;
		private String type;
		
		public GiftCardMenuInfo() {
			super();
		}
		public GiftCardMenuInfo(String menu_ko, int price) {
			super();
			this.menu_ko = menu_ko;
			this.price = price;
		}
		public GiftCardMenuInfo(int index, String menu_ko, String menu_en,
				int price, String type) {
			super();
			this.index = index;
			this.menu_ko = menu_ko;
			this.menu_en = menu_en;
			this.price = price;
			this.type = type;
		}
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getMenu_ko() {
			return menu_ko;
		}
		public void setMenu_ko(String menu_ko) {
			this.menu_ko = menu_ko;
		}
		public String getMenu_en() {
			return menu_en;
		}
		public void setMenu_en(String menu_en) {
			this.menu_en = menu_en;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
}