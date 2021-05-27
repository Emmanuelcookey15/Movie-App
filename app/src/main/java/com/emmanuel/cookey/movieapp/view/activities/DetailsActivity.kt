package com.emmanuel.cookey.movieapp.view.activities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.emmanuel.cookey.movieapp.BaseActivity
import com.emmanuel.cookey.movieapp.R
import com.emmanuel.cookey.movieapp.data.model.Movie
import com.emmanuel.cookey.movieapp.data.network.MoviesApi
import com.emmanuel.cookey.movieapp.databinding.ActivityDetailsBinding
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*

class DetailsActivity : BaseActivity() {

    lateinit var binding: ActivityDetailsBinding

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    override fun getToolbarInstance(): Toolbar? {
        return toolbar
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentMovie = intent.getParcelableExtra<Movie>(MainActivity.INTENT_LIST_KEY)

        setUpViews(intentMovie)

    }


    fun setUpViews(movie: Movie?){

        val radius = 20f
        val decorView: View = window.decorView
        val windowBackground: Drawable = decorView.getBackground()
        binding.blurView.setupWith(decorView.findViewById(android.R.id.content))
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(false)

        if (movie != null){
            binding.detailMovieTitle.text = movie.title

            binding.detailMovieReleaseDate.text = movie.releaseDate

            binding.detailMovieDescription.text = movie.overview


            Glide.with(this)
                .asBitmap()
                .load(movie.getFullImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : CustomTarget<Bitmap?>() {

                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        val bitmapDrawable = BitmapDrawable(this@DetailsActivity.resources, resource)
                        binding.detailLayout.background = bitmapDrawable
                    }
                })


        }

    }
}