package com.fbse.recommentmobilesystem.XZHL0530;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0510.XZHL0510_DeviceArray;
import com.fbse.recommentmobilesystem.XZHL0520.XZHL0520_RepairArray;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("WorldReadableFiles")
public class XZHL0530_Activity extends Activity {

	private static final Integer Maxnumber = 20;
	private static final String CHULIZHONG = "处理中";
	private static final String CHULIWANCHENG = "处理结束";
	// 设备报修变化
	private TextView shebeibianhao;
	// 设备类型
	private TextView shebeileixing;
	// 设备品牌
	private TextView shebeipinpai;
	// 设备型号
	private TextView shebeixinghao;
	// 维修编号
	private TextView weixiubianhao;
	// 设备序列号
	private TextView shebeixuliehao;
	// 设备原始号
	private TextView shebeiyuanshihao;
	// 报修日
	private TextView baoxiuri;
	// 维修状态
	private TextView weixiuzhuangtai;
	// 维修原因
	private EditText weixiuyuanyin;
	// 维修结果
	private TextView weixiujieguo;
	// 提交按钮
	private Button tijiaobtn;
	// 返回按钮
	private Button fanhuibtn;
	// 布局块定义
	@SuppressWarnings("unused")
    private LinearLayout shebeibianhaolayout;
	@SuppressWarnings("unused")
    private LinearLayout shebeileixinglayout;
	@SuppressWarnings("unused")
    private LinearLayout shebeipinpailayout;
	@SuppressWarnings("unused")
    private LinearLayout sheibeixinghaolayout;
	@SuppressWarnings("unused")
    private LinearLayout weixiubianhaolayout;
	@SuppressWarnings("unused")
    private LinearLayout shebeixuliehaolayout;
	@SuppressWarnings("unused")
    private LinearLayout shebeiyuanshihaolayout;
	@SuppressWarnings("unused")
    private LinearLayout baoxiurilayout;
	private LinearLayout weixiuzhuangtailayout;
	@SuppressWarnings("unused")
    private RelativeLayout weixiuyuanyinlayout;
	private LinearLayout weixiujieguolayout;
	@SuppressWarnings("unused")
    private LinearLayout tijiaobtnlayout;
	@SuppressWarnings("unused")
    private LinearLayout fanhuibtnlayout;
	//
	@SuppressWarnings("unused")
    private String serial;
	private String id;

	ArrayList<String> list = null;

	private SharedPreferences shared;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shared = this.getSharedPreferences("data", Context.MODE_WORLD_READABLE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		setContentView(R.layout.xzhl0530_baoxiuxiangxi);
		findviewbyid();
		getintentmessage();
		setListener();
	}

	/**
	 * 
	 * 绑定控件
	 */
	private void findviewbyid() {
		// 对应控件与后台布局绑定
		shebeibianhao = (TextView) findViewById(R.id.shebeibianhao);
		shebeileixing = (TextView) findViewById(R.id.sheibeileixing);
		shebeipinpai = (TextView) findViewById(R.id.sheibeipinpai);
		shebeixinghao = (TextView) findViewById(R.id.sheibeixinghao);
		weixiubianhao = (TextView) findViewById(R.id.weixiubianhao);
		shebeixuliehao = (TextView) findViewById(R.id.shebeixuliehao);
		shebeiyuanshihao = (TextView) findViewById(R.id.sheibeiyuanshihao);
		baoxiuri = (TextView) findViewById(R.id.baoxiuri);
		weixiuzhuangtai = (TextView) findViewById(R.id.weixiuzhuangtai);
		weixiuyuanyin = (EditText) findViewById(R.id.weixiuyuanyin);
		weixiujieguo = (TextView) findViewById(R.id.weixiujieguo);
		tijiaobtn = (Button) findViewById(R.id.xzhl0530_tijiaobtn);
		fanhuibtn = (Button) findViewById(R.id.xzhl0530_gobackBtn);

		// 布局绑定

		shebeibianhaolayout = (LinearLayout) findViewById(R.id.layout_shebeibianhao);
		shebeileixinglayout = (LinearLayout) findViewById(R.id.layout_shebeibiexing);
		shebeipinpailayout = (LinearLayout) findViewById(R.id.layout_shebeipinpai);
		sheibeixinghaolayout = (LinearLayout) findViewById(R.id.layout_shebeixinghao);
		weixiubianhaolayout = (LinearLayout) findViewById(R.id.layout_weixiubianhao);
		shebeixuliehaolayout = (LinearLayout) findViewById(R.id.layout_shebeixuliehao);
		shebeiyuanshihaolayout = (LinearLayout) findViewById(R.id.layout_shebeiyuanshihao);
		baoxiurilayout = (LinearLayout) findViewById(R.id.layout_baoxiuri);
		weixiuzhuangtailayout = (LinearLayout) findViewById(R.id.layout_weixiuzhuangtai);
		weixiuyuanyinlayout = (RelativeLayout) findViewById(R.id.layout_weixiuyuanyin);
		weixiujieguolayout = (LinearLayout) findViewById(R.id.layout_weixiujieguo);

	}

