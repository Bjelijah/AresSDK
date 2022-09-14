package com.cbj.sdk.libui.bean

import androidx.paging.*
import kotlinx.coroutines.flow.Flow

/**
 * @author : CBJ
 * @date   : 2022/8/12 14:48
 * @desc   :
 */
open class SimplePagingSource<T:Any>(
    val requestData:suspend (page:Int, pageSize:Int)->List<T>
): PagingSource<Int, T>() {


    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key?:0
        val pageSize = params.loadSize
        val items = requestData(page,pageSize)
        val prevKey = if (page > 1) page - 1 else null
        val nextKey = if (items.isNotEmpty()) page + 1 else null
        return LoadResult.Page(items,prevKey,nextKey)
    }
}

fun <T:Any>getPageData(requestData:suspend (page:Int, pageSize:Int)->List<T>,pageSize:Int = 20): Flow<PagingData<T>> {
    return Pager(
        config = PagingConfig(pageSize,pageSize),
        pagingSourceFactory = { SimplePagingSource(requestData) }
    ).flow
}