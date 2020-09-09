package com.dawar.evreka.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dawar.evreka.R
import com.dawar.evreka.extensions.gone
import com.dawar.evreka.extensions.showToastMsg
import com.dawar.evreka.extensions.visible
import com.dawar.evreka.utils.InternetMonitor
import kotlinx.android.synthetic.main.container_layout.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        setContentView(R.layout.container_layout)
    }

    private val internetMonitor = InternetMonitor(object :
        InternetMonitor.OnInternetConnectivityListener {
        override fun onInternetAvailable() {
            runOnUiThread {
                top_connection_shower.gone()
            }
        }

        override fun onInternetLost() {
            runOnUiThread {
                top_connection_shower.visible()
                showToastMsg(getString(R.string.no_internet))
            }
        }
    })

    override fun onResume() {
        super.onResume()
        internetMonitor.register(this)
    }

    override fun onPause() {
        super.onPause()
        internetMonitor.unregister(this)
    }
}