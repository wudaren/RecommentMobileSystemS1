package com.fbse.recommentmobilesystem.XZHL0120;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class XZHL0120_MyListVIew extends ListView {

	public XZHL0120_MyListVIew(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
