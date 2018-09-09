package com.chris.box.presenter;

import android.util.Log;

import com.chris.box.view.IMessageView;
import com.chris.box.model.MessageModle;
import com.chris.box.model.NewsModel;

import java.util.List;


/**
 * 表示层
 */
public class NewsPresenter<T extends IMessageView> extends BasePresenter<T>  {


    private static final String TAG = "NewsPresenter";
    /**
     * view层
     */
    IMessageView mIMessageView;
    /**
     * modle层
     */
    NewsModel mNewsModel = new NewsModel();


    /**
     * 执行UI操作
     */
    public void fetch() {


        if (mWeakReference.get() != null) {
            if (mNewsModel != null) {
                mNewsModel.loadMessage(new MessageModle.MessageLoadListener() {
                    @Override
                    public void onComplete(List data) {
                        Log.d(TAG, "onComplete: " + data.size());
                        mWeakReference.get().showMessageItem(data);
                    }
                });
            }

        } else {
            Log.d(TAG, "fetch: mWeakReference=null");
        }
    }


}
