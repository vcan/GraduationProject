package com.zszdevelop.planman.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.utils.RiseNumberTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangshengzhong on 16/2/21.
 */
public class FigureFragment extends BaseFragment {


    @Bind(R.id.tv_figure_title)
    TextView tvFigureTitle;

    @Bind(R.id.tv_figure_time)
    TextView tvFigureTime;
    @Bind(R.id.btn_figure_complete)
    Button btnFigureComplete;
    @Bind(R.id.rntv_figure_number)
    RiseNumberTextView rntvFigureNumber;
    private int type;

    public static FigureFragment newInstanceFragment(int type) {
        FigureFragment fragment = new FigureFragment();
        fragment.type = type;
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_figure;
    }

    @Override
    protected void onBindFragment(View view) {
        rntvFigureNumber.withNumber(78)
                .setDuration(3000)
                .start();
        rntvFigureNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rntvFigureNumber.withNumber(78)
                        .setDuration(3000)
                        .start();
            }
        });

        btnFigureComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("daole");


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
}
