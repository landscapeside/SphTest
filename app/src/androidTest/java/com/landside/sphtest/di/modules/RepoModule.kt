package com.landside.sphtest.di.modules

import com.landside.sphtest.apis.RetrofitService
import com.landside.sphtest.repo.MobileUsageRepo
import com.landside.sphtest.ui.repo.FakeRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideMobileUsageRepo(service: RetrofitService): MobileUsageRepo {
        return FakeRepo()
    }
}