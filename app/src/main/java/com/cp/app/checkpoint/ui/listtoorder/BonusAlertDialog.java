package com.cp.app.checkpoint.ui.listtoorder;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.utils.StaticValues;

/**
 * Created by Ahmed Yehya on 19/02/2018.
 */

public class BonusAlertDialog extends DialogFragment {
    private TextView tvNewBonus, tvScore;
    private Button btnClose;
    private String newBonus, score;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_alert_bonus, container, false);
        tvNewBonus = rootView.findViewById(R.id.tv_new_bonus);
        tvScore = rootView.findViewById(R.id.tv_score);
        btnClose = rootView.findViewById(R.id.btn_close_alert);
        getDialog().setTitle("New Score");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("fragment","done");
        newBonus = getArguments().getString(StaticValues.NEW_BONUS_KEY);
        score = getArguments().getString(StaticValues.NEW_SCORE_KEY);
        tvNewBonus.setText(newBonus);
        tvScore.setText(score);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
