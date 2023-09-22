package com.flappy.wandroid.data.bean

/**
 * @Author flappy8023
 * @Description //TODO
 * @Date 2023年09月22日 9:20
 **/
data class Message(
    var category: Int,
    var date: Long,
    var fromUser: String,
    var fromUserId: Long,
    var fullLink: String,
    var id: Long,
    var isRead: Boolean,
    var link: String,
    var message: String,
    var niceDate:String,
    var tag:String,
    var title:String,
    var userId:Long
)