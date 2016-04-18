package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.fragment.HomeFragment;
import com.zszdevelop.planman.fragment.InsertPlanFragment;
import com.zszdevelop.planman.fragment.MaterialRecycleViewFragment;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MaterialRecycleViewFragment.RefreshCallBack ,HomeFragment.BindToolbarCallBack{


    @Bind(R.id.fl_main_content)
    FrameLayout flMainContent;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Fragment mContent;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();

    }


    private void initView() {

//        DrawerToolUtils.initToolbar(this, toolbar, "首页");
//        DrawerToolUtils.interactorNavigation(this, toolbar, navigation, drawerLayout);

        homeFragment = HomeFragment.newInstanceFragment(toolbar);
        mContent = homeFragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main_content, homeFragment);
        fragmentTransaction.commit();


    }


    private void initListener() {
    }

    private void fillData() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
//
//    @Override
//    public void fillDataListener(int currentPage, final MaterialRecycleViewFragment fragment) {
//        String url = String.format(API.CONSUME_RECORD_URI, Helper.getUserId());
//        HttpRequest.get(url, new HttpRequestListener() {
//            @Override
//            public void onSuccess(String json) {
//                Gson gson = new Gson();
//                Type listType = new TypeToken<List<ConsumeRecordInfo>>() {
//                }.getType();
//                List<ConsumeRecordInfo> consumeRecords = gson.fromJson(json, listType);
//
//                fragment.setViewPagerData(consumeRecords);
//
//            }
//        });
//    }


    @Override
    public void getToolbar(Toolbar homeToolbar) {
        if (homeToolbar!=null){
            DrawerToolUtils.initToolbar(this, homeToolbar, "3333");
            interactorNavigation( homeToolbar, navigation, drawerLayout);


        }
    }

    public  void interactorNavigation(Toolbar toolbar, final NavigationView navigation, final DrawerLayout drawerLayout) {

        if (navigation.getHeaderCount() > 0){
            View headerView = navigation.getHeaderView(0);
            ImageView ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
            TextView tv_name = (TextView) headerView.findViewById(R.id.tv_name);
            TextView tv_describe = (TextView) headerView.findViewById(R.id.tv_describe);
            ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ModifyUserActivity.class);
                    startActivity(intent);
                }
            });
        }

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
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


                menuItem.setChecked(true);
                preMenuItem = menuItem;


                switch (menuItem.getItemId()) {
                    case R.id.navigation_mian:
//                                Intent  intent = new Intent(act, MaterialMainActivity.class);
//                                act.startActivity(intent);
                        switchContent(MainActivity.this,mContent,homeFragment);

                        break;
                    case R.id.navigation_new_plan:
                        switchContent(MainActivity.this,mContent,new InsertPlanFragment());
//                        new Handler().postDelayed(new Runnable() {
//                            public void run() {
//                        Intent intent1 = new Intent(this, InsertPlanActivity.class);
//                        act.startActivity(intent1);
//                            }
//                        }, 1000);
////                        act.finish();
                        break;

                }
                drawerLayout.closeDrawers();


                return true;
            }
        });
    }


    public  void switchContent(AppCompatActivity context, Fragment from, Fragment to) {

        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
//            FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction().setCustomAnimations(
//                    android.R.anim.fade_in, R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fl_main_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public void fillDataListener(int currentPage, MaterialRecycleViewFragment fragment) {

    }

    @Override
    public void setHideMenu() {
    }

    @Override
    public void setShowMenu() {


    }
}
