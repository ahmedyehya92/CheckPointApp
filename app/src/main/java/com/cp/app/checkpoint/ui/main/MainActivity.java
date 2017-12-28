package com.cp.app.checkpoint.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.subcategoriy.SubCategoryActivity;
import com.cp.app.checkpoint.utils.StaticValues;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView{

    MainPresenter mainPresenter;

    LinearLayout hotDrinksButton, coldDrinksButton, desertsButton;
    TextView tvListOrderCounter;


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

        hotDrinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SubCategoryActivity.getStartIntent(MainActivity.this, StaticValues.HOT_DRINKS_ID));
            }
        });
        coldDrinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SubCategoryActivity.getStartIntent(MainActivity.this,StaticValues.COLD_DRINKS_ID));
            }
        });
        desertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SubCategoryActivity.getStartIntent(MainActivity.this,StaticValues.DESERTS_ID));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvListOrderCounter.setText(mainPresenter.getNumberOfItemsListOrder().toString());

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }


    @Override
    public void initViews() {
        hotDrinksButton = findViewById(R.id.btn_hot_drinks);
        coldDrinksButton = findViewById(R.id.btn_cold_drinks);
        desertsButton = findViewById(R.id.btn_deserts);
        tvListOrderCounter = findViewById(R.id.tv_list_order_counter);
    }
}
