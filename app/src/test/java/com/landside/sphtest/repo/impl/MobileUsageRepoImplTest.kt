package com.landside.sphtest.repo.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.gson.reflect.TypeToken
import com.landside.rehost.JSONS
import com.landside.sphtest.MockFileReader
import com.landside.sphtest.apis.MobileUsageApi
import com.landside.sphtest.apis.RetrofitService
import com.landside.sphtest.domin.MobileUsageItem
import com.landside.sphtest.dto.MobileUsageDto
import com.landside.support.exceptions.ServerException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MobileUsageRepoImplTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var service: RetrofitService

    @MockK
    private lateinit var api: MobileUsageApi

    val getUsageParamSlot = slot<String>()

    val normalDto: MobileUsageDto? = JSONS.parseObject(
        MockFileReader.getFileAsString(
            javaClass.classLoader!!.getResourceAsStream("mobile.json")
        ),
        object : TypeToken<MobileUsageDto>() {}.type
    )

    val errDto: MobileUsageDto? = JSONS.parseObject(
        MockFileReader.getFileAsString(
            javaClass.classLoader!!.getResourceAsStream("mobile_err.json")
        ),
        object : TypeToken<MobileUsageDto>() {}.type
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every {
            service.createApi(MobileUsageApi::class.java)
        } returns api
    }

    @Test
    fun getUsages_success() {
        every {
            api.getUsages(capture(getUsageParamSlot))
        } returns Observable.just(normalDto)
        val repo = MobileUsageRepoImpl(service)
        val testObserver = TestObserver<List<MobileUsageItem>>()
        repo.getUsages("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
            .subscribe(testObserver)
        testObserver.assertValue {
            verify { api.getUsages(any()) }
            Truth.assertThat(getUsageParamSlot.captured)
                .isEqualTo("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
            Truth.assertThat(it.size)
                .isEqualTo(11)
            Truth.assertThat(it.count { it.hasDecrease }).isEqualTo(3)
            Truth.assertThat(it[3].year).isEqualTo("2011")
            Truth.assertThat(it[3].hasDecrease).isEqualTo(true)
            Truth.assertThat(it[3].value).isEqualTo(14.638703)
            true
        }
    }

    @Test
    fun getUsages_fail() {
        every {
            api.getUsages(capture(getUsageParamSlot))
        } returns Observable.just(errDto)
        val repo = MobileUsageRepoImpl(service)
        val testObserver = TestObserver<List<MobileUsageItem>>()
        repo.getUsages("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
            .subscribe(testObserver)
        testObserver.assertError {
            Truth.assertThat(it).isInstanceOf(ServerException::class.java)
            Truth.assertThat(it.message).isEqualTo("服务器异常")
            true
        }
    }
}