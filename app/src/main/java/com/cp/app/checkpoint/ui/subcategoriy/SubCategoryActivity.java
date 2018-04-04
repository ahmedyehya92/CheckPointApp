package com.cp.app.checkpoint.ui.subcategoriy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.cp.app.checkpoint.ui.listtoorder.ListToOrderActivity;
import com.cp.app.checkpoint.ui.main.MainActivity;
import com.cp.app.checkpoint.ui.timer.TimerActivity;
import com.cp.app.checkpoint.utils.StaticValues;

public class SubCategoryActivity extends BaseActivity implements SubCategoryMvpView,SubCategoriesExpandableListAdapter.customButtonListener,PopupAddToCart {


    private SubCategoriesExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;

    private EditText etPopupAddToCart, etNotes;
    private LinearLayout btnAddToCart;
    private Button btnYourList;
    private TextView tvListItemCounter, tvTitle;

    private Handler handler;
    private Runnable runnable;
    private Intent intent;
    private String categoryId;
    private String categoryName;
    private LinearLayout errorConnectionLayout;
    private ProgressBar mprogressBar;
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

        intent = getIntent();
        categoryId = intent.getStringExtra(StaticValues.KEY_CATEGORY_ID);
        categoryName = intent.getStringExtra(StaticValues.KEY_CATEGORY_NAME);
        tvTitle.setText(categoryName);

        btnYourList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onYourListButtonClick();
            }
        });
        subCategoryPresenter.reqSubCategories(categoryId);

        // TODO get intent that send from main activity to get id to can send request with id
/*
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

*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        tvListItemCounter.setText(subCategoryPresenter.getNumberOfListItemsOrder().toString());
        checkTimer();
    }

    @Override
    public void initViews() {
        expandableListView = findViewById(R.id.list_item_sub_category);
        btnYourList = findViewById(R.id.btn_your_list);
        tvListItemCounter = findViewById(R.id.tv_listItem_counter);


        tvTitle = findViewById(R.id.logo_menu);


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

                String notes = etNotes.getText().toString();
                if (notes.isEmpty())
                    notes = getString(R.string.no_notes);
                Integer quantity = Integer.parseInt(etContent);

                if (!(quantity>10) )
                {
                    Integer totalPrice = quantity * priceOfOnePiece;

                    onAddToCartButtonClick(popWindow, itemId, itemName, quantity,totalPrice,notes);
                }
                else
                    Toast.makeText(SubCategoryActivity.this, R.string.toast_exceeded_maximum_allowed_quantity, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onYourListButtonClick() {
        if (buttonHasCounter())
        {
            startActivity(TimerActivity.getStartIntent(SubCategoryActivity.this));
        }
        else
            startActivity(ListToOrderActivity.getStartIntent(SubCategoryActivity.this));

    }

    @Override
    public void setupSubCategoryList(ArrayList<SubCategoryGroupModel> list) {
        expandableListAdapter = new SubCategoriesExpandableListAdapter(SubCategoryActivity.this,list);
        expandableListAdapter.setCustomButtonListner(SubCategoryActivity.this);
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
    public void showErrorConnectionLayout() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    errorConnectionLayout.setVisibility(View.VISIBLE);
                }
            });


    }

    @Override
    public void showToast(final int msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SubCategoryActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void initPopubVies(View v) {
        etPopupAddToCart = v.findViewById(R.id.et_quantity_add_to_cart);
        btnAddToCart = v.findViewById(R.id.layout_btn_add_to_cart);
        etNotes = v.findViewById(R.id.et_notes);

    }

    @Override
    public void onAddToCartButtonClick(PopupWindow popupWindow, String itemId, String itemName, Integer quantity, Integer totalPrice, String notes) {
        // TODO change this to action you want
        String stringQuantity = quantity.toString();
        String stringPrice = totalPrice.toString();
        subCategoryPresenter.addItemToOrderList(itemId,itemName,stringQuantity,stringPrice,notes);
        popupWindow.dismiss();
        tvListItemCounter.setText(subCategoryPresenter.getNumberOfListItemsOrder().toString());

    }

    @Override
    public void onButtonClickListner(View v, int groupPosition, int childPosition, String itemId,String itemName, Integer price) {
        openAddToCartPopup(v,itemId,itemName, price);
    }

    public static Intent getStartIntent(Context context, String categoryId, String catName) {
        Intent intent = new Intent(context, SubCategoryActivity.class);
        intent.putExtra(StaticValues.KEY_CATEGORY_ID,categoryId);
        intent.putExtra(StaticValues.KEY_CATEGORY_NAME, catName);
        return intent;
    }

    public void checkTimer()
    {
        String date = subCategoryPresenter.getCounterDate();
        if (date != null)
        {
            countDownStart(date);
        }
        else btnYourList.setText(getString(R.string.btn_your_list));
    }
    public boolean buttonHasCounter()
    {
        String date = subCategoryPresenter.getCounterDate();
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

}
