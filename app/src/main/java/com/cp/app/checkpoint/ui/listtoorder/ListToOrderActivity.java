package com.cp.app.checkpoint.ui.listtoorder;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.data.adapters.ListToOrderAdabter;
import com.cp.app.checkpoint.data.dphelper.ItemContract;
import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;
import com.cp.app.checkpoint.services.OnAlarmReceiver;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.registration.RegistrationActivity;
import com.cp.app.checkpoint.ui.timer.TimerActivity;
import com.cp.app.checkpoint.utils.StaticValues;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.cp.app.checkpoint.data.dphelper.ItemContract.*;

public class ListToOrderActivity extends BaseActivity implements ListToOrderMvpView, ListToOrderAdabter.customButtonListener {
    private ListView listToOrderListView;
    private TextView tvTotalPrice;
    private Button btnOrder;
    private ArrayList<ListOfOneOrderModel> itemsList;
    private ListToOrderAdabter arrayAdapter;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int seconds;

    ListToOrderPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_to_order);
        initViewsActivity();

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new ListToOrderPresenter(dataManager);
        presenter.onAttach(this);
        itemsList = new ArrayList<>();
        itemsList = presenter.getOrderList();
        arrayAdapter = new ListToOrderAdabter(ListToOrderActivity.this,itemsList);
        arrayAdapter.setCustomButtonListner(ListToOrderActivity.this);
        listToOrderListView.setAdapter(arrayAdapter);
        updateTotalPrice();
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOrderButtonClick();
            }
        });
    }

    public void updateTotalPrice()
    {
        Integer totalPrice = 0;
        int arrayLength = itemsList.size();
        for (int i = 0; i<arrayLength;i++)
        {
            final ListOfOneOrderModel item = itemsList.get(i);
            totalPrice+=item.getPriceForDesiredQuantity();
        }

        tvTotalPrice.setText(totalPrice.toString()+" "+getString(R.string.unit_egypt));
    }

    @Override
    public void initViewsActivity() {
        listToOrderListView = findViewById(R.id.list_item_to_order);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnOrder = findViewById(R.id.btn_order);
    }

    @Override
    public void onOrderButtonClick() {
        presenter.sendOrder();
        // setAlarm();
        // showNewBonusAlert("5","20");
    }

    @Override
    public void setAlarm() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        seconds = c.get(Calendar.SECOND);


        Intent i = new Intent(this, OnAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
                PendingIntent.FLAG_ONE_SHOT);
        Calendar calendar = Calendar.getInstance();

        int minutePlus = 15;
        int newMinutes = minute+minutePlus;
        presenter.setCounterDate(new StringBuilder().append(year).append("-").append(month+1).append("-")
                .append(day).append(" ").append(hour).append(":").append(newMinutes).append(":").append(seconds).toString());

        calendar.set(Calendar.MINUTE,newMinutes);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (minutePlus*60 * 1000), pi);
        // startActivity(TimerActivity.getStartIntent(this));
    }

    @Override
    public void showNewBonusAlert(String newBonus, String score) {
        final String bonus = newBonus;
        final String newScore = score;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getFragmentManager();
                BonusAlertDialog bonusAlertDialog = new BonusAlertDialog();
                Bundle args = new Bundle();
                args.putString(StaticValues.NEW_BONUS_KEY, bonus);
                args.putString(StaticValues.NEW_SCORE_KEY, newScore);
                bonusAlertDialog.setArguments(args);
                bonusAlertDialog.show(fm,"Bonus Alert Dialog");
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
    public void showToast(int message) {
        final String mesg = getString(message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ListToOrderActivity.this, mesg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRemoveButtonClickListner(String dbId,View buttonView, int position) {
        removeListItem(getViewByPosition(position,listToOrderListView),position);

        presenter.deleteItemFromOrderList(buildContentUri(dbId));


    }
    public Uri buildContentUri (String dbId){
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ITEMS+"/"+dbId);
        return CONTENT_URI;
    }
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ListToOrderActivity.class);
        return intent;
    }

    protected void removeListItem(View rowView, final int positon) {
        final Animation animation = AnimationUtils.loadAnimation(ListToOrderActivity.this, android.R.anim.slide_out_right);
        rowView.startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                itemsList.remove(positon);
                updateTotalPrice();
                arrayAdapter.notifyDataSetChanged();
                animation.cancel();


            }
        }, 100);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


}
