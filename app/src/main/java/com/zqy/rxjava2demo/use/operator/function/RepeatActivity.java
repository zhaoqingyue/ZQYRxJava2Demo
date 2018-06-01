package com.zqy.rxjava2demo.use.operator.function;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RepeatActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;
    @BindView(R.id.tv_content)
    TextView content;
    @BindView(R.id.btn_extra)
    Button extra;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_base;
    }

    @Override
    protected String getTitleName() {
        return "repeat操作符";
    }

    @Override
    protected void initData() {
        extra.setVisibility(View.VISIBLE);
        extra.setText("repeatWhen");
        /**
         * 需求场景：
         * 重复不断地发送被观察者事件
         *
         * 作用：
         * 无条件地、重复发送 被观察者事件
         */
        des.setText("需求场景：\n" +
                "重复不断地发送被观察者事件\n" +
                "作用：\n" +
                "无条件地、重复发送 被观察者事件");

        // 不传入参数 = 重复发送次数 = 无限次
        // repeat（）；
        // 传入参数 = 重复发送次数有限
        // repeatWhen（Integer int）；

        // 注：
        // 1. 接收到.onCompleted()事件后，触发重新订阅 & 发送
        // 2. 默认运行在一个新的线程上
        Observable.just(1, 2)
                .repeat(3) // 重复创建次数 = 3次
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        LogUtils.d(TAG, "接收到了事件" + value);
                        content.setText(content.getText() + "\n" + "接收到了事件: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    @OnClick({R.id.btn_extra})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_extra: {
                Intent intent = new Intent(this, RepeatWhenActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
