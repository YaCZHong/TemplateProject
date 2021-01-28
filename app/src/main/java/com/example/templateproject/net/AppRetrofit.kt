package com.example.templateproject.net;

import com.czh.xhlib.net.BaseRetrofit
import com.example.templateproject.BuildConfig

/**
 * @description:
 * @author: czh
 * @time: 2021/1/18 17:19
 */

object AppRetrofit : BaseRetrofit<Api>() {
    override fun baseUrl() = BuildConfig.BASE_URL
    override fun apiClass(): Class<Api> = Api::class.java
}
