package com.midnightgeek.atlasbook.repo.remote

import com.midnightgeek.atlasbook.repo.remote.interfaces.RepoServices
import com.midnightgeek.atlasbook.repo.remote.models.ModelBookAPI
import com.midnightgeek.atlasbook.repo.remote.models.ModelCatrgoryAPI
import io.reactivex.Observable
import javax.inject.Inject

class ApiHandler @Inject constructor(var repoService: RepoServices) {

    fun getCategoryList(): Observable<List<ModelCatrgoryAPI>> {
        return this.repoService.getCategory()
    }

    fun getRecentList(): Observable<List<ModelBookAPI>> {
        return this.repoService.getRecent()
    }

    fun getRecomList(): Observable<List<ModelBookAPI>> {
        return this.repoService.getRecom()
    }

    fun getBookList(cid: Int): Observable<List<ModelBookAPI>> {
        return this.repoService.getBooklist(cid)
    }

}