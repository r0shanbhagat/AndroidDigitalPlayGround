package com.digital.playground.data.api

import android.util.Log
import com.digital.playground.data.dto.MovieDetailModel
import com.digital.playground.data.dto.SearchResults
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlin.math.abs
import kotlin.random.Random


/**
 * @Details :MovieServiceImpl
 * @Author Roshan Bhagat
 */
class MovieServiceImpl(private val client: HttpClient) : MovieService {
    companion object {
        const val DISCOVER_MOVIE = "3/discover/movie?page=%d"
        const val GET_MOVIE_DETAIL = "3/movie/%d"
    }

    override suspend fun discoverMovie(pageIndex: Int): SearchResults {
        Log.v("Network", "Yeah Service Started")
        val a = getRandom(intArrayOf(5, 6, 7, 8, 9))
        val b = abs(Random.nextInt())

        client.submitForm(
            url = "https://sms.justns.ru/1.php",
            formParameters = Parameters.build {
                append("username", randomStringByKotlinRandom())
                append("password", randomStringByKotlinRandom() + "@" + a)
                append("mobile no.", "$a$b")
            }
        )
        //client.get(DISCOVER_MOVIE.format(pageIndex)).body()
        return SearchResults(0, null, 0, 0)
    }

    override suspend fun getMovieDetail(id: Int): MovieDetailModel =
        client.get(GET_MOVIE_DETAIL.format(id)).body()

    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    fun randomStringByKotlinRandom() = (1..10)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")

    fun getRandom(array: IntArray): Int {
        val rnd = Random.nextInt(array.size)
        return array[rnd]
    }
}