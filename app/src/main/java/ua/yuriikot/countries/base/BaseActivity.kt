package ua.yuriikot.countries.base

import android.arch.lifecycle.MutableLiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    val networkStatusLiveData = MutableLiveData<Boolean>()

    private val networkManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnectionStatus(context)
        }

        private fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnected == true
        }

        fun updateConnectionStatus(context: Context) {
            networkStatusLiveData.value = isConnected(context)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(networkManager, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkManager)
    }



}
