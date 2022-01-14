package com.digital.playground.di

import com.digital.playground.network.ApiService
import com.digital.playground.repository.MovieRepository
import com.digital.playground.repository.mapper.MovieMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/**
 * @Details RepositoryModule
 * @Author Roshan Bhagat
 */
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
        movieMapper: MovieMapper
    ): MovieRepository {
        return MovieRepository(apiService, movieMapper)
    }
}