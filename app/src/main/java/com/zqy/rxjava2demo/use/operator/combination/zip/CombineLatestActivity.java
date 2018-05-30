package com.zqy.rxjava2demo.use.operator.combination.zip;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class CombineLatestActivity extends BaseActivity {

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
        return "combineLatest操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 当两个Observables中的任何一个发送了数据后，
         * 将先发送了数据的Observables 的最新（最后）一个数据与另外一个Observable发送的每个数据结合，
         * 最终基于该函数的结果发送数据
         *
         * 与Zip（）的区别：
         * Zip（） = 按个数合并，即1对1合并；CombineLatest（）= 按时间合并，即在同一个时间点上合并
         */
        des.setText("作用：\n" +
                "1. 当两个Observables中的任何一个发送了数据后，" +
                "将先发送了数据的Observables 的最新（最后）一个数据与另外一个Observable发送的每个数据结合，" +
                "最终基于该函数的结果发送数据\n\n" +
                "与Zip（）的区别：\n" +
                "1. Zip（） = 按个数合并，即1对1合并；CombineLatest（）= 按时间合并，即在同一个时间点上合并");
        imageView.setImageResource(R.mipmap.combine);

        Observable.combineLatest(
                // 第1个发送数据事件的Observable
                Observable.just(1L, 2L, 3L),
                // 第2个发送数据事件的Observable：从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                new BiFunction<Long, Long, Long>() {

                    @Override
                    public Long apply(Long o1, Long o2) throws Exception {
                        // o1 = 第1个Observable发送的最新（最后）1个数据
                        // o2 = 第2个Observable发送的每1个数据
                        LogUtils.e(TAG, "合并的数据是： "+ o1 + " "+ o2);
                        return o1 + o2;
                        // 合并的逻辑 = 相加
                        // 即第1个Observable发送的最后1个数据 与 第2个Observable发送的每1个数据进行相加
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {

                    @Override
                    public void accept(Long s) throws Exception {
                        LogUtils.e(TAG, "合并的结果是： " + s);
                        content.setText(content.getText() + "\n" + "合并的结果是： " + s);
                    }
                });
    }
}
