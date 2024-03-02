package com.grupo3.historyar.di

import com.grupo3.historyar.data.network.api.clients.QualificationApiClient
import com.grupo3.historyar.data.network.api.clients.PointOfInterestApiClient
import com.grupo3.historyar.data.network.api.clients.TourApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
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
    fun provideCalificationApiClient(retrofit: Retrofit): QualificationApiClient {
        return retrofit.create(QualificationApiClient::class.java)
    }
}