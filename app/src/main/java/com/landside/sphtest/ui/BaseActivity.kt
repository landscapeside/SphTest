package com.landside.sphtest.ui

import com.landside.support.mvp.BasePresenterImpl
import com.landside.support.mvp.BaseView
import com.landside.support.mvp.MVPBaseActivity

abstract class BaseActivity<V : BaseView, P : BasePresenterImpl<V>>:MVPBaseActivity<V,P>() {



    override fun showProgress() {

    }

    override fun dismissProgress() {

    }

}