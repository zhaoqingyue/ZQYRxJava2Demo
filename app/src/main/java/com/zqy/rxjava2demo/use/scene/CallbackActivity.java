package com.zqy.rxjava2demo.use.scene;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.scene.ciba.GetRequest;
import com.zqy.rxjava2demo.use.scene.ciba.Translation1;
import com.zqy.rxjava2demo.use.scene.ciba.Translation2;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求嵌套回调
 */
public class CallbackActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @BindView(R.id.tv_result)
    TextView tv_result;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_callback;
    }

    @Override
    protected String getTitleName() {
        return "网络请求嵌套回调";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 背景--需要进行嵌套网络请求，即在第1个网络请求成功后，继续再进行1次网络请求
         * 2. 冲突--嵌套实现网络请求较为复杂，即嵌套调用函数
         * 3. 实例--先进行 用户注册 的网络请求，注册成功后，再发送 用户登录 的网络请求
         * 4. 解决方案--结合RxJava中的变换操作符flatMap()实现嵌套网络请求
         */
        des.setText("需求场景：\n" +
                "1. 背景--需要进行嵌套网络请求，即在第1个网络请求成功后，继续再进行1次网络请求\n" +
                "2. 冲突--嵌套实现网络请求较为复杂，即嵌套调用函数\n" +
                "3. 实例--先进行 用户注册 的网络请求，注册成功后，再发送 用户登录 的网络请求\n" +
                "4. 解决方案--结合RxJava中的变换操作符flatMap()实现嵌套网络请求");
        /**
         * 功能说明：
         * 1. 实现功能----发送嵌套网络请求（将英文翻译成中文，翻译两次）
         * 即先翻译 Register（注册），再翻译 Login（登录）
         * 2. 实现方案
         * A. 采用Get方法对 金山词霸API 发送网络请求
         * B. 采用 Gson 进行数据解析
         */
        instruction.setText("功能说明：\n" +
                "1. 实现功能----发送嵌套网络请求（将英文翻译成中文，翻译两次）" +
                "即先翻译 Register（注册），再翻译 Login（登录）\n" +
                "2. 实现方案\n"+
                "A. 采用Get方法对 金山词霸API 发送网络请求\n"+
                "B. 采用 Gson 进行数据解析");
    }

    @OnClick({R.id.btn_fanyi})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fanyi: {
                fanyi();
                break;
            }
        }
    }

    // 定义Observable接口类型的网络请求对象
    private Observable<Translation1> observable1;
    private Observable<Translation2> observable2;

    private void fanyi() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) // 设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        GetRequest request = retrofit.create(GetRequest.class);

        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
        observable1 = request.getCall3();
        observable2 = request.getCall4();

        observable1.subscribeOn(Schedulers.io())           //（初始被观察者）切换到IO线程进行网络请求1
                .observeOn(AndroidSchedulers.mainThread()) //（新观察者）切换到主线程 处理网络请求1的结果
                .doOnNext(new Consumer<Translation1>() {

                    @Override
                    public void accept(Translation1 result) throws Exception {
                        LogUtils.d(TAG, "第1次网络请求成功");
                        // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
                        tv_result.setText(result.show());
                    }
                })
                .observeOn(Schedulers.io()) //（新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，
                // 所以通过observeOn切换线程 但对于初始观察者，它则是新的被观察者
                .flatMap(new Function<Translation1, ObservableSource<Translation2>>() {

                    // 作变换，即作嵌套网络请求
                    @Override
                    public ObservableSource<Translation2> apply(Translation1 result) throws Exception {
                        // 将网络请求1转换成网络请求2，即发送网络请求2
                        return observable2;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //（初始观察者）切换到主线程 处理网络请求2的结果
                .subscribe(new Consumer<Translation2>() {

                    @Override
                    public void accept(Translation2 result) throws Exception {
                        LogUtils.d(TAG, "第2次网络请求成功");
                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                        tv_result.setText(tv_result.getText() + "\n" + result.show());
                    }
                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(TAG, "登录失败");
                    }
                });
    }
}
