package com.grupo3.historyar.di

import com.grupo3.historyar.data.network.api.clients.QualificationApiClient
import com.grupo3.historyar.data.network.api.clients.PointOfInterestApiClient
import com.grupo3.historyar.data.network.api.clients.RouteApiClient
import com.grupo3.historyar.data.network.api.clients.SubscriptionApiClient
import com.grupo3.historyar.data.network.api.clients.TourApiClient
import com.grupo3.historyar.data.network.api.clients.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private var routeApiRetrofit: Retrofit? = null
    private val okHttpClient = OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(true)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://tesis-web.onrender.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserApiClient(retrofit: Retrofit): UserApiClient {
        return retrofit.create(UserApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideTourApiClient(retrofit: Retrofit): TourApiClient {
        return retrofit.create(TourApiClient::class.java)
    }

    @Singleton
    @Provides
    fun providePointOfInterestApiClient(retrofit: Retrofit): PointOfInterestApiClient {
        return retrofit.create(PointOfInterestApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideQualificationApiClient(retrofit: Retrofit): QualificationApiClient {
        return retrofit.create(QualificationApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideQSubscriptionApiClient(retrofit: Retrofit): SubscriptionApiClient {
        return retrofit.create(SubscriptionApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRouteApiClient(): RouteApiClient {
        if (routeApiRetrofit == null) {
            routeApiRetrofit = Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return routeApiRetrofit!!.create(RouteApiClient::class.java)
    }
}