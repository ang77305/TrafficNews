package com.fiona.trafficnews.data

data class NewsDataModel(
    val News: ArrayList<New>,
    val updateTime: String
)

data class New(
    val chtmessage: String,
    val content: String,
    val endtime: String,
    val engmessage: String,
    val starttime: String,
    val updatetime: String,
    val url: String
)