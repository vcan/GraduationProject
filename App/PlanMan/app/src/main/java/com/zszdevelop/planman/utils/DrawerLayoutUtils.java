package com.zszdevelop.planman.utils;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.activity.InsertPlanActivity;
import com.zszdevelop.planman.activity.MaterialMainActivity;
import com.zszdevelop.planman.activity.ModifyUserActivity;
import com.zszdevelop.planman.activity.MyPlanActivity;
import com.zszdevelop.planman.activity.RecordFigureActivity;
import com.zszdevelop.planman.activity.SearchActivity;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.config.ResultCode;

/**
 * Created by zhangshengzhong on 16/3/26.
 */
public class DrawerLayoutUtils {

    public static void interactorNavigation(final BaseActivity act, NavigationView navigation, final DrawerLayout drawerLayout) {

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


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //用于辨别此前是否已有选中条目
            MenuItem preMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                Snackbar.make(MainActivity.this, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();

                if (preMenuItem != null){
                    preMenuItem.setChecked(false);
                }

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                preMenuItem = menuItem;


                menuItem.setChecked(true);
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_mian:
                        intent = new Intent(act, MaterialMainActivity.class);
                        act.startActivity(intent);
                        act.finish();
                        break;
                    case R.id.navigation_new_plan:
                        intent = new Intent(act, InsertPlanActivity.class);
                        act.startActivity(intent);
                        act.finish();
                        break;
                    case R.id.navigation_plan:
                        intent = new Intent(act, MyPlanActivity.class);
                        act.startActivity(intent);
                        act.finish();
                        break;
                    case R.id.navigation_record_figure:
                        intent = new Intent(act, RecordFigureActivity.class);
                        act.startActivity(intent);
                        act.finish();
                        break;
                    case R.id.navigation_search_food:
                        intent = new Intent(act, SearchActivity.class);
                        intent.putExtra("SearchType", ResultCode.FOOD_CODE);
                        act.startActivity(intent);
                        break;

                    case R.id.navigation_search_sport:
                        intent = new Intent(act, SearchActivity.class);
                        intent.putExtra("SearchType", ResultCode.SPORTS_CODE);
                        act.startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
