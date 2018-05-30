package com.zqy.rxjava2demo.use.operator.combination.zip;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

public class CollectActivity extends BaseActivity {

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
        return "collect操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 将被观察者Observable发送的数据事件收集到一个数据结构里
         */
        des.setText("作用：\n" +
                "1. 将被观察者Observable发送的数据事件收集到一个数据结构里");

        Observable.just(1, 2, 3 ,4, 5, 6)
                .collect(new Callable<ArrayList<Integer>>() {

                             // 1. 创建数据结构（容器），用于收集被观察者发送的数据
                            @Override
                            public ArrayList<Integer> call() throws Exception {
                                return new ArrayList<>();
                            }
                        }, new BiConsumer<ArrayList<Integer>, Integer>() {

                            // 2. 对发送的数据进行收集
                            @Override
                            public void accept(ArrayList<Integer> list, Integer integer) throws Exception {
                                // 参数说明：list = 容器，integer = 后者数据
                                list.add(integer);
                                // 对发送的数据进行收集
                            }
                        }).subscribe(new Consumer<ArrayList<Integer>>() {

                            @Override
                            public void accept(@NonNull ArrayList<Integer> s) throws Exception {
                                LogUtils.e(TAG, "本次发送的数据是： " + s);
                                content.setText(content.getText() + "\n" + "本次发送的数据是： " + s);
                            }
                        });
    }
}
