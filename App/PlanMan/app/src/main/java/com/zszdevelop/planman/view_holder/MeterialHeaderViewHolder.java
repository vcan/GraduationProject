package com.zszdevelop.planman.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zszdevelop.planman.R;

/**
 * Created by zhangshengzhong on 16/3/11.
 */
public class MeterialHeaderViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_meterial;

    public MeterialHeaderViewHolder(View itemView) {
        super(itemView);
        iv_meterial = (ImageView) itemView.findViewById(R.id.iv_meterial);
    }
}
