package gmarket.itheima.cn.gmarket.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.base.BaseHolder;
import gmarket.itheima.cn.gmarket.model.net.HomeDetailBean;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;

/**
 * Created by asus on 2017/2/13.
 */

public class DetailDesHolder extends BaseHolder<HomeDetailBean> {

    @BindView(R.id.tv_detail_desc)
    TextView mTvDetailDesc;
    @BindView(R.id.tv_detail_author)
    TextView mTvDetailAuthor;

    public DetailDesHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    protected View initView(ViewGroup parent) {
        View desView = CommonUtil.getInflater().inflate(R.layout.layout_detail_des, parent);
        ButterKnife.bind(this, desView);
        return desView;
    }

    @Override
    public void bindData(HomeDetailBean data) {
        //设置数据  ，作者    简介
        mTvDetailDesc.setText(data.getDes());
        mTvDetailAuthor.setText(data.getAuthor());
    }
}
