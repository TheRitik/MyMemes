package com.example.useapi
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import layout.MySingleton

class MainActivity() : AppCompatActivity() {
    val currentImageUrl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Loadmeme()
    }
    private fun Loadmeme() {
        // Instantiate the RequestQueue.
        val progress = findViewById<ProgressBar>(R.id.progress)
         progress.visibility= View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"
        val memeimage = this.findViewById<ImageView>(R.id.memeimage)
// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val url  = response.getString("url")
                 Glide.with(this).load(url).listener(object : RequestListener<Drawable>{
                     override fun onLoadFailed(e: GlideException?,
                         model: Any?,
                         target: Target<Drawable>?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progress.visibility = View.GONE
                         return false
                     }
                     override fun onResourceReady(
                         resource: Drawable?,
                         model: Any?,
                         target: Target<Drawable>?,
                         dataSource: DataSource?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progress.visibility = View.GONE
                         return false
                     }
                 }).into(memeimage)
                // Display the first 500 characters of the response string.
            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun NextMeme(view: android.view.View) {
        Loadmeme()
    }
    fun ShareMeme(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey , Checkout this I got for you $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this on ...")
        startActivity(chooser)
    }
}