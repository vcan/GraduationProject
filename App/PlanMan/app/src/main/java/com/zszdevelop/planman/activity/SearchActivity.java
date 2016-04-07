package com.zszdevelop.planman.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.redbooth.SlidingDeck;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.SearchAdapter;
import com.zszdevelop.planman.adapter.SlidingDeckAdapter;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.Food;
import com.zszdevelop.planman.bean.SlidingDeckModel;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.db.DBHelper;
import com.zszdevelop.planman.db.DatabaseHelper;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.http.ToastUtil;
import com.zszdevelop.planman.utils.DrawerToolUtils;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.view.PullLoadMoreRecyclerView;
import com.zszdevelop.planman.view_holder.SearchViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_go_search)
    ImageView ivGoSearch;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_search_save)
    TextView tvSearchSave;
    @Bind(R.id.slidingDeck)
    SlidingDeck slidingDeck;
    @Bind(R.id.tv_search_title)
    TextView tvSearchTitle;
    @Bind(R.id.emptyView)
    LinearLayout emptyView;
    @Bind(R.id.plmrv_search)
    PullLoadMoreRecyclerView plmrvSearch;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private DatabaseHelper helper;
    List<Food> foods = new ArrayList<>();

    OptionsPickerView pvOptions;
    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<String> options1ItemsSports = new ArrayList<String>();
    private ArrayList<ArrayList<Integer>> options2Items = new ArrayList<ArrayList<Integer>>();

    private SearchAdapter searchAdapter;

    private SlidingDeckAdapter slidingAdapter;
    private String searchStr;
    private int searchType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initView();
        initListener();

    }

    private void initView() {
        searchType = getIntent().getIntExtra("SearchType", ResultCode.FOOD_CODE);

        initRecyclerView();
        initializeSlidingDeck();
        DrawerToolUtils.initToolbar(this, toolbar, "");
        DrawerToolUtils.interactorNavigation(this, toolbar, navigation, drawerLayout);
        if (searchType == ResultCode.FOOD_CODE) {
            initOptionFood();
            etSearch.setHint("搜索饮食");
            tvSearchTitle.setText("添加饮食记录吧");

        } else {
            etSearch.setHint("搜索运动");
            tvSearchTitle.setText("添加运动记录吧");
            initOptionSport();

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (searchType == ResultCode.FOOD_CODE) {

            navigation.setCheckedItem(R.id.navigation_search_food);
        } else {

            navigation.setCheckedItem(R.id.navigation_search_sport);
        }
    }

    private void initListener() {

        ivGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchStr = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchStr)) {
                    ToastUtil.showToast("请输入搜索内容");
                    return;
                }
                initDB();
            }
        });

        tvSearchSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (slidingAdapter.getCount() > 0) {
                    submitData();
                } else {
                    ToastUtil.showToast("您还没选保存的食物");
                    return;
                }


            }
        });

    }

    private void submitData() {
        List<SlidingDeckModel> tempList = new ArrayList<SlidingDeckModel>();
        double totalCC = 0;

        for (int i = 0; i < slidingAdapter.getCount(); i++) {
            tempList.add(slidingAdapter.getItem(i));
            totalCC += slidingAdapter.getItem(i).getTotalCC();
        }
        Gson gson = new Gson();
        String listJson = gson.toJson(tempList);

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(Helper.getUserId()));
        map.put("authToken", Helper.getToken());
        map.put("consumeCC", String.valueOf(totalCC));
        map.put("consumeRecordType", String.valueOf(ResultCode.FOOD_CODE));
        map.put("consumeRecordContent", listJson);
        map.put("consumeRecordTime", String.valueOf(System.currentTimeMillis()));

        HttpRequest.post(API.SUBMIT_CONSUME_RECORD_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                ToastUtil.showToast("提交成功");
                finish();
            }
        });

    }

    private void initDB() {
        searchAdapter.clear();
        helper = new DatabaseHelper(this);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//            String sql = "select * from foods where name=\""+searchStr+"\"";
        String sql;
        if (searchType == ResultCode.FOOD_CODE) {
            sql = "select * from foods where name like \'%" + searchStr + "%\'";
        } else {
            sql = "select * from activities where name like \'%" + searchStr + "%\'";
        }

        LogUtils.e(sql);
        Cursor cursor = db.rawQuery(sql, null);

        List<Food> foodses = new ArrayList<>();
        Log.e("info", "length = " + cursor.getCount());
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Food foods = new Food();
                foods.setId(cursor.getString(0));
                foods.setName(cursor.getString(1));
                if (searchType == ResultCode.FOOD_CODE){
                    foods.setCalory(cursor.getDouble(2));
                }else {
                    foods.setCalory(cursor.getDouble(2)*61);
                }

                foodses.add(foods);

            }
        } else {
            ToastUtil.showToast("没有找到您要搜索的食物");
        }

        searchAdapter.appendData(foodses);
        searchAdapter.notifyDataSetChanged();
        cursor.close();
        db.close();

    }


    private void initializeSlidingDeck() {
        slidingAdapter = new SlidingDeckAdapter(this);

        slidingDeck = (SlidingDeck) findViewById(R.id.slidingDeck);
        slidingDeck.setAdapter(slidingAdapter);
        slidingDeck.setSwipeEventListener(new SlidingDeck.SwipeEventListener() {
            @Override
            public void onSwipe(SlidingDeck parent, View item) {
                SlidingDeckModel model = (SlidingDeckModel) item.getTag();
                slidingAdapter.remove(model);
//                slidingAdapter.insert(model, slidingAdapter.getCount());
                slidingAdapter.notifyDataSetChanged();

            }
        });
    }


    private void initRecyclerView() {
        searchAdapter = new SearchAdapter(this, R.layout.item_search, foods);
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void OnItemClicked(Food bean, SearchViewHolder holder) {
//                ToastUtil.showToast("您选择了:" + bean.getName());
//                optionFood(holder);
                setOptionFood(bean);
                pvOptions.show();
            }
        });
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


    private void initOptionFood() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);

        options1Items.add("早餐");
        options1Items.add("午餐");
        options1Items.add("晚餐");
        ArrayList<Integer> optionsItemsNumber = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            optionsItemsNumber.add(i * 10);
        }
        options2Items.add(optionsItemsNumber);
        options2Items.add(optionsItemsNumber);
        options2Items.add(optionsItemsNumber);


        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择克数");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        pvOptions.setSelectOptions(1, 10);


    }

    private void initOptionSport() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);

        for (int i = 0; i < 100; i++) {
            options1ItemsSports.add(i + "分钟");
        }


        pvOptions.setPicker(options1ItemsSports);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择运动时长");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        pvOptions.setSelectOptions(10);


    }

    private void setOptionFood(final Food bean) {

        slidingDeck.setEmptyView(findViewById(R.id.emptyView));
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                SlidingDeckModel slidingDeckModel = new SlidingDeckModel();
                double aloneCC = bean.getCalory();
                slidingDeckModel.setAloneCC(aloneCC);
                if (searchType == ResultCode.FOOD_CODE) {
                    Integer gram = options2Items.get(options1).get(option2);
                    slidingDeckModel.setGram(gram);
                    slidingDeckModel.setTotalCC(aloneCC * (gram / 100));
                    slidingDeckModel.setSlidingTime(options1Items.get(options1));

                } else {
                    slidingDeckModel.setSlidingTime("今日运动");
                    int time = Integer.parseInt(options1ItemsSports.get(options1).replace("分钟",""));
                    double v = aloneCC * (time / 60);
//                    slidingDeckModel.setTotalCC(aloneCC * ( time/ 60));
                    slidingDeckModel.setGram(time);
                    slidingDeckModel.setTotalCC(v);


                }
                slidingDeckModel.setSlidingName(bean.getName());





                slidingAdapter.insert(slidingDeckModel, 0);//插入在第一条
                slidingAdapter.notifyDataSetChanged();
            }
        });
    }


}
