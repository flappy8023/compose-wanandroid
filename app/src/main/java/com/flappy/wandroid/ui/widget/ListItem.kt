package com.flappy.wandroid.ui.widget

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.flappy.wandroid.data.bean.Article

/**
 * Article item
 *
 * @param article
 * @param modifier
 * @param pinToTop 是否置顶文章
 */
@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    pinToTop: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null
) {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    onClick?.invoke()
                }, onLongPress = {
                    onLongPress?.invoke()
                })
            }
    ) {
        val (author, date, title, tags, topFlag) = createRefs()
        Text(
            color = MaterialTheme.colorScheme.primary,
            text = article.author,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.constrainAs(author) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })
        Text(
            text = article.niceDate,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }, color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = Html.fromHtml(article.title,Html.FROM_HTML_MODE_LEGACY).toString(),
            maxLines = 2,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(author.bottom, margin = 4.dp)
                }
                .fillMaxWidth()
                .wrapContentHeight()
        )
        if (pinToTop) {
            Text(
                text = "Top", modifier = Modifier
                    .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 2.dp, vertical = 1.dp)
                    .constrainAs(topFlag) {
                        top.linkTo(title.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                    },
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red
            )
        }
        Text(
            text = "${article.superChapterName}/${article.chapterName}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(tags) {
                top.linkTo(topFlag.top)
                bottom.linkTo(topFlag.bottom)
                start.linkTo(topFlag.end, margin = 20.dp)
            }
        )

    }
}


@Preview
@Composable
fun ArticlePreview() {
    ArticleItem(
        modifier = Modifier
            .background(Color.White),
        article = Article(
            true,
            "",
            title = "分三六九等法律解释劳动法",
            shareUser = "弘扬",
            niceDate = "2023-12-12 10:10",
            superChapterName = "广场",
            chapterName = "自主"
        ), pinToTop = true
    )
}