package com.emmanuel.cookey.movieapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.emmanuel.cookey.movieapp.data.db.MovieDatabase
import com.emmanuel.cookey.movieapp.data.model.Movie
import com.emmanuel.cookey.movieapp.data.model.MoviesResponse
import com.emmanuel.cookey.movieapp.data.network.MoviesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    private lateinit var repository: MovieRepository

    @Mock
    lateinit var api: MoviesApi

    @Mock
    lateinit var db: MovieDatabase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        repository = MovieRepository(db, api)
    }


    @Test
    fun `test that the MoviesResponse Model from MovieApi call is being proccessed to List of Movie Model as represented in Database`(){

        val movieResponse = MoviesResponse(1, 10000, 500, mutableListOf<Movie>())

        assertEquals(repository.processData(movieResponse), mutableListOf<Movie>())

    }

}