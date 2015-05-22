package ghost.android.hbapp.department;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * @author HyeonWook Kim
 * @email khw0867@gmail.com
 * @classname DepartmentShopOverlay.java
 * @package ghost.android.hbapp.department
 * @date 2012. 10. 23. 오전 10:43:03
 * @purpose : 매장 소개 Google Map 위치지정 화살표 Class
 * @comment :
 */

public class DepartmentShopOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public DepartmentShopOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) { 
		mOverlays.add(overlay); 
		populate(); 
	}
}
