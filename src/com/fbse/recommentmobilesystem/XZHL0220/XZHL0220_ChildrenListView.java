package com.fbse.recommentmobilesystem.XZHL0220;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

//自定义的ListView控件继承于ListView
public class XZHL0220_ChildrenListView extends ListView {
    public XZHL0220_ChildrenListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XZHL0220_ChildrenListView(Context context) {
        super(context);
    }

    public XZHL0220_ChildrenListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 重写ListView的计算高度的方法
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
