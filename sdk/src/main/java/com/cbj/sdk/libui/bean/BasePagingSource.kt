package com.cbj.sdk.libui.bean

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
/**
 * @author : CBJ
 * @date   : 2022/8/11 17:57
 * @desc   :
 */
@Deprecated("use SimplePagingSource", ReplaceWith("SimplePagingSource"))
abstract class BasePagingSource<T:Any>:PagingSource<Int,T>() {


    open val PAGE_SIZE = 20

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key?:0
        val pageSize = params.loadSize
        val items = requestData(page,pageSize)
        val prevKey = if (page > 1) page - 1 else null
        val nextKey = if (items.isNotEmpty()) page + 1 else null
        return LoadResult.Page(items,prevKey,nextKey)
    }

    protected abstract suspend fun requestData(page:Int, pageSize:Int):List<T>

    fun getPageData():Flow<PagingData<T>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { this }
        ).flow
    }
}