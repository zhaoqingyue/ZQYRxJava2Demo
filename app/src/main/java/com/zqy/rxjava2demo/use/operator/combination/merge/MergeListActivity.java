package com.zqy.rxjava2demo.use.operator.combination.merge;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MergeListActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_merge_list;
    }

    @Override
    protected String getTitleName() {
        return "组合多个被观察者";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 1. 组合多个被观察者
         */
        des.setText("作用：\n" +
                "1. 组合多个被观察者");
    }

    @OnClick({R.id.btn_concat, R.id.btn_concatArray, R.id.btn_merge, R.id.btn_mergeArray,
            R.id.btn_concatDelayError, R.id.btn_mergeDelayError})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_concat: {
                // concat
                Intent intent = new Intent(this, ConcatActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_concatArray: {
                // concatArray
                Intent intent = new Intent(this, ConcatArrayActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_merge: {
                // merge
                Intent intent = new Intent(this, MergeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_mergeArray: {
                // mergeArray
                Intent intent = new Intent(this, MergeArrayActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_concatDelayError: {
                // concatDelayError
                Intent intent = new Intent(this, ConcatDelayErrorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_mergeDelayError: {
                // mergeDelayError
                Intent intent = new Intent(this, MergeDelayErrorActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
