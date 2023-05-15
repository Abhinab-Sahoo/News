package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = retrofitBuilder.build()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val retrofitData = apiInterface.getProductData("in", "36bbde316e1f44faaa0a47b17cbca829")


        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {

                val responseBody = response.body()
                val newsList = responseBody?.articles!!

                myAdapter = NewsAdapter(newsList)
                recyclerView.adapter = myAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("main activity", "onFailure: " + t.message)
            }
        })
    }
}