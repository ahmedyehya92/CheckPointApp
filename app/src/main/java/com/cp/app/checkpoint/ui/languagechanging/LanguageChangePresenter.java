package com.cp.app.checkpoint.ui.languagechanging;

import com.cp.app.checkpoint.data.DataManager;
import com.cp.app.checkpoint.ui.base.BasePresenter;

/**
 * Created by Ahmed Yehya on 23/12/2017.
 */

public class LanguageChangePresenter<V extends LanguageChangeMvpView> extends BasePresenter<V> implements LanguageChangeMvpPresenter<V> {
    public LanguageChangePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void setAppLanguage(String appLanguage) {
        getDataManager().setAppLanguage(appLanguage);
    }
}
