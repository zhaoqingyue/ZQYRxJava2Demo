package com.zqy.rxjava2demo;

import android.widget.TextView;

import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;

public class FlowableActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;
    @BindView(R.id.tv_content)
    TextView content;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_flowable;
    }

    @Override
    protected String getTitleName() {
        return "Flowable";
    }

    @Override
    protected void initData() {
        /**
         * 背景：观察者 & 被观察者 之间存在2种订阅关系：同步 & 异步。
         * ----对于异步订阅关系，存在 被观察者发送事件速度 与 观察者接收事件速度 不匹配的情况：
         * A. 发送 & 接收事件速度 = 单位事件内 发送 & 接收事件的数量；
         * B. 大多数情况， 主要是 被观察者发送事件速度 > 观察者接收事件速度。
         *
         * 问题：被观察者 发送事件速度太快，而观察者 来不及接收所有事件，
         * 从而导致观察者无法及时响应 / 处理所有发送过来的事件的问题，最终导致缓存区溢出、事件丢失 & OOM。
         * A. 如点击按钮事件：连续过快的点击按钮10次，则只会造成点击2次的效果；
         * B. 解释：因为点击速度太快了，所以按钮来不及响应。
         *
         * 解决方案：采用背压策略。
         *
         * 在事件发送 & 接收的整个生命周期过程中进行操作
         * 如：发送事件前的初始化、发送事件后的回调请求等
         *
         * 作用：
         * 在某个事件的生命周期中调用
         */
        des.setText("需求场景：\n" +
                "在事件发送 & 接收的整个生命周期过程中进行操作\n" +
                "如：发送事件前的初始化、发送事件后的回调请求等\n" +
                "作用：\n" +
                "在某个事件的生命周期中调用");

    }
}
