package com.zqy.rxjava2demo.use;

import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseUseActivity extends BaseActivity {

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
        return "基本用法";
    }

    @Override
    protected void initData() {
        des.setText("Rxjava2的基本使用：");
        // 分步实现
        // 1. 通过create（）创建被观察者 Observable 对象
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                // 定义需要发送的事件
                e.onNext("1. 创建被观察者 Observable 对象");
                e.onNext("2. 创建观察者 Observer对象 & 定义响应事件的行为");
                e.onNext("3. 订阅（subscribe）连接观察者和被观察者");
                e.onComplete();
            }
        });

        // 2. 创建观察者 & 定义响应事件的行为
        Observer observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String s) {
                LogUtils.d(TAG, "接收到了事件: " + s);
                content.setText(content.getText() + "\n" + s);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogUtils.d(TAG, "对Complete事件作出响应");
            }
        };

        // 3. 通过通过订阅（subscribe）连接观察者和被观察者
        observable.subscribe(observer);
    }
}
