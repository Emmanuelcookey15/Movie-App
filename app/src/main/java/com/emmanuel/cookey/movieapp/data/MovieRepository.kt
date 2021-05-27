/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.emmanuel.cookey.movieapp.data


import androidx.room.withTransaction
import com.emmanuel.cookey.movieapp.data.db.MovieDatabase
import com.emmanuel.cookey.movieapp.data.model.MoviesResponse
import com.emmanuel.cookey.movieapp.data.network.MoviesApi
import com.emmanuel.cookey.movieapp.data.model.Movie
import com.emmanuel.cookey.movieapp.util.Resource
import com.emmanuel.cookey.movieapp.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(
  private val db: MovieDatabase,
  private val api: MoviesApi
) {

  private val movieDao = db.movieDao()



  fun getMovies(onFetchSuccess: () -> Unit,
                   onFetchFailed: (Throwable) -> Unit
  ): Flow<Resource<List<Movie>>> = networkBoundResource(
    query = {
      movieDao.getAll()
    },
    fetch = {
      delay(2000)
      api.getMovies(MoviesApi.API_KEY)
    },
    saveFetchResult = {

      val movieList = processData(it)

      db.withTransaction {
        movieDao.deleteMovies()
        movieDao.insertMovies(movieList)
      }
    },
    onFetchSuccess = onFetchSuccess,
    onFetchFailed = { t ->
      if (t !is HttpException && t !is IOException) {
        throw t
      }
      onFetchFailed(t)
    }
  )

  fun processData(moviesResponse: MoviesResponse): List<Movie> {

    return moviesResponse.results!!

  }


}