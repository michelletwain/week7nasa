package com.example.week5_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler

class MainActivity : AppCompatActivity() {
    var imageURL = ""
    var titleURL = ""
    var dateURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("nasaImageURL", "nasa image URL set")

        val button = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.image)
        val titleView = findViewById<TextView>(R.id.title)
        val dateView = findViewById<TextView>(R.id.date)

        getNextImage(imageView, button, titleView, dateView)
    }

    private fun getNextImage(imageView: ImageView, button: Button, titleView: TextView, dateView: TextView) {
        button.setOnClickListener {
            getNasaURL()
            titleView.text = titleURL
            dateView.text = dateURL

            Glide.with(this@MainActivity)
                .load(imageURL)
                .fitCenter()
                .into(imageView)
        }
    }

    private fun getNasaURL() {
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["count"] = "1"
        client["https://api.nasa.gov/planetary/apod?api_key=9yW5YophAkwEb3PbAdxbO9DiqeWHrmNA48dfBuZ7&count=1", params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: okhttp3.Headers?, json: JSON) {
                Log.d("Nasa", "response successful${json.jsonArray.getJSONObject(0).getString("url")}")
                imageURL = json.jsonArray.getJSONObject(0).getString("url")
                titleURL = json.jsonArray.getJSONObject(0).getString("title")
                dateURL = json.jsonArray.getJSONObject(0).getString("date")
            }

            override fun onFailure(
                statusCode: Int,
                headers: okhttp3.Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("Nasa first picture Error", response!!)
            }


        }]
    }
}
