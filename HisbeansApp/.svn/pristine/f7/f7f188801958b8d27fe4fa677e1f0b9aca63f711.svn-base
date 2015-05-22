package ghost.android.hbapp.setting;

import ghost.android.hbapp.R;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname SettingNoticeExpanListAdapter.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 8:05:00
 * @purpose : 공지사항 Custom Expan List View Adapter
 * @comment :
 */

public class SettingNoticeExpanListAdapter extends BaseExpandableListAdapter{
	private LayoutInflater inflater;
	private SettingNoticeModel notices;

	public SettingNoticeExpanListAdapter(Context c, SettingNoticeModel notices){
		super();
		this.inflater = LayoutInflater.from(c);
		this.notices = notices;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return notices.get(groupPosition).getContent();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null){
			v = inflater.inflate(R.layout.setting_notice_expan_child_row, parent, false);
		}

		// Notice Content Set
		TextView content = (TextView) v.findViewById(R.id.setting_notice_expan_child_row_content);
		content.setText(Html.fromHtml(notices.get(groupPosition).getContent()));

		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return notices.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return notices.getNotices().size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null){
			v = inflater.inflate(R.layout.setting_notice_expan_row, parent, false);
		}

		// Notice Title Set
		ImageView indicator = (ImageView) v.findViewById(R.id.setting_notice_expan_row_indicator);
		TextView title = (TextView) v.findViewById(R.id.setting_notice_expan_row_title);

		title.setText(notices.get(groupPosition).getTitle());
		

		if(isExpanded)
			indicator.setImageResource(R.drawable.depart_menu_expan_on);
		else
			indicator.setImageResource(R.drawable.depart_menu_expan_off);

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