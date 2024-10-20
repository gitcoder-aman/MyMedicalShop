package com.tech.mymedicalshopuser.di

import android.content.Context
import androidx.room.Room
import com.tech.mymedicalshopuser.data.services.ApiServices
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.data.repository.MedicalRepositoryImpl
import com.tech.mymedicalshopuser.local.dao.AddressDao
import com.tech.mymedicalshopuser.local.dao.CartDao
import com.tech.mymedicalshopuser.local.database.AppDatabase
import com.tech.mymedicalshopuser.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "medical_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }
    @Provides
    @Singleton
    fun provideAddressDao(database: AppDatabase) : AddressDao{
        return database.addressDao()
    }

}