package ghost.android.hbapp.setting;

import ghost.android.hbapp.R;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
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
 * @classname SettingQNAExpanListAdapter.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 8:06:13
 * @purpose : 사용자 도움말 - QNA Custom Expan List View Adapter
 * @comment :
 */

public class SettingQNAExpanListAdapter extends BaseExpandableListAdapter{
	private LayoutInflater inflater;

	private ArrayList<String> groupList;
	private ArrayList<ArrayList<String>> childList;

	public SettingQNAExpanListAdapter(Context c, ArrayList<String> groupList, 
			ArrayList<ArrayList<String>> childList){
		super();
		this.inflater = LayoutInflater.from(c);
		this.groupList = groupList;
		this.childList = childList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null){
			v = inflater.inflate(R.layout.setting_qna_expan_child_row, parent, false);
		}

		// QNA Content Set
		TextView content = (TextView) v.findViewById(R.id.setting_qna_expan_child_row_content);
		content.setText(childList.get(groupPosition).get(childPosition));

		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
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
			v = inflater.inflate(R.layout.setting_qna_expan_row, parent, false);
		}
		
		// QNA Title Set
		TextView title = (TextView) v.findViewById(R.id.setting_qna_expan_row_title);
		ImageView indicator = (ImageView) v.findViewById(R.id.setting_qna_expan_row_indicator);
		
		// set indicator and title
		if(isExpanded)
			indicator.setImageResource(R.drawable.depart_menu_expan_on);
		else
			indicator.setImageResource(R.drawable.depart_menu_expan_off);
		title.setText(groupList.get(groupPosition));

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