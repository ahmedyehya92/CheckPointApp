package com.cp.app.checkpoint.ui.languagechanging;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cp.app.checkpoint.MvpApp;
import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.ui.base.BaseActivity;
import com.cp.app.checkpoint.ui.main.MainActivity;

import java.util.Locale;

public class LanguageChangeActivity extends BaseActivity implements LanguageChangeMvpView {
     private Spinner mLanguageSpinner;
    private static Context context;

     private ArrayAdapter<String> languageSpinnerAdapter;
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LanguageChangeActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_change);
        initViews();

        String[] languageArray = {getString(R.string.chose_language),getString(R.string.language_english), getString(R.string.language_arabic)};
        languageSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.language_spinner_row, R.id.language, languageArray);
        languageSpinnerAdapter.setDropDownViewResource(R.layout.language_spinner_dropdown_row);

        setupSpinner();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_language :
                return true;
        }
        return true;
    }

    @Override
    public void initViews() {
        mLanguageSpinner = findViewById(R.id.spinner_languages);
    }


    private void setupSpinner() {
        mLanguageSpinner.setAdapter(languageSpinnerAdapter);
        mLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);

                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.chose_language)))
                    {

                    }
                    else if (selection.equals(getString(R.string.language_english))) {
                        setLocale("en");
                    } else if (selection.equals(getString(R.string.language_arabic))) {
                        setLocale("ar");
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLayoutDirection(myLocale);
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}
