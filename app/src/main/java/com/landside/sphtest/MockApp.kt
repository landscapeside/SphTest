package com.landside.sphtest


class MockApp : TestApp() {

  override fun onCreate() {
    runMode = RunMode.UT
    super.onCreate()
  }
}