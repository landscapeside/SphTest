package com.landside.sphtest.di.components;

import com.landside.sphtest.TestApp;
import com.landside.sphtest.di.modules.AllActivitysModule;
import com.landside.sphtest.di.modules.AllFragmentsModule;
import com.landside.sphtest.di.modules.AppModule;
import com.landside.sphtest.di.modules.ContextModule;
import com.landside.sphtest.di.modules.RepoModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
    AndroidInjectionModule.class,
    AndroidSupportInjectionModule.class,
    ContextModule.class,
    AppModule.class,
    RepoModule.class,
    AllActivitysModule.class,
    AllFragmentsModule.class
})
public interface AppComponent extends AndroidInjector<TestApp> {

  @Component.Builder
  abstract class Builder extends AndroidInjector.Builder<TestApp> {
  }
}
