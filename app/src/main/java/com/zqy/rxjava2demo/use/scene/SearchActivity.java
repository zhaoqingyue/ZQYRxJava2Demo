package com.zqy.rxjava2demo.use.scene;

import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SearchActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @BindView(R.id.et_key)
    EditText key;

    @BindView(R.id.tv_result)
    TextView result;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_search;
    }

    @Override
    protected String getTitleName() {
        return "联想搜索优化";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 背景--实现联想搜索功能
         * ----即每当用户输入1个字符，立即显示与当前输入框内字符相关的搜索结果
         * 最基本的实现流程：
         * A. 通过EditText.addTextChangedListener()监听输入框变化
         * B. 当输入框发生变化后，回调afterTextChanged()将当前输入框的文字向服务器发起请求
         * C. 服务器返回与该搜索文字关联的结果
         *
         * 2. 冲突--在用户搜索需求明确的情况下（体现为连续输入），可能会发起一些不必要的网络请求
         * 示例：
         * A. 用户搜索需求明确 = abc，即连续输入了abc
         * B. 按上面实现，客户端会向服务器发起a、ab、abc 3个网络请求，即多发起了a、ab 2个不必要的网络请求
         * 3. 解决方案--通过根据指定时间过滤事件的过滤操作符（debounce）实现，防止不必要的网络请求
         */
        des.setText("需求场景：\n" +
                "1. 背景--实现联想搜索功能\n" +
                "----即每当用户输入1个字符，立即显示与当前输入框内字符相关的搜索结果\n" +
                "最基本的实现流程：\n" +
                "A. 通过EditText.addTextChangedListener()监听输入框变化\n" +
                "B. 当输入框发生变化后，回调afterTextChanged()将当前输入框的文字向服务器发起请求\n" +
                "C. 服务器返回与该搜索文字关联的结果\n" +
                "2. 冲突--在用户搜索需求明确的情况下（体现为连续输入），可能会发起一些不必要的网络请求\n" +
                "示例：\n" +
                "A. 用户搜索需求明确 = abc，即连续输入了abc\n" +
                "B. 按上面实现，客户端会向服务器发起a、ab、abc 3个网络请求，即多发起了a、ab 2个不必要的网络请求\n" +
                "3. 解决方案--通过根据指定时间过滤事件的过滤操作符（debounce）实现，防止不必要的网络请求");

        /**
         * 功能说明：
         * 1. 实现原理--通过根据指定时间过滤事件的过滤操作符（debounce）实现，防止不必要的网络请求
         * 2. 功能逻辑：
         * A. 当输入框发生变化时，不会立即将当前输入框内的文字发送给服务器，而是等待一段时间
         * B. 若在这段时间内，输入框不再有文字输入（无发生变化），那么才发送输入框内的文字给服务器
         * C. 如在这段时间内，输入框有文字输入（发生变化），则继续等待该段时间，循环上述过程
         */
        instruction.setText("功能说明：\n" +
                "1. 实现原理--通过根据指定时间过滤事件的过滤操作符（debounce）实现，防止不必要的网络请求\n" +
                "2. 功能逻辑：\n" +
                "A. 当输入框发生变化时，不会立即将当前输入框内的文字发送给服务器，而是等待一段时间\n" +
                "B. 若在这段时间内，输入框不再有文字输入（无发生变化），那么才发送输入框内的文字给服务器\n" +
                "C. 如在这段时间内，输入框有文字输入（发生变化），则继续等待该段时间，循环上述过程");

         /**
         * 说明
         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），
          * 需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，输入字符时都会发送数据事件（此处不会马上发送，因为使用了debounce（））
         * 3. 采用skip(1)原因：跳过 第1次请求 = 初始输入框的空字符状态
         */
        RxTextView.textChanges(key)
                .debounce(1, TimeUnit.SECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        result.setText("发送给服务器的字符 = " + charSequence.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应" );

                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
