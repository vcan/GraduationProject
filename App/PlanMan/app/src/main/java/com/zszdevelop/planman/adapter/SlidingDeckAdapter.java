/*
 * Copyright Txus Ballesteros 2016 (@txusballesteros)
 *
 * This file is part of some open source application.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Contact: Txus Ballesteros <txus.ballesteros@gmail.com>
 */
package com.zszdevelop.planman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.redbooth.SlidingDeck;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.bean.SlidingDeckModel;
import com.zszdevelop.planman.config.ResultCode;

public class SlidingDeckAdapter extends ArrayAdapter<SlidingDeckModel> {
    private int actionType;
    public SlidingDeckAdapter(Context context,int actionType) {
        super(context, R.layout.sliding_item);
        this.actionType = actionType;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sliding_item, parent, false);
        }
        SlidingDeckModel item = getItem(position);
        view.setTag(item);
        TextView alone_cc = (TextView) view.findViewById(R.id.tv_sliding_alone_cc);
        TextView total_cc = (TextView) view.findViewById(R.id.tv_sliding_total_cc);
        TextView sliding_gram = (TextView) view.findViewById(R.id.tv_sliding_gram);
        TextView sliding_name = (TextView) view.findViewById(R.id.tv_sliding_name);
        TextView sliding_time = (TextView) view.findViewById(R.id.tv_sliding_time);
        TextView total_ccstr = (TextView) view.findViewById(R.id.tv_sliding_total_ccstr);

        if (actionType == ResultCode.FOOD_CODE){

            total_cc.setText(String.format("%.2f 大卡", item.getTotalCC()));
            sliding_gram.setText(String.format("摄入: %.2f 克", item.getGram()));
            alone_cc.setText(String.format("每100g热量: %.2f 大卡", item.getAloneCC()));
            sliding_name.setText(item.getSlidingName());
            sliding_time.setText(item.getSlidingTime() + " : ");
            total_ccstr.setText("摄入的总热量: ");

        }else {

            total_cc.setText(String.format("%.2f 大卡", item.getTotalCC()));
            sliding_gram.setText(String.format("运动: %s 分钟",item.getGram()));
            alone_cc.setText(String.format("每60分钟: %.2f 大卡", item.getAloneCC()));
            sliding_name.setText(item.getSlidingName());
            sliding_time.setText(item.getSlidingTime() + " : ");
            total_ccstr.setText("消耗的总热量: ");

        }
        final View completeView = view.findViewById(R.id.completeCommand);
        completeView.setTag(view);
        completeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SlidingDeck slidingDeck = (SlidingDeck)parent;
                slidingDeck.swipeItem((View)view.getTag(), new SlidingDeck.SwipeEventListener() {
                    @Override
                    public void onSwipe(SlidingDeck parent, View item) {
                        final SlidingDeckModel slidingDeckModel = (SlidingDeckModel)item.getTag();
                        remove(slidingDeckModel);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        return view;
    }


}
