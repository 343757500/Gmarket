package gmarket.itheima.cn.gmarket.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;

import gmarket.itheima.cn.gmarket.base.BaseFragment;

/**
 * Created by asus on 2017/2/5.
 * 片段的工厂 ，通过简单的工厂设计模式创建片段,避免改了一处地方导致子类都要修改
 */

public class FragmentFractory {
    //设置为静态是避免内存回收BaseFragment被回收掉
    private static HashMap<Integer,BaseFragment> fragments =new HashMap<>();
    //依据不同的条目创建Fragment ，条件就是 position
    public static Fragment createFragment(int position){
        BaseFragment fragment=fragments.get(position);

        if(fragment==null) {
            switch (position) {
                case 0://首页片段
                    fragment = new HomeFragment();
                    break;
                case 1://应用片段
                    fragment = new AppFragment();
                    break;
                case 2://游戏片段
                    fragment = new GameFragment();
                    break;
                case 3://专题片段
                    fragment = new SubjectFragment();
                    break;
                case 4://分类片段
                    fragment = new RecommendFragment();
                    break;
                case 5://推荐片段CategoryFragment
                    fragment = new CategoryFragment();
                    break;
                case 6://排行片段
                    fragment = new HotFragment();
                    break;
                default://缺少情况构建的是首页片段
                    fragment=new HomeFragment();
                    break;
            }
            //把新构建的片段对象添加到容器中
            fragments.put(position,fragment);
        }
        return fragment;
    }
}
