package gmarket.itheima.cn.gmarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.base.BaseFragment;
import gmarket.itheima.cn.gmarket.fragment.AppFragment;
import gmarket.itheima.cn.gmarket.fragment.CategoryFragment;
import gmarket.itheima.cn.gmarket.fragment.FragmentFractory;
import gmarket.itheima.cn.gmarket.fragment.GameFragment;
import gmarket.itheima.cn.gmarket.fragment.HomeFragment;
import gmarket.itheima.cn.gmarket.fragment.HotFragment;
import gmarket.itheima.cn.gmarket.fragment.RecommendFragment;
import gmarket.itheima.cn.gmarket.fragment.SubjectFragment;
import gmarket.itheima.cn.gmarket.utils.CommonUtil;

/**
 * Created by asus on 2017/2/4.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {


    String [] tabs;
    public MainPagerAdapter(FragmentManager fm) {
        //FragmentManager内部已经避免了每次点击时会重新new 子类  new HomeFragment
        super(fm);
        //这里获取的是资源文件中定义的array
        tabs = CommonUtil.getStringArray(R.array.tab_names);
    }

    //获取片段
    @Override
    public Fragment getItem(int position) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Fragment fragment = FragmentFractory.createFragment(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override //TabLayout的页签标题依赖该方法进行展示
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
