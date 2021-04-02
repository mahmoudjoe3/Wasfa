package com.mahmoudjoe3.wasfa.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mahmoudjoe3.wasfa.ui.main.account.AccountFragment;
import com.mahmoudjoe3.wasfa.ui.main.fav.FavoritesFragment;
import com.mahmoudjoe3.wasfa.ui.main.home.HomeFragment;
import com.mahmoudjoe3.wasfa.ui.main.post.PostFragment;
import com.mahmoudjoe3.wasfa.ui.main.search.SearchFragment;

public class viewpagerAdapter extends FragmentStatePagerAdapter {
    public viewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new SearchFragment();
            case 2:
                return new PostFragment();
            case 3:
                return new FavoritesFragment();
            case 4:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
