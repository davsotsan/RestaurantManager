package com.dima.restaurantmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MiFragmentPagerAdapter extends FragmentPagerAdapter {

    private String numero_mesa;
    final int PAGE_COUNT = 5;
    private String tabTitles[] =
            new String[] { "Bebidas", "Entrantes", "Primeros", "Segundos", "Postres"};

    public MiFragmentPagerAdapter(FragmentManager fm, String numero_mesa) {
        super(fm);
        this.numero_mesa = numero_mesa;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        f = FragmentMenu.newInstance(tabTitles[position].toLowerCase(), numero_mesa);

//        f = FragmentMenu.newInstance(tabTitles[position].toLowerCase(), numero_mesa);

        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}