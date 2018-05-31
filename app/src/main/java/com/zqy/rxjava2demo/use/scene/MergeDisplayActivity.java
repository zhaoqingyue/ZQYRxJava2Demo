package com.zqy.rxjava2demo.use.scene;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 使用merge实现：合并 + 展示数据
 */
public class MergeDisplayActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_merge_display;
    }

    @Override
    protected String getTitleName() {
        return "合并 + 展示数据";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 背景--获取数据 & 统一展示到客户端
         * 2. 冲突--来自不同的地方（本地 、网络），即：数据源多样
         * 3. 解决方案--采用RxJava组合操作符merge() 和 zip()
         */
        des.setText("需求场景：\n" +
                "1. 背景--获取数据 & 统一展示到客户端\n" +
                "2. 冲突--来自不同的地方（本地 、网络），即：数据源多样\n" +
                "3. 解决方案--采用RxJava组合操作符merge() 和 zip()");

        /**
         * 功能说明：
         * 同时向2个数据源获取数据 -> 合并数据 -> 统一展示到客户端
         */
        instruction.setText("功能说明：\n" +
                "同时向2个数据源获取数据 -> 合并数据 -> 统一展示到客户端");
    }

    @OnClick({R.id.btn_display_merge, R.id.btn_display_zip})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_display_merge: {
                // 使用merge实现
                onMerge();
                break;
            }
            case R.id.btn_display_zip: {
                // 使用zip实现
                Intent intent = new Intent(this, ZipDisplayActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    // 用于存放最终展示的数据
    String result = "数据源来自 = ";

    private void onMerge() {
        // 设置第1个Observable：通过网络获取数据
        Observable<String> network = Observable.just("网络");

        // 设置第2个Observable：通过本地文件获取数据
        Observable<String> file = Observable.just("本地文件");

        // 通过merge（）合并事件 & 同时发送事件
        Observable.merge(network, file)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(String value) {
                        LogUtils.d(TAG, "数据源有： " + value);
                        result += value + "+";
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应");
                    }

                    // 接收合并事件后，统一展示
                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "获取数据完成");
                        // 去掉最后一个"+"
                        result = result.toString().substring(0, result.length() - 1);
                        LogUtils.d(TAG,  result);
                        Toast.makeText(MergeDisplayActivity.this, result, Toast.LENGTH_LONG).show();

                        // 重新赋值
                        result = "数据源来自 = ";
                    }
                });
    }
}
