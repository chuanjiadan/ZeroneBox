package com.chris.box;

import android.os.Bundle;
import android.util.Log;

import com.chris.box.bean.galaxy.HotsaleBean;
import com.chris.box.bean.galaxy.NewsBean;
import com.chris.box.view.IMessageView;
import com.chris.box.presenter.NewsPresenter;

import java.util.List;

public class MainActivity
        extends BaseActivity<IMessageView, NewsPresenter<IMessageView>>
        implements IMessageView {


    private static final String TAG = "MainActivity sniper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBasePresenter.fetch();


    }

    @Override
    protected NewsPresenter<IMessageView> craetPresenter() {
        return new NewsPresenter<>();
    }


    @Override
    public void showMessageItem(List data) {
        Log.d(TAG, "showMessageItem: " + data.size());
        for (int i = 0; i < data.size(); i++) {
            Object o = data.get(i);

            if (o instanceof NewsBean.DataBean) {
                NewsBean.DataBean newsBean = (NewsBean.DataBean) o;
                Log.d(TAG, "newsBean: " + newsBean.toString());
            } else {
                HotsaleBean.DataBean hotsaleBean = (HotsaleBean.DataBean) o;
                Log.d(TAG, "hotsaleBean: " + hotsaleBean.toString());

            }
        }

    }

    @Override
    public void showLoadding() {

    }


}
