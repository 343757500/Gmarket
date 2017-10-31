package gmarket.itheima.cn.gmarket.holder;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.base.BaseHolder;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;
import gmarket.itheima.cn.gmarket.utils.ServerAPI;

/**
 * Created by asus on 2017/2/9.
 */

public class HomeHeaderHolder extends BaseHolder<List<String>> {
    private List<String> pictures=new ArrayList<>();
    private LinearLayout mDotContainer;
    private RollPager adapter;
    private ViewPager mViewPager;


    public HomeHeaderHolder(ViewGroup parent) {
        super(parent);
    }
    //初始轮播图界面,xml或者java代码 在代码中布局方便于数据的不确定性,比如有100个轮播点的话
    @Override
    protected View initView(ViewGroup parent) {
      //构建轮播图的根节点(父控件)视图
        RelativeLayout rootRlt=new RelativeLayout(mContext);
        //定义轮播图父控件的高度为134sp
        //参数是水平 高度为134sp
        ListView.LayoutParams rootLayoutParams=
                new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dp2px(134));
        rootRlt.setLayoutParams(rootLayoutParams);

        // 构建ViewPager,展示轮播图
        mViewPager = new ViewPager(mContext);
        RelativeLayout.LayoutParams vpLayoutParams=
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        //把viewpager添加到父控件
        rootRlt.addView(mViewPager,vpLayoutParams);

        //构建圆点的容器
        mDotContainer = new LinearLayout(mContext);
        //参数是包裹
        RelativeLayout.LayoutParams dotContainerLayoutParams=
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置属性
        //设置水平排列
        mDotContainer.setOrientation(LinearLayout.HORIZONTAL);
        //设置距离父亲下面的6dp
        dotContainerLayoutParams.bottomMargin=CommonUtil.dp2px(6);
        //添加布局规则  位于父亲的右下角
        dotContainerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dotContainerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        rootRlt.addView(mDotContainer,dotContainerLayoutParams);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               //圆点状态同步
                for (int i = 0; i < pictures.size(); i++) {
                        if (i == position) {
                            mDotContainer.getChildAt(position).setBackgroundResource(R.drawable.indicator_selected);
                        } else {
                            mDotContainer.getChildAt(i).setBackgroundResource(R.drawable.indicator_normal);
                        }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //开始轮播图片
        CommonUtil.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //避免/0  如果picture.size()可能=0
                int i = pictures.size();
                mViewPager.setCurrentItem(i==0?0:(mViewPager.getCurrentItem()+1)%pictures.size());
                CommonUtil.getHandler().postDelayed(this,2000);
            }
        },1000);
       return rootRlt;


    }




    //加载数据绑定数据
    @Override
    public void bindData(List<String> data) {
        if(data!=null&&data.size()>0){
            //清除轮播图图片地址集合的数据
            pictures.clear();
            pictures.addAll(data);
            //初始化圆点
            initDotView();

            if(adapter==null) {
                adapter = new RollPager();
                mViewPager.setAdapter(adapter);
            }else{
                CommonUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        }
    }

    private void initDotView() {
        //移除圆点容器的视图
        mDotContainer.removeAllViews();
        for (int i = 0; i < pictures.size(); i++) {
            View dotView=new View(mContext);
            //设置圆点的宽高
            LinearLayout.LayoutParams dotLayoutParams=
                    new LinearLayout.LayoutParams(CommonUtil.dp2px(8),CommonUtil.dp2px(8));
            dotLayoutParams.rightMargin=CommonUtil.dp2px(6);
            if(i==0){
                dotView.setBackgroundResource(R.drawable.indicator_selected);
            }else{
                dotView.setBackgroundResource(R.drawable.indicator_normal);
            }
            //添加到容器中
            mDotContainer.addView(dotView,dotLayoutParams);
        }

    }
    ///轮播图页面适配器
    public class RollPager extends PagerAdapter {

        @Override
        public int getCount() {
            return pictures.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(container.getContext());
            x.image().bind(imageView, ServerAPI.IMAGE_BASE_URL+pictures.get(position));
            //内部 对 ImageView.set src   backgroud
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //!!!!!!!!!!!!!!!
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
        }


    }



}
