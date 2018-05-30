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

public class EmptyActivity extends BaseActivity {

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
        return "empty操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 创建被观察者对象
         * 发送事件的特点：仅发送Complete事件，直接通知完成
         */
        des.setText("作用：\n" +
                "1. 创建1个被观察者对象（Observable）\n" +
                "2. 发送事件的特点：仅发送Complete事件，直接通知完成");
        // 观察者接收后会直接调用onCompleted（）
        Observable.empty()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Object o) {
                        LogUtils.d(TAG, "接收到了事件" + o.toString());
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
