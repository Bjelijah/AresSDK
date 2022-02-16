package com.cbj.aresandroid.demo.di.module

import com.cbj.aresandroid.demo.api.DemoService
import com.cbj.sdk.libnet.http.HttpManager
import com.cbj.sdk.libnet.http.helper.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideDemoService(retrofit: Retrofit): DemoService = retrofit.create(
        DemoService::class.java
    )

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit = HttpManager.instance.getRetrofit(
        "http://121.196.217.158:8980/api/",
        listOf(HeaderInterceptor().apply {
            HeaderInterceptor.VERSION_CODE = 125
        })
    )

}