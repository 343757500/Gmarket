package gmarket.itheima.cn.gmarket.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.base.BaseFragment;
import gmarket.itheima.cn.gmarket.holder.DetailDesHolder;
import gmarket.itheima.cn.gmarket.holder.DetailInfoHolder;
import gmarket.itheima.cn.gmarket.holder.DetailSafeHolder;
import gmarket.itheima.cn.gmarket.holder.DetailScreenHolder;
import gmarket.itheima.cn.gmarket.model.net.HomeDetailBean;
import gmarket.itheima.cn.gmarket.model.net.ResultState;
import gmarket.itheima.cn.gmarket.protocol.HomeDetailProtocol;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;
import gmarket.itheima.cn.gmarket.utils.ServerAPI;

/**
 * Created by asus on 2017/2/12.
 */

public class HomeDetailFragment extends BaseFragment {


    @BindView(R.id.flt_detail_info)
    FrameLayout mFltDetailInfo;
    @BindView(R.id.flt_detail_safe)
    FrameLayout mFltDetailSafe;
    @BindView(R.id.hsv_detail_screen)
    HorizontalScrollView mHsvDetailScreen;
    @BindView(R.id.flt_detail_des)
    FrameLayout mFltDetailDes;

    @BindView(R.id.pb_bar)
    ProgressBar pbBar; //驼峰法则


    @BindView(R.id.tv_download_state)
    TextView tvDownloadState;


    private DetailInfoHolder mDetailInfoHolder;
    private String mPackageName;

    private HomeDetailBean datas;
    private DetailSafeHolder safeHolder;
    private DetailScreenHolder screenHolder;
    private DetailDesHolder desHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPackageName = getArguments().getString("packageName");
    }

    @Override
    public View onSubCreateSuccessView() {
        View view = CommonUtil.inflate(R.layout.fragment_detail_home);
        ButterKnife.bind(this, view);

        //1.1 初始化模块界面，即把四个模块的界面根节点添加到相应的容器中
        initModeViw();
        return view;
    }

    private void initModeViw() {
        //1.1 把详情信息模块的界面添加到相应的容器中
        mDetailInfoHolder = new DetailInfoHolder(mFltDetailInfo);

        //把信息模块的界面添加到容器中
        //mFltDetailInfo.addView(mDetailInfoHolder.getRootView());
        //1.2 添加安全模块界面
        safeHolder = new DetailSafeHolder(mFltDetailSafe);

        //1.3 添加截屏模块
        screenHolder = new DetailScreenHolder(mHsvDetailScreen);

        //1.4 添加简介模块
        desHolder = new DetailDesHolder(mFltDetailDes);


    }

    //初始花模块视图的数据
    private void initModeData() {
        mDetailInfoHolder.bindData(datas);
        safeHolder.bindData(datas);

        //2.3 绑定截屏模块数据
        screenHolder.bindData(datas);
        //2.4 绑定简介模块数据
        desHolder.bindData(datas);
    }

    //2. 加载数据
    @Override
    protected ResultState onSubLoad() {
        //HomeDetailProtocol
        HomeDetailProtocol protocol = new HomeDetailProtocol();
        //传递包名
        protocol.setPackageName(mPackageName);
        datas = protocol.getData(0);
        //设置数据
        CommonUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initModeData();
            }
        });

        //检测状态
        if (datas == null) {
            return ResultState.LOAD_ERROR;
        } else {
            if (datas.getSize() == 0) {
                return ResultState.LOAD_EMPTY;
            } else {
                return ResultState.LOAD_SUCCESS;
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    /**
     * 用xutils3 实现下载
     * 1. 下载apk的url
     * 2. 下载到哪里
     * 3. 下载状态的更新
     * 4. 正在下载的话，是不能点击重复下载
     *
     * @param v
     */
    @OnClick(R.id.pb_bar)
    public void download(View v) {
        String apkUrl = ServerAPI.APK_BASE_URL + datas.getDownloadUrl();
        RequestParams requestParams = new RequestParams(apkUrl);
        //本地存储apk的位置,外部存储的私有路径中
        // mnt/sdcard/android/data/cn.itheima.gmarket/files/downloads/youyuan.apk
        String savaApkFilePath = mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
                + "/" + datas.getPackageName() + ".apk";
        requestParams.setSaveFilePath(savaApkFilePath);
//        requestParams.addBodyParameter("account","android");
//        requestParams.addBodyParameter("pwd","123");
        x.http().post(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //LogUtil.e("download", "onSuccess");
                //改文本显示的状态
                tvDownloadState.setText("下载完成");
                pbBar.setEnabled(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            //请求不管什么状态，最后都会调用该方法
            @Override
            public void onFinished() {
            }

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
               // LogUtil.e("download", "onStarted");
                tvDownloadState.setText("开始下载");
                //设置 进度条是不能点击的
                pbBar.setEnabled(false);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //LogUtil.e("download", "onLoading");
                pbBar.setMax((int) total); //进度的最大值
                pbBar.setProgress((int) current); //设置当前的进度
                tvDownloadState.setText("下载中...");
            }
        });


    }
  /*  private AsyncTask MyTask =new AsyncTask<String,Integer,String>() {
        @Override
        protected String doInBackground(String[] params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    };*/

}
