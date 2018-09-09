package com.chris.box.bean.galaxy;

import java.util.List;

public class NewsBean {

    public static final String TITLE_MSG = "title";
    public static final String DATE = "date";
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String DATA = "data";
    public static final String BEAN_TYPE = "news";


    /**
     * title : 银河资讯 视屏
     * data : [{"title":"消息称百度内部已经启动CDR准备 最快下个月回归A股","date":"20180613 20:27:39","url":"https://cdns.chinastock.com.cn/cdn/jlcms/pic/zxpt_v1_wjgg_s.jpg"},{"title":"证监会：紧紧抓住当前的历史性机遇 实施资本市场大数据战略","date":"20180613 18:59:39","url":"https://cdns.chinastock.com.cn/cdn/jlcms/pic/JLCMS_DJ_HYYB.png"},{"title":"中央采购的台式机和笔记本都要求预装国产LINUX操作系统","date":"20180610 20:59:39","url":"https://cdns.chinastock.com.cn/cdn/jlcms/pic/JLCMS_REMEN.png"},{"title":"消息称百度内部已经启动CDR准备 最快下个月回归A股","date":"20180610 20:59:39","url":"https://cdns.chinastock.com.cn/cdn/jlcms/pic/zxpt_v1_wjgg_s.jpg"}]
     * type : news
     */

    private String title;
    private String type;
    private List<DataBean> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 消息称百度内部已经启动CDR准备 最快下个月回归A股
         * date : 20180613 20:27:39
         * url : https://cdns.chinastock.com.cn/cdn/jlcms/pic/zxpt_v1_wjgg_s.jpg
         */

        private String title;
        private String date;
        private String url;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", date='" + date + '\'' +
                    ", url='" + url + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }


}
