package com.zqy.rxjava2demo.use.operator.convert;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class ConcatMapActivity extends BaseActivity {

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
        return "concatMap操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 拆分 & 重新合并生成的事件序列的顺序 = 被观察者旧序列生产的顺序
         *
         * 应用场景：
         * 有序的将被观察者发送的整个事件序列进行变换
         */
        des.setText("作用：\n" +
                "1. 拆分 & 重新合并生成的事件序列的顺序 = 被观察者旧序列生产的顺序\n\n" +
                "应用场景：\n" +
                "1. 有序的将被观察者发送的整个事件序列进行变换");
        imageView.setImageResource(R.mipmap.concatmap);

        // 采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {

            // // 采用concatMap（）变换操作符
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过concatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {

            @Override
            public void accept(@NonNull String s) throws Exception {
                LogUtils.d(TAG, s);
                content.setText(content.getText() + "\n" + s);
            }
        });
    }
}
