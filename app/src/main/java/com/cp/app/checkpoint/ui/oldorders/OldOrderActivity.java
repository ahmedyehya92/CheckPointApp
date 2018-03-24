package com.cp.app.checkpoint.ui.oldorders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.adapters.OldOrdersExpandableListAdapter;
import com.cp.app.checkpoint.data.models.OldOrderChildModel;
import com.cp.app.checkpoint.data.models.OldOrderGroupModel;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.languagechanging.LanguageChangeActivity;
import com.cp.app.checkpoint.ui.profile.ProfileActivity;
import com.cp.app.checkpoint.ui.registration.RegistrationActivity;
import com.cp.app.checkpoint.utils.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OldOrderActivity extends BaseActivity implements OldOrdersMvpView {
    private OldOrdersExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private OldOrdersPresenter oldOrdersPresenter;

    private LinearLayout errorConnectionLayout;
    private ProgressBar mprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_order);
        initViews();

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        oldOrdersPresenter = new OldOrdersPresenter(dataManager);
        oldOrdersPresenter.onAttach(this);

        oldOrdersPresenter.reqOldOrders();
/*
        String stringResponse = "{\n" +
                "\t\"On 2:00 pm Mon 2017/10/12\":\n" +
                "\t\t[\n" +
                "\t\t\t{\"total_price\":\"40\"},\n" +
                "\t\t\t{\"name\":\"spresso\",\"quantity\":\"10\"},\n" +
                "\t\t\t{\"name\":\"spresso\",\"quantity\":\"10\"},\n" +
                "\t\t\t{\"name\":\"spresso\",\"quantity\":\"10\"},\n" +
                "\t\t\t{\"name\":\"spresso\",\"quantity\":\"10\"}\n" +
                "\t\t\t],\n" +
                "\t\n" +
                "\t\"On 3:00 pm Mon 2017/10/15\":\n" +
                "\t\t[\n" +
                "\t\t\t{\"total_price\":\"30\"},\n" +
                "\t\t\t{\"name\":\"spresso\",\"quantity\":\"10\"},\n" +
                "\t\t\t{\"name\":\"spresso\",\"quantity\":\"10\"},\n" +
                "\t\t\t{\"name\":\"spresso\",\"quantity\":\"10\"},\n" +
                "\t\t\t{\"name\":\"spresso\",\"quantity\":\"10\"}\n" +
                "\t\t]\n" +
                "\t\n" +
                "}";

        try {
            JSONObject jsonResponse = new JSONObject(stringResponse);
            ArrayList<OldOrderGroupModel> list = new ArrayList<OldOrderGroupModel>();
            ArrayList<OldOrderChildModel> ch_list;

            Iterator<String> key = jsonResponse.keys();

            while (key.hasNext()) {
                String k = key.next();
                OldOrderGroupModel itemGroup;
                ch_list = new ArrayList<OldOrderChildModel>();
                JSONArray ja = jsonResponse.getJSONArray(k);
                String totalPrice = null;
                for (int i = 0; i<ja.length();i++)
                {
                    if (i==0)
                    {
                        JSONObject priceObject = ja.getJSONObject(i);
                        totalPrice = priceObject.getString("total");
                    }
                    else {
                        JSONObject jo = ja.getJSONObject(i);
                        OldOrderChildModel ch = new OldOrderChildModel(jo.getString("name"), jo.getInt("quantity"));
                        ch_list.add(ch);
                    }
                }
                OldOrderGroupModel gru = new OldOrderGroupModel(k,ch_list);
                gru.setTotalPriceForOrder(totalPrice);
                list.add(gru);
            }

            expandableListAdapter = new OldOrdersExpandableListAdapter(OldOrderActivity.this,list);
            expandableListView.setAdapter(expandableListAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }


    ExpandableListView.OnGroupExpandListener onGroupExpandListenser = new ExpandableListView.OnGroupExpandListener() {
        int previousGroup =-1;
        @Override
        public void onGroupExpand(int groupPosition) {
            if(groupPosition!= previousGroup)
                expandableListView.collapseGroup(previousGroup);
            previousGroup = groupPosition;
        }
    };

    @Override
    public void initViews() {
        expandableListView = findViewById(R.id.list_item_old_order);
        expandableListView.setOnGroupExpandListener(onGroupExpandListenser);
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
    public void setupOldOrdersList(ArrayList<OldOrderGroupModel> list) {
        expandableListAdapter = new OldOrdersExpandableListAdapter(OldOrderActivity.this,list);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                expandableListView.setAdapter(expandableListAdapter);
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

    @Override
    public void returnToRegistrationActivity() {
        startActivity(RegistrationActivity.getStartIntent(this));
        finish();
        Toast.makeText(this, R.string.signup_to_con, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OldOrderActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_language :
                startActivity(LanguageChangeActivity.getStartIntent(getApplicationContext()));
                return true;
            case R.id.item_old_order :
                return true;
            case R.id.item_profile :
                startActivity(ProfileActivity.getStartIntent(getApplicationContext()));
        }
        return true;
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, OldOrderActivity.class);
        return intent;
    }
}
