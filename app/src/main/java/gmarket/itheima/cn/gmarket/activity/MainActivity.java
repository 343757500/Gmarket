package gmarket.itheima.cn.gmarket.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import gmarket.itheima.cn.gmarket.GMApplication;
import gmarket.itheima.cn.gmarket.R;
import gmarket.itheima.cn.gmarket.adapter.MainPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //模拟网络访问，获取用户的昵称
        String nick = "Android";
        //取得唯一的Application对象
        GMApplication application = (GMApplication) getApplication();
        //设置application维护的全局变量
        application.setNick(nick);
        Toast.makeText(getApplicationContext(), application.getNick(), Toast.LENGTH_SHORT).show();
        System.out.print("1111");
        initView();
    }

    private void initView() {
        //1,获取取得NavigationView对象
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //2,设置NavigationView 选择项监听
        navigationView.setNavigationItemSelectedListener(this);
        //3,引入 DrawerLayout对象
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        //4,引入及初始化Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //初始化Toolbar，注意在setSupportActionBar之前初始化Toolbar
        //设置Toolbar标题颜色
        toolbar.setTitleTextColor(Color.WHITE);
        //取代Actionbar ,要设置没有Actionbar主题,要在清单文件主题中设置没有AntionBar主题才不报异常
        setSupportActionBar(toolbar);


        //1.4 设置Toolbar的导航图标
        ActionBarDrawerToggle barDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        barDrawerToggle.syncState();//同步状态
        mDrawerLayout.addDrawerListener(barDrawerToggle);


        //初始化ViewPager
        ViewPager viewPager= (ViewPager) findViewById(R.id.main_viewpager);
        //!!!!!!!!!!!!!!!!!!!!!!!设置显示哪个页面的Fragment
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

        //初始化TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//滚动模式,这样TabLayout才不会铺满而不能滚动到隐藏效果
        //与ViewPager 绑定在一起 ,TabLayout的页签标题通过PagerAdpater的getPagerTitle（）来取得
        tabLayout.setupWithViewPager(viewPager);
    }

    //导航项选择监听
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //关闭抽屉，隐藏菜单
        mDrawerLayout.closeDrawers();
        //设置为选中状态
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.nav_camera:
                Toast.makeText(getApplicationContext(), "电影", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_gallery:
                Toast.makeText(getApplicationContext(), "gallery", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_manage:
                Toast.makeText(getApplicationContext(), "信息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(getApplicationContext(), "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(getApplicationContext(), "发送", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_slideshow:
                Toast.makeText(getApplicationContext(), "展示", Toast.LENGTH_SHORT).show();
                break;


        }

        return false;
    }
}
