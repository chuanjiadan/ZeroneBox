package com.chris.box.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.chris.box.bean.galaxy.HotsaleBean;
import com.chris.box.bean.galaxy.NewsBean;
import com.galaxy.model.MainModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsModel implements MessageModle {


    private static final String TAG = "NewsModel ";
    private List mList;

    @Override
    public void loadMessage(MessageLoadListener mMessageLoadListener) {
        MainModel mainModel = new MainModel();
        mList = new ArrayList<>();
        mList.clear();

        String s = mainModel.queryData();
        Log.d(TAG, "getMainData: " + s);
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String type = jsonObject.getString(NewsBean.TYPE);
                Log.d(TAG, "getMainData: " + jsonObject.toString());
                if (type.equals(NewsBean.BEAN_TYPE)) {
                    getNewsBeans(jsonObject);

                } else if (type.equals(HotsaleBean.BEAN_TYPE)) {
                    getHotBeans(jsonObject);
                }

            }

            Log.e(TAG, "hotsaleBeans ***: " + mList.size());


        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "err: " + e.getMessage());
        }

        mMessageLoadListener.onComplete(mList);
    }


    @NonNull
    private ArrayList<HotsaleBean.DataBean> getHotBeans(JSONObject hot) throws JSONException {
        HotsaleBean hotsaleBean = new HotsaleBean();
        hotsaleBean.setTitle(hot.getString(HotsaleBean.TITLE));
        hotsaleBean.setType(hot.getString(HotsaleBean.TYPE));
        JSONArray hotdata = (JSONArray) hot.get(HotsaleBean.DATA);
        ArrayList<HotsaleBean.DataBean> dataBeans = new ArrayList<>();
        Log.d(TAG, "hot dataBeans legth : " + hotdata.length());
        for (int i = 0; i < hotdata.length(); i++) {
            JSONObject o = (JSONObject) hotdata.get(i);
            HotsaleBean.DataBean dataBean = new HotsaleBean.DataBean();
            dataBean.setAmount(o.getString(HotsaleBean.AMOUNT));
            dataBean.setContent(o.getString(HotsaleBean.CONTENT));
            dataBean.setTags(o.getString(HotsaleBean.TAGS));
            dataBean.setTerm(o.getString(HotsaleBean.TERM));
            dataBean.setYieldtitle(o.getString(HotsaleBean.YIELDTITLE));
            dataBean.setYieldvalue(o.getString(HotsaleBean.YIELDVALUE));
            mList.add(dataBean);
        }
        return dataBeans;
    }

    @NonNull
    private ArrayList<NewsBean.DataBean> getNewsBeans(JSONObject jsonObject) throws JSONException {
        NewsBean newsBean = new NewsBean();
        newsBean.setType(jsonObject.getString(NewsBean.TYPE));
        newsBean.setTitle(jsonObject.getString(NewsBean.TITLE));
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(NewsBean.DATA);
        Log.e(TAG, "news jsonArray-----: " + jsonArray2.length());
        ArrayList<NewsBean.DataBean> data = new ArrayList<>();
//

        for (int i = 0; i < jsonArray2.length(); i++) {
            JSONObject o = (JSONObject) jsonArray2.get(i);
            NewsBean.DataBean dataBean = new NewsBean.DataBean();

            dataBean.setDate(o.getString(NewsBean.DATE));
            dataBean.setTitle(o.getString(NewsBean.TITLE_MSG));
            dataBean.setUrl(o.getString(NewsBean.URL));
            mList.add(dataBean);
        }
        return data;
    }
}
