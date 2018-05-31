package com.zqy.rxjava2demo.use.scene;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.scene.ciba.GetRequest;
import com.zqy.rxjava2demo.use.scene.ciba.Translation;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求轮询（无条件）
 */
public class UnconditionalActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @BindView(R.id.tv_result)
    TextView tv_result;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_unconditional;
    }

    @Override
    protected String getTitleName() {
        return "网络请求轮询（无条件）";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 背景--实现 轮询 需求，也称：pull
         * A. 客户端隔固定时间主动向服务器发送请求获取信息
         * 2. 冲突--之前的实现方式较为复杂 & 可扩展性差
         * A. 通过Handler发送延时消息
         * B. 通过Timer定时器
         * 3. 解决方案--采用RxJava中的延时操作符
         * A. interval（）无限次轮询
         * B. intervalRange（）有限次轮询
         */
        des.setText("需求场景：\n" +
                "1. 背景--实现 轮询 需求，也称：pull\n" +
                "A. 客户端隔固定时间主动向服务器发送请求获取信息\n" +
                "2. 冲突--之前的实现方式较为复杂 & 可扩展性差\n" +
                "A. 通过Handler发送延时消息\n" +
                "B. 通过Timer定时器\n" +
                "3. 解决方案--采用RxJava中的延时操作符\n" +
                "A. interval（）无限次轮询\n" +
                "B. intervalRange（）有限次轮询");

        /**
         * 功能说明：
         * 1. Get方法 金山词霸API按规定时间重复发送网络请求，从而模拟轮询需求实现
         * A. 采用 Gson 进行数据解析
         */
        instruction.setText("功能说明：\n" +
                "1. Get方法 金山词霸API按规定时间重复发送网络请求，从而模拟轮询需求实现\n" +
                "A. 采用 Gson 进行数据解析");
    }

    @OnClick({R.id.btn_pull})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pull: {
                pull();
                break;
            }
        }
    }

    Disposable disposable0;
    Disposable disposable1;

    private void pull() {
        /**
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         */
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                 /**
                  * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                  * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                  */
                .doOnNext(new Consumer<Long>() {

                    @Override
                    public void accept(final Long integer) throws Exception {
                        LogUtils.d(TAG, "第 " + integer + " 次轮询" );
                        /**
                         * 步骤3：通过Retrofit发送网络请求
                         */
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                                .addConverterFactory(GsonConverterFactory.create()) // 设置使用Gson解析(记得加入依赖)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                                .build();

                        // b. 创建 网络请求接口 的实例
                        GetRequest request = retrofit.create(GetRequest.class);

                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Translation> observable = request.getCall0();

                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())             // 切换到IO线程进行网络请求
                                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                                .subscribe(new Observer<Translation>() {

                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        disposable1 = d;
                                    }

                                    @Override
                                    public void onNext(Translation result) {
                                        // e.接收服务器返回的数据
                                        tv_result.setText(tv_result.getText() + "\n" +
                                                "第" + integer + "次轮询结果是： "  + result.content.out);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(TAG, "请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                }).subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable0 = d;
                    }

                    @Override
                    public void onNext(Long value) {

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

        if (disposable0 != null)
            disposable0.dispose();

        if (disposable1 != null)
            disposable1.dispose();
    }
}
