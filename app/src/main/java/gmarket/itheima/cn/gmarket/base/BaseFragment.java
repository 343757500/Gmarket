package gmarket.itheima.cn.gmarket.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gmarket.itheima.cn.gmarket.model.net.ResultState;
import gmarket.itheima.cn.gmarket.views.ContentPage;

/**
 * Created by asus on 2017/2/5.
 * 所以片段的基类，继承V4库中的fragment
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext; //公共的属性
    public ContentPage mContentPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //!!!!!!!!!创建共有界面实现访问网络获取数据的方法onCreateSuccessView  再由子类的onSubLoad中调用homeProtocol.getData(0)获取数据
        mContentPage = new ContentPage(mContext) {
            @Override
            public ResultState onLoad() {
                ResultState resultState=onSubLoad();
                return resultState;
            }

            @Override
            public View onCreateSuccessView() {
                View view=onSubCreateSuccessView();
                return view;
            }
        };
        return mContentPage;



    }

    //加载数据的方法，由于每个功能模块访问网络的url都有差异，且取得的数据也不同，所以不能在基类BaseFragment中实现，应该抽象出去，由子类来实现
    protected abstract ResultState onSubLoad() ;
    //创建成功界面方法 ,由于每个功能模块的成功界面都有差异，所以不能在基类BaseFragment中实现，应该抽象出去，由子类来实现
    public abstract View onSubCreateSuccessView();
}
