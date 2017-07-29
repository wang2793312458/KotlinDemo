package com.ly.kotlindemo.net

import com.hail.kotlindemo.model.GankResults
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Shinelon on 2017/6/23.
 */

interface ApiServices {
    @GET("data/{type}/{number}/{page}")
    fun getGankData(@Path("type") type: String,
                    @Path("number") pageSize: Int,
                    @Path("page") pageNum: Int): Flowable<GankResults>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): ApiServices {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://gank.io/api/")
                    .build()
            return retrofit.create(ApiServices::class.java)
        }
    }

    class SearchRepository(val apiService: ApiServices) {
       public object SearchRepositoryProvider {
            fun provideSearchRepository(): SearchRepository {
                return SearchRepository(create())
            }
        }
        fun searchUsers(type: String, number: Int, page: Int): Flowable<GankResults> {
            return apiService.getGankData(type, number, page)
        }
    }
}
