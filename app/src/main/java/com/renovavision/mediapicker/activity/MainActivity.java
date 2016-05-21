package com.renovavision.mediapicker.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.renovavision.mediapicker.R;
import com.renovavision.mediapicker.fragment.SelectImageFragment;
import com.renovavision.mediapicker.fragment.SelectVideoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tabLayout)
    TabLayout tabsView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.pager)
    ViewPager pagerView;

    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    private ActorTabAdapter actorTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        setUpViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void setUpViewPager() {
        actorTabAdapter = new ActorTabAdapter(getSupportFragmentManager());
        pagerView.setAdapter(actorTabAdapter);
        tabsView.setupWithViewPager(pagerView);
    }

    private final class ActorTabAdapter extends FragmentPagerAdapter {

        public ActorTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new SelectImageFragment();
            } else {
                return new SelectVideoFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Image";
            } else {
                return "Video";
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
