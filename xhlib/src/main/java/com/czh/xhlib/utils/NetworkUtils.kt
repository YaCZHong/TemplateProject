package com.czh.xhlib.utils

import android.content.Context
import android.net.ConnectivityManager
import com.czh.xhlib.config.AppConfig

class NetworkUtils {
    companion object {
        fun isConnected(): Boolean {
            val info = (AppConfig.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return info?.isConnected ?: false
        }
    }
}