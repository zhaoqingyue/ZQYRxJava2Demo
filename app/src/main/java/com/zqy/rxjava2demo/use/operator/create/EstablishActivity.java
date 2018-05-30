package com.zqy.rxjava2demo.use.operator.create;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.operator.create.base.CreateActivity;
import com.zqy.rxjava2demo.use.operator.create.delay.DelayActivity;
import com.zqy.rxjava2demo.use.operator.create.fast.FastActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EstablishActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_create;
    }

    @Override
    protected String getTitleName() {
        return "创建操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 1. 创建被观察者（Observable）对象 & 发送事件
         *
         * 应用场景：
         * 1. 完整 & 快速创建被观察者
         * 2. 定时操作
         * 3. 周期性操作
         * 4. 数组/集合遍历
         */
        des.setText("作用：\n" +
                "1. 创建被观察者（Observable）对象 & 发送事件\n\n" +
                "应用场景：\n" +
                "1. 完整 & 快速创建被观察者\n" +
                "2. 定时操作\n" +
                "3. 周期性操作\n" +
                "4. 数组/集合遍历");
    }

    @OnClick({R.id.btn_create_base, R.id.btn_create_fast, R.id.btn_create_delay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_base: {
                // 基本创建
                Intent intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_create_fast: {
                // 快速创建
                Intent intent = new Intent(this, FastActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_create_delay: {
                // 延迟创建
                Intent intent = new Intent(this, DelayActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
