package ua.yuriikot.countries

import android.app.Application
import ua.yuriikot.countries.dagger.AppComponent
import ua.yuriikot.countries.dagger.DaggerAppComponent
import ua.yuriikot.countries.dagger.DefaultModule


class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = buildComponent()
    }

    private fun buildComponent() = DaggerAppComponent.builder().defaultModule(DefaultModule(this)).build()

}
