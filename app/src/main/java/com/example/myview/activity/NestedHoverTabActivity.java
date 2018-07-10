package com.example.myview.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.myview.R;
import com.example.myview.adapter.CommonTabPagerAdapter;
import com.example.myview.databinding.ActivityTabRecyclerBinding;
import com.example.myview.fragment.EmptyFragment;
import com.example.myview.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchunhao on 2018/7/10.
 */

public class NestedHoverTabActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ActivityTabRecyclerBinding binding;
    private CommonTabPagerAdapter adapter;
    private List<String> list = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private HomeFragment homeFragment;
    private EmptyFragment emptyFragment;
    private EmptyFragment emptyFragment1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_recycler);

        //binding.collapsingToolbar.setTitle("返回");
        //binding.collapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
        //binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的

        list.add("预售");
        list.add("闪购");
        list.add("品牌");

        homeFragment = new HomeFragment();
        emptyFragment = new EmptyFragment();
        emptyFragment1 = new EmptyFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(emptyFragment);
        fragmentList.add(emptyFragment1);

        adapter = new CommonTabPagerAdapter(getSupportFragmentManager(), fragmentList, list, this);
        binding.viewpager.setAdapter(adapter);
        binding.tabLayout.setOnTabSelectedListener(this);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        binding.tabLayout.setupWithViewPager(binding.viewpager);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
