package com.xiaonuo.yure;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.xiaonuo.yure.home.HomePageFragment;
import com.xiaonuo.yure.message.MessageFragment;
import com.xiaonuo.yure.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

import static com.xiaonuo.yure.R.*;

public class MainActivity extends AppCompatActivity {

    private SpaceTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);


        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomePageFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new MineFragment());

        ViewPager viewPager = (ViewPager) findViewById(id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = findViewById(id.spaceTabLayout);

        //we need the savedInstanceState to get the position
        tabLayout.initialize(viewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
