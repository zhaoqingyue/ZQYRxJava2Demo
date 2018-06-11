package com.zqy.rxjava2demo;

import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.base.BaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class FlowableActivity extends BaseActivity {
    public static final String TAG = "ZQY";

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
         */

        /**
         * 定义：一种 控制事件流速 的策略。
         *
         * 作用：在 异步订阅关系 中，控制时间发送 & 接收的速度。
         * ----注意：背压的作用域 = 异步订阅关系，即 被观察者 & 观察者处在不同的线程中。
         *
         * 解决的问题：解决了因被观察者发送事件速度 与 观察者接收事件速度不匹配（一般是前者 > 后者），
         * 从而导致观察者无法及时响应 / 处理所有被观察者发送事件的问题。
         *
         * 应用场景：网络请求，比如有很多网络请求需要执行，但执行者的速度没那么快，此时就需要背压策略来进行控制。
         */

        des.setText("背景：观察者 & 被观察者 之间存在2种订阅关系：同步 & 异步。\n" +
                "----对于异步订阅关系，存在 被观察者发送事件速度 与 观察者接收事件速度 不匹配的情况：\n" +
                "A. 发送 & 接收事件速度 = 单位事件内 发送 & 接收事件的数量；\n" +
                "B. 大多数情况， 主要是 被观察者发送事件速度 > 观察者接收事件速度。\n\n" +

                "问题：被观察者 发送事件速度太快，而观察者 来不及接收所有事件，从而导致观察者无法及时响应 / " +
                "处理所有发送过来的事件的问题，最终导致缓存区溢出、事件丢失 & OOM。\n" +
                "A. 如点击按钮事件：连续过快的点击按钮10次，则只会造成点击2次的效果；\n" +
                "B. 解释：因为点击速度太快了，所以按钮来不及响应。\n" +
                "解决方案：采用背压策略。\n\n" +

                "定义：一种 控制事件流速 的策略。\n" +
                "作用：在 异步订阅关系 中，控制时间发送 & 接收的速度。\n" +
                "----注意：背压的作用域 = 异步订阅关系，即 被观察者 & 观察者处在不同的线程中。\n" +
                "解决的问题：解决了因被观察者发送事件速度 与 观察者接收事件速度不匹配（一般是前者 > 后者），" +
                "从而导致观察者无法及时响应 / 处理所有被观察者发送事件的问题。\n" +
                "应用场景：网络请求，比如有很多网络请求需要执行，但执行者的速度没那么快，" +
                "此时就需要背压策略来进行控制。");

//        /**
//         * 步骤1：创建被观察者 =  Flowable
//         */
//        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
//
//            @Override
//            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onComplete();
//            }
//        }, BackpressureStrategy.ERROR);
//        // 需要传入背压参数BackpressureStrategy，下面会详细讲解
//
//        /**
//         * 步骤2：创建观察者 =  Subscriber
//         */
//        Subscriber<Integer> downstream = new Subscriber<Integer>() {
//
//            @Override
//            public void onSubscribe(Subscription s) {
//                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
//                // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
//                // 不同点：Subscription增加了void request(long n)
//                LogUtils.d(TAG, "onSubscribe");
//                s.request(Long.MAX_VALUE);
//                // 关于request()下面会继续详细说明
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                LogUtils.d(TAG, "onNext: " + integer);
//                content.setText(content.getText() + "\n" + "接收到了事件: " + integer);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                LogUtils.w(TAG, "onError: ", t);
//            }
//
//            @Override
//            public void onComplete() {
//                LogUtils.d(TAG, "onComplete");
//            }
//        };
//
//        /**
//         * 步骤3：建立订阅关系
//         */
//        upstream.subscribe(downstream);

        // 更加优雅的链式调用

        // 步骤1：创建被观察者 =  Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    // 步骤2：创建观察者 =  Subscriber & 建立订阅关系

                    @Override
                    public void onSubscribe(Subscription s) {
                        LogUtils.d(TAG, "onSubscribe");
                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtils.d(TAG, "接收到了事件: " + integer);
                        content.setText(content.getText() + "\n" + "接收到了事件: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "onComplete");
                    }
                });

    }
}
