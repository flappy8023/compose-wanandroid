package com.flappy.wandroid.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.flappy.wandroid.R


@Composable
fun HomeAppToolbar(title: String) {
    ConstraintLayout(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {
        val (searchIcon, titleText, userAvatar) = createRefs()
        Icon(
            painter = painterResource(id = R.drawable.ic_round_search_24),
            contentDescription = "",
            modifier = Modifier.constrainAs(searchIcon) {
                start.linkTo(parent.start, margin = 20.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
        Text(
            text = title,
            fontSize = 20.sp,
            maxLines = 1,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        Icon(
            painter = painterResource(id = R.drawable.icon_user_default),
            contentDescription = "",
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .constrainAs(userAvatar) {
                    end.linkTo(parent.end, margin = 20.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}