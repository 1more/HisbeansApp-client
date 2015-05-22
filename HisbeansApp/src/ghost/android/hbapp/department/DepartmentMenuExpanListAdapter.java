package ghost.android.hbapp.department;

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

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname DepartmentMenuExpanListAdapter.java
 * @package ghost.android.hbapp.department
 * @date 2012. 10. 23. 오전 10:37:12
 * @purpose : 매뉴 소개 Custom Expan List Adapter Class
 * @comment :
 */

public class DepartmentMenuExpanListAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater;

	private ArrayList<String> groupList;
	private ArrayList<ArrayList<String>> childList;

	public DepartmentMenuExpanListAdapter(Context c,
			ArrayList<String> groupList, ArrayList<ArrayList<String>> childList) {
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
		if (v == null) {
			v = inflater.inflate(R.layout.department_menu_expan_child_row,
					parent, false);
		}
		ImageView image = (ImageView) v.findViewById(R.id.depart_menu_expan_child_row_image);

		// coffee
		if (groupPosition == 0) {
			if (childPosition == 0) {
				image.setImageResource(R.drawable.depart_menu_coffee01);
			} else if (childPosition == 1) {
				image.setImageResource(R.drawable.depart_menu_coffee02);
			} else if (childPosition == 2) {
				image.setImageResource(R.drawable.depart_menu_coffee03);
			} else if (childPosition == 3) {
				image.setImageResource(R.drawable.depart_menu_coffee04);
			} else if (childPosition == 4) {
				image.setImageResource(R.drawable.depart_menu_coffee05);
			} else if (childPosition == 5) {
				image.setImageResource(R.drawable.depart_menu_coffee06);
			} else if (childPosition == 6) {
				image.setImageResource(R.drawable.depart_menu_coffee07);
			} else if (childPosition == 7) {
				image.setImageResource(R.drawable.depart_menu_coffee08);
			} else if (childPosition == 8) {
				image.setImageResource(R.drawable.depart_menu_coffee09);
			} else if (childPosition == 9) {
				image.setImageResource(R.drawable.depart_menu_coffee10);
			} else if (childPosition == 10) {
				image.setImageResource(R.drawable.depart_menu_coffee11);
			} else if (childPosition == 11) {
				image.setImageResource(R.drawable.depart_menu_coffee12);
			}
		}
		// herb & tea
		else if (groupPosition == 1) {
			if (childPosition == 0) {
				image.setImageResource(R.drawable.depart_menu_herb01);
			} else if (childPosition == 1) {
				image.setImageResource(R.drawable.depart_menu_herb02);
			} else if (childPosition == 2) {
				image.setImageResource(R.drawable.depart_menu_herb03);
			} else if (childPosition == 3) {
				image.setImageResource(R.drawable.depart_menu_herb04);
			} else if (childPosition == 4) {
				image.setImageResource(R.drawable.depart_menu_herb05);
			} else if (childPosition == 5) {
				image.setImageResource(R.drawable.depart_menu_herb06);
			} else if (childPosition == 6) {
				image.setImageResource(R.drawable.depart_menu_herb07);
			} else if (childPosition == 7) {
				image.setImageResource(R.drawable.depart_menu_herb08);
			} else if (childPosition == 8) {
				image.setImageResource(R.drawable.depart_menu_herb09);
			}
		}
		// beverage
		else if (groupPosition == 2) {
			if (childPosition == 0) {
				image.setImageResource(R.drawable.depart_menu_bever01);
			} else if (childPosition == 1) {
				image.setImageResource(R.drawable.depart_menu_bever02);
			} else if (childPosition == 2) {
				image.setImageResource(R.drawable.depart_menu_bever03);
			} else if (childPosition == 3) {
				image.setImageResource(R.drawable.depart_menu_bever04);
			} else if (childPosition == 4) {
				image.setImageResource(R.drawable.depart_menu_bever05);
			} else if (childPosition == 5) {
				image.setImageResource(R.drawable.depart_menu_bever06);
			} else if (childPosition == 6) {
				image.setImageResource(R.drawable.depart_menu_bever07);
			} else if (childPosition == 7) {
				image.setImageResource(R.drawable.depart_menu_bever08);
			} else if (childPosition == 8) {
				image.setImageResource(R.drawable.depart_menu_bever09);
			}
		}
		// waffle
		else if (groupPosition == 3) {
			if (childPosition == 0) {
				image.setImageResource(R.drawable.depart_menu_waffle01);
			} else if (childPosition == 1) {
				image.setImageResource(R.drawable.depart_menu_waffle02);
			} else if (childPosition == 2) {
				image.setImageResource(R.drawable.depart_menu_waffle03);
			} else if (childPosition == 3) {
				image.setImageResource(R.drawable.depart_menu_waffle04);
			} else if (childPosition == 4) {
				image.setImageResource(R.drawable.depart_menu_waffle05);
			} else if (childPosition == 5) {
				image.setImageResource(R.drawable.depart_menu_waffle06);
			} else if (childPosition == 6) {
				image.setImageResource(R.drawable.depart_menu_waffle07);
			}
		}

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
		if (v == null) {
			v = inflater.inflate(R.layout.department_menu_expan_row, parent,
					false);
		}

		ImageView indicator = (ImageView) v
				.findViewById(R.id.depart_menu_expan_row_indicator);
		ImageView image = (ImageView) v
				.findViewById(R.id.depart_menu_expan_row_image);

		// coffee
		if (groupPosition == 0) 
			image.setImageResource(R.drawable.depart_menu_coffee);
		// herb & tea
		else if (groupPosition == 1) 
			image.setImageResource(R.drawable.depart_menu_herb_tea);
		// beverage
		else if (groupPosition == 2) 
			image.setImageResource(R.drawable.depart_menu_beverage);
		// waffle
		else if (groupPosition == 3) 
			image.setImageResource(R.drawable.depart_menu_waffle);

		if (isExpanded) 
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