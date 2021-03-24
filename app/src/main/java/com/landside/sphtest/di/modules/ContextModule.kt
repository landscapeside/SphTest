package com.landside.sphtest.di.modules

import android.content.Context
import com.landside.sphtest.TestApp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ContextModule {
  @Singleton
  @Binds
  abstract fun context(application: TestApp): Context
}