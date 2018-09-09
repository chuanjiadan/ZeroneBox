package com.chris.box.model;

import java.util.List;

/**
 * 加载数据
 */
public interface MessageModle {

    void loadMessage(MessageLoadListener mMessageLoadListener);

    //内部回调接口
    interface MessageLoadListener {
        void onComplete(List data);
    }


}
