package com.czh.xhlib.utils

import android.widget.Toast
import com.czh.xhlib.config.AppConfig

/**
 * @Description:
 * @Author: czh
 * @CreateDate: 2021/1/22 17:25
 */
class ToastUtils {
    companion object {

        private var mToast: Toast? = null

        fun show(text: String, duration: Int = Toast.LENGTH_SHORT) {
            mToast = Toast.makeText(AppConfig.getApplication(), text, duration)
            mToast?.show()
        }

        fun showNoOneByOne(text: String, duration: Int = Toast.LENGTH_SHORT) {
            if (mToast == null) {
                mToast = Toast.makeText(AppConfig.getApplication(), text, duration)
            } else {
                mToast?.setText(text)
            }
            mToast?.show()
        }
    }
}