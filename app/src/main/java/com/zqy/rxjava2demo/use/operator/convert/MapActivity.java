package com.zqy.rxjava2demo.use.operator.convert;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MapActivity extends BaseActivity {

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
        return "map操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 对被观察者发送的每1个事件都通过指定的函数处理，从而变换成另外一种事件
         * 即，将被观察者发送的事件转换为任意的类型事件
         *
         * 应用场景：
         * 数据类型转换
         */
        des.setText("作用：\n" +
                "1. 对被观察者发送的每1个事件都通过指定的函数处理，从而变换成另外一种事件\n\n" +
                "应用场景：\n" +
                "1. 数据类型转换");
        imageView.setImageResource(R.mipmap.map);

        // 采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {

            // 1. 被观察者发送事件：参数为整型 = 1、2、3
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {

            // 2. 使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "map" + integer;
            }
        }).subscribe(new Consumer<String>() {

            // 3. 观察者接收事件时，是接收到变换后的事件 = 字符串类型
            @Override
            public void accept(@NonNull String s) throws Exception {
                LogUtils.d(TAG, s);
                content.setText(content.getText() + "\n" + s);
            }
        });
    }
}
