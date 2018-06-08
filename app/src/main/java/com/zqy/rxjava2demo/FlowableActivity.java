package com.zqy.rxjava2demo;

import android.widget.TextView;

import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;

public class FlowableActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;
    @BindView(R.id.tv_content)
    TextView content;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_flowable;
    }

    @Override
    protected String getTitleName() {
        return "Flowable";
    }

    @Override
    protected void initData() {

    }
}
