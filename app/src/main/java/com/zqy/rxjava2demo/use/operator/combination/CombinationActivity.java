package com.zqy.rxjava2demo.use.operator.combination;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;
import com.zqy.rxjava2demo.use.operator.combination.count.CountActivity;
import com.zqy.rxjava2demo.use.operator.combination.merge.MergeListActivity;
import com.zqy.rxjava2demo.use.operator.combination.start.StartListActivity;
import com.zqy.rxjava2demo.use.operator.combination.zip.ZipListActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CombinationActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_combination;
    }

    @Override
    protected String getTitleName() {
        return "组合/合并操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 1. 组合多个被观察者（`Observable`）& 合并需要发送的事件
         */
        des.setText("作用：\n" +
                "1. 组合多个被观察者（ Observable ）& 合并需要发送的事件");
    }

    @OnClick({R.id.btn_combination_merge, R.id.btn_combination_zip,
            R.id.btn_combination_start, R.id.btn_combination_count})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_combination_merge: {
                // merge
                Intent intent = new Intent(this, MergeListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_combination_zip: {
                // zip
                Intent intent = new Intent(this, ZipListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_combination_start: {
                // start
                Intent intent = new Intent(this, StartListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_combination_count: {
                // count
                Intent intent = new Intent(this, CountActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
