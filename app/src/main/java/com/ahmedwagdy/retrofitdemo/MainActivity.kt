package com.ahmedwagdy.retrofitdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmedwagdy.retrofitdemo.databinding.ActivityMainBinding
import com.ahmedwagdy.retrofitdemo.models.Comment
import com.ahmedwagdy.retrofitdemo.models.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getPosts()
        // getComments("id","desc",2,3)
        //createPost()
        //updatePost()
        //deletePost()
    }

    private fun getPosts() {

        ServiceBuilder.call.enqueue(object : Callback<List<Post>> {

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    binding.textViewResult.text = "Code: ${response.code()}"
                    return
                }
                val posts: List<Post>? = response.body()
                if (posts != null)
                    for (post in posts) {
                        var content = ""
                        content += "ID: " + post.id + "\n"
                        content += "User ID: " + post.userId + "\n"
                        content += "Title: " + post.title + "\n"
                        content += "Text: " + post.text + "\n\n"

                        binding.textViewResult.append(content)
                    }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                binding.textViewResult.text = t.message
            }

        })
    }


    private fun getComments(sort: String?, order: String?, vararg postId: Int?) {

        val list = arrayListOf<Int?>(1, 2)
        val parameters = HashMap<String, String>()
        parameters["postId"] = "1";
        parameters["_sort"] = "id";
        parameters["_order"] = "desc";

        val url: String = "posts/1/comments"

        val call: Call<List<Comment>> = ServiceBuilder.apiInterface
            //.getComments(1)
            // .getCommentsWherePostId(1)
            //  .getCommentsWithVararg(sort, order, *postId)
            //  .getCommentsWithList(list,sort, order)
            //  .getCommentsWithMap(parameters)
            .getCommentsWithUrl("posts/1/comments")

        call.enqueue(object : Callback<List<Comment>> {

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (!response.isSuccessful) {
                    binding.textViewResult.text = "Code: ${response.code()}"
                    return
                }
                val comments: List<Comment>? = response.body()
                if (comments != null)
                    for (comment in comments) {
                        var content = ""
                        content += "ID: " + comment.id + "\n"
                        content += "Post ID: " + comment.postId + "\n"
                        content += "Name: " + comment.name + "\n"
                        content += "Email: " + comment.email + "\n"
                        content += "Text: " + comment.text + "\n\n"

                        binding.textViewResult.append(content)
                    }


            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                binding.textViewResult.text = t.message
            }

        })
    }

    private fun createPost() {
        val post = Post(23, null, "new title", "new text")

        val fields = HashMap<String, String>()
        fields["userId"] = "12"
        fields["title"] = "welcome"
        fields["body"] = "new body"

        val call: Call<Post> = ServiceBuilder.apiInterface
            //.createPost(post)
            //.createPost(23,"New Title","New Text")
            .createPost(fields)

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    binding.textViewResult.text = "Code: ${response.code()}"
                    return
                }
                val postResponse: Post? = response.body()
                if (postResponse != null) {
                    var content = ""
                    content += "Code: " + response.code() + "\n"
                    content += "ID: " + postResponse.id + "\n"
                    content += "User ID: " + postResponse.userId + "\n"
                    content += "Title: " + postResponse.title + "\n"
                    content += "Text: " + postResponse.text + "\n\n"
                    binding.textViewResult.text = content
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                binding.textViewResult.text = t.message
            }

        })
    }

    private fun updatePost() {
        val post = Post(12, null, null, "New Text")

        val headers = HashMap<String, String>()
        headers["Map-Header1"] = "def"
        headers["Map-Header2"] = "ghi"

        val call: Call<Post> = ServiceBuilder.apiInterface
            //.putPost(5,post)
            // .patchPost(5,post)
            //.putPost("abc", 5, post)
            .patchPost(headers, 5, post)

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    binding.textViewResult.text = "Code: ${response.code()}"
                    return
                }
                val postResponse: Post? = response.body()
                if (postResponse != null) {
                    var content = ""
                    content += "Code: " + response.code() + "\n"
                    content += "ID: " + postResponse.id + "\n"
                    content += "User ID: " + postResponse.userId + "\n"
                    content += "Title: " + postResponse.title + "\n"
                    content += "Text: " + postResponse.text + "\n\n"
                    binding.textViewResult.text = content
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                binding.textViewResult.text = t.message
            }

        })

    }

    private fun deletePost() {
        val call: Call<Void> = ServiceBuilder.apiInterface.deletePost(2)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                binding.textViewResult.text = "Code: ${response.code()}"
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.textViewResult.text = t.message
            }

        })
    }

}