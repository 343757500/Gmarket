package gmarket.itheima.cn.gmarket.fragment;

import android.view.View;
import android.widget.TextView;

import gmarket.itheima.cn.gmarket.base.BaseFragment;
import gmarket.itheima.cn.gmarket.model.net.ResultState;

/**
 * Created by asus on 2017/2/5.
 */

public class GameFragment extends BaseFragment {
    @Override
    protected ResultState onSubLoad() {
        return ResultState.LOAD_SUCCESS;
    }

    @Override
    public View onSubCreateSuccessView() {
        TextView textView=new TextView(mContext);
        textView.setText("GameFragment成功页面");


        return textView;
    }
}
