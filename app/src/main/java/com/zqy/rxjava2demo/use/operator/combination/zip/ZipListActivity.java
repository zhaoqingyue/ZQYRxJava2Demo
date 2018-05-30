package com.zqy.rxjava2demo.use.operator.combination.zip;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ZipListActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_zip_list;
    }

    @Override
    protected String getTitleName() {
        return "合并多个事件";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 对多个被观察者中的事件进行合并处理
         */
        des.setText("作用：\n" +
                "1. 合并多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送");
    }

    @OnClick({R.id.btn_zip, R.id.btn_combineLatest, R.id.btn_combineLatestDelayError,
            R.id.btn_reduce, R.id.btn_collect})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zip: {
                // zip
                Intent intent = new Intent(this, ZipActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_combineLatest: {
                // combineLatest
                Intent intent = new Intent(this, CombineLatestActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_combineLatestDelayError: {
                break;
            }
            case R.id.btn_reduce: {
                // reduce
                Intent intent = new Intent(this, ReduceActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_collect: {
                // collect
                Intent intent = new Intent(this, CollectActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
