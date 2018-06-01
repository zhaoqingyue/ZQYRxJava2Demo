package com.zqy.rxjava2demo.use.operator.function;

import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class DoActivity extends BaseActivity {

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
        return "do操作符";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
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

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        })      // 1. 当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {

                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        LogUtils.d(TAG, "doOnEach: " + integerNotification.getValue());
                    }
                })
                // 2. 执行Next事件前调用
                .doOnNext(new Consumer<Integer>() {

                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.d(TAG, "doOnNext: " + integer);
                    }
                })
                // 3. 执行Next事件后调用
                .doAfterNext(new Consumer<Integer>() {

                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.d(TAG, "doAfterNext: " + integer);
                    }
                })
                // 4. Observable正常发送事件完毕后调用
                .doOnComplete(new Action() {

                    @Override
                    public void run() throws Exception {
                        LogUtils.e(TAG, "doOnComplete: ");
                    }
                })
                // 5. Observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {

                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.d(TAG, "doOnError: " + throwable.getMessage());
                    }
                })
                // 6. 观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {

                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        LogUtils.e(TAG, "doOnSubscribe: ");
                    }
                })
                // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
                .doAfterTerminate(new Action() {

                    @Override
                    public void run() throws Exception {
                        LogUtils.e(TAG, "doAfterTerminate: ");
                    }
                })
                // 8. 最后执行
                .doFinally(new Action() {

                    @Override
                    public void run() throws Exception {
                        LogUtils.e(TAG, "doFinally: ");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        LogUtils.d(TAG, "接收到了事件" + value  );
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
}
