package com.example.leimu.htmltextview;

import android.content.res.AssetManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.webview);
        configWebViewSetting();

        loadHTMLContent();

    }


    /**
     * 加载assert中的HTML
     * <p>
     * 项目中不允许出现绝对路径  因此 不能直接使用 load("file:///android_asset/xxx.html")
     * mWebview.loadUrl("file:///android_asset/License/privacy.html");
     */

    private void loadHTMLContent() {
        String content = getHTMLContent();
        if (TextUtils.isEmpty(content)) {
            Log.i(TAG, "content = null");
            return;
        }
        mWebview.loadData(content, "text/html", "UTF-8");
    }

    private String getHTMLContent() {
        String content = "";
        try {
            AssetManager am = getAssets();
            InputStream in = am.open("License/privacy.html");
            content = IOUtils.toString(in,"UTF-8");
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
        return content;
    }


    /**
     * 配置webview
     * webview 存在漏洞  因为不需要js交互,文件加载,路径加载  所以设为false  避免被攻击.
     */
    private void configWebViewSetting() {
        WebSettings settings = mWebview.getSettings();

        settings.setJavaScriptEnabled(false);

        settings.setAllowFileAccess(false);

        settings.setAllowFileAccessFromFileURLs(false);
        //设置一下背景  防止和原布局中的背景色不协调
        mWebview.setBackgroundColor(0);
    }

}
