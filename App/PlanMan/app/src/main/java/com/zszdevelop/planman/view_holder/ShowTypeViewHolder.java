package com.zszdevelop.planman.view_holder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zszdevelop.planman.R;


/**
 * @author 作者 ShengZhong E-mail: zszdevelop@163.com
 * @version 创建时间：2015年11月23日 类说明
 */

public class ShowTypeViewHolder extends RecyclerView.ViewHolder{

	public TextView btn_select_type;

	
	public ShowTypeViewHolder(View itemView) {
		super(itemView);
		btn_select_type = (TextView) itemView.findViewById(R.id.btn_select_type);

	}


		
		
	
}
