package com.zqy.rxjava2demo.use.operator.create.fast;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FastActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_fast;
    }

    @Override
    protected String getTitleName() {
        return "快速创建";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 快速的创建被观察者对象
         */
        des.setText("需求场景：\n" +
                "1. 快速的创建被观察者对象");
    }

    @OnClick({R.id.btn_just, R.id.btn_fromArray, R.id.btn_fromIterable,
            R.id.btn_empty, R.id.btn_error, R.id.btn_never})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_just: {
                // just
                Intent intent = new Intent(this, JustActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_fromArray: {
                // fromArray
                Intent intent = new Intent(this, FromArrayActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_fromIterable: {
                // fromIterable
                Intent intent = new Intent(this, FromIterableActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_empty: {
                // empty
                Intent intent = new Intent(this, EmptyActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_error: {
                // error
                Intent intent = new Intent(this, ErrorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_never: {
                // never
                Intent intent = new Intent(this, NeverActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
