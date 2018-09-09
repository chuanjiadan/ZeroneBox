package com.chris.box;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chris.box.presenter.BasePresenter;

public abstract class BaseActivity<V, T extends BasePresenter<V>>
        extends Activity {

    /**
     * 表示层引用
     */

    public T mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasePresenter = craetPresenter();
        mBasePresenter.attachView((V) this);
    }

    protected abstract T craetPresenter();


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePresenter.detachView();
    }


}
