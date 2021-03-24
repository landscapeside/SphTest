package com.landside.sphtest.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.landside.support.extensions.SP_NAME_DEFAULT
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tech.linjiang.pandora.Pandora
import timber.log.Timber
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class AppModule {

    /**
     * 实际网络请求类
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        sslSocketFactory: SSLSocketFactory?
    ): OkHttpClient {
        /** 手动创建一个OkHttpClient并设置参数  */
        val builder = Builder()
        /** 设置请求时间  */
        builder.connectTimeout(15, SECONDS)
        builder.readTimeout(20, SECONDS)
        builder.writeTimeout(20, SECONDS)
        /** 设置忽略https安全证书验证  */
        builder.hostnameVerifier { hostname, session -> true }
        builder.sslSocketFactory(sslSocketFactory)
        /** 通过Interceptor实现url参数的添加  */ //        builder.addInterceptor(new UrlParamsInterceptor()); //使用请开启
        /** 通过Interceptor来定义静态请求头  */ //        builder.addInterceptor(new HeaderInterceptor()); //使用请开启
        /** 添加日志拦截器  */
        val loggingInterceptor =
            HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message: String? ->
                    //打印retrofit日志
                    Timber.i(message)
                }
            )
        loggingInterceptor.level = BODY
        builder.addInterceptor(loggingInterceptor)
        Pandora.get()
            ?.let {
                builder.addInterceptor(Pandora.get().interceptor)
            }
        //builder.addInterceptor(nullInterceptor);
        return builder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient?,
        gson: Gson?
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
    }

    @Provides @Singleton fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    /**
     * 信任https
     */
    @Provides
    @Singleton
    fun provideSSLSocketFactory(context: Context?): SSLSocketFactory {
        var sc: SSLContext? = null
        try {
            sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(
                        CertificateException::class
                    )
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(
                        CertificateException::class
                    )
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            ), SecureRandom())
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return sc!!.socketFactory
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE)
    }
}