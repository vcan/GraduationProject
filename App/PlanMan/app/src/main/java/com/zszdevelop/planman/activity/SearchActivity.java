package com.zszdevelop.planman.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.SearchAdapter;
import com.zszdevelop.planman.bean.Food;
import com.zszdevelop.planman.db.DBHelper;
import com.zszdevelop.planman.db.DatabaseHelper;
import com.zszdevelop.planman.http.ToastUtil;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.view.PullLoadMoreRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.til_search)
    TextInputLayout tilSearch;
    @Bind(R.id.plmrv_search)
    PullLoadMoreRecyclerView plmrvSearch;
    @Bind(R.id.iv_go_search)
    ImageView ivGoSearch;
    private DatabaseHelper helper;

    List<Food> foods = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private String searchStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initView();
        initListener();
        initDB();

    }

    private void initView() {

        initRecyclerView();
    }

    private void initListener() {

        ivGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchStr = tilSearch.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(searchStr)){
                    ToastUtil.showToast("请输入搜索内容");
                    return;
                }
                initDB();
            }
        });



    }

    private void initDB() {
        helper = new DatabaseHelper(this);
        DBHelper dbHelper = new DBHelper(SearchActivity.this);
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
//            String sql = "select * from foods where name=\""+searchStr+"\"";
            String sql = "select * from foods";
            LogUtils.e(sql);
            Cursor cursor = db.rawQuery(sql, null);

            List<Food> foodses = new ArrayList<>();
            Log.e("info", "length = " + cursor.getCount());
            while (cursor.moveToNext()) {
                Food foods = new Food();
                foods.setId(cursor.getString(0));
                foods.setName(cursor.getString(1));
                foods.setCalory(cursor.getDouble(2));
                foodses.add(foods);

            }
            searchAdapter.appendData(foodses);
            searchAdapter.notifyDataSetChanged();
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initRecyclerView() {
        searchAdapter = new SearchAdapter(this, R.layout.item_search, foods);
        plmrvSearch.setAdapter(searchAdapter);
        plmrvSearch.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                plmrvSearch.setPullLoadMoreCompleted();
            }

            @Override
            public void onLoadMore() {

                plmrvSearch.setPullLoadMoreCompleted();

            }
        });
        plmrvSearch.setLinearLayout();
    }
}
