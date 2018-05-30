package com.zqy.rxjava2demo.use.operator.combination.start;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class StartWithArrayActivity extends BaseActivity {

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
        return "startWithArray操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
         */
        des.setText("作用：\n" +
                "1. 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者");

        // 注：追加数据顺序 = 后调用先追加
        Observable.just(4, 5, 6)
                .startWithArray(1, 2, 3) // 追加多个数据 = startWithArray()
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        LogUtils.d(TAG, "接收到了事件" + value);
                        content.setText(content.getText() + "\n" + "接收到了事件 " + value);
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
}
