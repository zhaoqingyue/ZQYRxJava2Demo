package com.zqy.rxjava2demo.use.operator.combination.zip;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class ReduceActivity extends BaseActivity {

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
        return "reduce操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 把被观察者需要发送的事件聚合成1个事件 & 发送
         *
         * 聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
         */
        des.setText("作用：\n" +
                "1. 把被观察者需要发送的事件聚合成1个事件 & 发送\n" +
                "2. 聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推");

        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {

                    // 在该方法中写聚合的逻辑
                    @Override
                    public Integer apply(@NonNull Integer s1, @NonNull Integer s2) throws Exception {
                        LogUtils.e(TAG, "本次计算的数据是： " + s1 +" 乘 "+ s2);
                        content.setText(content.getText() + "\n" + "本次计算的数据是： " + s1 +" 乘 "+ s2);
                        return s1 * s2;
                        // 本次聚合的逻辑是：全部数据相乘起来
                        // 原理：第1次取前2个数据相乘，之后每次获取到的数据 = 返回的数据x原始下1个数据每
                    }
                }).subscribe(new Consumer<Integer>() {

                    @Override
                    public void accept(@NonNull Integer s) throws Exception {
                        LogUtils.e(TAG, "最终计算的结果是： " + s);
                        content.setText(content.getText() + "\n" + "最终计算的结果是： " + s);
                    }
                });
    }
}
