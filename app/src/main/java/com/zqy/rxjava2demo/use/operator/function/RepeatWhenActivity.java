package com.zqy.rxjava2demo.use.operator.function;

import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class RepeatWhenActivity extends BaseActivity {

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
        return "repeatWhen操作符";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 重复不断地发送被观察者事件
         *
         * 作用：
         * 有条件地、重复发送 被观察者事件
         *
         * 原理：
         * 1. 将原始 Observable 停止发送事件的标识（Complete（） / Error（））转换成1个
         *  Object 类型数据传递给1个新被观察者（Observable），以此决定是否重新订阅 & 发送原来的 Observable
         * A. 若新被观察者（Observable）返回1个Complete / Error事件，则不重新订阅 & 发送原来的 Observable
         * B. 若新被观察者（Observable）返回其余事件时，则重新订阅 & 发送原来的 Observable
         */
        des.setText("需求场景：\n" +
                "重复不断地发送被观察者事件\n" +
                "作用：\n" +
                "有条件地、重复发送 被观察者事件\n" +
                "原理：\n" +
                "将原始 Observable 停止发送事件的标识（Complete（） / Error（））" +
                "转换成1个Object 类型数据传递给1个新被观察者（Observable），" +
                "以此决定是否重新订阅 & 发送原来的 Observable：\n" +
                "A. 若新被观察者（Observable）返回1个Complete / Error事件，则不重新订阅 & 发送原来的 Observable\n" +
                "B. 若新被观察者（Observable）返回其余事件时，则重新订阅 & 发送原来的 Observable");

        Observable.just(1, 2, 4).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {

            // 在Function函数中，必须对输入的 Observable<Object>进行处理，这里使用flatMap操作符接收上游的数据
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））
                // 转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable
                // 此处有2种情况：
                // 1. 若新被观察者（Observable）返回1个Complete（） /  Error（）事件，
                // 则不重新订阅 & 发送原来的 Observable
                // 2. 若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {
                        // 情况1：若新被观察者（Observable）返回1个Complete（） /  Error（）事件，
                        // 则不重新订阅 & 发送原来的 Observable
                        return Observable.empty();
                        // Observable.empty() = 发送Complete事件，但不会回调观察者的onComplete（）

                        // return Observable.error(new Throwable("不再重新订阅事件"));
                        // 返回Error事件 = 回调onError（）事件，并接收传过去的错误信息。

                        // 情况2：若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                        // return Observable.just(1);
                        // 仅仅是作为1个触发重新订阅被观察者的通知，发送的是什么数据并不重要，
                        // 只要不是Complete（） /  Error（）事件
                    }
                });
            }
        }).subscribe(new Observer<Integer>() {

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
                LogUtils.d(TAG, "对Error事件作出响应：" + e.toString());
            }

            @Override
            public void onComplete() {
                LogUtils.d(TAG, "对Complete事件作出响应");
            }
        });
    }
}
