package com.skeleton.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.skeleton.fragment.BaseFragment;
import com.skeleton.fragment.SuperFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oem on 13/1/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentNameList  = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position) ;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentNameList.add(title);

    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentNameList.get(position);
    }
}
