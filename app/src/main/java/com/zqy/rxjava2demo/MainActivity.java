package com.zqy.rxjava2demo;

import android.content.Intent;
import android.view.View;

import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.BaseUseActivity;
import com.zqy.rxjava2demo.use.operator.OperatorActivity;
import com.zqy.rxjava2demo.use.scene.SceneActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.app_name);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected boolean isHasNaviIcon() {
        return false;
    }

    @OnClick({R.id.btn_base, R.id.btn_operator, R.id.btn_use})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_base: {
                // 基本用法
                Intent intent = new Intent(this, BaseUseActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_operator: {
                // 操作符
                Intent intent = new Intent(this, OperatorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_use: {
                // 使用场景
                Intent intent = new Intent(this, SceneActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
