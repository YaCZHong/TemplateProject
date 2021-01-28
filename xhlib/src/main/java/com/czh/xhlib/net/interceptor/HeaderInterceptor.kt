package com.czh.xhlib.net.interceptor

import com.czh.xhlib.net.NetConstant
import com.czh.xhlib.utils.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author chenxz
 * @date 2018/9/26
 * @desc HeaderInterceptor: 设置请求头
 */
class HeaderInterceptor : Interceptor {

    /**
     * token
     */
    private var token = SPUtils.getInstance().getString(NetConstant.TOKEN_KEY, "")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")
            .addHeader("token", token)

        return chain.proceed(builder.build())
    }
}