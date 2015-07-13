/* XZHL0150_MemberQuery.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012 */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/* 项目名称 ：信智互联                                                                                                 */
/* 画面ＩＤ ：XZHL0510                                                                                                 */
/* 画面名   ：商品信息查询                                                                                              */
/* 实现功能 ：显示商品列表数据库存储                                                                                    */
/*                                                                                                                    */
/* 变更历史                                                                                                           */
/* NO 日付 Ver 更新者 内容                                                                                            */
/* 1 2014/05/23 V01L01 FBSE)张宁 新規作成                                                                             */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/

package com.fbse.recommentmobilesystem.XZHL0150;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0130.XZHL0130_VipInfoActivity;
import com.fbse.recommentmobilesystem.XZHL0160.XZHL0160_MemberAllQuery;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebService;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;
import com.fbse.recommentmobilesystem.common.common_entity.MemberBean;
import com.fbse.recommentmobilesystem.common.view.XListView;
import com.fbse.recommentmobilesystem.common.view.XListView.IXListViewListener;

/**
 * 页面加载类
 * 显示分页一览画面
 */
@SuppressLint("WorldReadableFiles")
public class XZHL0150_MemberQuery extends Activity implements OnClickListener, IXListViewListener {

    // 返回按钮
    private Button btnGoBack;

    // 新增按钮
    private Button btnAddUser;

    // 查询按钮
    private Button btnSearch;

    // 列表
    private XListView lvUser;

    // 查询条件录入框
    private EditText etSearch;

    // 全局显示list
    private List<MemberBean> list;

    // 配置文件
    private SharedPreferences shared;

    // 适配器
    private XZHL0150_Adapter adapter;

    // 没有数据提示信息
    private TextView tvNodata;

    // topbar块
    private RelativeLayout rltopbar;

    // 加载中进度条
    private ProgressBar pbLoading;

    // 下拉选择框
    private Spinner assistantRole_Spinner;

    // 当前显示最后页数
    private Integer page = 0;

    // 单页数据数
    private final Integer ONEPAGE = 20;

    // 检索条件
    private Integer tiaojiankey = 0;

    // 检索条件列表
    // private String[] tiaojianArray;

    private ListView lvdel;

    private Boolean delflg = false;

    // 初始化标记
    private Boolean firstFlg = true;

    private List<Boolean> dellist;

    // 配置文件
    private Properties properties = null;

    // 线程返回值 成功
    private static final int RESULT_OK = 2;

    // 线程返回值 没有数据
    private static final int RESULT_NODATA = 3;

    // 线程返回值 本地异常
    private static final int RESULT_ERROR_LOCAL = 0;

    // 线程返回值 远程异常
    private static final int RESULT_ERROR_NET = 1;

    /**
     * 页面创建
     * @param savedInstanceState 状态维持
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.logStart();
        super.onCreate(savedInstanceState);
        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 加载布局
        setContentView(R.layout.xzhl0150_memberquery);
        // 加载配置文件
        loadProperties();
        // 绑定控件
        findViewById();
        // 设置监听器
        setListener();
        // initData();
        // 设置设配器
        setAdapter();
        // 初始化筛选器适配器
        initAdapter();
        LogUtil.logEnd();
    }

    /**
     * 初始化下拉菜单
     */
    private void initAdapter() {

        LogUtil.logStart();
        // 初始化适配器
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vipinfo_select,
                android.R.layout.simple_spinner_item);

        // 初始化 下拉框数据
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 添加适配器
        assistantRole_Spinner.setAdapter(adapter);
        // 添加事件typeSpinner事件监听
        assistantRole_Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            /**
             * 、
             * 选择某一项时执行此方法
             */
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                LogUtil.logStart();
                // 将数据复制给筛选条件
                tiaojiankey = arg2;

