package com.ahmedwagdy.retrofitdemo

import com.ahmedwagdy.retrofitdemo.models.Comment
import com.ahmedwagdy.retrofitdemo.models.Post
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    /**
     * this Query parameters which consist of name and value
     * this writes in Url like comments?postId=1
     */
    @GET("comments")
    fun getCommentsWherePostId(@Query("postId") postId: Int): Call<List<Comment>>

    /**
     *  We can put more than one parameter like comments?postId=1&_sort=id&_order=desc
     *  we can pass null in the parameter that we don't want to use
     *  we can repeat the parameter that we want to fetch more than one
     *  for example if i want to get the the postId=1 and postId=2
     *  in this case we repeat the postId parameter or use the List or array
     *  or pass the vararg but should be the latest parameter in the method
     */
    @GET("comments")
    fun getCommentsWithMultipleParameter(
            @Query("postId") postId: Int?,
            @Query("_sort") sort: String?,
            @Query("_order") order: String?): Call<List<Comment>>


    @GET("comments")
    fun getCommentsWithMultipleParameter(
            @Query("postId") postId1: Int?,
            @Query("postId") postId2: Int?,
            @Query("_sort") sort: String?,
            @Query("_order") order: String?): Call<List<Comment>>

    @GET("comments")
    fun getCommentsWithVararg(
            @Query("_sort") sort: String?,
            @Query("_order") order: String?,
            @Query("postId") vararg postId: Int?): Call<List<Comment>>

    @GET("comments")
    fun getCommentsWithList(
            @Query("postId") postId: ArrayList<Int?>,
            @Query("_sort") sort: String?,
            @Query("_order") order: String?): Call<List<Comment>>

    /**
     * in this case we will pass the map which contain the name and value
     * without specify them here
     */
    @GET("comments")
    fun getCommentsWithMap(@QueryMap parameters: Map<String, String>): Call<List<Comment>>

    // here we just pass the url where the comments will come from
    @GET
    fun getCommentsWithUrl(@Url url: String): Call<List<Comment>>

    // the same like Queries we can add multiple paths in url {}/{} etc...
    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Call<List<Comment>>

    /**
     * pass data to the server the Gson converter automatically convert
     * this post to json and send it to the server
     */
    @POST("posts")
    fun createPost(@Body post: Post): Call<Post>

    /**
     * another way to send data to the server by using FormUrlEncoded with separated fields
     * the url will be like this userId=23&New20%Title&body=New20%Text
     */
    @FormUrlEncoded
    @POST("posts")
    fun createPost(
            @Field("userId") userId: Int,
            @Field("title") title: String,
            @Field("body") text: String): Call<Post>

    // we can pass fields in map or in list or vararg like the Query
    @FormUrlEncoded
    @POST("posts")
    fun createPost(@FieldMap field: Map<String,String>): Call<Post>

    /**
     * ***************************************************************
     *  the following methods used to update data on the server
     * ***************************************************************
     */

    /**
     * this will replace the post which the same id with the data in post object
     * and if the object dose not exist maybe will create new one or not this depend on the server
     * if you put id that not exist you will get 404 response
     */
    @PUT("posts/{id}")
    fun putPost(@Path("id") id:Int, @Body post:Post): Call<Post>

    // to pass the header in url like password
    @Headers("Static-Header1: 123", "Static-Header2: 456")
    @PUT("posts/{id}")
    fun putPost(@Header("Dynamic-Header") header:String,
                @Path("id") id:Int,
                @Body post:Post): Call<Post>

    // this will modify the post that has the same id with the data in post object
    @PATCH("posts/{id}")
    fun patchPost(@Path("id") id:Int, @Body post:Post): Call<Post>

    @PATCH("posts/{id}")
    fun patchPost(@HeaderMap headers: Map<String, String>,
                  @Path("id") id:Int,
                  @Body post:Post): Call<Post>

    // this will delete the post that has the same id
    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id:Int): Call<Void>

}