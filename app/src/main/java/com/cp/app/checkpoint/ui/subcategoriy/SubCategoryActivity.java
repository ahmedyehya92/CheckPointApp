package com.cp.app.checkpoint.ui.subcategoriy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.adapters.SubCategoriesExpandableListAdapter;
import com.cp.app.checkpoint.data.models.SubCategoryChildModel;
import com.cp.app.checkpoint.data.models.SubCategoryGroupModel;
import com.cp.app.checkpoint.ui.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SubCategoryActivity extends BaseActivity implements SubCategoryMvpView,SubCategoriesExpandableListAdapter.customButtonListener {

    private SubCategoriesExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        initActivityViews();

        String stringResponse = "{\n" +
                "\t\"cafe\":\n" +
                "\t\t[\n" +
                "\t\t\t{\"id\":3,\"name\":\"spresso\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\"},{\"id\":3,\"name\":\"spresso\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\"},{\"id\":3,\"name\":\"spresso\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\"},{\"id\":3,\"name\":\"spresso\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\"},{\"id\":3,\"name\":\"spresso\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\"},{\"id\":3,\"name\":\"spresso\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\"},{\"id\":3,\"name\":\"spresso\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\"}],\n" +
                "\t\n" +
                "\t\"classic\":\n" +
                "\t\t[\n" +
                "\t\t\t{\"id\":6,\"name\":\"tea\"}]\n" +
                "\t\n" +
                "}";

        try {
            JSONObject jsonResponse = new JSONObject(stringResponse);
            ArrayList<SubCategoryGroupModel> list = new ArrayList<SubCategoryGroupModel>();
            ArrayList<SubCategoryChildModel> ch_list;

            Iterator<String> key = jsonResponse.keys();

            while (key.hasNext()){
                String k = key.next();
                SubCategoryGroupModel productsGroup;
                ch_list = new ArrayList<SubCategoryChildModel>();
                JSONArray ja = jsonResponse.getJSONArray(k);

                for (int i = 0; i<ja.length();i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    SubCategoryChildModel ch = new SubCategoryChildModel(jo.getInt("id"),jo.getString("name"));
                    ch_list.add(ch);
                }
                SubCategoryGroupModel gru = new SubCategoryGroupModel(k,ch_list);
                list.add(gru);
            }
            expandableListAdapter = new SubCategoriesExpandableListAdapter(SubCategoryActivity.this,list);
            expandableListAdapter.setCustomButtonListner(SubCategoryActivity.this);
            expandableListView.setAdapter(expandableListAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initActivityViews() {
        expandableListView = (ExpandableListView) findViewById(R.id.list_item_sub_category);

    }

    @Override
    public void onButtonClickListner(int groupPosition, int childPosition, String value) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
}
