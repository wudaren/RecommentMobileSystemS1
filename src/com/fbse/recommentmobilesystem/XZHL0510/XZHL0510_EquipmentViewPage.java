package com.fbse.recommentmobilesystem.XZHL0510;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class XZHL0510_EquipmentViewPage extends PagerAdapter {

	private List<View> viewLists;
	private List<String> titleList;

	public XZHL0510_EquipmentViewPage(List<View> viewLists,List<String> titleList) {
		this.viewLists = viewLists;
		this.titleList=titleList;
	}

	@Override
	public int getCount() {
		return viewLists.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titleList.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View view, int position, Object object) {
		((ViewPager) view).removeView(viewLists.get(position));
	}

	@Override
	public Object instantiateItem(View view, int position) {
		((ViewPager) view).addView(viewLists.get(position), 0);
		return viewLists.get(position);
	}
}
