package com.zqy.rxjava2demo.use.operator.create.stay;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class IntervalActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;
    @BindView(R.id.tv_content)
    TextView content;
    @BindView(R.id.iv_img)
    ImageView imageView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_base;
    }

    @Override
    protected String getTitleName() {
        return "interval操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 快速创建1个被观察者对象（Observable）
         * 发送事件的特点：每隔指定时间 就发送 事件
         */
        des.setText("作用：\n" +
                "1. 快速创建1个被观察者对象（Observable）\n" +
                "2. 发送事件的特点：每隔指定时间就发送事件");
        /**
         * 参数说明：
         * 参数1 = 第1次延迟时间；
         * 参数2 = 间隔时间数字；
         * 参数3 = 时间单位；
         */
        Observable.interval(3, 1, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：
                // 延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        LogUtils.d(TAG, "接收到了事件" + value);
                        content.setText("" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                        imageView.setImageResource(R.mipmap.interval);
                    }
                });
    }
}
