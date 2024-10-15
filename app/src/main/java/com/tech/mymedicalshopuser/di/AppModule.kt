package com.tech.mymedicalshopuser.di

import android.content.Context
import android.content.SharedPreferences
import com.tech.mymedicalshopuser.data.services.ApiServices
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.data.repository.MedicalRepositoryImpl
import com.tech.mymedicalshopuser.utils.BASE_URL
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
class AppModule {

    @Provides
    @Singleton
    fun provideMedicalApi() :ApiServices{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }
    @Provides
    @Singleton
    fun provideMedicalRepository(apiServices: ApiServices) : MedicalRepository{
        return MedicalRepositoryImpl(apiServices)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context : Context): SharedPreferences {
        return context.getSharedPreferences("my_medical_pref", Context.MODE_PRIVATE)
    }

}