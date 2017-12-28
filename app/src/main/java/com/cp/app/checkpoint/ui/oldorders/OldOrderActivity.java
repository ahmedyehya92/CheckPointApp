package com.cp.app.checkpoint.ui.oldorders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.adapters.OldOrdersExpandableListAdapter;
import com.cp.app.checkpoint.data.models.OldOrderChildModel;
import com.cp.app.checkpoint.data.models.OldOrderGroupModel;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.languagechanging.LanguageChangeActivity;
import com.cp.app.checkpoint.utils.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OldOrderActivity extends BaseActivity implements OldOrdersMvpView {
    private OldOrdersExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_order);
        initViews();
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
                        totalPrice = priceObject.getString("total_price");
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_language :
                startActivity(LanguageChangeActivity.getStartIntent(getApplicationContext()));
                return true;
            case R.id.item_old_order :
                return true;
        }
        return true;
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, OldOrderActivity.class);
        return intent;
    }
}
