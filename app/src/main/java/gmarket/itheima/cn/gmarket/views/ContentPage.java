package gmarket.itheima.cn.gmarket.views;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.model.net.ResultState;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;

/**
 * Created by asus on 2017/2/5.
 * 片段的公共内容界面,一个页面有四个子页面
 *
 */

public abstract class ContentPage extends FrameLayout {
    //状态常量
    public static final int STATE_DEFAULT=0;//缺省状态
    public  static final int  STATE_LOADING=1;//正在加载状态
    public static final int STATE_ERROR=2;//访问网络出现异常状态
    public static final int STATE_EMPTY=3;//访问网络成功,但数据为NULL的状态
    public static final int STATE_SUCCESS=4;//访问网络成功且有数据的状态

    //状态变量
    private int state=STATE_DEFAULT;//默认为缺省状态  缺省状态默认为Loading状态

    private View emptyView;//空页面
    private View errorView;//访问网络出错页面
    private View loadingView;//正在加载页面
    private View successView;//成功页面
    public ContentPage(Context context) {
        super(context);
        initPager();
    }
    //初始化页面
    private void initPager() {
        //1.1 添加空界面
        if(emptyView==null){
            emptyView=onCreateEmptyView();
            //把界面添加到FrameLayout
            this.addView(emptyView);
        }
        //1.2 添加出错界面
        if(errorView==null){
            errorView=onCreateErrorView();
            this.addView(errorView);
        }
        //1.3 添加正在加载界面
        if (loadingView==null){
            loadingView=onCreateLoadingView();
            this.addView(loadingView);
        }
        //1.4 添加成功界面 ， 由于每个模块的成功界面都相对复杂，建议再显示界面时创建成功界面
      /*  if (successView==null){
            successView=onCreateSuccessView();
            this.addView(successView);
        }*/


        //根据状态显示不同的子页面
        showPager();


        //1.5 加载或者刷新数据方法的调用,也就是根据传过来的状态设置为成功,空,错误或者loading界面
        loadDataOrRefresh();
    }

    public void loadDataOrRefresh() {
        //3.1 重置状态  ，把原理的状态重置为缺省状态
        // 3.2 当处于正在加载状态时，不能再重新加载数据 ，排除正在加载数据状态Loading
        //state==state_loading
        if(state==STATE_EMPTY||state==STATE_ERROR||state==STATE_SUCCESS||state==STATE_DEFAULT){
            state=STATE_LOADING;
        }
        // 当状态处于缺省状态时，访问网络，加载数据
        if (state==STATE_LOADING){
            //访问网络，加载数据
            //开启子线程，访问网络
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //todo  访问网络，加载数据的操作 ，返回访问网络的结果状态
                    final ResultState resultState=onLoad();

                    //因为更新要在主线程中执行
                    //依据访问网络的结果状态更新界面
                    //与UI相关的操作要在UI线程执行
                    CommonUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState!=null){
                                state=resultState.getState();
                            }
                            //根据状态显示不同的子页面  显示页面  重新调动一次
                            showPager();
                        }
                    });
                }
            }).start();
        }


    }

    //2根据状态显示不同的子页面  显示页面
    private void showPager() {
        if(loadingView!=null){
            //2.1 当处于缺省状态或者正在加载数据状态，都显示转圈圈界面（正在加载界面）
            if (state==STATE_DEFAULT||state==STATE_LOADING){
                loadingView.setVisibility(View.VISIBLE);
            }else{
                loadingView.setVisibility(View.INVISIBLE);
            }
        }
        //2.3 显示为空界面
        if(emptyView!=null){
            if (state==STATE_EMPTY){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                emptyView.setVisibility(View.INVISIBLE);
            }
        }
        //2.2  显示出错界面
        if (errorView!=null){
            if (state==STATE_ERROR){
                errorView.setVisibility(View.VISIBLE);
            }else{
                errorView.setVisibility(View.INVISIBLE);
            }
        }

        if(successView==null){
            successView=onCreateSuccessView();
            if (successView!=null) {
                CommonUtil.reamovParent(successView);
                this.addView(successView);
            }
        }

        //2.4.2 显示或者隐藏成功界面
        if (successView!=null){
            if(state==STATE_SUCCESS){
                successView.setVisibility(View.VISIBLE);
            }else{
                successView.setVisibility(View.INVISIBLE);
            }
        }
    }
    //3. 加载数据或者刷新数据的方法


    //加载数据的方法，由于每个模块访问网络的url都有差异，且取得的数据也不同，所以不能在公共模块实现，由实现者去实现
    //访问网络结果的状态： 出错、为空、成功，枚举几个状态的常量
    public abstract ResultState onLoad();


    //初始化成功界面 ,由于每个功能模块的成功界面都有差异，所以不能在公共模块界面ContentPager中实现，应该抽象出去，由实现者来实现
    public  abstract View onCreateSuccessView();
    //初始化正在加载界面
    private View onCreateLoadingView() {
       // CommonUtil.inflate(R.layout.layout_loading);
        return  CommonUtil.inflate(R.layout.layout_loading);
    }
    // //初始化网络连接出错界面
    private View onCreateErrorView() {
        return CommonUtil.inflate(R.layout.layout_error);
    }
    //初始化网络连接数据为空界面
    private View onCreateEmptyView() {
        return CommonUtil.inflate(R.layout.layout_empty);
    }
}