	/**
	 * 设备监听事件
	 * 
	 */
	private void setListener() {
		// 提交按钮点击事件定义
		tijiaobtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断网络状态
				if (Commonutil.isNetworkAvailable(XZHL0530_Activity.this)) {

					if ("".equals(weixiuyuanyin.getText().toString().trim())) {
						// 提示框
						MessageUtil.commonToast(XZHL0530_Activity.this,
								MessageUtil.getMessage(XZHL0530_Activity.this,
										Msg.E0018), 3000);
						return;
					}
					// 定义对话框

					AlertDialog.Builder builder = new Builder(
							XZHL0530_Activity.this);
					// 定义提示标题
					builder.setTitle(MessageUtil.getMessage(
							XZHL0530_Activity.this, Msg.Q006));
					// 定义确定按钮事件
					builder.setPositiveButton(MessageUtil.getMessage(
							XZHL0530_Activity.this, Msg.I012),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 判断原因框是否为空

									list = new ArrayList<String>();
									// 时间格式化
									// list.add(JsonUtil.xzhl0530cretetJSON(
									// serial, weixiuyuanyin.getText()
									// .toString(), smf
									// .format(new Date())));
									// 调用网络访问线程
									Tijiao tijiao = new Tijiao();
									tijiao.execute();

								}

							});
					// 定义取消按钮
					builder.setNegativeButton(MessageUtil.getMessage(
							XZHL0530_Activity.this, Msg.I005),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
					builder.show();
					// 执行提交操作

				}
			}
		});
		// 返回按钮事件定义
		fanhuibtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				XZHL0530_Activity.this.finish();
			}
		});
		// 维修原因 输入长度限制设定
		weixiuyuanyin.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;

			@Override
			// 初始化长度
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				temp = s;

			}

			// 输入中事件为空
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			// 判断输入长度是否超过 最大长度 超出则删除并提示
			@Override
			public void afterTextChanged(Editable s) {
				selectionStart = weixiuyuanyin.getSelectionStart();
				selectionEnd = weixiuyuanyin.getSelectionEnd();
				if (temp.length() > Maxnumber) {
					MessageUtil.commonToast(XZHL0530_Activity.this, MessageUtil
							.getMessage(XZHL0530_Activity.this,
									Msg.E0017, new String[] { Maxnumber
											+ "" }), Toast.LENGTH_SHORT);

					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionStart;
					weixiuyuanyin.setText(s);
					weixiuyuanyin.setSelection(tempSelection);

				}
			}
		});

	}

	// 获得从前页面传过来的值
	@SuppressLint("SimpleDateFormat")
    private void getintentmessage() {
		Intent intent = getIntent();
		// 定义两个跳转方式的接收类
		XZHL0510_DeviceArray device = null;

		XZHL0520_RepairArray repairLogStrBean = null;

		switch (intent.getFlags()) {
		case 2:
			device = (XZHL0510_DeviceArray) intent
					.getSerializableExtra("equipbaoxiu");
			// 如果点击报修按钮跳转到该页面 则 让部分控件不显示
			// weixiubianhaolayout.setVisibility(View.GONE);
			weixiuzhuangtailayout.setVisibility(View.GONE);
			weixiujieguolayout.setVisibility(View.GONE);
			// 初始化显示值
			shebeibianhao.setText(device.getId());
			shebeileixing.setText(device.getType());
			shebeixinghao.setText(device.getModel());
			shebeipinpai.setText(device.getName());
			shebeixuliehao.setText(device.getSerial());
			shebeiyuanshihao.setText(device.getOrgin_serial());
			SimpleDateFormat smf = new SimpleDateFormat("yyyy/MM/dd");
			baoxiuri.setText(smf.format(new Date()));
			// 初始化设备序列号
			serial = device.getSerial();
			id = device.getId();
			// 赋值操作

			break;
		case 1:
			// 初始化布局显示信息
			repairLogStrBean = (XZHL0520_RepairArray) intent
					.getSerializableExtra("equipmentLog");
			device = (XZHL0510_DeviceArray) intent
					.getSerializableExtra("equipmentLog1");
			weixiuyuanyin.setFocusable(false);
			shebeibianhao.setText(device.getId());
			shebeileixing.setText(device.getType());
			shebeipinpai.setText(device.getBrand());
			shebeixinghao.setText(device.getModel());
			weixiubianhao.setText(repairLogStrBean.getId());
			shebeixuliehao.setText(repairLogStrBean.getSerial());
			shebeiyuanshihao.setText(repairLogStrBean.getOrgin_serial());
			baoxiuri.setText(repairLogStrBean.getDate());
			weixiuzhuangtai
					.setText((repairLogStrBean.getStatus().equals("1")) ? CHULIZHONG
							: CHULIWANCHENG);
			weixiuyuanyin.setText(repairLogStrBean.getReason());
			weixiujieguo.setText(repairLogStrBean.getResult());
			if (repairLogStrBean.getStatus().equals("1")) {
				weixiujieguolayout.setVisibility(View.GONE);
			}
			tijiaobtn.setVisibility(View.GONE);
			break;
		default:
			// 没有接收到数据 提示错误
			MessageUtil.commonToast(XZHL0530_Activity.this,
					MessageUtil.getMessage(XZHL0530_Activity.this, Msg.E0021),
					Toast.LENGTH_SHORT);

			break;
		}
		;

	}

	/**
	 * 网络访问 提交报修数据
	 * 
	 * @author F1322
	 * 
	 */
	class Tijiao extends AsyncTask<Integer, String, Integer> {
		@SuppressWarnings("unused")
        @Override
		protected Integer doInBackground(Integer... params) {
			WebServiceOfHttps webService = new WebServiceOfHttps();
			// serial：设备序列号
			// reason:报修原因
			// date:报修日
			// 访问网络 取得返回值
			WebServiceOfHttps woh = new WebServiceOfHttps();
			String[] key = { "id", "reason" };
			String[] value = { id, weixiuyuanyin.getText().toString() };
			String re = woh.WSservers(XZHL0530_Activity.this, "repair/add",
					JsonUtil.DataToJson(
							"a001",
							JsonUtil.DataToJson(key, value),
							shared.getString("ID", ""),
							JsonUtil.getSign("a001",
									JsonUtil.DataToJson(key, value),
									shared.getString("ID", ""))));

			// 返回值验证
			if ("error".equals(re)) {
				return 0;
			} else {
				Log.i("result", re + "");
				XZHL0530_RepairAddBean rab = JsonUtil.JsonToRepairAdd(re);
				if ("1".equals(rab.getSuccess())) {
					return 1;
				} else {
					return 2;
				}
			}

		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.i("result", result + "");
			// 根据返回标志
			switch (result) {
			case 0:
				// 网络异常
				MessageUtil.commonToast(XZHL0530_Activity.this, MessageUtil
						.getMessage(XZHL0530_Activity.this, Msg.E0002),
						Toast.LENGTH_SHORT);
				break;
			case 1:
				// 报修成功
				MessageUtil.commonToast(XZHL0530_Activity.this, MessageUtil
						.getMessage(XZHL0530_Activity.this, Msg.I016),
						Toast.LENGTH_SHORT);
				XZHL0530_Activity.this.finish();
				break;
			case 2:
				// 报修失败
				MessageUtil.commonToast(XZHL0530_Activity.this, MessageUtil
						.getMessage(XZHL0530_Activity.this, Msg.E0019),
						Toast.LENGTH_SHORT);
				break;
			default:
				break;
			}
		}

	}
}
