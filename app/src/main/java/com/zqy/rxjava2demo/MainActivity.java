package com.zqy.rxjava2demo;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.BaseUseActivity;
import com.zqy.rxjava2demo.use.operator.OperatorActivity;
import com.zqy.rxjava2demo.use.scene.SceneActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.app_name);
    }

    @Override
    protected void initData() {
        /**
         * 定义：RxJava 是一个 基于事件流、实现异步操作的库
         * 作用：实现异步操作（类似于 Android中的 AsyncTask 、Handler作用）
         * 特点：链式调用、观察者模式、异步操作
         * 原理：基于 一种扩展的观察者模式
         * RxJava的扩展观察者模式中有4个角色：
         * 被观察者（Observable）---- 产生事件
         * 观察者（Observer）---- 接收事件，并给出响应动作
         * 订阅（Subscribe）---- 连接 被观察者 & 观察者
         * 事件（Event）---- 被观察者 & 观察者 沟通的载体
         */
        des.setText("1. 定义：RxJava 是一个 基于事件流、实现异步操作的库\n" +
                "2. 作用：实现异步操作（类似于 Android中的 AsyncTask 、Handler作用）\n" +
                "3. 特点：链式调用、观察者模式、异步操作\n" +
                "4. 原理：基于 一种扩展的观察者模式\n\n" +
                "RxJava的扩展观察者模式中有4个角色：\n" +
                "被观察者（Observable）---- 产生事件\n" +
                "观察者（Observer）---- 接收事件，并给出响应动作\n" +
                "订阅（Subscribe）---- 连接 被观察者 & 观察者\n" +
                "事件（Event）---- 被观察者 & 观察者 沟通的载体");
    }

    @Override
    protected boolean isHasNaviIcon() {
        return false;
    }

    @OnClick({R.id.btn_base, R.id.btn_operator, R.id.btn_use})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_base: {
                // 基本用法
                Intent intent = new Intent(this, BaseUseActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_operator: {
                // 操作符
                Intent intent = new Intent(this, OperatorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_use: {
                // 使用场景
                Intent intent = new Intent(this, SceneActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
