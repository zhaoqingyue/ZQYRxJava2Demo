package com.zqy.rxjava2demo.use.operator.create.delay;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TimerActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;
    @BindView(R.id.tv_content)
    TextView content;
    @BindView(R.id.iv_img)
    ImageView imageView;

    int delay = 3;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_base;
    }

    @Override
    protected String getTitleName() {
        return "timer操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 快速创建1个被观察者对象（Observable）
         * 发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）
         */
        des.setText("作用：\n" +
                "1. 快速创建1个被观察者对象（Observable）\n" +
                "2. 发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）");

        content.setText("正在" + delay + "秒倒计时...");
        Observable.timer(delay, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        LogUtils.d(TAG, "接收到了事件" + value);
                        Toast.makeText(getApplicationContext(), "倒计时结束", Toast.LENGTH_SHORT).show();
                        content.setText("倒计时结束");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                        imageView.setImageResource(R.mipmap.timer);
                    }
                });
    }
}
