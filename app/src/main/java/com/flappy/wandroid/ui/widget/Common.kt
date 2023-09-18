package com.flappy.wandroid.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年09月18日 17:20
 **/

@Composable
fun TabLayoutBar(
    titles: List<String>,
    checkedPosition: Int = 0,
    contentAlignment: Alignment = Alignment.Center,
    onSelect: (Int) -> Unit
) {
    Box(
        Modifier
            .height(48.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.align(contentAlignment)) {
            titles.forEachIndexed { index, title ->
                Text(
                    text = title,
                    fontSize = if (index == checkedPosition) 18.sp else 16.sp,
                    fontWeight = if (index == checkedPosition) FontWeight.ExtraBold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onSelect(index)
                        }
                )
            }
        }
    }

}