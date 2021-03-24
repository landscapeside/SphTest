package com.landside.sphtest.ui.mobile

import com.landside.shadowstate_annotation.InjectAgent
import com.landside.sphtest.repo.MobileUsageRepo
import com.landside.sphtest.repo.impl.MobileUsageRepoImpl
import com.landside.support.mvp.BasePresenterImpl
import javax.inject.Inject

class MobileUsagePresenter @Inject constructor(
    var repo: MobileUsageRepo
) : BasePresenterImpl<MobileUsageView>() {

    @InjectAgent
    lateinit var agent: MobileUsageAgent

    val id: String
        get() = agent.state.id

    fun load() {
        withRequest(repo.getUsages(id)){
            mView?.showProgress()
            done { mView?.dismissProgress() }
            next {items->
                agent.setState { it.copy(usages = items) }
            }
        }
    }
}