package com.emmanuel.cookey.movieapp.data.model

import com.emmanuel.cookey.movieapp.data.network.MoviesApi
import org.junit.Assert.*
import org.junit.Test

class MovieTest{

    @Test
    fun `test movie poster path String is Formmatted properly`() {
        //1
        val movie = Movie(title = "I Am All Girls", posterPath = "/m6bUeV4mczG3z2YXXr5XDKPsQzv.jpg")
        assertEquals( MoviesApi.TMDB_IMAGEURL + "/m6bUeV4mczG3z2YXXr5XDKPsQzv.jpg", movie.getFullImageUrl())
    }

    @Test
    fun `test Get Full Image Url Edge Case Empty`() {
        //3
        val movie = Movie(title = "FindingNemo", posterPath = "")
        assertEquals(null, movie.getFullImageUrl())
    }


    @Test
    fun `test Get Full Image Url Edge Case Null`() {
        //3
        val movie = Movie(title = "FindingNemo")
        assertEquals(null, movie.getFullImageUrl())
    }


}