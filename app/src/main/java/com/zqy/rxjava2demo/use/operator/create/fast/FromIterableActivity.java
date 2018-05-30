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

public class FromIterableActivity extends BaseActivity {

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
        return "fromIterable操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 快速创建1个被观察者对象（Observable）
         * 发送事件的特点：直接发送 传入的集合List数据
         *
         * 应用场景：
         * 快速创建被观察者对象（Observable） & 发送10个以上事件（集合形式）
         * 集合元素遍历
         */
        des.setText("作用：\n" +
                "1. 快速创建1个被观察者对象（Observable）\n" +
                "2. 发送事件的特点：直接发送传入的集合List数据");

        // 1. 设置需要传入的数组
        String[] items = { "应用场景：", "1. 快速创建被观察者对象（Observable）& 发送10个以上事件（集合形式）",
                "2. 集合元素遍历"};

        // 2. 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
        Observable.fromArray(items)
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtils.d(TAG, "接收到了事件"+ s);
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
                });
    }
}
