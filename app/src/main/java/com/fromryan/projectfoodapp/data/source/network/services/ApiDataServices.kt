package com.fromryan.projectfoodapp.data.source.network.services

import com.fromryan.projectfoodapp.BuildConfig
import com.fromryan.projectfoodapp.data.source.network.catalog.CatalogResponse
import com.fromryan.projectfoodapp.data.source.network.category.CategoryResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiDataServices {
    @GET("category")
    suspend fun getCategories(): CategoryResponse

    @GET("listmenu")
    suspend fun getMenu(
        @Query("c") category: String? = null,
    ): CatalogResponse

    companion object {
        @JvmStatic
        operator fun invoke(): ApiDataServices {
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(ApiDataServices::class.java)
        }
    }
}
