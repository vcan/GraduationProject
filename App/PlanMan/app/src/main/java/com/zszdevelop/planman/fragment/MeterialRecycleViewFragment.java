package com.zszdevelop.planman.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.MeterialRVAdapter;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.view.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangshengzhong on 16/3/11.
 */
public class MeterialRecycleViewFragment extends BaseFragment {


    @Bind(R.id.plmrv_material)
    PullLoadMoreRecyclerView plmrvMaterial;
    private MeterialRVAdapter adapter;
    private int currentPage;
    private int actionType;
    List<ConsumeRecordInfo> lists = new ArrayList<>();
    private RefreshCallBack refreshCallBack;


    public static MeterialRecycleViewFragment newInstanceFragment(int type) {

        MeterialRecycleViewFragment fragment = new MeterialRecycleViewFragment();
        fragment.actionType = type;
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
        return R.layout.fragment_material_recycleview;
    }

    @Override
    protected void onBindFragment(View view) {

        initView();
        fillData();

    }



    private void initView() {
        initRecycleView();
    }

    private void fillData() {
        adapter.clear();
        currentPage = 1;
        refreshCallBack.fillDataListener(currentPage, actionType);
    }


    private void initRecycleView() {

        adapter = new MeterialRVAdapter(getActivity(), R.layout.item_consume_record,lists);
        plmrvMaterial.setAdapter(adapter);
        plmrvMaterial.setLinearLayout();
        plmrvMaterial.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                currentPage = 1;
                refreshCallBack.fillDataListener(currentPage, actionType);
            }

            @Override
            public void onLoadMore() {
                refreshCallBack.fillDataListener(++currentPage, actionType);
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

    public void notifyAdapter(List<ConsumeRecordInfo> data) {
        plmrvMaterial.setPullLoadMoreCompleted();
        if (data == null) {
            return;
        }
        lists.addAll(data);
        adapter.notifyDataSetChanged();

    }


    public interface RefreshCallBack {
         void fillDataListener(int currentPage,int actionType);
    }

}
