package com.example.assignmentbookxpert.di

import android.content.Context
import androidx.room.Room
import com.example.assignmentbookxpert.Database.AssignmentDatabase
import com.example.assignmentbookxpert.dao.MasterDao
import com.example.assignmentbookxpert.entitty.ApiEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideBaseUrl() = "https://api.restful-api.dev/"

    @Provides
    @Singleton
    fun provideApiService(@Named("baseUrl") baseUrl: String): ApiEntity =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiEntity::class.java)


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AssignmentDatabase {
        return Room.databaseBuilder(
            context,
            AssignmentDatabase::class.java,
            "assignment_database"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AssignmentDatabase): MasterDao = db.masterDao()
}