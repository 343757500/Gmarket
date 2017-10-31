package gmarket.itheima.cn.gmarket.holder;

import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.base.BaseHolder;
import gmarket.itheima.cn.gmarket.model.net.AppInfo;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;
import gmarket.itheima.cn.gmarket.utils.ServerAPI;


/**
 * Created by asus on 2017/2/8.
 */

public class AppHolder extends BaseHolder<AppInfo> {


   @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rating_bar)
    RatingBar mRatingBar;
    @BindView(R.id.tv_app_size)
    TextView mTvAppSize;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;



    public AppHolder(ViewGroup parent) {
        super(parent);
    }

    //绑定点击事件

    @OnClick(R.id.iv_icon)
    //注意方法签名参数要与该方法的接口声明参数一致
    public void testClick(View v){
        Toast.makeText(CommonUtil.getContext(),"点击监听",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected View initView(ViewGroup parent) {
        View itemView = CommonUtil.getInflater().inflate(R.layout.item_app, parent, false);
       // mTvNaeme = (TextView) view.findViewById(R.id.tv_name);
        // object :表示当前操作的对象 ,view: 要自动注入的视图对象的根节点
        ButterKnife.bind(this,itemView);
        return itemView;
    }

    @Override
    public void bindData(AppInfo data) {
        mTvName.setText(data.getName());
        mTvDesc.setText(data.getDes());
        mTvAppSize.setText(Formatter.formatFileSize(CommonUtil.getContext(),data.getSize()));
        //评分
        mRatingBar.setRating(data.getStars());

        //设置图片  app/com.itheima.www/icon.jpg  因为Goson解析得到的是String字符串所以要用xutils3来转换成图片
        String iconUrl = data.getIconUrl();

       x.image().bind(mIvIcon, ServerAPI.IMAGE_BASE_URL+iconUrl);
        Log.e("~~!",ServerAPI.IMAGE_BASE_URL+iconUrl);
    }


}
