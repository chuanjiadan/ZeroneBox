package com.chris.box.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chris.box.R;
import com.chris.box.bean.galaxy.HotsaleBean;
import com.chris.box.bean.galaxy.NewsBean;

import java.util.List;

public class ListviewAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "ListviewAdapter chris";
    private final int NEWS_TYPE = 0;
    private final int HOT_TYPE = 1;

    private onPageViewClickListner mOnPageViewClickListner;

    private Context mContext;
    private List mlist;


    public ListviewAdapter(Context mContext, List mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        if (mlist.isEmpty()) {
            return 0;
        }

        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        Object o = mlist.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case HOT_TYPE:
                HotsaleBean.DataBean hot = (HotsaleBean.DataBean) o;
                view = LayoutInflater.from(mContext).inflate(R.layout.news_view, parent, false);
                TextView view1Tv1 = (TextView) view.findViewById(R.id.textView);
                TextView view1Tv2 = (TextView) view.findViewById(R.id.textView2);
                TextView view1Tv3 = (TextView) view.findViewById(R.id.textView4);
                TextView view1Tv4 = (TextView) view.findViewById(R.id.textView5);
                TextView view1Tv5 = (TextView) view.findViewById(R.id.textView6);
                LinearLayout titleNews = (LinearLayout) view.findViewById(R.id.title);
                view1Tv1.setText(hot.getYieldvalue());
                view1Tv2.setText(hot.getYieldtitle());
                view1Tv3.setText(hot.getContent());
                if (position == getTypePosition(HOT_TYPE)) {
                    titleNews.setVisibility(View.VISIBLE);
                } else {
                    titleNews.setVisibility(View.GONE);

                }
                view1Tv4.setText(hot.getTerm() + "  " + hot.getAmount());
                view1Tv5.setText(hot.getTags());
                view.setOnClickListener(this);
                break;
            case NEWS_TYPE:
                view = LayoutInflater.from(mContext).inflate(R.layout.hot_view, parent, false);
                view.setOnClickListener(this);

                View view_yy = view.findViewById(R.id.title);
                if (position == getTypePosition(NEWS_TYPE)) {
                    view_yy.setVisibility(View.VISIBLE);
                } else {
                    view_yy.setVisibility(View.GONE);

                }
                TextView title = (TextView) view.findViewById(R.id.textView3);
                TextView time = (TextView) view.findViewById(R.id.textView7);
                ImageView pic = (ImageView) view.findViewById(R.id.imageView);

                NewsBean.DataBean newsbean = (NewsBean.DataBean) o;
                title.setText(newsbean.getTitle());
                time.setText(newsbean.getDate());
                Glide.with(mContext).load(newsbean.getUrl())
                        .into(pic);
                break;

        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (mlist.get(position) instanceof NewsBean.DataBean) {
            return NEWS_TYPE;
        }
        return HOT_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private int getTypePosition(int type) {
        int postion = 0;
        for (int i = 0; i < mlist.size(); i++) {
            Object o = mlist.get(i);
            if (o instanceof NewsBean.DataBean && type == NEWS_TYPE) {
                postion = i;
                break;

            } else if (o instanceof HotsaleBean.DataBean && type == HOT_TYPE) {
                postion = i;
                break;
            }
        }
        Log.d(TAG, "getTypePosition: " + postion);

        return postion;
    }

    @Override
    public void onClick(View v) {
        mOnPageViewClickListner.onPageClik();
    }

    public ListviewAdapter setOnPageViewClickListner(onPageViewClickListner onPageViewClickListner) {
        this.mOnPageViewClickListner = onPageViewClickListner;
        return this;
    }

}
