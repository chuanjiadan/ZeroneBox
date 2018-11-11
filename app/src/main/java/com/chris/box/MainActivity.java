package com.chris.box;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chris.box.bean.galaxy.HotsaleBean;
import com.chris.box.bean.galaxy.NewsBean;
import com.chris.box.presenter.NewsPresenter;
import com.chris.box.utiles.PluginManager;
import com.chris.box.view.IMessageView;

import java.util.List;

public class MainActivity
        extends BaseActivity<IMessageView, NewsPresenter<IMessageView>>
        implements IMessageView {


    private static final String TAG = "MainActivity sniper";
    static final String ACTION = "com.lanhuzi.lode.dowlodreceive.action";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBasePresenter.fetch();
        registerReceiver(receiver, new IntentFilter(ACTION));


    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "onReceive: 主app接收到插件的广播");

        }
    };

    public void start(View view) {
        PluginManager.getInstance().load(this);

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
