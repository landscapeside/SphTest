package com.landside.sphtest.ui.mobile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.landside.sphtest.LiveDataTestUtil
import com.landside.sphtest.domin.MobileUsageItem
import com.landside.sphtest.repo.impl.MobileUsageRepoImpl
import com.landside.support.exceptions.ServerException
import com.landside.support.scheduler.ImmediateSchedulerProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MobileUsagePresenterTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repo: MobileUsageRepoImpl

    @MockK
    private lateinit var view: MobileUsageView

    @SpyK
    var agent: MobileUsageAgent = MobileUsageAgent()

    val normalUsages = listOf<MobileUsageItem>(
        MobileUsageItem(
            year = "2009",
            value = 1.0,
            hasDecrease = false
        ),
        MobileUsageItem(
            year = "2010",
            value = 2.0,
            hasDecrease = true
        ),
        MobileUsageItem(
            year = "2011",
            value = 3.0,
            hasDecrease = false
        )
    )

    val getUsageParamSlot = slot<String>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun initPresenter(): MobileUsagePresenter {
        val presenter = MobileUsagePresenter(repo)
        presenter.schedulerProvider = ImmediateSchedulerProvider()
        agent.liveData.value = agent.initState(null)
        agent.bindView(view)
        agent.init()
        verify { agent.init() }
        presenter.agent = agent
        presenter.attachView(view)
        return presenter
    }

    @Test
    fun checkId() {
        val presenter = initPresenter()
        Truth.assertThat(presenter.id).isEqualTo("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
    }

    @Test
    fun load_success() {
        every {
            repo.getUsages(capture(getUsageParamSlot))
        } returns Observable.just(normalUsages)
        val presenter = initPresenter()
        presenter.load()
        verify { repo.getUsages(any()) }
        verify { view.showProgress() }
        verify { view.dismissProgress() }
        Truth.assertThat(getUsageParamSlot.captured)
            .isEqualTo("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.agent.liveData).usages.size)
            .isEqualTo(3)
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.agent.liveData).usages[1].year)
            .isEqualTo("2010")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.agent.liveData).usages[1].hasDecrease)
            .isEqualTo(true)
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.agent.liveData).usages.count { it.hasDecrease })
            .isEqualTo(1)
    }

    @Test
    fun load_fail(){
        every {
            repo.getUsages(capture(getUsageParamSlot))
        } returns Observable.error(ServerException("服务器异常"))
        val presenter = initPresenter()
        presenter.load()
        verify { repo.getUsages(any()) }
        verify { view.showProgress() }
        verify { view.dismissProgress() }
        Truth.assertThat(getUsageParamSlot.captured)
            .isEqualTo("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
        Truth.assertThat(LiveDataTestUtil.getValue(presenter.agent.liveData).usages.size)
            .isEqualTo(0)
    }
}