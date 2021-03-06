package com.mahmoudjoe3.wasfaty.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mahmoudjoe3.wasfaty.ui.main.account.AccountFragment;
import com.mahmoudjoe3.wasfaty.ui.main.interaction.interactionsFragment;
import com.mahmoudjoe3.wasfaty.ui.main.home.HomeFragment;
import com.mahmoudjoe3.wasfaty.ui.main.post.PostFragment;
import com.mahmoudjoe3.wasfaty.ui.main.search.SearchFragment;

public class viewpagerAdapter extends FragmentStatePagerAdapter {
    public viewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return SearchFragment.getInstance();
            case 2:
                return PostFragment.getInstance();
            case 3:
                return new interactionsFragment();
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
