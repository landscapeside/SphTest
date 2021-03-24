package com.landside.sphtest

import android.content.Context
import androidx.multidex.MultiDex
import com.landside.rehost.ReHost
import com.landside.shadowstate.ShadowState
import com.landside.sphtest.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class TestApp : DaggerApplication() {

    @Inject
    lateinit var rxErrHandler: RxErrHandler
    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        rxErrHandler.init()
        ShadowState.init(this, BuildConfig.DEBUG, BuildConfig.DEBUG, arrayOf(AppStateManager()))
        ReHost.init(
            this,
            BuildConfig.DEBUG,
            retrofitBuilder
        )
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}