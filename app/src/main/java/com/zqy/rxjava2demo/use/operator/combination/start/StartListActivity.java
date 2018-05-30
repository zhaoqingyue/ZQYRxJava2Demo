package com.zqy.rxjava2demo.use.operator.combination.start;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class StartListActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_start_list;
    }

    @Override
    protected String getTitleName() {
        return "发送事件前追加发送事件";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 1. 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
         */
        des.setText("作用：\n" +
                "1. 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者");
    }

    @OnClick({R.id.btn_startWith, R.id.btn_startWithArray})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startWith: {
                // startWith
                Intent intent = new Intent(this, StartWithActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_startWithArray: {
                // startWithArray
                Intent intent = new Intent(this, StartWithArrayActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
