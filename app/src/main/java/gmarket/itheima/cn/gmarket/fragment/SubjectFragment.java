package gmarket.itheima.cn.gmarket.fragment;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import gmarket.itheima.cn.gmarket.adapter.SubjectAdapter;
import gmarket.itheima.cn.gmarket.base.BaseFragment;
import gmarket.itheima.cn.gmarket.base.BaseListFreshFragment;
import gmarket.itheima.cn.gmarket.base.BaseProtocol;
import gmarket.itheima.cn.gmarket.base.GMBaseListAdapter;
import gmarket.itheima.cn.gmarket.model.net.ResultState;
import gmarket.itheima.cn.gmarket.model.net.SubjectInfo;
import gmarket.itheima.cn.gmarket.protocol.SubjuctProtocol;

/**
 * Created by asus on 2017/2/5.
 */

public class SubjectFragment extends BaseListFreshFragment<SubjectInfo> {


    @Override
    public GMBaseListAdapter getAdapter() {
        return new SubjectAdapter();
    }

    @Override
    protected BaseProtocol<List<SubjectInfo>> getProtocol() {
        SubjuctProtocol subjuctProtocol=new SubjuctProtocol();
        return subjuctProtocol;
    }

}
