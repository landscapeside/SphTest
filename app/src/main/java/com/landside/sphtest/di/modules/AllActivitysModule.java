package com.landside.sphtest.di.modules;

import com.landside.sphtest.ui.mobile.MobileUsageActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AllActivitysModule {

    @ContributesAndroidInjector
    abstract MobileUsageActivity contributeMobileUsageActivityInjector();
}
