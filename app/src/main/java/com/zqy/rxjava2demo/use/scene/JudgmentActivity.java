package com.zqy.rxjava2demo.use.scene;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zqy.rxjava2demo.R;
import com.zqy.rxjava2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * 联合判断
 */
public class JudgmentActivity extends BaseActivity {

    public static final String TAG = "ZQY";

    @BindView(R.id.tv_des)
    TextView des;

    @BindView(R.id.tv_instruction)
    TextView instruction;

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.age)
    EditText age;

    @BindView(R.id.job)
    EditText job;

    @BindView(R.id.button)
    Button button;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_judgment;
    }

    @Override
    protected String getTitleName() {
        return "联合判断";
    }

    @Override
    protected void initData() {
        /**
         * 需求场景：
         * 1. 需要同时对多个事件进行联合判断
         * 2. 如，填写表单时，需要表单里所有信息（姓名、年龄、职业等）都被填写后，才允许点击 “提交” 按钮
         */
        des.setText("需求场景：\n" +
                "1. 需要同时对多个事件进行联合判断\n" +
                "2. 如，填写表单时，需要表单里所有信息（姓名、年龄、职业等）都被填写后，才允许点击 “提交” 按钮");

        /**
         * 功能说明：
         * 1. 此处采用 填写表单 作为联合判断功能展示
         * 2. 即，表单里所有信息（姓名、年龄、职业等）都被填写后，才允许点击 “提交” 按钮
         * 3. 原理--采用 RxJava 组合操作符中的combineLatest（） 实现
         */
        instruction.setText("功能说明：\n" +
                "1. 此处采用 填写表单 作为联合判断功能展示\n" +
                "2. 即，表单里所有信息（姓名、年龄、职业等）都被填写后，才允许点击 “提交” 按钮\n" +
                "3. 原理--采用 RxJava 组合操作符中的combineLatest（） 实现");

        /**
         * 步骤1：为每个EditText设置被观察者，用于发送监听事件
         *
         * 说明：
         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），
         * 需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
         * 3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
         */
        Observable<CharSequence> nameObservable = RxTextView.textChanges(name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(age).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(job).skip(1);

        // 步骤2：通过combineLatest（）合并事件 & 联合判断
        Observable.combineLatest(nameObservable,
                ageObservable,
                jobObservable,
                new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {

                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2,
                                         @NonNull CharSequence charSequence3) throws Exception {
                        // 步骤3：规定表单信息输入不能为空
                        // 1. 姓名信息
                        boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) ;
                        // 除了设置为空，也可设置长度限制
                        // boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) &&
                        // (name.getText().toString().length() > 2 &&
                        // name.getText().toString().length() < 9);

                        // 2. 年龄信息
                        boolean isUserAgeValid = !TextUtils.isEmpty(age.getText());
                        // 3. 职业信息
                        boolean isUserJobValid = !TextUtils.isEmpty(job.getText());

                        // 步骤4：返回信息 = 联合判断，即3个信息同时已填写，"提交按钮"才可点击
                        return isUserNameValid && isUserAgeValid && isUserJobValid;
                    }
        }).subscribe(new Consumer<Boolean>() {

            @Override
            public void accept(Boolean s) throws Exception {
                // 步骤5：返回结果 & 设置按钮可点击样式
                LogUtils.e(TAG, "提交按钮是否可点击： " + s);
                button.setEnabled(s);
            }
        });

        RxView.clicks(button)
                // 只发送 2s内第1次点击按钮的事件
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Object value) {
                        LogUtils.d(TAG, "提交个人信息" );
                        Toast.makeText(JudgmentActivity.this, "开始提交个人信息", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "对Error事件作出响应" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
