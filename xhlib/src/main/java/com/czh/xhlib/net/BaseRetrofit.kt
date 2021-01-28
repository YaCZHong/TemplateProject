package com.czh.xhlib.net

import com.czh.xhlib.BuildConfig
import com.czh.xhlib.net.NetConstant.DEFAULT_TIMEOUT
import com.czh.xhlib.net.interceptor.QueryParameterInterceptor
import com.czh.xhlib.net.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.concurrent.TimeUnit

abstract class BaseRetrofit<T> {

    var api: T
    private var mBaseUrl = ""
    private val mRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .client(createOkHttpClient())
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    abstract fun baseUrl(): String

    abstract fun apiClass(): Class<T>

    init {
        mBaseUrl = this.baseUrl()
        if (mBaseUrl.isEmpty()) {
            throw RuntimeException("base url can not be empty!")
        }
        api = mRetrofit.create(this.apiClass())
    }

    /**
     * 获取 OkHttpClient 实例对象
     * 子类可重写，自定义 OkHttpClient
     */
    open fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        builder.run {
            addInterceptor(httpLoggingInterceptor)
//            addInterceptor(HeaderInterceptor())
//            addInterceptor(QueryParameterInterceptor())
            connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }
        return builder.build()
    }
}