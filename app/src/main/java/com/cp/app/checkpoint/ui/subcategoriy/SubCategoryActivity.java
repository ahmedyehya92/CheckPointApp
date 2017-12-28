package com.cp.app.checkpoint.ui.subcategoriy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.adapters.SubCategoriesExpandableListAdapter;
import com.cp.app.checkpoint.data.models.SubCategoryChildModel;
import com.cp.app.checkpoint.data.models.SubCategoryGroupModel;
import com.cp.app.checkpoint.ui.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import com.cp.app.checkpoint.ui.listtoorder.ListToOrderActivity;
import com.cp.app.checkpoint.utils.StaticValues;

public class SubCategoryActivity extends BaseActivity implements SubCategoryMvpView,SubCategoriesExpandableListAdapter.customButtonListener,PopupAddToCart {


    private SubCategoriesExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;

    private EditText etPopupAddToCart;
    private LinearLayout btnAddToCart;
    private Button btnYourList;
    private TextView tvListItemCounter;

    SubCategoryPresenter subCategoryPresenter;

    // TODO get user id from shared preference
    String userId = "5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        initViews();

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        subCategoryPresenter = new SubCategoryPresenter(dataManager);
        subCategoryPresenter.onAttach(this);


        // TODO get intent that send from main activity to get id to can send request with id

        String stringResponse = "{\n" +
                "\t\"cafe\":\n" +
                "\t\t[\n" +
                "\t\t\t{\"id\":3,\"name\":\"spresso\",\"price\":\"10\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\",\"price\":\"10\"},{\"id\":3,\"name\":\"spresso\",\"price\":\"10\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\",\"price\":\"10\"},{\"id\":3,\"name\":\"spresso\",\"price\":\"10\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\",\"price\":\"10\"},{\"id\":3,\"name\":\"spresso\",\"price\":\"10\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\",\"price\":\"10\"},{\"id\":3,\"name\":\"spresso\",\"price\":\"10\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\",\"price\":\"10\"},{\"id\":3,\"name\":\"spresso\",\"price\":\"10\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\",\"price\":\"10\"},{\"id\":3,\"name\":\"spresso\",\"price\":\"10\"},\n" +
                "\t\t\t{\"id\":4,\"name\":\"Double\",\"price\":\"10\"}],\n" +
                "\t\n" +
                "\t\"classic\":\n" +
                "\t\t[\n" +
                "\t\t\t{\"id\":6,\"name\":\"tea\",\"price\":\"10\"}]\n" +
                "\t\n" +
                "}";
        btnYourList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onYourListButtonClick();
            }
        });

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
                    SubCategoryChildModel ch = new SubCategoryChildModel(jo.getInt("id"),jo.getString("name"),jo.getInt("price"));
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
    protected void onStart() {
        super.onStart();
        tvListItemCounter.setText(subCategoryPresenter.getNumberOfListItemsOrder().toString());
    }

    @Override
    public void initViews() {
        expandableListView = findViewById(R.id.list_item_sub_category);
        btnYourList = findViewById(R.id.btn_your_list);
        tvListItemCounter = findViewById(R.id.tv_listItem_counter);
    }

    @Override
    public void openAddToCartPopup(View v,final String itemId, final String itemName,final Integer priceOfOnePiece) {
        final PopupWindow popWindow;

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.popup_add_to_cart_layout, null, false);
        initPopubVies(inflatedView);


        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        popWindow = new PopupWindow(inflatedView, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT, true );

        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_add_to_cart_popup));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etContent = etPopupAddToCart.getText().toString();
                Integer quantity = Integer.parseInt(etContent);

                if (!(quantity>10) )
                {
                    Integer totalPrice = quantity * priceOfOnePiece;
                    onAddToCartButtonClick(popWindow, userId, itemId, itemName, quantity,totalPrice);
                }
                else
                    Toast.makeText(SubCategoryActivity.this, R.string.toast_exceeded_maximum_allowed_quantity, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onYourListButtonClick() {
        startActivity(ListToOrderActivity.getStartIntent(SubCategoryActivity.this));
    }


    @Override
    public void initPopubVies(View v) {
        etPopupAddToCart = (EditText) v.findViewById(R.id.et_quantity_add_to_cart);
        btnAddToCart = (LinearLayout) v.findViewById(R.id.layout_btn_add_to_cart);

    }

    @Override
    public void onAddToCartButtonClick(PopupWindow popupWindow, String userId, String itemId, String itemName, Integer quantity, Integer totalPrice) {
        // TODO change this to action you want
        String stringQuantity = quantity.toString();
        String stringPrice = totalPrice.toString();
        subCategoryPresenter.addItemToOrderList(itemId,itemName,stringQuantity,stringPrice);
        popupWindow.dismiss();
        tvListItemCounter.setText(subCategoryPresenter.getNumberOfListItemsOrder().toString());

    }

    @Override
    public void onButtonClickListner(View v, int groupPosition, int childPosition, String itemId,String itemName, Integer price) {
        openAddToCartPopup(v,itemId,itemName, price);
    }

    public static Intent getStartIntent(Context context, String categoryId) {
        Intent intent = new Intent(context, SubCategoryActivity.class);
        intent.putExtra(StaticValues.KEY_CATEGORY_ID,categoryId);
        return intent;
    }

}
