package com.zqy.rxjava2demo.use.operator;

import android.content.Intent;
import android.view.View;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.operator.combination.CombinationActivity;
import com.zqy.rxjava2demo.use.operator.condition.ConditionActivity;
import com.zqy.rxjava2demo.use.operator.convert.ConvertActivity;
import com.zqy.rxjava2demo.use.operator.create.EstablishActivity;
import com.zqy.rxjava2demo.use.operator.filter.FilterActivity;
import com.zqy.rxjava2demo.use.operator.function.FunctionActivity;

import butterknife.OnClick;

public class OperatorActivity extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_operator;
    }

    @Override
    protected String getTitleName() {
        return "操作符";
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_create, R.id.btn_function, R.id.btn_filter,
            R.id.btn_combination, R.id.btn_convert, R.id.btn_condition})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create: {
                // 创建操作符
                Intent intent = new Intent(this, EstablishActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_convert: {
                // 变换操作符
                Intent intent = new Intent(this, ConvertActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_combination: {
                // 组合/合并操作符
                Intent intent = new Intent(this, CombinationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_function: {
                // 功能性操作符
                Intent intent = new Intent(this, FunctionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_filter: {
                // 过滤操作符
                Intent intent = new Intent(this, FilterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_condition: {
                // 条件/布尔操作符
                Intent intent = new Intent(this, ConditionActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
