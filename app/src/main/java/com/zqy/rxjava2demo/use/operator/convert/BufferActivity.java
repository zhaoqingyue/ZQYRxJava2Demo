package com.zqy.rxjava2demo.use.operator.convert;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BufferActivity extends BaseActivity {

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
        return "buffer操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 定期从被观察者（Obervable）需要发送的事件中获取一定数量的事件 & 放到缓存区中，最终发送
         *
         * 应用场景：
         * 缓存被观察者发送的事件
         */
        des.setText("作用：\n" +
                "1. 定期从被观察者（Obervable）需要发送的事件中获取一定数量的事件 & 放到缓存区中，最终发送\n\n" +
                "应用场景：\n" +
                "1. 缓存被观察者发送的事件");
        imageView.setImageResource(R.mipmap.buffer);

        // 被观察者需要发送5个数字
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1) // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        LogUtils.d(TAG, " 缓存区里的事件数量 = " +  stringList.size());
                        content.setText(content.getText() + "\n" + " 缓存区里的事件数量 = " +  stringList.size());
                        for (Integer value : stringList) {
                            LogUtils.d(TAG, " 事件 " + value);
                            content.setText(content.getText() + "\n" + " 事件 " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应" );
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
