package com.zszdevelop.planman.utils;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.activity.InsertPlanActivity;
import com.zszdevelop.planman.activity.MaterialMainActivity;
import com.zszdevelop.planman.activity.ModifyUserActivity;

/**
 * Created by zhangshengzhong on 16/3/26.
 */
public class DrawerToolUtils {

    public static void interactorNavigation(final AppCompatActivity act, Toolbar toolbar, final NavigationView navigation, final DrawerLayout drawerLayout) {

        if (navigation.getHeaderCount() > 0){
            View headerView = navigation.getHeaderView(0);
            ImageView ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
            TextView tv_name = (TextView) headerView.findViewById(R.id.tv_name);
            TextView tv_describe = (TextView) headerView.findViewById(R.id.tv_describe);
            ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(act, ModifyUserActivity.class);
                    act.startActivity(intent);
                }
            });
        }

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(act, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //用于辨别此前是否已有选中条目
            MenuItem preMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {




                switch (menuItem.getItemId()) {
                    case R.id.navigation_mian:
//                        new Handler().postDelayed(new Runnable() {
//                            public void run() {
                                Intent  intent = new Intent(act, MaterialMainActivity.class);
                                act.startActivity(intent);
//                            }
//                        }, 1000);

                        break;
                    case R.id.navigation_new_plan:
//                        new Handler().postDelayed(new Runnable() {
//                            public void run() {
                                Intent intent1 = new Intent(act, InsertPlanActivity.class);
                                act.startActivity(intent1);
//                            }
//                        }, 1000);
////                        act.finish();
                        break;

                }
                drawerLayout.closeDrawers();

//                menuItem.setChecked(true);
//                preMenuItem = menuItem;

                return true;
            }
        });
    }


//    case R.id.navigation_plan:
//    intent = new Intent(act, MyPlanActivity.class);
//    act.startActivity(intent);
////                        act.finish();
//    break;
//    case R.id.navigation_record_figure:
//    intent = new Intent(act, RecordFigureActivity.class);
//    act.startActivity(intent);
////                        act.finish();
//    break;
//    case R.id.navigation_search_food:
//    intent = new Intent(act, SearchActivity.class);
//    intent.putExtra("SearchType", ResultCode.FOOD_CODE);
//    act.startActivity(intent);
//    break;
//
//    case R.id.navigation_search_sport:
//    intent = new Intent(act, SearchActivity.class);
//    intent.putExtra("SearchType", ResultCode.SPORTS_CODE);
//    act.startActivity(intent);
//    break;
//    case R.id.navigation_change:
//    intent = new Intent(act, ChangeActivity.class);
//    act.startActivity(intent);
//    break;

    public static void initToolbar(final AppCompatActivity act,Toolbar toolbar,String title) {
        toolbar.setTitle(title);
        act.setSupportActionBar(toolbar);
        if (act.getSupportActionBar() != null) {
            act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.onBackPressed();
            }
        });
    }
}
