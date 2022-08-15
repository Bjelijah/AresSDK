package com.cbj.aresandroid.demo.di.module

import com.blankj.utilcode.util.Utils
import com.cbj.aresandroid.demo.api.ApiService
import com.cbj.sdk.libnet.http.NetManager
import com.cbj.sdk.libnet.http.provider.BaseNetProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideDemoService(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java
    )


    @Provides
    @Singleton
    fun provideRetrofit():Retrofit = NetManager.getRetrofit(
        "http://118.178.193.58:8080/tyh/",
        BaseNetProvider(Utils.getApp())
    )

}