package com.example.templateproject

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.czh.xhlib.net.rx.BaseObserver
import com.czh.xhlib.net.bean.HttpResult
import com.czh.xhlib.net.widget.ILoadingView
import com.example.templateproject.bean.JokeBean
import com.example.templateproject.net.AppRetrofit
import com.example.templateproject.vm.MainVM
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ILoadingView {

    companion object {
        private const val TAG = "MainActivity"
    }

    lateinit var progress: ProgressDialog
    lateinit var mainVM: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress = ProgressDialog(this)
        mainVM = ViewModelProvider(this).get(MainVM::class.java)
        mainVM.data.observe(this, Observer {
            tv.text = it.toString()
        })
        mainVM.isShowLoading.observe(this, Observer {
            when (it) {
                true -> showLoading("网络请求中...")
                false -> closeLoading()
            }
        })
        btn.setOnClickListener {
            mainVM.loadData()
        }

    }

    //在Activity中使用
//    private fun loadData() {
//        AppRetrofit.api.loadData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//            .subscribe(object : BaseObserver<HttpResult<JokeBean>>(this, "网络请求中...") {
//                override fun onNext(t: HttpResult<JokeBean>) {
//                    super.onNext(t)
//                    tv.text = Html.fromHtml(t.toString())
//                }
//
//                override fun onError(e: Throwable) {
//                    super.onError(e)
//                    e.printStackTrace()
//                }
//            })
//    }

    override fun showLoading(msg: String) {
        progress.setTitle(msg)
        progress.show()
    }

    override fun closeLoading() {
        progress.dismiss()
    }
}
