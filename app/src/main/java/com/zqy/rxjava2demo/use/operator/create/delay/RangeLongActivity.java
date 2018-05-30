package com.zqy.rxjava2demo.use.operator.create.delay;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RangeLongActivity extends BaseActivity {

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
        return "rangeLong操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 快速创建1个被观察者对象（Observable）
         * 发送事件的特点：连续发送 1个事件序列，可指定范围
         * 类似于range（），区别在于该方法支持数据类型 = Long
         */
        des.setText("作用：\n" +
                "1. 快速创建1个被观察者对象（Observable）\n" +
                "2. 发送事件的特点：连续发送 1个事件序列，可指定范围\n" +
                "3. 类似于range（），区别在于该方法支持数据类型 = Long");
        /**
         * 参数说明：
         * 参数1 = 事件序列起始点；
         * 参数2 = 事件数量；
         * 注：若设置为负数，则会抛出异常
         */
        Observable.rangeLong(1000000, 10)
                // 该例子发送的事件序列特点：
                // 从3开始发送，每次发送事件递增1，一共发送10个事件
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        LogUtils.d(TAG, "接收到了事件" + value);
                        content.setText("" + value);
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
