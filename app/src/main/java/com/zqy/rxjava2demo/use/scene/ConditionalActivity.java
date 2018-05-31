package com.zqy.rxjava2demo.use.scene;

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
 * 网络请求轮询（有条件）
 */
public class ConditionalActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @BindView(R.id.tv_result)
    TextView tv_result;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_conditional;
    }

    @Override
    protected String getTitleName() {
        return "网络请求轮询（有条件）";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 背景--实现 轮询 需求，也称：pull
         * A. 客户端隔固定时间主动向服务器发送请求获取信息
         * B. 可根据服务器返回信息停止轮询，即有条件轮询
         * 2. 冲突--之前的实现方式较为复杂 & 可扩展性差
         * A. 通过Handler发送延时消息
         * B. 通过Timer定时器
         * 3. 解决方案--采用RxJava中的repeatWhen操作符
         */
        des.setText("需求场景：\n" +
                "1. 背景--实现 轮询 需求，也称：pull\n" +
                "A. 客户端隔固定时间主动向服务器发送请求获取信息\n" +
                "B. 可根据服务器返回信息停止轮询，即有条件轮询\n" +
                "2. 冲突--之前的实现方式较为复杂 & 可扩展性差\n" +
                "A. 通过Handler发送延时消息\n" +
                "B. 通过Timer定时器\n" +
                "3. 解决方案--采用RxJava中的repeatWhen操作符");

        /**
         * 功能说明：
         * 1. 采用Get方法对金山词霸API按规定时间重复发送网络请求，从而模拟轮询需求实现
         * A. 停止轮询的条件 = 当轮询到第4次时
         * B. 采用 Gson 进行数据解析
         */
        instruction.setText("功能说明：\n" +
                "1. 采用Get方法对金山词霸API按规定时间重复发送网络请求，从而模拟轮询需求实现\n" +
                "A. 停止轮询的条件 = 当轮询到第4次时\n"+
                "B. 采用 Gson 进行数据解析");
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

    // 定义Observable接口类型的网络请求对象
    private Observable<Translation> observable;

    // 设置变量 = 模拟轮询服务器次数
    private int i = 0 ;

    private void pull() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        GetRequest request = retrofit.create(GetRequest.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        observable = request.getCall0();

        // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {

            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {
                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (i > 3) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "轮询开始");
                    }

                    @Override
                    public void onNext(Translation result) {
                        // e.接收服务器返回的数据
                        i++;
                        tv_result.setText(tv_result.getText() + "\n" +
                                "第" + i + "次轮询结果是： "  + result.content.out);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        LogUtils.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "轮询完成");
                    }
                });
    }
}
