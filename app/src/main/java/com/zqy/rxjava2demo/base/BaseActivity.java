package com.zqy.rxjava2demo.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.zqy.rxjava2demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhaoqy on 2018/5/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        unbinder = ButterKnife.bind(this);
        initImmersionBar();
        initToolbar();
        initData();
    }

    protected abstract int getLayoutResID();

    protected abstract String getTitleName();

    protected abstract void initData();

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 是否有Toolbar
     */
    protected boolean hasToolbar() {
        return true;
    }

    /**
     * 是否有导航图标
     */
    protected boolean isHasNaviIcon() {
        return true;
    }

    /**
     * 初始化mImmersionBar
     */
    protected void initImmersionBar() {
        if (isImmersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this);
            if (ImmersionBar.isSupportStatusBarDarkFont()) {
                // 状态栏字体颜色为深色(android6.0以上或者miuiv6以上或者flymeOS4+)
                mImmersionBar.statusBarDarkFont(true);
            }
            mImmersionBar.init();
            if (hasToolbar()) {
                mImmersionBar.titleBar(toolbar).init();
            }
        }
    }

    private void initToolbar() {
        if (hasToolbar()) {
            if (toolbar != null) {
                toolbar.setTitle("");
                tv_title.setText(getTitleName());
                if (isHasNaviIcon()) {
                    // 设置导航按钮图标
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                }

                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null && isHasNaviIcon()) {
                    // 设置导航按钮enable
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setHomeButtonEnabled(true);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Toolbar导航按钮的按键事件
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();

        if (mImmersionBar != null) {
            // 销毁mImmersionBar
            mImmersionBar.destroy();
        }
    }
}
