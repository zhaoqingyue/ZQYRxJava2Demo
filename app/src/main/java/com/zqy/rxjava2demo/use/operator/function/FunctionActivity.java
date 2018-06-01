package com.zqy.rxjava2demo.use.operator.function;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FunctionActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_function;
    }

    @Override
    protected String getTitleName() {
        return "功能性操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 1. 辅助被观察者（Observable） 在发送事件时实现一些功能性需求
         */
        des.setText("作用：\n" +
                "1. 辅助被观察者（Observable） 在发送事件时实现一些功能性需求");
    }

    @OnClick({R.id.btn_function_subscribe, R.id.btn_function_schedule, R.id.btn_function_delay,
            R.id.btn_function_error, R.id.btn_function_repeat, R.id.btn_function_do})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_function_subscribe: {
                // 连接被观察者 与 观察者
                Intent intent = new Intent(this, SubscribeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_function_schedule: {
                // 线程调度
                break;
            }
            case R.id.btn_function_delay: {
                // 延迟操作
                Intent intent = new Intent(this, DelayActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_function_error: {
                // 错误处理
//                Intent intent = new Intent(this, BufferActivity.class);
//                startActivity(intent);
                break;
            }
            case R.id.btn_function_repeat: {
                // 重复发送操作
                Intent intent = new Intent(this, RepeatActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_function_do: {
                // 生命周期do
                Intent intent = new Intent(this, DoActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
