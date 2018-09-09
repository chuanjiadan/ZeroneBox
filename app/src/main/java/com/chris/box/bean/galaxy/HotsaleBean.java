package com.chris.box.bean.galaxy;

import java.util.List;

public class HotsaleBean {

    public static String YIELDVALUE = "yieldvalue";
    public static String YIELDTITLE = "yieldtitle";
    public static String CONTENT = "content";
    public static String TERM = "term";
    public static String AMOUNT = "amount";
    public static String TAGS = "tags";
    public static String TITLE = "title";
    public static String TYPE = "type";
    public static String DATA = "data";
    public static final String BEAN_TYPE = "hotsale";


    /**
     * title : 银河优选
     * data : [{"yieldvalue":"4.93%","yieldtitle":"预期年化收益率","content":"江苏聚宝财富聚溢融B6客户Y21(定制)APP","term":"187天","amount":"5万元起购","tags":"中低风险|收益稳定"},{"yieldvalue":"4.18%~6.00%","yieldtitle":"七日年化收益率","content":"银河金山收益凭证2465期(首次购买专享)","term":"35天","amount":"1万元起购","tags":"低风险"},{"yieldvalue":"4.93%","yieldtitle":"预期年化收益率","content":"江苏聚宝财富聚溢融B6客户Y21(定制)APP","term":"187天","amount":"5万元起购","tags":"中低风险|收益稳定"},{"yieldvalue":"4.93%","yieldtitle":"预期年化收益率","content":"江苏聚宝财富聚溢融B6客户Y21(定制)APP","term":"187天","amount":"5万元起购","tags":"中低风险|收益稳定"}]
     * type : hotsale
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "yieldvalue='" + yieldvalue + '\'' +
                    ", yieldtitle='" + yieldtitle + '\'' +
                    ", content='" + content + '\'' +
                    ", term='" + term + '\'' +
                    ", amount='" + amount + '\'' +
                    ", tags='" + tags + '\'' +
                    ", pyte='" + pyte + '\'' +
                    '}';
        }

        /**
         * yieldvalue : 4.93%
         * yieldtitle : 预期年化收益率
         * content : 江苏聚宝财富聚溢融B6客户Y21(定制)APP
         * term : 187天
         * amount : 5万元起购
         * tags : 中低风险|收益稳定
         */

        private String yieldvalue;
        private String yieldtitle;
        private String content;
        private String term;
        private String amount;
        private String tags;
        private String pyte;

        public String getPyte() {
            return pyte;
        }

        public void setPyte(String pyte) {
            this.pyte = pyte;
        }

        public String getYieldvalue() {
            return yieldvalue;
        }

        public void setYieldvalue(String yieldvalue) {
            this.yieldvalue = yieldvalue;
        }

        public String getYieldtitle() {
            return yieldtitle;
        }

        public void setYieldtitle(String yieldtitle) {
            this.yieldtitle = yieldtitle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }
    }


}
