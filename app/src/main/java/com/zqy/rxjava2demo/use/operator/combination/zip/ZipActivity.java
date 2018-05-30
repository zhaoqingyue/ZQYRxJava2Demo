package com.zqy.rxjava2demo.use.operator.combination.zip;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class ZipActivity extends BaseActivity {

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
        return "zip操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 1. 合并多个被观察者（Observable）发送的事件，
         * 2. 生成一个新的事件序列（即组合过后的事件序列），并最终发送
         * 特别注意：
         * 1. 事件组合方式 = 严格按照原先事件序列进行对位合并
         * 2. 最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
         */
        des.setText("作用：\n" +
                "1. 合并多个被观察者（Observable）发送的事件\n" +
                "2. 生成一个新的事件序列（即组合过后的事件序列），并最终发送\n\n" +
                "特别注意：\n" +
                "1. 事件组合方式 = 严格按照原先事件序列进行对位合并\n" +
                "2. 最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量");
        imageView.setImageResource(R.mipmap.zip);

        // 创建第1个被观察者
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogUtils.d(TAG, "被观察者1发送了事件1");
                emitter.onNext(1);

                LogUtils.d(TAG, "被观察者1发送了事件2");
                emitter.onNext(2);

                LogUtils.d(TAG, "被观察者1发送了事件3");
                emitter.onNext(3);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作

        // 创建第2个被观察者
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogUtils.d(TAG, "被观察者2发送了事件A");
                emitter.onNext("A");

                LogUtils.d(TAG, "被观察者2发送了事件B");
                emitter.onNext("B");

                LogUtils.d(TAG, "被观察者2发送了事件C");
                emitter.onNext("C");

                LogUtils.d(TAG, "被观察者2发送了事件D");
                emitter.onNext("D");

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());// 设置被观察者2在工作线程2中工作
        // 假设不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送

        // 使用zip变换操作符进行事件合并
        // 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
        Observable.zip(observable1, observable2,
                new BiFunction<Integer, String, String>() {

                    @Override
                    public String apply(Integer integer, String string) throws Exception {
                        return  integer + string;
                    }
                }).subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        LogUtils.d(TAG, "最终接收到的事件 =  " + value);
                        content.setText(content.getText() + "\n" + "最终接收到的事件 =  " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "onComplete");
                    }
                });
    }
}
