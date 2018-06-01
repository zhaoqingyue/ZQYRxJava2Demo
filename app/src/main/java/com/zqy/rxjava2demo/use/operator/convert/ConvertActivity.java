package com.zqy.rxjava2demo.use.operator.convert;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ConvertActivity extends BaseActivity {

    @BindView(R.id.tv_des)
    TextView des;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_convert;
    }

    @Override
    protected String getTitleName() {
        return "变换操作符";
    }

    @Override
    protected void initData() {
        /**
         * 作用：
         * 1. 对事件序列中的事件 / 整个事件序列进行加工处理（即变换），
         * 使得其转变成不同的事件 / 整个事件序列
         *
         * 应用场景：
         * 1. 嵌套回调（Callback）
         */
        des.setText("作用：\n" +
                "1. 对事件序列中的事件 / 整个事件序列进行加工处理（即变换），" +
                "使得其转变成不同的事件 / 整个事件序列\n\n" +
                "应用场景：\n" +
                "1. 嵌套回调（Callback）");
    }

    @OnClick({R.id.btn_convert_map, R.id.btn_convert_flatMap,
            R.id.btn_convert_concatMap, R.id.btn_convert_buffer})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_convert_map: {
                // map
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_convert_flatMap: {
                // flatMap
                Intent intent = new Intent(this, FlatMapActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_convert_concatMap: {
                // concatMap
                Intent intent = new Intent(this, ConcatMapActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_convert_buffer: {
                // buffer
                Intent intent = new Intent(this, BufferActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
