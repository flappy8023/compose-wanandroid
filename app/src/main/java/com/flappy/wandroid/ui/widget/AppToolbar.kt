package com.flappy.wandroid.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
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
            modifier = Modifier
                .constrainAs(searchIcon) {
                    start.linkTo(parent.start, margin = 20.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .size(24.dp)
        )
        Text(
            text = title,
            fontSize = 20.sp,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 60.dp)
                .constrainAs(titleText) {
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

@Composable
fun AppToolbar(
    navController: NavController,
    title: String,
    rightIcon: Int = 0,
    clickRight: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {
        val (searchIcon, titleText, userAvatar) = createRefs()
        Icon(
            painter = painterResource(id = R.drawable.round_arrow_back_24),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(searchIcon) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .size(24.dp)
                .clickable {
                    //返回
                    navController.navigateUp()
                })
        Text(
            text = title,
            fontSize = 20.sp,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 60.dp)
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        if (rightIcon != 0) {
            Icon(
                painter = painterResource(id = rightIcon),
                contentDescription = "",
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .constrainAs(userAvatar) {
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        clickRight()
                    }
            )
        }
    }
}