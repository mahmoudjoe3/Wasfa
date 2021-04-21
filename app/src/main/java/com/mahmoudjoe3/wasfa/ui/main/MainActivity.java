package com.mahmoudjoe3.wasfa.ui.main;


import android.os.Bundle;
import android.os.PersistableBundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.prevalent.prevalent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.navigationBar)
    ChipNavigationBar navigationBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SetUpNavViewWithViewPager(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(prevalent.saved_nav_id, navigationBar.getSelectedItemId());
    }

    private void SetUpNavViewWithViewPager(Bundle savedInstanceState) {
        viewpagerAdapter adapter = new viewpagerAdapter(getSupportFragmentManager()
                , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        navigationBar.setItemSelected(R.id.menu_search, true);
                        break;
                    case 2:
                        navigationBar.setItemSelected(R.id.menu_post, true);
                        break;
                    case 3:
                        navigationBar.setItemSelected(R.id.menu_favorites, true);
                        break;
                    case 4:
                        navigationBar.setItemSelected(R.id.menu_account, true);
                        break;
                    default:
                        navigationBar.setItemSelected(R.id.menu_home, true);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (savedInstanceState == null) {
            navigationBar.setItemSelected(R.id.menu_home, true);
            viewPager.setCurrentItem(0);
        } else {
            int item_id = savedInstanceState.getInt(prevalent.saved_nav_id);
            navigationBar.setItemSelected(item_id, true);
            selectFragByiId(item_id);
        }
        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                selectFragByiId(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(navigationBar.getSelectedItemId()!=R.id.menu_home){
            selectFragByiId(R.id.menu_home);
            navigationBar.setItemSelected(R.id.menu_home,true);
        }else
            super.onBackPressed();
    }

    private void selectFragByiId(int item_id) {
        switch (item_id) {
            case R.id.menu_search:
                viewPager.setCurrentItem(1);
                break;
            case R.id.menu_post:
                viewPager.setCurrentItem(2);
                break;
            case R.id.menu_favorites:
                viewPager.setCurrentItem(3);
                break;
            case R.id.menu_account:
                viewPager.setCurrentItem(4);
                break;
            default:
                viewPager.setCurrentItem(0);
                break;
        }
    }
}