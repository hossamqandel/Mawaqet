package com.devabits.mawaqet.di

import com.devabits.mawaqet.feature_mawaqet.data.repository.MawaqetRepositoryImpl
import com.devabits.mawaqet.feature_mawaqet.domain.repository.MawaqetRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMawaqetRepository(
        repo: MawaqetRepositoryImpl
    ): MawaqetRepository
}