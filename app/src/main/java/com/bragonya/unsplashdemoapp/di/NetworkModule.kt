package com.bragonya.unsplashdemoapp.di

import com.bragonya.unsplashdemoapp.BuildConfig
import com.bragonya.unsplashdemoapp.common.BASE_URL
import com.bragonya.unsplashdemoapp.network.UnsplashAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRESTClient(
        client: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(UnsplashAPI::class.java)

    @Provides
    fun provideLogger(
        @Named("AuthInterceptor") authInterceptor: Interceptor
    ): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(authInterceptor)
        return httpClient.build()
    }

    @Provides
    @Named("AuthInterceptor")
    fun provideAuthInterceptor() = Interceptor { chain ->
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Client-ID ${BuildConfig.UNSPLASH_TOKEN}"
            )
            .build()
        chain.proceed(newRequest)
    }
}