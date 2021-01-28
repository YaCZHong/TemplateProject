package com.example.templateproject.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.czh.xhlib.net.bean.HttpResult
import com.czh.xhlib.net.rx.BaseObserver
import com.example.templateproject.bean.JokeBean
import com.example.templateproject.net.AppRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @Description:
 * 一些 View 相关的操作，往往是异步的，比如下拉刷新、点击关注按钮，都是用户先触发行为，然后客户端调用后端 API ，后端返回数据后更新 View 状态。
 * 对于传统的 MVC 构架，你可以在 C 层做这些异步操作；对于早年流行的 MVP 构架，你可以在 P 层做这些异步操作；
 * 但不管如何，与 View 相关的异步操作你都需要关心 View 的生命周期，不然等你异步回调的时候通常会收获一个崩溃。
 * 以前我们会在 BaseActivity 或者 BaseFragment 自己管理异步回调，或者借助 uber/AutoDispose 这样的库来辅助管理。
 * 现在你可以把这些异步操作统一移动到 ViewModel 中，并在其 onCleared() 的回调中统一取消。
 * @Author: czh
 * @CreateDate: 2021/1/27 14:26
 */

class MainVM : ViewModel() {

    companion object {
        private const val TAG = "MainVM"
    }

    private var disposeList: MutableList<Disposable> = mutableListOf()

    val isShowLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also { it.value = false }
    }

    private val _data: MutableLiveData<HttpResult<JokeBean>> by lazy {
        MutableLiveData<HttpResult<JokeBean>>()
    }

    val data: MutableLiveData<HttpResult<JokeBean>>
        get() = _data

    fun loadData() {
        AppRetrofit.api.loadData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<HttpResult<JokeBean>>() {

                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    disposeList.add(d)
                    isShowLoading.postValue(true)
                }

                override fun onNext(t: HttpResult<JokeBean>) {
                    super.onNext(t)
                    _data.value = t
                    isShowLoading.value = false
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    isShowLoading.value = false
                }
            })
    }

    override fun onCleared() {
        super.onCleared()

        //及时释放资源，不过不做也没事，因为没有直接引用到view
        for (d in disposeList) {
            if (!d.isDisposed) d.dispose()
        }
    }
}