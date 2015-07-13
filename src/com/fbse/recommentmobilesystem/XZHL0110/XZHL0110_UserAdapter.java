package com.fbse.recommentmobilesystem.XZHL0110;

import java.util.ArrayList;
import java.util.List;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.common_entity.ShowInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class XZHL0110_UserAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<ArrayList<ShowInfo>> UserViewList = new ArrayList<ArrayList<ShowInfo>>();
    private List<Bitmap> imageList;
    private ViewHolder viewHolder;
    public List<Boolean> mChecked;
    private Integer number = 0;
    private Boolean flg = false;
    List<Boolean> dellist = null;

    public XZHL0110_UserAdapter(Context context,
            ArrayList<ArrayList<ShowInfo>> userlist, Integer usernumber,
            List<Bitmap> imagelist, Boolean flg, List<Boolean> dellist) {
        this.UserViewList = userlist;
        this.imageList = imagelist;
        this.mInflater = LayoutInflater.from(context);
        this.number = usernumber;
        this.flg = flg;
        this.dellist = dellist;
    }

    public synchronized void refresh(Context context,
            ArrayList<ArrayList<ShowInfo>> userlist, Integer usernumber,
            List<Bitmap> imagelist, Boolean flg, List<Boolean> dellist) {
        this.imageList = imagelist;
        mInflater = LayoutInflater.from(context);
        this.number = usernumber;
        this.flg = flg;
        this.dellist = dellist;
        notifyDataSetChanged();
    }

    public void clean() {
        UserViewList.clear();
    }

    @Override
    public int getCount() {
        return number;
    }

    @Override
    public Object getItem(int position) {
        if (position >= getCount()) {
            return null;
        }
        return UserViewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 控件内容绑定
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 初始化控件
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.xzhl0110_girdview_item,
                    null);
            viewHolder.nameTextView = (TextView) convertView
                    .findViewById(R.id.feihuiyuan);
            viewHolder.phoneTextView = (TextView) convertView
                    .findViewById(R.id.main_phone);
            viewHolder.photoimage = (ImageView) convertView
                    .findViewById(R.id.main_imageview);

            viewHolder.plusimage = (ImageView) convertView
                    .findViewById(R.id.plusimage);

            viewHolder.delCheckBox = (CheckBox) convertView
                    .findViewById(R.id.delcheckbox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 绑定内容
        @SuppressWarnings("unused")
        ShowInfo model = UserViewList.get(UserViewList.size() - position - 1)
                .get(0);

        viewHolder.photoimage.setImageBitmap(imageList.get(number - position
                - 1));
        // 判断提示数字
        Log.i("", number + " " + position);

        Log.i("", " " + UserViewList.get(number - position - 1).size());

        if (UserViewList.get(number - position - 1).size() == 1) {
            viewHolder.plusimage.setVisibility(View.VISIBLE);
            viewHolder.plusimage.setImageResource(R.drawable.fei6);

        } else if (UserViewList.get(number - position - 1).size() == 2) {
            viewHolder.plusimage.setVisibility(View.GONE);
        } else if (UserViewList.get(number - position - 1).size() == 3) {
            viewHolder.plusimage.setVisibility(View.VISIBLE);
            viewHolder.plusimage.setImageResource(R.drawable.p2);
        } else if (UserViewList.get(number - position - 1).size() == 4) {
            viewHolder.plusimage.setVisibility(View.VISIBLE);
            viewHolder.plusimage.setImageResource(R.drawable.p3);
        }
        // 删除选择框的选择状态
        if (flg) {
            viewHolder.delCheckBox.setVisibility(View.VISIBLE);
        } else {
            viewHolder.delCheckBox.setVisibility(View.GONE);
        }
        if (dellist != null) {

            if (dellist.get(position)) {
                viewHolder.delCheckBox.setChecked(true);
            } else {
                viewHolder.delCheckBox.setChecked(false);
            }
        }
        return convertView;
    }

    // 控件容器
    class ViewHolder {
        public ImageView photoimage;
        public TextView nameTextView;
        public TextView phoneTextView;
        public ImageView plusimage;
        public CheckBox delCheckBox;
    }

}
