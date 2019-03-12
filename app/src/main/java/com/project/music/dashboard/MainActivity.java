package com.project.music.dashboard;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.project.music.R;
import com.project.music.tabFragments.view.TabAdapter;
import com.project.music.common.BaseActivity;
import com.project.music.tabFragments.classic.classicView.ClassicFragment;
import com.project.music.tabFragments.pop.popView.PopFragment;
import com.project.music.tabFragments.rock.rockView.RockFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
    this.getSupportActionBar().hide();


    TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
    adapter.addFragment(new RockFragment(), "Rock");
    adapter.addFragment(new ClassicFragment(), "Classic");
    adapter.addFragment(new PopFragment(), "Pop");

    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);
    setupTabIcons();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }


    private int[] tabIcons = {
            R.drawable.ic_rock,
            R.drawable.ic_classic,
            R.drawable.ic_pop
    };
}
