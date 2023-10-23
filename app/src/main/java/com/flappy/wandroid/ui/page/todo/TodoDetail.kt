package com.flappy.wandroid.ui.page.todo

import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.flappy.wandroid.R
import com.flappy.wandroid.data.bean.Todo
import com.flappy.wandroid.utils.DateUtil

/**
 * @Author: luweiming
 * @Date: 2023/10/23 20:46
 * @Description:
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetail(todo: Todo? = null, viewModel: TodoViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val isCreate = todo == null
    var titleStr = todo?.title ?: ""
    var dateStr = todo?.dateStr ?: DateUtil.parseToString(System.currentTimeMillis())
    var contentStr = todo?.content ?: ""
    var showDateDialog by remember {
        mutableStateOf(false)
    }
    if (showDateDialog) {
        DateDialog(
            date = todo?.date ?: System.currentTimeMillis(),
            onDismiss = { showDateDialog = false },
            onSelect = {
                dateStr = DateUtil.parseToString(it)
            })
    }
    ModalBottomSheet(sheetState = sheetState, onDismissRequest = { }) {
        ConstraintLayout {
            val (title, date, content, btSave) = createRefs()
            TextField(
                value = titleStr,
                onValueChange = { titleStr = it },
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                    top.linkTo(parent.top, 20.dp)
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.title),
                        style = MaterialTheme.typography.labelMedium
                    )
                })
            Text(
                text = dateStr,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clickable {
                        if (isCreate) {
                            showDateDialog = true
                        }
                    }
                    .constrainAs(date) {
                        start.linkTo(title.start)
                        top.linkTo(title.bottom, 20.dp)

                    }, maxLines = 1, overflow = TextOverflow.Ellipsis

            )
            TextField(value = contentStr, onValueChange = { contentStr = it }, label = {
                Text(
                    text = stringResource(
                        id = R.string.content
                    ),
                    style = MaterialTheme.typography.labelMedium
                )
            }, modifier = Modifier.constrainAs(content) {
                top.linkTo(date.bottom, 20.dp)
                start.linkTo(date.start)
                end.linkTo(title.end)
            }, maxLines = 10)
            OutlinedButton(onClick = {
                viewModel.sendEvent(TodoContract.Event.Add(titleStr, dateStr, contentStr))
            }, modifier = Modifier.constrainAs(btSave) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(content.bottom, 20.dp)
                bottom.linkTo(parent.bottom, 20.dp)
            }) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(date: Long, onDismiss: () -> Unit, onSelect: (Long) -> Unit) {
    val datePickerState = rememberDatePickerState()
    datePickerState.setSelection(date)
    val selectDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
    DatePickerDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        Button(onClick = { onSelect(selectDate) }) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }) {
        DatePicker(state = datePickerState)
    }
}

