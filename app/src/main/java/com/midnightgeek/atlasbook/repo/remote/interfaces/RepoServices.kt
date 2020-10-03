package com.midnightgeek.atlasbook.repo.remote.interfaces

import com.midnightgeek.atlasbook.repo.remote.models.ModelBookAPI
import com.midnightgeek.atlasbook.repo.remote.models.ModelCatrgoryAPI
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoServices {
    @GET("/catglist")
    fun getCategory(): Observable<List<ModelCatrgoryAPI>>

    @GET("/recent")
    fun getRecent(): Observable<List<ModelBookAPI>>

    @GET("/recom")
    fun getRecom(): Observable<List<ModelBookAPI>>

    @GET("/booklist")
    fun getBooklist(@Query("cid") cid: Int): Observable<List<ModelBookAPI>>

}