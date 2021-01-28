package com.czh.xhlib.net.rx

import com.czh.xhlib.net.widget.ILoadingView
import com.czh.xhlib.utils.NetworkUtils
import com.czh.xhlib.utils.ToastUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @Description: 基础Observer
 * @Author: czh
 * @CreateDate: 2021/1/22 19:21
 */
abstract class BaseObserver<T> : Observer<T> {

    private var mLoadingView: ILoadingView? = null
    private var mLoadingMsg: String? = null

    constructor(loadingView: ILoadingView? = null, loadingMsg: String? = null) {
        mLoadingView = loadingView
        mLoadingMsg = loadingMsg
    }

    override fun onComplete() {}

    override fun onSubscribe(d: Disposable) {
        when (NetworkUtils.isConnected()) {
            true -> mLoadingView?.showLoading(mLoadingMsg ?: "")
            false -> ToastUtils.show("当前网络不可用，请检查网络设置")
        }
    }

    override fun onNext(t: T) {
        mLoadingView?.closeLoading()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        mLoadingView?.closeLoading()
    }
}