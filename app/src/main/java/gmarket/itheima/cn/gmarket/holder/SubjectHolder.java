package gmarket.itheima.cn.gmarket.holder;

import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.base.BaseHolder;
import gmarket.itheima.cn.gmarket.model.net.SubjectInfo;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;
import gmarket.itheima.cn.gmarket.utils.ServerAPI;
import gmarket.itheima.cn.gmarket.views.RatioImageView;


/**
 * Created by asus on 2017/2/8.
 */

public class SubjectHolder extends BaseHolder<SubjectInfo> {

    @BindView(R.id.iv_picture)
    RatioImageView mIvPicture;
    @BindView(R.id.tv_des)
    TextView mTvDes;

    public SubjectHolder(ViewGroup parent) {
        super(parent);
    }


    @Override
    protected View initView(ViewGroup parent) {
        View itemView = CommonUtil.getInflater().inflate(R.layout.item_subject, parent, false);

        ButterKnife.bind(this, itemView);
        return itemView;
    }

    @Override
    public void bindData(SubjectInfo data) {

        mTvDes.setText(data.getDes());
        //设置图片  app/com.itheima.www/icon.jpg  因为Goson解析得到的是String字符串所以要用xutils3来转换成图片
        String iconUrl = data.getUrl();

        x.image().bind(mIvPicture, ServerAPI.IMAGE_BASE_URL + iconUrl);
        Log.e("~~!", ServerAPI.IMAGE_BASE_URL + iconUrl);
    }


}
