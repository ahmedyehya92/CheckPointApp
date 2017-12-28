package com.cp.app.checkpoint.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cp.app.checkpoint.R;
import com.cp.app.checkpoint.ui.languagechanging.LanguageChangeActivity;
import com.cp.app.checkpoint.ui.oldorders.OldOrderActivity;

/**
 * Created by Ahmed Yehya on 12/12/2017.
 */

public class BaseActivity extends AppCompatActivity implements MvpView {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_action_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_language :
                startActivity(LanguageChangeActivity.getStartIntent(getApplicationContext()));
              return true;
            case R.id.item_old_order :
                startActivity(OldOrderActivity.getStartIntent(getApplicationContext()));
                return true;
        }
        return true;
    }
}
