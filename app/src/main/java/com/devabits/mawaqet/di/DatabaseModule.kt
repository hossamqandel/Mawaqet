package com.devabits.mawaqet.di

import android.content.Context
import androidx.room.Room
import com.devabits.mawaqet.core.constants.local.RoomUtil
import com.devabits.mawaqet.core.local.room.MawaqetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMawaqetDatabase(
        @ApplicationContext context: Context
    ): MawaqetDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MawaqetDatabase::class.java,
            name = RoomUtil.DATABASE_NAME
        ).build()
    }

}