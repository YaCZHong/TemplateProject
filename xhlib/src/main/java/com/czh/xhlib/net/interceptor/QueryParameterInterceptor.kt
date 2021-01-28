package com.czh.xhlib.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author chenxz
 * @date 2018/9/26
 * @desc QueryParameterInterceptor 设置公共参数
 */
class QueryParameterInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val modifiedUrl = originalRequest.url.newBuilder()
            // Provide your custom parameter here
//            .addQueryParameter("phoneSystem", "")
//            .addQueryParameter("phoneModel", "")
            .build()

        val request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }
}