package com.flappy.wandroid.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flappy.wandroid.R

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


@Preview
@Composable
fun ErrorPage(retry: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.error_24),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = stringResource(id = R.string.list_content_error),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 20.dp)
            )
            OutlinedButton(
                onClick = { retry() },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.list_content_retry),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary

                )
            }
        }
    }
}


@Composable
fun EmptyPage(hint: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_empty),
            contentDescription = null,
            alignment = Alignment.Center
        )
        Text(text = hint, style = MaterialTheme.typography.bodySmall)
    }
}