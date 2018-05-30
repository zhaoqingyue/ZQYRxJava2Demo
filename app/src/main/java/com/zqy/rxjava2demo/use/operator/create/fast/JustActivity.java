package com.zqy.rxjava2demo.use.operator.create.fast;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class JustActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;
    @BindView(R.id.tv_content)
    TextView content;
    @BindView(R.id.iv_img)
    ImageView imageView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_base;
    }

    @Override
    protected String getTitleName() {
        return "just操作符";
    }

    @Override
    protected void initData() {
        des.setText("作用：\n" +
                "1. 快速创建1个被观察者对象（Observable）\n" +
                "2. 发送事件的特点：直接发送传入的事件\n\n" +
                "应用场景: \n" +
                "1. 快速创建被观察者对象（Observable）& 发送10个以下事件");

        // 1. 创建时传入"Hello World", "Hello RxJava2"
        // 在创建后就会发送这些对象，相当于执行了onNext("Hello World")、onNext("Hello RxJava2")
        Observable.just("Hello World", "Hello RxJava2")
                // 2. 通过通过订阅（subscribe）连接观察者和被观察者
                // 3. 创建观察者 & 定义响应事件的行为
                .subscribe(new Observer<String>() {

                    // 默认最先调用 onSubscribe（）
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtils.d(TAG, "接收到了事件:" + s);
                        content.setText(content.getText() + "\n" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                        imageView.setImageResource(R.mipmap.just);
                    }
                });
    }
}
