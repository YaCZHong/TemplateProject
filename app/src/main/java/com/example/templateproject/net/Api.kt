package com.example.templateproject.net

import com.czh.xhlib.net.bean.HttpResult
import com.example.templateproject.bean.JokeBean
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {

    @GET("joke")
    fun loadData(): Observable<HttpResult<JokeBean>>
}