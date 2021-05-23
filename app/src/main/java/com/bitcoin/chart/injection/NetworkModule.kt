package com.bitcoin.chart.injection

import com.bitcoin.chart.network.interceptor.NoConnectivityInterceptor
import com.bitcoin.chart.network.service.BitcoinService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(noConnectivityInterceptor: NoConnectivityInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(noConnectivityInterceptor)
        builder.hostnameVerifier { _, _ -> true }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideOrderAPI(retrofit: Retrofit): BitcoinService =
        retrofit.create(BitcoinService::class.java)

    @Provides
    @Singleton
    fun provideBaseUrl() = "https://api.blockchain.info"

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}