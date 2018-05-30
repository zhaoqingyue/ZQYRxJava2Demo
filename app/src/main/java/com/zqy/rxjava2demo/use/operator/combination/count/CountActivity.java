package com.zqy.rxjava2demo.use.operator.combination.count;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class CountActivity extends BaseActivity {

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
        return "count操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 统计被观察者发送事件的数量
         */
        des.setText("作用：\n" +
                "1. 统计被观察者发送事件的数量");

        // 注：返回结果 = Long类型
        Observable.just(1, 2, 3, 4)
                .count()
                .subscribe(new Consumer<Long>() {

                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtils.e(TAG, "发送的事件数量 =  " + aLong);
                        content.setText(content.getText() + "\n" + "发送的事件数量 =  " + aLong);
                    }
                });
    }
}
