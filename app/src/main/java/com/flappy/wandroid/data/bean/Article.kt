package com.flappy.wandroid.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: luweiming
 * @Description:首页相关接口响应数据定义
 * @Date: Created in 17:00 2022/8/22
 */

data class Article(
    val adminAdd: Boolean = false,
    val apkLink: String = "",
    val audit: String = "",
    val author: String = "",
    val canEdit: Boolean = false,
    val chapterId: Long = 0,
    val chapterName: String = "",
    val collect: Boolean = false,
    val courseId: Long = 0,
    val desc: String = "",
    val descMd: String = "",
    val envelopePic: String = "",
    val fresh: Boolean = false,
    val host: String = "",
    val id: Long = 0,
    val link: String = "",
    val niceDate: String = "",
    val niceShareDate: String = "",
    val origin: String = "",
    val prefix: String = "",
    val projectLink: String = "",
    val publishTime: Long = 0,
    val realSuperChapterId: Long = 0,
    val selfVisible: Int = 0,
    val shareDate: Long = 0,
    val shareUser: String = "",
    val superChapterId: Long = 0,
    val superChapterName: String = "",
    val tag: List<ArticleTag>? = null,
    val title: String = "",
    val type: Int = 0,
    val userId: Long = 0,
    val visible: Int = 0,
    val zan: Long = 0,
    val indexInResponse: Int = -1,
    @PrimaryKey(autoGenerate = true)
    val aId: Int = 0
)

data class ArticleTag(var url: String, var name: String)

@Entity
data class BannerItem(
    var desc: String,
    var id: Long,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
)

data class CommonWebSite(
    var category: String,
    var icon: String,
    var id: Int,
    var link: String,
    var name: String,
    var order: Int,
    var visible: Int
)


data class TreeItem(
    var articleList: List<Article>,
    var author: String,
    var children: List<TreeItem>,
    var courseId: Long,
    var cover: String,
    var desc: String,
    var id: Long,
    var lisense: String,
    var lisenseLink: String,
    var name: String,
    var order: Long,
    var parentChapterId: Long,
    var type: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)

