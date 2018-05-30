package com.zqy.rxjava2demo.use.scene.zipdisplay;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhaoqy on 2018/5/30.
 */

public interface  GetRequest {

    // 网络请求1
    @GET("ajax.php?a=fy&f=auto&t=auto&w=i%20love%20world")
    Observable<Translation1> getCall1();

    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=i%20love%20china")
    Observable<Translation2> getCall2();

    /**
     * 注解里传入 网络请求 的部分URL地址
     * Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
     * 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
     * 采用Observable<...>接口
     *  getCall()是接受网络请求数据的方法
     */
}
