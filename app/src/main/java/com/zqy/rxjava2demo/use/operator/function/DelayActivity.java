package com.zqy.rxjava2demo.use.operator.function;

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

public class DelayActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;
    @BindView(R.id.tv_content)
    TextView content;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_base;
    }

    @Override
    protected String getTitleName() {
        return "delay操作符";
    }

    Disposable disposable;

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 在被观察者发送事件前进行一些延迟的操作
         *
         * 作用：
         * 使得被观察者延迟一段时间再发送事件
         */
        des.setText("需求场景：\n" +
                "在被观察者发送事件前进行一些延迟的操作\n" +
                "作用：\n" +
                "使得被观察者延迟一段时间再发送事件");

        // 1. 指定延迟时间
        // 参数1 = 时间；参数2 = 时间单位
        // delay(long delay, TimeUnit unit)

        // 2. 指定延迟时间 & 调度器
        // 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器
        // delay(long delay, TimeUnit unit, Scheduler scheduler)

        // 3. 指定延迟时间  & 错误延迟
        // 错误延迟，即：若存在Error事件，则如常执行，执行后再抛出错误异常
        // 参数1 = 时间；参数2 = 时间单位；参数3 = 错误延迟参数
        // delay(long delay, TimeUnit unit, boolean delayError)

        // 4. 指定延迟时间 & 调度器 & 错误延迟
        // 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器；参数4 = 错误延迟参数
        // delay(long delay, TimeUnit unit, Scheduler scheduler, boolean delayError)
        // 指定延迟多长时间并添加调度器，错误通知可以设置是否延迟

        Observable.just(1, 2, 3)
                .delay(3, TimeUnit.SECONDS) // 延迟3s再发送
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                        disposable = d;
                    }

                    @Override
                    public void onNext(Integer value) {
                        LogUtils.d(TAG, "接收到了事件: " + value);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}
