package com.zqy.rxjava2demo.use.operator.create.delay;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DelayActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_delay;
    }

    @Override
    protected String getTitleName() {
        return "延迟创建";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 定时操作：在经过了x秒后，需要自动执行y操作
         * 2. 周期性操作：每隔x秒后，需要自动执行y操作
         */
        des.setText("需求场景：\n" +
                "1. 定时操作：在经过了x秒后，需要自动执行y操作\n" +
                "2. 周期性操作：每隔x秒后，需要自动执行y操作");
    }

    @OnClick({R.id.btn_defer, R.id.btn_timer, R.id.btn_interval,
            R.id.btn_intervalRange, R.id.btn_range, R.id.btn_rangeLong})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_defer: {
                // defer
                Intent intent = new Intent(this, DeferActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_timer: {
                // timer
                Intent intent = new Intent(this, TimerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_interval: {
                // interval
                Intent intent = new Intent(this, IntervalActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_intervalRange: {
                // intervalRange
                Intent intent = new Intent(this, IntervalRangeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_range: {
                // range
                Intent intent = new Intent(this, RangeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_rangeLong: {
                // rangeLong
                Intent intent = new Intent(this, RangeLongActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
