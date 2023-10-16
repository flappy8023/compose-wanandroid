package com.flappy.wandroid.ui.page.web

import android.annotation.SuppressLint
import android.view.ViewGroup.LayoutParams
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController

@Composable
fun WebViewPage(navController: NavController,webItem: WebItem) {
    ConstraintLayout {
        var webViewManager: WebViewManager? by remember {
            mutableStateOf(null)
        }
        val (progressbar, webContainer) = createRefs()
        var progress = remember {
            0
        }

        if (progress != 100) {
            LinearProgressIndicator(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .constrainAs(progressbar) {
                    top.linkTo(parent.top)
                }, progress = progress.toFloat()
            )
        }
        AndroidView(modifier = Modifier.constrainAs(webContainer) {
            top.linkTo(progressbar.top)
        }, factory = { context ->
            FrameLayout(context).apply {
                layoutParams =
                    FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                val webView = WebView(context).apply {
                    layoutParams =
                        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                }
                addView(webView)
                webViewManager = WebViewManager(webContainer = this, webItem.link, onProgress = {
                    progress = it
                }).apply {
                    initWebView()
                }
            }
        }
        )
    }

}

data class WebItem(
    val title: String,
    val link: String,
    val collect: Boolean = false,
    val showCollect: Boolean = false
)

class WebViewManager(
    val webContainer: FrameLayout,
    val url: String,
    val onProgress: (Int) -> Unit = { _ -> }
) {
    private lateinit var webView: WebView
    fun initWebView() {
        webView = webContainer.getChildAt(0) as WebView
        initSettings()
        initClient()
        webView.loadUrl(url)
    }

    fun destroy() {
        webContainer.removeAllViews()
        webView.destroy()
    }

    fun refresh() {
        webView.loadUrl(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initSettings() {
        webView.settings.apply {
            allowFileAccess = true
            javaScriptEnabled = true
            //内容适应屏幕
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(true)
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            setGeolocationEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            domStorageEnabled = true
            displayZoomControls = false
        }
    }

    private fun initClient() {
        webView.webViewClient = MyWebClient()
        webView.webChromeClient = MyWebChromeClient()
    }

    inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            onProgress(newProgress)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }
    }

    inner class MyWebClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }
    }
}