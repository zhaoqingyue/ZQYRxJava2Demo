package com.zqy.rxjava2demo.use.scene.ciba;

/**
 * Created by zhaoqy on 2018/5/30.
 */

public class Translation {
    /**
     * Json示例
     {
      "status":1,
      "content":{
        "from":"en-EU",
        "to":"zh-CN",
        "vendor":"baidu",
        "out":"你好世界,
        "errNo":0
       }
     }
     */

    public int status;      // 请求成功时返回1
    public content content; // 内容信息

    public static class content {
        public String from;   // 原文内容类型
        public String to;     // 译文内容类型
        public String vendor; // 来源平台
        public String out;    // 译文内容
        public int errNo;     // 请求成功时取0
    }

    //定义 输出返回数据 的方法
    public String show() {
        return ("第1次翻译= " + content.out);
    }
}
