package com.cp.app.checkpoint.ui.timer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerActivity extends BaseActivity implements TimerMvpView {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    TextView tvMinutes,tvSeconds;
    private Handler handler;
    private Runnable runnable;

    TimerPresenter timerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        initViews();
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        timerPresenter = new TimerPresenter(dataManager);
        timerPresenter.onAttach(this);

        setTimerView();


    }

    public void setTimerView()
    {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        String date = timerPresenter.getCounterDate();
        if (date != null)
        {
            countDownStart(date);
        }
    }

    @Override
    public void initViews() {
        tvMinutes = findViewById(R.id.tv_minutes);
        tvSeconds = findViewById(R.id.tv_seconds);
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


                        tvMinutes.setText(""
                                + String.format("%02d", minutes));
                        tvSeconds.setText(""
                                + String.format("%02d", seconds));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, TimerActivity.class);
        return intent;
    }

}
