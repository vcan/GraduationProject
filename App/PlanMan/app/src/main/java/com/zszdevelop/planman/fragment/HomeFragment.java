package com.zszdevelop.planman.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.MaterialPagerAdapter;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangshengzhong on 16/2/21.
 */
public class HomeFragment extends BaseFragment {


    @Bind(R.id.materialViewPager)
    MaterialViewPager materialViewPager;
    List<Fragment> fragmentList = new ArrayList<>();
    private Toolbar actToolbar;
    public Toolbar toolbar;
    private BindToolbarCallBack bindToolbarCallBack;

    public static HomeFragment newInstanceFragment(Toolbar toolbar) {
        HomeFragment fragment = new HomeFragment();
        fragment.actToolbar = toolbar;
        fragment.actToolbar.setVisibility(View.GONE);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
//        actToolbar.setVisibility(View.VISIBLE);
    }

    //当该Fragment被添加,显示到Activity时调用该方法
    //在此判断显示到的Activity是否已经实现了接口
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof HomeFragment.BindToolbarCallBack)) {
            throw new IllegalStateException("TitlesListFragment所在的Activity必须实现TitlesListFragmentCallBack接口");
        }
        bindToolbarCallBack = (BindToolbarCallBack) activity;
    }

    //当该Fragment从它所属的Activity中被删除时调用该方法
    @Override
    public void onDetach() {
        super.onDetach();
        bindToolbarCallBack = null;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onBindFragment(View view) {
        initView();
        initListener();
        fillData();

    }


    private void initView() {

        if (bindToolbarCallBack != null){
            Toolbar toolbar = materialViewPager.getToolbar();
            bindToolbarCallBack.getToolbar(toolbar);
        }
//        DrawerToolUtils.initToolbar((AppCompatActivity) getActivity(), toolbar, "");

    }


    private void initListener() {

        // viewpager 滑动监听
        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
//                    case 0:
//                        return HeaderDesign.fromColorAndDrawable(
//                                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark),
//                                ContextCompat.getDrawable(getActivity(), R.drawable.smssdk_search_icon));
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
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

    }


    private void fillData() {

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
                MaterialPagerAdapter pagerAdapter = new MaterialPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
                materialViewPager.getViewPager().setAdapter(pagerAdapter);
                materialViewPager.getViewPager().setOffscreenPageLimit(materialViewPager.getViewPager().getAdapter().getCount());
                materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());


            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface BindToolbarCallBack {
         void getToolbar(Toolbar toolbar);
    }

}

