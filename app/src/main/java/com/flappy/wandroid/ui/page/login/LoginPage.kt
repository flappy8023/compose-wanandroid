package com.flappy.wandroid.ui.page.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.flappy.wandroid.R
import com.flappy.wandroid.ui.widget.LoadingButton
import com.flappy.wandroid.utils.showToast
import kotlinx.coroutines.flow.collectLatest

/**
 * @Author flappy8023
 * @Description 登录页
 * @Date 2023年09月18日 16:10
 **/
@Composable
fun LoginPage(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var userName by remember {
                mutableStateOf("")
            }
            var pwd by remember {
                mutableStateOf("")
            }
            val state = viewModel.viewState.value
            val context = LocalContext.current
            LaunchedEffect(key1 = null, block = {
                viewModel.effect.collectLatest {
                    when (it) {
                        is LoginContract.Effect.ShowToast -> context.showToast(it.msg)
                        is LoginContract.Effect.NavBack -> navController.navigateUp()
                    }
                }
            })
            Image(
                painter = painterResource(id = R.drawable.icon_logo),
                null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                value = userName,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = { userName = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.username),
                    )
                })
            OutlinedTextField(value = pwd,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { pwd = it },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.password
                        )
                    )
                })
            Spacer(modifier = Modifier.height(20.dp))
            LoadingButton(
                showLoading = state.loggingIn,
                text = stringResource(id = R.string.login),
                modifier = Modifier
                    .clickable {
                        viewModel.sendEvent(LoginContract.Event.GoLogin(userName, pwd))
                    })
        }
    }
}