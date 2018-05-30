package com.zqy.rxjava2demo.use.scene;

import android.content.Intent;
import android.view.View;

import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.OnClick;

public class SceneActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_scene;
    }

    @Override
    protected String getTitleName() {
        return "实例应用";
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_shake, R.id.btn_cache, R.id.btn_merge_display, R.id.btn_judgment, R.id.btn_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shake: {
                // 功能防抖
                Intent intent = new Intent(this, ShakeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_cache: {
                // 获取缓存数据
                Intent intent = new Intent(this, CacheActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_merge_display: {
                // 合并 + 展示数据
                Intent intent = new Intent(this, MergeDisplayActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_judgment: {
                // 联合判断
                Intent intent = new Intent(this, JudgmentActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_search: {
                // 联想搜索优化
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
