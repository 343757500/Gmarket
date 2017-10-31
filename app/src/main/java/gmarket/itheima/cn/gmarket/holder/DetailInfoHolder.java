package gmarket.itheima.cn.gmarket.holder;

import android.graphics.Color;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.base.BaseHolder;
import gmarket.itheima.cn.gmarket.model.net.HomeDetailBean;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;
import gmarket.itheima.cn.gmarket.utils.ServerAPI;

import static gmarket.itheima.cn.gmarket.R.id.appicon;

/**
 * Created by asus on 2017/2/13.
 */

public class DetailInfoHolder extends BaseHolder<HomeDetailBean> {
    @BindView(appicon)
    ImageView mAppicon;
    @BindView(R.id.appname)
    TextView mAppname;
    @BindView(R.id.appstar)
    RatingBar mAppstar;
    @BindView(R.id.appdownload)
    TextView mAppdownload;
    @BindView(R.id.appversion)
    TextView mAppversion;
    @BindView(R.id.apptime)
    TextView mApptime;
    @BindView(R.id.appsize)
    TextView mAppsize;

    public DetailInfoHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    protected View initView(ViewGroup parent) {
        View detailView = CommonUtil.getInflater().inflate(R.layout.layout_detail_appinfo, parent);
        ButterKnife.bind(this,detailView);

        return detailView;
    }

    @Override
    public void bindData(HomeDetailBean data) {
       mAppname.setText(data.getName());
        mAppname.setTextColor(Color.RED);
        mAppdownload.setText(data.getDownloadNum());
        mApptime.setText(data.getDate());
        mAppversion.setText(data.getVersion());
        mAppsize.setText(Formatter.formatFileSize(CommonUtil.getContext(),data.getSize()));
        mAppstar.setRating((float) data.getStars());

        //设置图片
        x.image().bind(mAppicon, ServerAPI.IMAGE_BASE_URL+data.getIconUrl());
    }
}
