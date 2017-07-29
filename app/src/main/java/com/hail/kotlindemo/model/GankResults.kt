package com.hail.kotlindemo.model

import com.ly.kotlindemo.model.BaseModel

/**
 * Created by Ly on 2017/6/22.
 */

class GankResults : BaseModel() {

    var results: List<Item>? = null


    class Item {
        var _id: String? = null
        var _ns: String? = null
        var createdAt: String? = null
        var desc: String? = null
        var publishedAt: String? = null
        var source: String? = null
        var type: String? = null
        var url: String? = null
        var used: String? = null
        var who: String? = null
    }

}
