package com.zqy.rxjava2demo.use.scene;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.scene.zipdisplay.GetRequest;
import com.zqy.rxjava2demo.use.scene.zipdisplay.Translation1;
import com.zqy.rxjava2demo.use.scene.zipdisplay.Translation2;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ZipDisplayActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_zip_display;
    }

    @Override
    protected String getTitleName() {
        return "zip操作符实现合并 + 展示数据";
    }

    @Override
    protected void initData() {
        /**
         * 功能说明：
         * 结合 Retrofit 与RxJava，实现
         * A. 从不同数据源（2个服务器）获取数据，即 合并网络请求的发送
         * B. 统一显示结果
         *
         * 实现方案：
         * 1. 采用Get方法对金山词霸API发送2个网络请求（将英文翻译成中文，翻译2次）& 将2次翻译的结果一起显示
         * 2. 采用 Gson 进行数据解析
         */
        des.setText("功能说明：\n" +
                "结合 Retrofit 与RxJava，实现\n" +
                "A. 从不同数据源（2个服务器）获取数据，即 合并网络请求的发送\n" +
                "B. 统一显示结果\n" +
                "实现方案：\n" +
                "1. 采用Get方法对金山词霸API发送2个网络请求（将英文翻译成中文，翻译2次）& 将2次翻译的结果一起显示\n" +
                "2. 采用 Gson 进行数据解析");
        /**
         * 步骤说明 ：
         * 1. 添加依赖
         * 2. 创建接收服务器返回数据的类
         * 3. 创建用于描述网络请求的接口
         * 4. 创建Retrofit 实例
         * 5. 创建网络请求接口实例并配置网络请求参数
         * 6. 发送网络请求
         * 7. 发送网络请求
         * 8. 对返回的数据进行处理
         */
        instruction.setText("步骤说明：\n" +
                "1. 添加依赖\n" +
                "2. 创建接收服务器返回数据的类\n" +
                "3. 创建用于描述网络请求的接口\n"+
                "4. 创建Retrofit 实例\n"+
                "5. 创建网络请求接口实例并配置网络请求参数\n"+
                "6. 发送网络请求\n"+
                "7. 发送网络请求\n"+
                "8. 对返回的数据进行处理");


    }

    @OnClick({R.id.btn_fanyi})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fanyi: {
                // 定义Observable接口类型的网络请求对象
                Observable<Translation1> observable1;
                Observable<Translation2> observable2;

                // 步骤1：创建Retrofit对象
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                        .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                        .build();

                // 步骤2：创建 网络请求接口 的实例
                GetRequest request = retrofit.create(GetRequest.class);

                // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
                observable1 = request.getCall1().subscribeOn(Schedulers.io()); // 新开线程进行网络请求1
                observable2 = request.getCall2().subscribeOn(Schedulers.io()); // 新开线程进行网络请求2
                // 即2个网络请求异步 & 同时发送

                // 步骤4：通过使用Zip（）对两个网络请求进行合并再发送
                Observable.zip(observable1, observable2,
                        new BiFunction<Translation1, Translation2, String>() {

                            // 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
                            @Override
                            public String apply(Translation1 translation1,
                                                Translation2 translation2) throws Exception {
                                return translation1.show() + " & " + translation2.show();
                            }
                        }).observeOn(AndroidSchedulers.mainThread()) // 在主线程接收 & 处理数据
                        .subscribe(new Consumer<String>() {

                            // 成功返回数据时调用
                            @Override
                            public void accept(String s) throws Exception {
                                // 结合显示2个网络请求的数据结果
                                LogUtils.d(TAG, "最终接收到的数据是：" + s);
                                Toast.makeText(ZipDisplayActivity.this, "最终接收到的数据是：" + s, Toast.LENGTH_LONG).show();
                            }
                        }, new Consumer<Throwable>() {

                            // 网络请求错误时调用
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                LogUtils.d(TAG, "登录失败");
                            }
                        });
                break;
            }
        }
    }
}
