package doyou.know.javascriptdata

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        main_webview.settings.javaScriptEnabled = true
        main_webview.addJavascriptInterface(AndroidBridge(), "sendToNative")
        main_webview.loadUrl("file:///android_asset/sample.html")
        main_webview.webViewClient = WebViewClient()
        main_webview.webChromeClient = WebChromeClient()
    }

    var handler = Handler()

    inner class AndroidBridge {
        @JavascriptInterface
        fun sendData(msg: String) {
            handler.post(Runnable {
                var message = msg
                if (message.isEmpty()) {
                    message = "입력 없음"
                }
                Toast.makeText(this@MainActivity, "msg ::: $message", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
