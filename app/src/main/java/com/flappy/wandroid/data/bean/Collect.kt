package com.flappy.wandroid.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: luweiming
 * @Description:
 * @Date: Created in 12:48 2022/8/23
 */
data class WebSiteBean(var desc:String,var icon:String,var id:Long,var link:String,var name:String,val order:Int,var userId:String,var visible:Int)

@Entity(tableName = "readHistory")
class ReadHistory(
    @PrimaryKey val link: String,
    val title: String,
    val time: Long,
    @androidx.annotation.IntRange(
        from = MIN_PERCENT,
        to = MAX_PERCENT
    ) var percent: Int
) {
    companion object {
        const val MIN_PERCENT = 0L
        const val MAX_PERCENT = 10000L
    }
}