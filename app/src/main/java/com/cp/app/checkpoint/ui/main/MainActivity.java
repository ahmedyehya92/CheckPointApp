package com.cp.app.checkpoint.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.adapters.CategoryAdapter;
import com.cp.app.checkpoint.data.models.CategoryModel;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.listtoorder.ListToOrderActivity;
import com.cp.app.checkpoint.ui.subcategoriy.SubCategoryActivity;
import com.cp.app.checkpoint.ui.timer.TimerActivity;
import com.cp.app.checkpoint.utils.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, CategoryAdapter.customButtonListener{

    private ListView categoryListView;
    private ArrayList<CategoryModel> itemsList;
    private CategoryAdapter arrayAdapter;
    MainPresenter mainPresenter;
    TextView tvListOrderCounter;
    Button btnYourList;
    private LinearLayout errorConnectionLayout;
    private Handler handler;
    private Runnable runnable;
    private ProgressBar mprogressBar;


    ArrayList<ListOfOneOrderModel> orderList;
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        mainPresenter = new MainPresenter(dataManager);
        mainPresenter.onAttach(this);
        mainPresenter.reqCategoriesList();
/*
        String stringResponse = "{\n" +
                "  \"categories\": [\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"},\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"},\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"},\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"},\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"},\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"},\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"},\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"},\n" +
                "    {\"id\":1,\"name\":\"Hot Drinks\"},\n" +
                "    {\"id\":2,\"name\":\"Cold Drinks\"}\n" +
                "  ]\n" +
                "}";

        try {
            JSONObject jsonResponse = new JSONObject(stringResponse);
            ArrayList<CategoryModel> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray();
            jsonArray= jsonResponse.getJSONArray("categories");

            for (int i = 0; i<jsonArray.length();i++)
            {
                JSONObject jo = jsonArray.getJSONObject(i);
                CategoryModel categoryModel = new CategoryModel(jo.getString("id"),jo.getString("name"));
                list.add(categoryModel);
            }
            arrayAdapter = new CategoryAdapter(MainActivity.this,list);
            arrayAdapter.setCustomButtonListner(MainActivity.this);
            categoryListView.setAdapter(arrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        */

        btnYourList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonYourListClicked();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvListOrderCounter.setText(mainPresenter.getNumberOfItemsListOrder().toString());
        checkTimer();

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }


    @Override
    public void initViews() {
        tvListOrderCounter = findViewById(R.id.tv_list_order_counter);
        btnYourList = findViewById(R.id.btn_your_list);
        categoryListView = findViewById(R.id.list_category);

        mprogressBar = findViewById(R.id.progress_bar);
        if (mprogressBar != null) {
            mprogressBar.setIndeterminate(true);
            mprogressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
            mprogressBar.setVisibility(View.VISIBLE);
        }
        errorConnectionLayout = findViewById(R.id.err_conn_layout);
        if(errorConnectionLayout != null )
        {
            errorConnectionLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onButtonYourListClicked() {
        if (buttonHasCounter())
        {
            startActivity(TimerActivity.getStartIntent(MainActivity.this));
        }
        else
        startActivity(ListToOrderActivity.getStartIntent(MainActivity.this));
    }

    @Override
    public void setupListView(ArrayList<CategoryModel> list) {
        arrayAdapter = new CategoryAdapter(MainActivity.this,list);
        arrayAdapter.setCustomButtonListner(MainActivity.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                categoryListView.setAdapter(arrayAdapter);
            }
        });

    }

    @Override
    public void hideProgressBar() {
        if (mprogressBar != null)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mprogressBar.setVisibility(View.GONE);
                }
            });

        }
    }

    @Override
    public void showLayoutErrorConnection() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                errorConnectionLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    public void checkTimer()
    {
        String date = mainPresenter.getCounterDate();
        if (date != null)
        {
            countDownStart(date);
        }
        else btnYourList.setText(getString(R.string.btn_your_list));
    }
    public boolean buttonHasCounter()
    {
        String date = mainPresenter.getCounterDate();
        return date != null;
    }

    public void countDownStart(final String time) {
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
// Please here set your event date//YYYY-MM-DD

                    Date futureDate = dateFormat.parse(time);
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        String time = String.format("%02d", minutes)+" : " + String.format("%02d", seconds);
                        btnYourList.setText(time);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


    }

    @Override
    public void onItemClickListner(String id, String catName, View buttonView, int position) {
        startActivity(SubCategoryActivity.getStartIntent(MainActivity.this, id, catName));
    }
}
