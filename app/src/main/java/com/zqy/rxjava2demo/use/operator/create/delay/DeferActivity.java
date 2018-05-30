package com.zqy.rxjava2demo.use.operator.create.delay;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.concurrent.Callable;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DeferActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;
    @BindView(R.id.tv_content)
    TextView content;
    @BindView(R.id.iv_img)
    ImageView imageView;

    String str = "" ;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_base;
    }

    @Override
    protected String getTitleName() {
        return "defer操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件
         * 1. 通过 Observable工厂方法创建被观察者对象（Observable）
         * 2. 每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的
         *
         * 应用场景：
         * 动态创建被观察者对象（Observable）& 获取最新的Observable对象数据
         */
        des.setText("作用：\n" +
                "直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件\n" +
                "1. 通过 Observable工厂方法创建被观察者对象（Observable）\n" +
                "2. 每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的\n\n" +
                "应用场景：\n" +
                "1. 动态创建被观察者对象（Observable）& 获取最新的Observable对象数据");

        // 1. 第1次对str赋值
        str = "初始化str";

        // 2. 通过defer定义被观察者对象
        // 注：此时被观察者对象还没创建
        Observable<String> observable = Observable.defer(new Callable<ObservableSource<? extends String>>() {

            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just(str);
            }
        });

        // 2. 第2次对i赋值
        str = "我是订阅后的defer";
        /**
         * 3. 观察者开始订阅
         * 注：此时，才会调用defer（）创建被观察者对象（Observable）
         */
        // 只有执行subscribe才创建Observable
        observable.subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String s) {
                LogUtils.d(TAG, "接收到的整数是: " + s);
                content.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogUtils.d(TAG, "对Complete事件作出响应");
                imageView.setImageResource(R.mipmap.defer);
            }
        });
    }
}
