package com.digital.playground.domain

import com.digital.playground.contract.Repository
import com.digital.playground.contract.UseCase
import com.digital.playground.di.IoDispatcher
import com.digital.playground.utils.DataState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Details BlogContentUseCase
 * @Author Roshan Bhagat
 * { @link https://developer.android.com/kotlin/coroutines/coroutines-best-practices}
 * @property repository
 * @property ioDispatcher
 * @constructor Create Movie content use case
 */
@ViewModelScoped
class MovieUseCase @Inject constructor(
    override val repository: Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UseCase {

    /**
     * Get movie content
     *
     * In parallel, fetch tenthCharacter , everyTenthChar and distinctWordCount and return
     * when all requests complete and the data is ready
     */
    override suspend fun getMovieList(): Flow<DataState> = repository.getMovieList()

}