package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.MaterialPagerAdapter;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.BodyData;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.fragment.MaterialBodyDataFragment;
import com.zszdevelop.planman.fragment.MaterialRecycleViewFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.DrawerToolUtils;
import com.zszdevelop.planman.utils.ShareUtils;
import com.zszdevelop.planman.utils.UpdateVersionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaterialMainActivity extends BaseActivity implements MaterialRecycleViewFragment.RefreshCallBack ,MaterialBodyDataFragment.RefreshCallBack{

    @Bind(R.id.materialViewPager)
    MaterialViewPager materialViewPager;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;



    @Bind(R.id.fab_new_plan)
    FloatingActionButton fabNewPlan;
    @Bind(R.id.fab_new_figure)
    FloatingActionButton fabNewFigure;
    @Bind(R.id.fab_new_foods)
    FloatingActionButton fabNewFoods;
    @Bind(R.id.fab_new_sports)
    FloatingActionButton fabNewSports;
    @Bind(R.id.fab_main_menu)
    FloatingActionMenu fabMainMenu;

    private int mScrollOffset = 4;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_main);
        ButterKnife.bind(this);


        initListener();
        fillData();
        initView();

        // 看是否需要更新版本
        UpdateVersionUtils updateVersionUtils = new UpdateVersionUtils();
        updateVersionUtils.requestUpdata(this);
    }


    private void initView() {

        initToolbar();
        initFloatActionButton();

    }

    private void initToolbar() {
        setTitle("");

        toolbar = materialViewPager.getToolbar();
        PagerSlidingTabStrip pagerTitleStrip = materialViewPager.getPagerTitleStrip();
        pagerTitleStrip.setTextColor(ContextCompat.getColor(this, R.color.white));
        pagerTitleStrip.setTextSize(40);

        DrawerToolUtils.initToolbar(this, toolbar, "");

        DrawerToolUtils.interactorNavigation(this, toolbar, navigation, drawerLayout);
    }

    @Override
    protected void onStart() {
        navigation.setCheckedItem(R.id.navigation_mian);
        super.onStart();
    }

    private void initListener() {

        // viewpager 滑动监听
        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorAndDrawable(
                                ContextCompat.getColor(MaterialMainActivity.this, R.color.green),
                                ContextCompat.getDrawable(MaterialMainActivity.this, R.mipmap.ic_wallpaper));
//                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
//                    case 0:
//                        return HeaderDesign.fromColorAndDrawable(
//                                ContextCompat.getColor(MaterialMainActivity.this, R.color.colorPrimaryDark),
//                                ContextCompat.getDrawable(MaterialMainActivity.this, R.drawable.smssdk_search_icon));
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });


        fabNewFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMainMenu.close(true);
                Intent intent = new Intent(MaterialMainActivity.this, RecordFigureActivity.class);
                startActivity(intent);

            }
        });

        fabNewFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMainMenu.close(true);
                Intent intent = new Intent(MaterialMainActivity.this, SearchActivity.class);
                intent.putExtra("SearchType", ResultCode.FOOD_CODE);
                startActivity(intent);

            }
        });

        fabNewSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMainMenu.close(true);
                Intent intent = new Intent(MaterialMainActivity.this, SearchActivity.class);
                intent.putExtra("SearchType", ResultCode.SPORTS_CODE);
                startActivity(intent);

            }
        });

        fabNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMainMenu.close(true);
                Intent intent = new Intent(MaterialMainActivity.this, InsertPlanActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initFloatActionButton() {
        fabMainMenu.setClosedOnTouchOutside(true);
        fabMainMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMainMenu.toggle(true);
            }
        });
    }


    private void fillData() {
        if (Helper.getInstance().getBodyData()!= null){
            MaterialBodyDataFragment fragment = MaterialBodyDataFragment.newInstanceFragment(Helper.getInstance().getBodyData());
            fragmentList.add(fragment) ;
            fillPlan();
        }else {
            String url = String.format(API.BODY_DATA_URI, Helper.getUserId());
            HttpRequest.get(url, new HttpRequestListener() {
                @Override
                public void onSuccess(String json) {

                    Gson gson = new Gson();
                    BodyData bodyData = gson.fromJson(json, BodyData.class);
                    Helper.getInstance().setBodyData(bodyData);
                    // 填充数据
                    MaterialBodyDataFragment fragment = MaterialBodyDataFragment.newInstanceFragment(bodyData);

                    fragmentList.add(fragment) ;
                    fillPlan();
                }
            });
        }



    }

    private void fillPlan() {
        // 填充目标记录数据,根据目标数量显示Viewpager/fragment 的数量
        String url = String.format(API.GET_GOAL_PLAN_URI, Helper.getUserId(), ResultCode.NO_COMPLETE_GOAL_RECODE_CODE);
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<GoalInfo>>() {
                }.getType();
                List<GoalInfo> goalList = gson.fromJson(json, listType);

                for (int i = 0; i < goalList.size(); i++) {
                    // 填充数据
                    MaterialRecycleViewFragment fragment = MaterialRecycleViewFragment.newInstanceFragment(i, goalList.get(i));

                    fragmentList.add(fragment);
                }
                MaterialPagerAdapter pagerAdapter = new MaterialPagerAdapter(getSupportFragmentManager(), fragmentList);
                materialViewPager.getViewPager().setAdapter(pagerAdapter);
                materialViewPager.getViewPager().setOffscreenPageLimit(materialViewPager.getViewPager().getAdapter().getCount());
                materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());


            }
        });
    }


    @Override
    public void fillDataListener(int currentPage, final MaterialRecycleViewFragment fragment) {
        String url = String.format(API.CONSUME_RECORD_URI, Helper.getUserId(),currentPage);
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ConsumeRecordInfo>>() {
                }.getType();
                List<ConsumeRecordInfo> consumeRecords = gson.fromJson(json, listType);

                if (fragment != null) {
                    fragment.setViewPagerData(consumeRecords);
                }


            }
        });
    }

    @Override
    public void setHideMenu() {
        fabMainMenu.hideMenu(true);
    }

    @Override
    public void setShowMenu() {
        fabMainMenu.showMenu(true);

    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void fillDataListener(int currentPage, final MaterialBodyDataFragment bodyDataFragment) {
        String url = String.format(API.CONSUME_RECORD_URI, Helper.getUserId(),currentPage);
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ConsumeRecordInfo>>() {
                }.getType();
                List<ConsumeRecordInfo> consumeRecords = gson.fromJson(json, listType);

                if (bodyDataFragment != null) {
                    bodyDataFragment.setViewPagerData(consumeRecords);
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_about_us:
                Intent intent = new Intent(MaterialMainActivity.this,AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_shared:
                ShareUtils.shareToSocial();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
