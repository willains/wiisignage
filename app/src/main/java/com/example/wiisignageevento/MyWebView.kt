package com.example.wiisignageevento

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.log

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SetJavaScriptEnabled")

@Composable
fun WebViewComposable(url: String) {
    AndroidView(
        factory = { context ->
            val fixedUrl = if (!url.startsWith("http")) "http://$url" else url

            WebView(context).apply {
                // Habilita o debugging do WebView
                WebView.setWebContentsDebuggingEnabled(true)

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    safeBrowsingEnabled = true
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    safeBrowsingEnabled = true
                    allowFileAccess = true
                    loadsImagesAutomatically = true
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                }

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean = false
                }

                webChromeClient = WebChromeClient()

                // Carregar a URL na WebView
                loadUrl(fixedUrl)
            }
        },
        update = { webView ->
            val fixedUrl = if (!url.startsWith("http")) "http://$url" else url
            webView.loadUrl(fixedUrl)
        }
    )
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewWebView() {
    WebViewComposable(url = "http://10.20.42.60/eventosDay")
}
