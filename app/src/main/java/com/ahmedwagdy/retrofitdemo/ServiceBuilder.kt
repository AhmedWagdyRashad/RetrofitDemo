package com.ahmedwagdy.retrofitdemo

import com.ahmedwagdy.retrofitdemo.models.Comment
import com.ahmedwagdy.retrofitdemo.models.Post
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    /**
     * if you want json to serialized nulls , because json by default dose not serialized nulls
     * and if you put a null parameter in patch it will not replace it in the default behaviour
     * in this case if you pass the null parameter in patch request it will replace the field
     * in the object in the server to be null and we will have to create another models which
     * contains the only fields that we will modified
     */
    private val gson = GsonBuilder().serializeNulls().create()


    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(object: Interceptor{
            // here we add the header in all requests
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val  newRequest = originalRequest.newBuilder()
                    .header("Interceptor-Header", "xyz")
                    .build()
                return chain.proceed(newRequest)
            }

        })
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    val apiInterface = retrofit.create(ApiInterface::class.java)

    val call: Call<List<Post>> = apiInterface.getPosts()

}