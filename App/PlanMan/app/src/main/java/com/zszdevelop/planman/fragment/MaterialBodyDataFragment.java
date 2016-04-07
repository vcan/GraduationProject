package com.zszdevelop.planman.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.MaterialBodyDataAdapter;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.bean.BodyData;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.view.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangshengzhong on 16/3/11.
 */
public class MaterialBodyDataFragment extends BaseFragment {


    @Bind(R.id.plmrv_material)
   public PullLoadMoreRecyclerView plmrvMaterial;




    private int mScrollOffset = 4;
    private int currentPage;
    List<ConsumeRecordInfo> lists = new ArrayList<>();
    private RefreshCallBack refreshCallBack;
    private RecyclerViewMaterialAdapter adapter;
    private BodyData bodyData;
    private MaterialBodyDataAdapter mBDAdapter;
    private MaterialBodyDataFragment fragment;

    public static MaterialBodyDataFragment newInstanceFragment(BodyData bodyData) {

        MaterialBodyDataFragment fragment = new MaterialBodyDataFragment();
        fragment.bodyData = bodyData;
        fragment.fragment = fragment;
        return fragment;
    }

    //当该Fragment被添加,显示到Activity时调用该方法
    //在此判断显示到的Activity是否已经实现了接口
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof RefreshCallBack)) {
            throw new IllegalStateException("Fragment所在的Activity必须实现RefreshCallBack接口");
        }
        refreshCallBack = (RefreshCallBack) activity;
    }


    //当该Fragment从它所属的Activity中被删除时调用该方法
    @Override
    public void onDetach() {
        super.onDetach();
        refreshCallBack = null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_material_body_data;
    }

    @Override
    protected void onBindFragment(View view) {


        initView();
        initListener();
        fillData();


    }

    private void initListener() {

//        fabNewFigure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fabMainMenu.close(true);
//                Intent intent = new Intent(getActivity(), RecordFigureActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//        fabNewFoods.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fabMainMenu.close(true);
//                Intent intent = new Intent(getActivity(), SearchActivity.class);
//                intent.putExtra("SearchType", ResultCode.FOOD_CODE);
//                startActivity(intent);
//
//            }
//        });
//
//        fabNewSports.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fabMainMenu.close(true);
//               Intent intent = new Intent(getActivity(), SearchActivity.class);
//                intent.putExtra("SearchType", ResultCode.SPORTS_CODE);
//                startActivity(intent);
//
//            }
//        });
//
//        fabNewPlan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fabMainMenu.close(true);
//                Intent intent = new Intent(getActivity(), InsertPlanActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }

    @Override
    protected void lazyLoad() {


    }


    private void initView() {
        initRecycleView();
        initFloatActionButton();
    }

    private void initFloatActionButton() {
//        fabMainMenu.setClosedOnTouchOutside(true);
//        fabMainMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fabMainMenu.toggle(true);
//            }
//        });
    }

    private void fillData() {

        // 设置头部文件\
        mBDAdapter.setHeaderData(bodyData);
        adapter.notifyDataSetChanged();

        currentPage = 1;
        refreshCallBack.fillDataListener(currentPage,fragment);
    }


    private void initRecycleView() {


        plmrvMaterial.setLinearLayout();
        plmrvMaterial.getRecyclerView().setHasFixedSize(true);
        // 应用adapter
        mBDAdapter = new MaterialBodyDataAdapter(getActivity(), R.layout.head_material_body_data, R.layout.item_consume_record, lists);
        // 并将应用的adapter 设置给 RecyclerViewMaterialAdapter
        this.adapter = new RecyclerViewMaterialAdapter(mBDAdapter);
        plmrvMaterial.setAdapter(this.adapter);
        plmrvMaterial.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                refreshCallBack.fillDataListener(currentPage, fragment);
            }

            @Override
            public void onLoadMore() {
                refreshCallBack.fillDataListener(++currentPage, fragment);
            }
        });

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), plmrvMaterial.getRecyclerView(), null);

        plmrvMaterial.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > mScrollOffset) {
                    if (dy > 0) {
//                        fabMainMenu.hideMenu(true);
                        refreshCallBack.setHideMenu();
                    } else {
//                        fabMainMenu.showMenu(true);
                        refreshCallBack.setShowMenu();
                    }
                }
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


    public void setViewPagerData(List<ConsumeRecordInfo> data) {
        plmrvMaterial.setPullLoadMoreCompleted();
        if (data == null) {
            return;
        }
        lists.addAll(data);
        adapter.notifyDataSetChanged();

    }


    public interface RefreshCallBack {
        void fillDataListener(int currentPage, MaterialBodyDataFragment fragment);
        void setHideMenu();
        void setShowMenu();
    }

}
