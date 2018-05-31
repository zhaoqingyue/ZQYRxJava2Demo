package com.zqy.rxjava2demo.use.scene;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.scene.ciba.GetRequest;
import com.zqy.rxjava2demo.use.scene.ciba.Translation;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求出错重连
 */
public class ReconnectionActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @BindView(R.id.tv_result)
    TextView tv_result;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_reconnection;
    }

    @Override
    protected String getTitleName() {
        return "网络请求出错重连";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 背景--发送网络请求
         * 2. 冲突--发送网络请求过程中，会出现某些错误，从而导致该次网络请求不成功
         * 3. 解决方案--当发生错误使得网络请求不成功时，自动重新发送网络请求，即差错自动重试机制
         */
        des.setText("需求场景：\n" +
                "1. 背景--发送网络请求\n" +
                "2. 冲突--发送网络请求过程中，会出现某些错误，从而导致该次网络请求不成功\n" +
                "3. 解决方案--当发生错误使得网络请求不成功时，自动重新发送网络请求，即差错自动重试机制");

        /**
         * 功能说明：
         * 1. 功能描述----当发生错误使得网络请求不成功时，自动重新发送网络请求
         * 2. 实现原理----采用RxJava中retryWhen()操作符
         *
         * 具体说明：
         * 1. 根据出错的错误类型，判断是否要重试（所有网络错误异常都属于I/O异常 = IOException）
         * 2. 若要重试，设置退避策略，即为请求重试设置1个合理的退避算法，而不是一出现错误就马上重试
         * 3. 合理的退避算法
         * A. 遇到错误时，等待一段时间后再重试
         * B. 若遇到的错误异常次数越多，等待退避时间应该越长
         * C. 限制可重试次数，避免无限重试
         * 即设置等待时间（随着错误异常次数增多而可变）+ 设置重试次数
         *
         * 实例说明：
         * 1. 采用Get方法对金山词霸API发送网络请求
         * A. 通过断开网络连接模拟网络异常错误（恢复网络即可成功发送请求）
         * B. 限制重试次数 = 10次
         * C. 采用 Gson 进行数据解析
         */
        instruction.setText("功能说明：\n" +
                "1. 功能描述----当发生错误使得网络请求不成功时，自动重新发送网络请求\n" +
                "2. 实现原理----采用RxJava中retryWhen()操作符\n\n" +

                "具体说明：\n" +
                "1. 根据出错的错误类型，判断是否要重试（所有网络错误异常都属于I/O异常 = IOException）\n" +
                "2. 若要重试，设置退避策略，即为请求重试设置1个合理的退避算法，而不是一出现错误就马上重试\n" +
                "3. 合理的退避算法\n" +
                "A. 遇到错误时，等待一段时间后再重试\n" +
                "B. 若遇到的错误异常次数越多，等待退避时间应该越长\n" +
                "C. 限制可重试次数，避免无限重试\n" +
                "即设置等待时间（随着错误异常次数增多而可变）+ 设置重试次数\n\n" +

                "实例说明：\n" +
                "1. 采用Get方法对金山词霸API发送网络请求\n" +
                "A. 通过断开网络连接模拟网络异常错误（恢复网络即可成功发送请求）\n"+
                "B. 限制重试次数 = 10次\n"+
                "C. 采用 Gson 进行数据解析");
    }

    @OnClick({R.id.btn_retry})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retry: {
                retry();
                break;
            }
        }
    }

    // 设置变量
    // 可重试次数
    private int maxConnectCount = 10;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;

    private Disposable disposable;

    private void retry() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) // 设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        GetRequest request = retrofit.create(GetRequest.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = request.getCall0();

        // 步骤4：发送网络请求 & 通过retryWhen（）进行重试
        // 注：主要异常才会回调retryWhen（）进行重试
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

            @Override
            public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        // 输出异常信息
                        LogUtils.d(TAG,  "发生异常 = "+ throwable.toString());
                        /**
                         * 需求1：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
                         */
                        if (throwable instanceof IOException){
                            LogUtils.d(TAG,  "属于IO异常，需重试");
                            /**
                             * 需求2：限制重试次数
                             * 即，当已重试次数 < 设置的重试次数，才选择重试
                             */
                            if (currentRetryCount < maxConnectCount){
                                // 记录重试次数
                                currentRetryCount++;
                                LogUtils.d(TAG,  "重试次数 = " + currentRetryCount);
                                /**
                                 * 需求2：实现重试
                                 * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，
                                 * 最终实现重试功能
                                 *
                                 * 需求3：延迟1段时间再重试
                                 * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                 *
                                 * 需求4：遇到的异常越多，时间越长
                                 * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                 */
                                // 设置等待时间
                                waitRetryTime = 1 + currentRetryCount;
                                LogUtils.d(TAG, "等待时间" +
                                        waitRetryTime + "秒后，开始第"+ currentRetryCount + "次重试");
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.SECONDS);
                            } else {
                                // 若重试次数已 > 设置重试次数，则不重试
                                // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " +
                                        currentRetryCount + "，不再重试"));
                            }
                        } else {
                            // 若发生的异常不属于I/O异常，则不重试
                            // 通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Translation result) {
                        // 接收服务器返回的数据
                        LogUtils.d(TAG,  "发送成功");
                        tv_result.setText(tv_result.getText() + "\n" + "请求成功： " + result.content.out);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取停止重试的信息
                        LogUtils.d(TAG,  e.toString());
                        tv_result.setText(tv_result.getText() + "\n" + "请求失败： " + e.toString());
                    }

                    @Override
                    public void onComplete() {

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
