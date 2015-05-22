package ghost.android.hbapp.giftcard;

import ghost.android.hbapp.R;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author HyeonWook Kim
 * @email khw0867@gmail.com
 * @classname SendGiftBoxListAdapter.java
 * @package ghost.android.hbapp.giftcard
 * @date 2012. 7. 26.
 * @purpose : 받은 선물함의 ListView를 customize하는데 이용된 Adapter
 * 
 */

public class GiftCardGetListAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater;
	private GiftCardModel giftcard;
	
	
	public GiftCardGetListAdapter(Context c, GiftCardModel giftcard) {
		super();
		this.inflater = LayoutInflater.from(c);
		this.giftcard = giftcard;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return giftcard.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = inflater.inflate(R.layout.giftcard_expand_child_row, parent,
					false);
		}

		// Giftcard Content Set
		TextView content = (TextView) v
				.findViewById(R.id.giftcard_expan_child_row_content);
		content.setText(Html.fromHtml(giftcard.get(groupPosition)
				.getUser_name()
				+ "님이 "
				+ giftcard.get(groupPosition).getSendDate().toString()
				+ "에 선물하신 " + giftcard.get(groupPosition).getMenu() + "입니다."));
		
		ImageView cardIv = (ImageView) v.findViewById(R.id.giftcardImageView);
		cardIv.setImageBitmap(giftcard.get(groupPosition).getCardImage());
		//cardIv.setAdjustViewBounds(true);
		//cardIv.setScaleType(ScaleType.FIT_XY);

		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}
	
	@Override
	public int getGroupCount() {
		return giftcard.getSize();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			v = inflater.inflate(R.layout.giftcard_expand_row, parent, false);
		}

		// Notice Title Set
		ImageView indicator = (ImageView) v
				.findViewById(R.id.giftcard_expan_row_indicator);
		TextView title = (TextView) v
				.findViewById(R.id.giftcard_expan_row_title);

		String used = " (사용)";
		String titleTxt = giftcard.get(groupPosition).getUser_name()
				+ "님이 선물하신 " + giftcard.get(groupPosition).getMenu();
		if (giftcard.get(groupPosition).getUsedFlag() == 1)
			titleTxt += used;
		title.setText(titleTxt);

		if (isExpanded) {
			indicator.setImageResource(R.drawable.depart_menu_expan_on);
		} else {
			indicator.setImageResource(R.drawable.depart_menu_expan_off);
		}

		return v;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}