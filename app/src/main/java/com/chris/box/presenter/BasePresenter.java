package com.chris.box.presenter;

import java.lang.ref.WeakReference;

public class BasePresenter<T> {

    /**
     * view层的引用
     */
    protected WeakReference<T> mWeakReference;


    //绑定
    public void attachView(T view) {

        mWeakReference = new WeakReference<>(view);

    }

    //解绑
    public void detachView() {
        mWeakReference.clear();

    }


}