                // 判断是不是页面初始化 不是初始化则执行加载 否则不执行
                if (!firstFlg) {
                    clearkeybord();
                    GetUserInfoByPageSearch getUserInfoByPage1 = new GetUserInfoByPageSearch();
                    getUserInfoByPage1.execute();
                }
                firstFlg = false;
                LogUtil.logEnd();
            }

            // 没有选择时执行
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        LogUtil.logEnd();
    }

    /**
     * 绑定控件
     */
    private void findViewById() {

        LogUtil.logStart();
        rltopbar = (RelativeLayout) findViewById(R.id.rl_topbar_0150);
        btnGoBack = (Button) findViewById(R.id.btn_goback_0150);
        btnAddUser = (Button) findViewById(R.id.btn_add_0150);
        btnSearch = (Button) findViewById(R.id.btn_chazhao_0150);
        lvUser = (XListView) findViewById(R.id.lv_huiyuanliebiao_0150);
        etSearch = (EditText) findViewById(R.id.ed_search_0150);
        tvNodata = (TextView) findViewById(R.id.tv_nodata_0150);
        lvdel = (ListView) findViewById(R.id.lv_delliebiao_0510);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading_0150);
        assistantRole_Spinner = (Spinner) findViewById(R.id.sp_assistantRole_Spinner_0150);
        LogUtil.logEnd();
    }

    /**
     * 添加监听器
     */
    private void setListener() {

        LogUtil.logStart();
        // 设置列表支持上拉加载更多
        lvUser.setPullLoadEnable(true);
        lvUser.setXListViewListener(this);
        btnGoBack.setOnClickListener(this);
        btnAddUser.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        // 列表点击事件
        lvUser.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                LogUtil.logStart();
                // 跳转到下一级页面 并传值

                Intent intent = new Intent();
                intent.setClass(XZHL0150_MemberQuery.this, XZHL0160_MemberAllQuery.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CommonConst.MEMBERBEAN, list.get(arg2 - 1));
                intent.putExtras(bundle);
                startActivity(intent);
                LogUtil.logEnd();

            }
        });
        lvdel.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                dellist.set(arg2, !dellist.get(arg2));
                XZHL0150_Adapter.ViewHolder viewHolder = (XZHL0150_Adapter.ViewHolder) arg1.getTag();
                viewHolder.cbdel.toggle();

            }
        });
        // 定义列表的触摸事件 取消软键盘
        lvUser.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                LogUtil.logStart();
                clearkeybord();
                LogUtil.logEnd();
                return false;
            }
        });
        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                LogUtil.logEnd();

                dellist = new ArrayList<Boolean>();

                for (int i = 0; i < list.size(); i++) {
                    dellist.add(false);
                }

                lvdel.setVisibility(View.VISIBLE);
                delflg = true;

                adapter = new XZHL0150_Adapter(XZHL0150_MemberQuery.this, list, delflg);
                lvdel.setAdapter(adapter);

                lvUser.setVisibility(View.GONE);
                btnSearch.setVisibility(View.GONE);
                btnAddUser.setBackgroundResource(R.drawable.wrong);

                LogUtil.logEnd();
                return true;
            }

        });
        LogUtil.logEnd();
    }

    /**
     * 初始化配置文件
     */
    private void loadProperties() {

        LogUtil.logStart();
        shared = this.getSharedPreferences(CommonConst.DATA, Context.MODE_WORLD_READABLE);
        properties = Commonutil.loadProperties(this);
        LogUtil.logEnd();
    }

    /**
     * 重回页面时执行此方法
     */
    @Override
    protected void onResume() {

        LogUtil.logStart();
        // 初始状态判断 初始化时不执行加载方法
        if (!firstFlg) {
            GetUserInfoByPageRefrsh getuser = new GetUserInfoByPageRefrsh();
            getuser.execute();

        }
        // 标题栏可见
        rltopbar.setVisibility(View.VISIBLE);

        super.onResume();
        LogUtil.logEnd();
    }

    /**
     * 绑定适配器方法
     */
    private void setAdapter() {

        LogUtil.logStart();
        if (list == null) {
            list = new ArrayList<MemberBean>();
        }
        dellist = new ArrayList<Boolean>();

        for (int i = 0; i < list.size(); i++) {
            dellist.add(false);
        }
        adapter = new XZHL0150_Adapter(this, list, delflg);
        lvUser.setAdapter(adapter);
        LogUtil.logEnd();
    }

    /**
     * 设置点击事件
     * @param v 控件
     */
    @Override
    public void onClick(View v) {

        LogUtil.logStart();
        // 点击任意按钮 取消软键盘
        clearkeybord();
        switch (v.getId()) {
        // 返回按钮
        case R.id.btn_goback_0150:
            if (delflg) {
                delflg = false;
                lvdel.setVisibility(View.GONE);
                lvUser.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.VISIBLE);
                btnAddUser.setBackgroundResource(R.drawable.add);
                GetUserInfoByPageRefrsh getuser = new GetUserInfoByPageRefrsh();
                getuser.execute();
            } else {
                finish();
            }
            break;
        // 添加按钮
        case R.id.btn_add_0150:
            if (delflg) {
                lvUser.setAdapter(adapter);
                DelMember delMember = new DelMember();
                delMember.execute();

                // 批量删除操作
                delflg = false;
                lvdel.setVisibility(View.GONE);
                lvUser.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.VISIBLE);
                btnAddUser.setBackgroundResource(R.drawable.add);
                GetUserInfoByPageRefrsh getuser = new GetUserInfoByPageRefrsh();
                getuser.execute();
            } else {

                Intent intent = new Intent(getApplicationContext(), XZHL0130_VipInfoActivity.class);
                startActivity(intent);
            }
            break;
        // 查询按钮
        case R.id.btn_chazhao_0150:
            GetUserInfoByPageSearch getUserInfoByPage1 = new GetUserInfoByPageSearch();
            getUserInfoByPage1.execute();
            break;
        default:
            break;
        }
        LogUtil.logEnd();
    }

    /**
     * 查询处理类
     * 查询操作
     */
    private class GetUserInfoByPageSearch extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            LogUtil.logStart();
            page = 1;
            // json构建
            String json1 = JsonUtil.DataToJson("a001", CommonConst.SPACE, shared.getString("ID", CommonConst.SPACE),
                    JsonUtil.getSign("a001", CommonConst.SPACE, shared.getString("ID", CommonConst.SPACE)));
            WebServiceOfHttps webService = new WebServiceOfHttps();
            String temp = tiaojiankey - 1 + CommonConst.SPACE;
            if ("-1".equals(temp)) {
                temp = CommonConst.SPACE;
            }

            String json = webService.WSservers1(XZHL0150_MemberQuery.this, "member/list", json1, CommonConst.SPACE
                    + page, ONEPAGE + CommonConst.SPACE, etSearch.getText().toString(), temp);
            // 分段json解析
            Integer result = jsonToMember(json);
            if (-1 == result) {
                return RESULT_ERROR_LOCAL;
            } else if (0 == result) {
                return RESULT_ERROR_NET;
            }
            // 返回状态解析成功则再次解析 数据
            List<MemberBean> temparray = json2List(json);
            if (temparray.size() == 0) {
                list = temparray;
                return RESULT_NODATA;
            }
            // 更新list
            list = temparray;
            LogUtil.logEnd();
            return RESULT_OK;
        }

        @Override
        protected void onPostExecute(Integer result) {

            LogUtil.logStart();
            onLoad();
            super.onPostExecute(result);
            switch (result) {
            // 没有获得正确返回值
            case RESULT_ERROR_LOCAL:
                MessageUtil.commonToast(XZHL0150_MemberQuery.this,
                        properties.getProperty(Msg.E0003, CommonConst.SPACE), Toast.LENGTH_SHORT);
                break;
            // 返回值正常，代理服务器没有访问到远程服务器
            case RESULT_ERROR_NET:
                tvNodata.setVisibility(View.VISIBLE);

                break;
            case RESULT_NODATA:
                // 条件查询没有结果
                tvNodata.setVisibility(View.GONE);
                page--;
                adapter.list = list;
                adapter.notifyDataSetChanged();

                MessageUtil.commonToast(XZHL0150_MemberQuery.this, properties.getProperty(Msg.I027, CommonConst.SPACE),
                        Toast.LENGTH_SHORT);
                break;
            // 有返回值 并显示
            case RESULT_OK:
                Log.i("select", list.toString());
                tvNodata.setVisibility(View.GONE);
                pbLoading.setVisibility(View.GONE);
                adapter.list = list;
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
            }
            LogUtil.logEnd();
        }
    }

    /**
     * 加载更多处理类
     * 加载操作
     */
    private class GetUserInfoByPageMore extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            LogUtil.logStart();
            // 新增页面
            page++;
            // json构建
            String json1 = JsonUtil.DataToJson("a001", CommonConst.SPACE, shared.getString("ID", CommonConst.SPACE),
                    JsonUtil.getSign("a001", CommonConst.SPACE, shared.getString("ID", CommonConst.SPACE)));
            String temp = tiaojiankey - 1 + CommonConst.SPACE;
            if ("-1".equals(temp)) {
                temp = CommonConst.SPACE;
            }
            WebServiceOfHttps webService = new WebServiceOfHttps();
            String json = webService.WSservers1(XZHL0150_MemberQuery.this, "member/list", json1, CommonConst.SPACE
                    + page, ONEPAGE + CommonConst.SPACE, etSearch.getText().toString(), temp);

            Integer result = jsonToMember(json);
            if (-1 == result) {
                return RESULT_ERROR_LOCAL;
            } else if (0 == result) {
                return RESULT_ERROR_NET;
            }
            List<MemberBean> temparray = json2List(json);
            if (temparray.size() == 0) {
                list = temparray;
                return RESULT_NODATA;
            }
            // 追加内容
            list.addAll(temparray);
            LogUtil.logEnd();
            return RESULT_OK;
        }

        @Override
        protected void onPostExecute(Integer result) {

            LogUtil.logStart();
            onLoad();
            super.onPostExecute(result);
            switch (result) {
            case RESULT_ERROR_LOCAL:
                page--;
                MessageUtil.commonToast(XZHL0150_MemberQuery.this,
                        properties.getProperty(Msg.E0003, CommonConst.SPACE), Toast.LENGTH_SHORT);
                break;
            case RESULT_ERROR_NET:
                tvNodata.setVisibility(View.VISIBLE);
                page--;
                break;
            case RESULT_NODATA:
                tvNodata.setVisibility(View.GONE);
                page--;
                adapter.notifyDataSetChanged();
                MessageUtil.commonToast(XZHL0150_MemberQuery.this, properties.getProperty(Msg.I026, CommonConst.SPACE),
                        Toast.LENGTH_SHORT);
                break;
            case RESULT_OK:

                tvNodata.setVisibility(View.GONE);
                pbLoading.setVisibility(View.GONE);
                adapter.list = list;

                adapter.notifyDataSetChanged();

                break;
            default:
                break;
            }
            LogUtil.logEnd();
        }
    }

    /**
     * 刷新操作处理类
     * 刷新操作
     */
    private class GetUserInfoByPageRefrsh extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            LogUtil.logStart();
            // 页码重置
            List<String> mlist = new ArrayList<String>();
            mlist.add(JsonUtil.DataToJson("a001", CommonConst.SPACE, shared.getString("ID", CommonConst.SPACE),
                    JsonUtil.getSign("a001", CommonConst.SPACE, shared.getString("ID", CommonConst.SPACE))));
            String json1 = JsonUtil.DataToJson("a001", CommonConst.SPACE, shared.getString("ID", CommonConst.SPACE),
                    JsonUtil.getSign("a001", CommonConst.SPACE, shared.getString("ID", CommonConst.SPACE)));
            String temp = tiaojiankey - 1 + CommonConst.SPACE;
            if ("-1".equals(temp)) {
                temp = CommonConst.SPACE;
            }
            WebServiceOfHttps webService = new WebServiceOfHttps();
            String json = webService.WSservers1(XZHL0150_MemberQuery.this, "member/list", json1, "1", (ONEPAGE * page)
                    + CommonConst.SPACE, etSearch.getText().toString(), temp);

            Integer result = jsonToMember(json);
            if (-1 == result) {
                return RESULT_ERROR_LOCAL;
            } else if (0 == result) {
                return RESULT_ERROR_NET;
            }
            List<MemberBean> temparray = json2List(json);
            if (temparray.size() == 0) {
                list = temparray;
                return RESULT_NODATA;
            }
            list = temparray;
            LogUtil.logEnd();
            return RESULT_OK;

        }

        @Override
        protected void onPostExecute(Integer result) {

            LogUtil.logStart();
            onLoad();
            super.onPostExecute(result);
            switch (result) {
            case RESULT_ERROR_LOCAL:
                MessageUtil.commonToast(XZHL0150_MemberQuery.this,
                        properties.getProperty(Msg.E0003, CommonConst.SPACE), Toast.LENGTH_SHORT);
                break;
            case RESULT_ERROR_NET:
                tvNodata.setVisibility(View.VISIBLE);

                break;
            case RESULT_NODATA:
                tvNodata.setVisibility(View.GONE);
                adapter.delflg = delflg;
                adapter.list = list;
                adapter.notifyDataSetChanged();
                MessageUtil.commonToast(XZHL0150_MemberQuery.this, properties.getProperty(Msg.I027, CommonConst.SPACE),
                        Toast.LENGTH_SHORT);
                break;
            case RESULT_OK:

                tvNodata.setVisibility(View.GONE);
                pbLoading.setVisibility(View.GONE);
                // 更新布局
                adapter.list = list;
                adapter.delflg = delflg;
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
            }
            LogUtil.logEnd();
        }
    }

    /**
     * 删除操作处理
     * 异步处理删除
     */
    class DelMember extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            WebService woh = new WebService();
            ArrayList<String> propert = new ArrayList<String>();
            propert.add("member/delete");
            propert.add(createJson(list, dellist));

            String json = woh.WSserversofdaili1(XZHL0150_MemberQuery.this, "deleteList", "MemberListPort", propert);
            Log.i("json111111111111", json);
            JSONObject jo;
            try {
                jo = new JSONObject(json);
                String success = jo.getString(CommonConst.SUCCESS);
                if ("1".equals(success)) {
                    return 1;
                } else if ("0".equals(success)) {
                    return 2;
                } else if ("9".equals(success)) {
                    return 3;
                } else {
                    return 4;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {

            Log.i("delres", result + "");
            switch (result) {
            case 1:
                MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(), Msg.I021, new String[] { CommonConst.SPACE }),
                        Toast.LENGTH_SHORT);
                break;
            case 2:
                MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(), Msg.E0025, new String[] { CommonConst.SPACE }),
                        Toast.LENGTH_SHORT);

                break;
            case 3:
                MessageUtil.commonToast(XZHL0150_MemberQuery.this,
                        properties.getProperty(Msg.E0028, CommonConst.SPACE), Toast.LENGTH_SHORT);
                break;
            case 4:
                MessageUtil.commonToast(XZHL0150_MemberQuery.this,
                        properties.getProperty(Msg.E0027, CommonConst.SPACE), Toast.LENGTH_SHORT);
                break;
            case 0:
                MessageUtil.commonToast(XZHL0150_MemberQuery.this,
                        properties.getProperty(Msg.E0003, CommonConst.SPACE), Toast.LENGTH_SHORT);
                break;

            default:
                break;
            }
            super.onPostExecute(result);
        }

    }

    /**
     * json解析
     * @param json JSON
     * @return 返回网络状态
     */
    public Integer jsonToMember(String json) {

        LogUtil.logStart();
        try {
            JSONObject jb = new JSONObject(json);
            LogUtil.logEnd();
            if ("1".equals(jb.getString(CommonConst.SUCCESS))) {
                return 1;

            } else if ("0".equals(jb.getString(CommonConst.SUCCESS))) {
                return 0;
            } else {
                return -1;
            }
        } catch (JSONException e) {
            LogUtil.logException(e);
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * JSON解析方法
     * @param json 传入json
     * @return 返回
     */
    public List<MemberBean> json2List(String json) {

        LogUtil.logStart();
        List<MemberBean> list = new ArrayList<MemberBean>();
        MemberBean member = null;
        try {
            JSONObject jb = new JSONObject(json);

            if ("1".equals(jb.getString(CommonConst.SUCCESS))) {
                JSONObject data = new JSONObject(jb.getString("data"));
                JSONArray listarray = data.getJSONArray("member");
                JSONObject userJson = null;
                for (int i = 0; i < listarray.length(); i++) {
                    userJson = listarray.getJSONObject(i);
                    member = new MemberBean();
                    member.setId(userJson.getString("id"));
                    member.setImage(userJson.getString("image"));
                    member.setName(userJson.getString("name"));
                    member.setType(userJson.getString("type"));
                    member.setTel(userJson.getString("tel"));
                    member.setSex(userJson.getString("sex"));
                    member.setBirthday(userJson.getString("birthday"));
                    member.setAddress(userJson.getString("address"));
                    member.setRemarks(userJson.getString("remarks"));
                    list.add(member);
                }
                LogUtil.logEnd();
                return list;
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.logException(e);
            return null;
        }
    }

    // 下拉刷新操作
    @Override
    public void onRefresh() {

        LogUtil.logStart();

        GetUserInfoByPageRefrsh getUserInfo = new GetUserInfoByPageRefrsh();
        getUserInfo.execute();
        LogUtil.logEnd();

    }

    // 上划加载更多接口
    @Override
    public void onLoadMore() {

        LogUtil.logStart();
        GetUserInfoByPageMore getUserInfo = new GetUserInfoByPageMore();
        getUserInfo.execute();
        LogUtil.logEnd();

    }

    /**
     * 界面的接触按钮 点击任意位置 取消软键盘
     * @param event 事件
     * @return boolean 状态值
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        LogUtil.logStart();
        etSearch.clearFocus();
        clearkeybord();
        LogUtil.logEnd();
        return super.onTouchEvent(event);

    }

    /**
     * 取消软键盘方法
     */
    private void clearkeybord() {

        LogUtil.logStart();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        LogUtil.logEnd();
    }

    /**
     * 构造批量删除JSON
     * @param user 用户列表
     * @param delflg 删除选择列表
     * @return JSON
     */
    private String createJson(List<MemberBean> user, List<Boolean> delflg) {

        String json = CommonConst.SPACE;
        try {

            JSONObject jodate = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < delflg.size(); i++) {
                if (delflg.get(i)) {
                    jsonArray.put(list.get(i).getId());
                }
            }
            jodate.put("id", jsonArray);
            json = jodate.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json1 = JsonUtil.DataToJson("a001", json, shared.getString("ID", CommonConst.SPACE),
                JsonUtil.getSign("a001", json, shared.getString("ID", CommonConst.SPACE)));
        Log.i("json", json1);
        return json1;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 如果没有处于删除状态 执行退出方法

            if (delflg) {
                delflg = false;
                lvdel.setVisibility(View.GONE);
                lvUser.setVisibility(View.VISIBLE);
                btnSearch.setVisibility(View.VISIBLE);
                btnAddUser.setBackgroundResource(R.drawable.add);
                GetUserInfoByPageRefrsh getuser = new GetUserInfoByPageRefrsh();
                getuser.execute();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return false;

    }

    /**
     * 加载结束
     */
    @SuppressLint("SimpleDateFormat")
    private void onLoad() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒");
        lvUser.stopRefresh();
        lvUser.stopLoadMore();
        lvUser.setRefreshTime(sdf.format(new Date()));
    }
}
