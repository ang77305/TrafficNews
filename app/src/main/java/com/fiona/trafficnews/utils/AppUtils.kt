package com.fiona.trafficnews.utils

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class AppUtils {

     fun showSnack(view: View, message: String) {
        val snackbar = Snackbar.make(
            view, message,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("ok") {
        }
        val snackbarView = snackbar.view
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.textSize = 14f
        snackbar.show()
    }
}