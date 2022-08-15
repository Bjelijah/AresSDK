package com.cbj.aresandroid.demo.api.bean


/**
 * @author : leo
 * @date : 2020/5/12
 */
data class NewsBean(
    var date: String? = "",
    var stories: List<StoriesBean> = emptyList()
) {

    data class StoriesBean(
        var type: Int = 0,
        var id: Int = 0,
        var ga_prefix: String? = null,
        var title: String? = null,
        var url: String? = null,
        var images: List<String>? = null
    )
}