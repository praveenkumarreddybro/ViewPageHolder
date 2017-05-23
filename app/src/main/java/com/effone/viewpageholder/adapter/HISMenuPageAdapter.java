package com.effone.viewpageholder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;


import com.effone.viewpageholder.common.UpdateableInterface;
import com.effone.viewpageholder.fragment.MenuViewFragment;
import com.effone.viewpageholder.model.Items;

import java.util.ArrayList;
import java.util.HashMap;

public class HISMenuPageAdapter extends FragmentPagerAdapter  {


    private HashMap<String ,ArrayList<Items>> mFullmenu;
    private ViewPager mViewPager;


    public HISMenuPageAdapter(FragmentManager fm, HashMap<String ,ArrayList<Items>> mFullmenu) {
        super(fm);

        this.mFullmenu = mFullmenu;
    }






    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFullmenu.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mViewPager=(ViewPager)container;
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        String heading = (String) (mFullmenu.keySet().toArray())[ position ];
        ArrayList<Items> hisMenuItems = mFullmenu.get( heading );
        MenuViewFragment menuViewFragment = new MenuViewFragment();
        menuViewFragment.setParentView(mViewPager);
        menuViewFragment.setHeading(heading,position);
        menuViewFragment.setValues(hisMenuItems);

        return menuViewFragment;

    }

    private void arrowShowingMethods(int position) {

    }

    public void update() {

        notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object) {
        if (object instanceof UpdateableInterface) {
            ((UpdateableInterface) object).update();
        }
        return super.getItemPosition(object);
    }

}

