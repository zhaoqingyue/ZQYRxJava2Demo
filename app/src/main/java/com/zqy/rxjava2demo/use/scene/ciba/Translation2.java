package com.zqy.rxjava2demo.use.scene.ciba;

/**
 * Created by zhaoqy on 2018/5/30.
 */

public class Translation2 {
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

    private int status;      // 请求成功时返回1
    private content content; // 内容信息

    private static class content {
        private String from;   // 原文内容类型
        private String to;     // 译文内容类型
        private String vendor; // 来源平台
        private String out;    // 译文内容
        private int errNo;     // 请求成功时取0
    }

    //定义 输出返回数据 的方法
    public String show() {
        return ("第2次翻译= " + content.out);
    }
}
