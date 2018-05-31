package com.zqy.rxjava2demo.use.scene;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 功能防抖
 */
public class ShakeActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @BindView(R.id.button)
    Button button;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_shake;
    }

    @Override
    protected String getTitleName() {
        return "功能防抖";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 背景--用户只需要使用功能1次
         * 2. 冲突--由于外部原因，多次触发了功能，导致出现冗余功能操作
         * 3. 实例--用户只需要使用网络请求功能1次（点击按钮），但是由于网络不好，点击1次后用户发现无响应，
         * 于是多次点击按钮，导致多次发送网络请求
         * 4. 解决方案--功能防抖
         * 5. 原理--通过根据指定时间过滤事件的过滤操作符实现，防止功能的抖动
         */
        des.setText("需求场景：\n" +
                "1. 背景--用户只需要使用功能1次\n" +
                "2. 冲突--由于外部原因，多次触发了功能，导致出现冗余功能操作\n" +
                "3. 实例--用户只需要使用网络请求功能1次（点击按钮），但是由于网络不好，点击1次后用户发现无响应，" +
                "于是多次点击按钮，导致多次发送网络请求\n" +
                "4. 解决方案--功能防抖\n" +
                "5. 原理--通过根据指定时间过滤事件的过滤操作符实现，防止功能的抖动");

        /**
         * 功能说明：
         * 1. 功能防抖--用户在规定的时间内多次触发该功能，仅会响应第1次触发操作
         * 2. 原理--使用根据指定时间过滤事件的过滤操作符实现
         * 3. throttleFirst：在规定的时间内，仅会响应第1次操作
         */
        instruction.setText("功能说明：\n" +
                "1. 功能防抖--用户在规定的时间内多次触发该功能，仅会响应第1次触发操作\n" +
                "2. 原理--使用根据指定时间过滤事件的过滤操作符实现\n" +
                "3. throttleFirst：在规定的时间内，仅会响应第1次操作");

         /**
         * 1. 此处采用了RxBinding：RxView.clicks(button) = 对控件点击进行监听，
          * 需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入Button控件，点击时，都会发送数据事件（但由于使用了throttleFirst（）操作符，
          * 所以只会发送该段时间内的第1次点击事件）
         */
        RxView.clicks(button)
                // 只发送 2s内第1次点击按钮的事件
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Object value) {
                        // 在2s内，无论点击多少次，也只会发送1次网络请求
                        LogUtils.d(TAG, "发送了网络请求" );
                        Toast.makeText(ShakeActivity.this, "开始发送网络请求", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应" + e.toString());
                        // 获取异常错误信息
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
