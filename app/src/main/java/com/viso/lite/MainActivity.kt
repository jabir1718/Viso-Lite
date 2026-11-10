package com.viso.lite

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {
    private var fileCallback: ValueCallback<Array<Uri>>? = null
    private lateinit var webView: WebView

    private val filePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val callback = fileCallback
        fileCallback = null
        if (callback != null) {
            val uris = if (result.resultCode == RESULT_OK && result.data != null) {
                WebChromeClient.FileChooserParams.parseResult(result.resultCode, result.data)
                    ?: result.data.clipData?.let { clip ->
                        Array(clip.itemCount) { index -> clip.getItemAt(index).uri }
                    }
                    ?: result.data.data?.let { uri -> arrayOf(uri) }
            } else null
            callback.onReceiveValue(uris)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(8, 6, 15)
        window.navigationBarColor = Color.rgb(8, 6, 15)
        window.decorView.systemUiVisibility = 0
        webView = WebView(this).apply {
            setBackgroundColor(Color.rgb(5, 4, 9))
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.javaScriptCanOpenWindowsAutomatically = false
            addJavascriptInterface(AndroidSave(this@MainActivity), "AndroidSave")
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    val url = request?.url?.toString().orEmpty()
                    return !url.startsWith("file:///android_asset/") && !url.startsWith("blob:")
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    view: WebView?,
                    callback: ValueCallback<Array<Uri>>?,
                    params: FileChooserParams?
                ): Boolean {
                    if (callback == null || params == null) return false
                    fileCallback?.onReceiveValue(null)
                    fileCallback = callback
                    val intent = try {
                        // Use WebView's own chooser intent so Android receives its expected
                        // action, MIME filters, capture hints and single/multiple selection mode.
                        params.createIntent()
                    } catch (_: Exception) {
                        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "*/*"
                            putExtra(
                                Intent.EXTRA_ALLOW_MULTIPLE,
                                params.mode == FileChooserParams.MODE_OPEN_MULTIPLE
                            )
                        }
                    }
                    return try {
                        filePicker.launch(intent)
                        true
                    } catch (_: Exception) {
                        // Some OEM document providers reject GET_CONTENT; retry with
                        // the platform document picker before returning failure to WebView.
                        try {
                            val fallback = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "*/*"
                                putExtra(
                                    Intent.EXTRA_ALLOW_MULTIPLE,
                                    params.mode == FileChooserParams.MODE_OPEN_MULTIPLE
                                )
                            }
                            filePicker.launch(fallback)
                            true
                        } catch (_: Exception) {
                            fileCallback?.onReceiveValue(null)
                            fileCallback = null
                            false
                        }
                    }
                }
            }
            loadUrl("file:///android_asset/index.html")
        }
        setContentView(webView)
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }

    override fun onDestroy() {
        if (::webView.isInitialized) {
            fileCallback?.onReceiveValue(null)
            fileCallback = null
            webView.removeJavascriptInterface("AndroidSave")
            webView.destroy()
        }
        super.onDestroy()
    }
}
